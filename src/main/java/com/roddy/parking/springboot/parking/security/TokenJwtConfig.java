package com.roddy.parking.springboot.parking.security;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;


public class TokenJwtConfig {

    // Shared HS256 key: must be stable across restarts and identical on every replica,
    // otherwise a token signed by one pod fails validation on another (random 401s).
    // Provided via the JWT_SECRET env var (Kubernetes Secret); the fallback is for local dev/tests only.
    private static final String SECRET = System.getenv("JWT_SECRET") != null
            ? System.getenv("JWT_SECRET")
            : "ZGV2LWxvY2FsLXNlY3JldC1jaGFuZ2UtbWUtMzItYnl0ZXMtbWluaW11bQ==";

    public static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
    public static final String PREFIX_TOKEN = "Bearer ";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String CONTENT_TYPE = "application/json";
}
