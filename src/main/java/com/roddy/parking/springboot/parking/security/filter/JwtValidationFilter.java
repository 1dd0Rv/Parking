package com.roddy.parking.springboot.parking.security.filter;

import com.roddy.parking.springboot.parking.security.SimpleGrantedAuthorityJsonCreator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import tools.jackson.databind.ObjectMapper;        // Jackson 3
import tools.jackson.databind.json.JsonMapper;     // Jackson 3 — builder

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.roddy.parking.springboot.parking.security.TokenJwtConfig.*;

public class JwtValidationFilter extends BasicAuthenticationFilter {

    // Mapper with the mixin registered, built ONCE (thread-safe and immutable in Jackson 3)
    private final ObjectMapper mapper = JsonMapper.builder()
            .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityJsonCreator.class)
            .build();

    public JwtValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        // 1. Read the header
        String header = request.getHeader(HEADER_AUTHORIZATION);

        // 2. If there is no token -> do NOT break the chain (it may be a public route)
        if (header == null || !header.startsWith(PREFIX_TOKEN)) {
            chain.doFilter(request, response);
            return;
        }

        // 3. Remove the "Bearer " prefix
        String token = header.replace(PREFIX_TOKEN, "");

        try {
            // 4a. Verify signature with SECRET_KEY and extract the claims
            Claims claims = Jwts.parser()
                    .verifyWith(SECRET_KEY)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            String username = claims.getSubject();
            String authoritiesClaim = claims.get("authorities").toString();

            // 4b. Rebuild the authorities from the JSON string (the mixin acts here)
            Collection<? extends SimpleGrantedAuthority> authorities = Arrays.asList(
                    mapper.readValue(authoritiesClaim, SimpleGrantedAuthority[].class));

            // 4c. Put the authentication into the context (password = null, already validated)
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            // 4d. Continue the chain, now authenticated
            chain.doFilter(request, response);

        } catch (JwtException e) {
            // 5. Invalid/expired token/bad signature -> 401 and do NOT continue the chain
            Map<String, String> body = new HashMap<>();
            body.put("error", e.getMessage());
            body.put("message", "El token JWT no es válido");

            response.setContentType(CONTENT_TYPE);
            response.setStatus(401);
            response.getWriter().write(mapper.writeValueAsString(body));
        }
    }
}