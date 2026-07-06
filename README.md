# Parking API

API REST para la gestión de un parking: control de plazas, tarifas, vehículos
(coches y motos) y registro de entradas/salidas. La autenticación se realiza
mediante **JWT** y los endpoints de administración están protegidos por roles
(`ROLE_ADMIN`).

Proyecto desarrollado como práctica de despliegue: además del código de la
aplicación, el repositorio incluye el `Dockerfile` y los manifiestos de
**Kubernetes** (`k8s/`) necesarios para levantarlo en un clúster local con
Minikube.

## Stack

| Capa | Tecnología |
|------|-----------|
| Lenguaje / runtime | Java 21 |
| Framework | Spring Boot 4.1.0 (Web MVC, Data JPA, Validation) |
| Seguridad | Spring Security + JWT (JJWT 0.13.0) |
| Base de datos | PostgreSQL 16 |
| Contenedores | Docker (build multi-stage) |
| Orquestación | Kubernetes (Minikube) |

## Estructura del repositorio

```
.
├── Dockerfile            # Build multi-stage (JDK 21 build -> JRE 21 runtime)
├── k8s/
│   ├── postgres.yaml     # Secret + PVC + Deployment + Service de PostgreSQL
│   └── app.yaml          # ConfigMap + Deployment + Service (NodePort) de la app
├── src/                  # Código fuente Spring Boot
└── pom.xml
```

## Endpoints principales

| Método | Ruta | Acceso |
|--------|------|--------|
| `POST` | `/login` | Público — devuelve el JWT |
| `POST` | `/api/parking/entry/{car\|moto}` | Público |
| `POST` | `/api/parking/exit/{matricula}` | Público |
| `*` | `/api/plazas/**` | `ROLE_ADMIN` |
| `*` | `/api/tarifas/**` | `ROLE_ADMIN` |
| `*` | `/api/vehicles/**` | Autenticado |

Para las rutas protegidas, enviar el token en la cabecera:
`Authorization: Bearer <token>`.

## Despliegue en Kubernetes (Minikube)

Requisitos: `minikube`, `kubectl` y `docker` instalados, con Minikube arrancado
(`minikube start`).

### 1. Construir la imagen dentro de Minikube

Se apunta el Docker local al daemon de Minikube para que la imagen quede
disponible en el clúster sin necesidad de un registro externo:

```bash
eval $(minikube docker-env)
docker build -t parking:1.0 .
```

### 2. Desplegar PostgreSQL y la aplicación

```bash
kubectl apply -f k8s/postgres.yaml
kubectl apply -f k8s/app.yaml
```

Comprobar que ambos pods quedan `Running 1/1`:

```bash
kubectl get pods
```

> La app espera a que PostgreSQL acepte conexiones mediante un `initContainer`
> (`wait-for-db`). La URL de la BD se inyecta desde un `ConfigMap` y las
> credenciales desde un `Secret`. Con `spring.jpa.hibernate.ddl-auto=update`,
> Hibernate crea el esquema automáticamente en la primera arrancada.

### 3. Sembrar el usuario administrador

El PostgreSQL del clúster arranca **vacío**, por lo que hay que crear el rol y
el administrador de prueba. La contraseña va cifrada con **BCrypt** (no en
texto plano):

```bash
# Sustituye <POD_POSTGRES> por el nombre real (kubectl get pods)
kubectl exec -i <POD_POSTGRES> -- psql -U root -d db_parking <<'SQL'
INSERT INTO role (name) VALUES ('ROLE_ADMIN');
INSERT INTO administrator (username, password, enabled)
VALUES ('admin', '$2a$10$REEMPLAZAR_POR_TU_HASH_BCRYPT', true);
INSERT INTO administrator_roles (administrator_id, roles_id) VALUES (1, 1);
SQL
```

> El hash BCrypt debe generarse a partir de la contraseña deseada (p. ej. con
> `BCryptPasswordEncoder`). No se versiona la contraseña en claro.

### 4. Obtener la URL de acceso

```bash
minikube service parking --url
```

Esa URL es la base de la API. Ejemplo de login:

```bash
curl -X POST <URL>/login \
  -H 'Content-Type: application/json' \
  -d '{"username":"admin","password":"<tu_contraseña>"}'
```

### Re-despliegue tras cambios

```bash
eval $(minikube docker-env)
docker build -t parking:1.0 .
kubectl rollout restart deploy/parking
```

## Configuración local (sin Kubernetes)

`application.properties` usa variables de entorno con valores por defecto para
desarrollo local contra un PostgreSQL en `localhost:5432/db_parking`. Pueden
sobrescribirse con `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME` y
`SPRING_DATASOURCE_PASSWORD`.

## Nota de seguridad (deuda técnica consciente)

Para este proyecto de aprendizaje/demo, las credenciales de la base de datos
están en el `Secret` de Kubernetes y en los *defaults* de
`application.properties` en texto plano. **En un entorno de producción esto no
sería aceptable**: las credenciales deberían gestionarse con un gestor de
secretos (HashiCorp Vault, Sealed Secrets, AWS/GCP Secrets Manager, etc.) y
**nunca versionarse** en el repositorio.

Del mismo modo, la clave de firma del JWT se genera en código al arrancar. Con
una sola réplica (`replicas: 1`) es válido, pero al escalar a varias réplicas
cada pod tendría su propia clave y los tokens dejarían de ser válidos entre
pods; en ese caso habría que externalizar la clave a un `Secret` compartido.
