# Security Module - README

## Implementación Completada ✅

El módulo de seguridad ha sido implementado siguiendo **Arquitectura Hexagonal** con principios **SOLID**.

## Estructura Creada

```
security/
├── domain/
│   ├── model/
│   │   ├── User.java
│   │   ├── UserId.java (Value Object)
│   │   ├── Email.java (Value Object)
│   │   ├── Password.java (Value Object)
│   │   ├── AuthToken.java (Value Object)
│   │   └── Role.java (Enum)
│   ├── port/
│   │   ├── in/
│   │   │   ├── RegisterUserUseCase.java
│   │   │   ├── AuthenticateUserUseCase.java
│   │   │   └── ValidateTokenUseCase.java
│   │   └── out/
│   │       ├── UserRepository.java
│   │       ├── PasswordEncoder.java
│   │       └── TokenGenerator.java
│   └── exception/
│       ├── InvalidEmailException.java
│       ├── WeakPasswordException.java
│       ├── UserAlreadyExistsException.java
│       ├── InvalidCredentialsException.java
│       ├── InvalidTokenException.java
│       └── UserInactiveException.java
│
├── application/
│   ├── usecase/
│   │   ├── RegisterUserUseCaseImpl.java
│   │   ├── AuthenticateUserUseCaseImpl.java
│   │   └── ValidateTokenUseCaseImpl.java
│   └── dto/
│       ├── RegisterUserCommand.java
│       └── AuthenticationCommand.java
│
├── infrastructure/
│   ├── persistence/
│   │   ├── entity/
│   │   │   └── UserJpaEntity.java
│   │   ├── repository/
│   │   │   └── UserJpaRepository.java
│   │   ├── mapper/
│   │   │   └── UserMapper.java
│   │   └── adapter/
│   │       └── UserRepositoryAdapter.java
│   ├── security/
│   │   ├── adapter/
│   │   │   ├── BcryptPasswordEncoderAdapter.java
│   │   │   └── JwtTokenGeneratorAdapter.java
│   │   └── filter/
│   │       └── JwtAuthenticationFilter.java
│   └── config/
│       └── SecurityConfig.java
│
└── entrypoint/
    └── rest/
        ├── controller/
        │   └── AuthController.java
        ├── dto/
        │   ├── RegisterRequest.java
        │   ├── LoginRequest.java
        │   ├── AuthResponse.java
        │   └── RegisterResponse.java
        └── mapper/
            └── AuthDtoMapper.java
```

## Características Implementadas

### ✅ Autenticación JWT
- Generación de tokens con expiración de 24 horas
- Validación de tokens en cada request
- Claims: userId, email, role
- Firmado con HS512

### ✅ Gestión de Usuarios
- Registro con validación de email único
- Contraseñas cifradas con BCrypt
- Roles: CUSTOMER, ADMIN, MANAGER
- Estados: activo/inactivo

### ✅ Value Objects
- Email con validación de formato
- Password con validación de longitud mínima
- UserId basado en UUID
- AuthToken con fecha de expiración

### ✅ Seguridad
- Endpoints públicos: `/auth/register`, `/auth/login`
- Endpoints protegidos: requieren JWT válido
- Sesiones stateless
- CSRF deshabilitado (API REST)

## Endpoints Disponibles

| Método | Endpoint         | Descripción            | Auth |
|--------|------------------|------------------------|------|
| POST   | /auth/register   | Registrar nuevo usuario| No   |
| POST   | /auth/login      | Autenticar usuario     | No   |

## Ejemplo de Uso

### 1. Registrar Usuario

**Request:**
```http
POST /auth/register
Content-Type: application/json

{
  "email": "john@example.com",
  "password": "securePass123",
  "firstName": "John",
  "lastName": "Doe"
}
```

**Response:**
```json
{
  "userId": "550e8400-e29b-41d4-a716-446655440000"
}
```

### 2. Login

**Request:**
```http
POST /auth/login
Content-Type: application/json

{
  "email": "john@example.com",
  "password": "securePass123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "expiresAt": "2025-12-07T19:36:00"
}
```

### 3. Acceder a Recursos Protegidos

```http
GET /api/protected-resource
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9...
```

## Configuración Requerida

### application.properties

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/shopping_cart
spring.datasource.username=root
spring.datasource.password=secret

jwt.secret=your-256-bit-secret-key-change-this-in-production
jwt.expiration=86400000

spring.jpa.hibernate.ddl-auto=update
```

### Base de Datos

Ejecutar el script SQL ubicado en:
```
src/main/resources/db/migration/V1__create_users_table.sql
```

## Compilación

```bash
mvn clean compile
```

**Estado:** ✅ BUILD SUCCESS

## Próximos Pasos

1. Ejecutar la aplicación
2. Probar endpoints con Postman
3. Verificar generación de tokens JWT
4. Implementar módulos restantes (Clients, Products, Orders, Payments)

---

**Versión:** 1.0  
**Fecha:** Diciembre 2025  
**Patrón:** Hexagonal + SOLID  
**Sin comentarios:** ✅
