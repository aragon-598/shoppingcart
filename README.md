# Shopping Cart - Sistema de Carrito de Compras

Sistema de carrito de compras desarrollado con JDK 21, MySQL, JWT, etc.

---

##  Arquitectura

El proyecto implementa la siguiente estructura por módulo:

```
{module}/
├── domain/              # Lógica de negocio pura
│   ├── model/          # Entidades y Value Objects
│   ├── port/           # Interfaces (contratos)
│   ├── factory/        # Factory patterns
│   ├── strategy/       # Strategy patterns
│   └── exception/      # Excepciones de dominio
├── application/         # Casos de uso
│   ├── port/
│   │   ├── in/        # Use cases
│   │   └── out/       # Repositorios
│   └── service/       # Implementaciones
├── infrastructure/      # Adaptadores técnicos
│   ├── persistence/   # JPA, MySQL
│   ├── external/      # APIs externas
│   └── config/        # Configuración Spring
└── entrypoint/         # Adaptadores de entrada
    └── rest/          # Controllers REST
```

### Módulos Implementados

1. **Security**: Autenticación y autorización con JWT
2. **Clients**: Gestión de clientes
3. **Products**: Catálogo de productos (OpenFeign + FakeStore API)
4. **Orders**: Gestión de órdenes
5. **Payments**: Procesamiento de pagos

---

##  Instalación y Ejecución

### Opción 1: Ejecutar con Docker (Recomendado)

Esta es la forma más sencilla y rápida de ejecutar el proyecto completo con base de datos.

#### Windows

1. **Clonar el repositorio**
```powershell
git clone https://github.com/aragon-598/shoppingcart.git
cd shopping-cart
```

2. **Ejecutar el script de deploy**
```powershell
.\deploy.bat
```

El script automáticamente:
- Limpia el proyecto anterior
- Compila el proyecto con Maven
- Crea la imagen Docker
- Levanta MySQL y la aplicación con docker-compose

3. **Esperar a que los servicios estén listos**

La aplicación estará disponible en:
- **API**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **MySQL**: localhost:3307

#### Linux/Mac

```bash
# Compilar el proyecto
mvn clean package -DskipTests

# Crear imagen Docker
docker build -t shopping-cart:latest .

# Iniciar servicios
cd resources/db
docker-compose up -d
```

### Opción 2: Ejecutar localmente (sin Docker)

1. **Instalar MySQL 8.0**
   - Crear base de datos `shopping_cart`
   - Usuario: `admin`
   - Password: `prueba-cuscatlan-2025`
   - Puerto: 3307

2. **Actualizar application.yml**
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3307/shopping_cart
    username: admin
    password: prueba-cuscatlan-2025
```

3. **Compilar y ejecutar**
```powershell
mvn clean package -DskipTests
java -jar target/shopping-cart-0.0.1-SNAPSHOT.jar
```

---

##  Estructura del Proyecto

```
shopping-cart/
├── src/
│   ├── main/
│   │   ├── java/com/store/shoppingcart/
│   │   │   ├── security/      # Autenticación JWT
│   │   │   ├── clients/       # Gestión de clientes
│   │   │   ├── products/      # Catálogo (FakeStore API)
│   │   │   ├── orders/        # Gestión de órdenes
│   │   │   ├── payments/      # Procesamiento de pagos
│   │   │   └── common/        # DTOs y utilidades
│   │   └── resources/
│   │       ├── application.yml
│   │       └── db/migration/  # Scripts Flyway
│   └── test/
├── docs/                      # Documentación detallada
│   ├── architecture.md
│   ├── security.md
│   ├── clients.md
│   ├── products.md
│   ├── orders.md
│   ├── payment.md
│   └── deliverables.md
├── resources/db/
│   └── docker-compose.yml    # Configuración Docker
├── Dockerfile                # Imagen de la aplicación
├── deploy.bat               # Script de deploy para Windows
├── pom.xml                  # Dependencias Maven
└── README.md                # Este archivo
```

---

##  Base de Datos

### Script de Inicialización

La base de datos se inicializa automáticamente al levantar Docker usando el script consolidado:
- **Ubicación**: `resources/db/init.sql`
- **Incluye**: Todas las tablas, índices y constraints necesarias

### Diagrama de Base de Datos

```
users
  ↓
clients
  ↓
orders ←→ order_items
  ↓
payments
```

### Acceso a MySQL

**Conectarse al contenedor:**
```powershell
docker exec -it mysql_dev mysql -uadmin -pprueba-cuscatlan-2025 shopping_cart
```

**Reinicializar base de datos:**
```powershell
# Detener y eliminar volúmenes
cd resources\db
docker-compose down -v

# Reiniciar (ejecuta init.sql automáticamente)
docker-compose up -d
```

---

##  Endpoints API

### Authentication

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/auth/register` | Registrar nuevo usuario |
| POST | `/auth/login` | Autenticar y obtener JWT |

### Clients

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/clients` | Crear cliente |
| GET | `/api/clients/{id}` | Obtener cliente |
| PUT | `/api/clients/{id}` | Actualizar cliente |
| GET | `/api/clients` | Listar clientes |

### Products

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/products/{id}` | Obtener producto |
| GET | `/api/products` | Listar productos |
| GET | `/api/products/category/{category}` | Filtrar por categoría |

### Orders

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/orders` | Crear orden |
| GET | `/api/orders/{id}` | Obtener orden |
| PATCH | `/api/orders/{id}/confirm` | Confirmar orden |
| PATCH | `/api/orders/{id}/cancel` | Cancelar orden |

### Order Items

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/orders/{orderId}/items` | Agregar item |
| GET | `/api/orders/{orderId}/items` | Listar items |
| PUT | `/api/orders/{orderId}/items/{itemId}` | Actualizar cantidad |
| DELETE | `/api/orders/{orderId}/items/{itemId}` | Eliminar item |

### Payments

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/payments` | Procesar pago |
| GET | `/api/payments/{id}` | Obtener pago |
| POST | `/api/payments/{id}/refund` | Reembolsar pago |

---

##  Swagger UI

La documentación interactiva de la API está disponible en:

**http://localhost:8080/swagger-ui.html**

### Cómo usar Swagger

1. Acceder a Swagger UI
2. Registrar un nuevo usuario en `/auth/register`
3. Autenticarse en `/auth/login` y copiar el token JWT
4. Hacer clic en el botón **Authorize** (🔓)
5. Pegar el token en el formato: `Bearer <token>`
6. Probar los endpoints protegidos

### Ejemplo de Autenticación

**Registrar un nuevo usuario:**
```json
POST /auth/register
{
  "email": "user@example.com",
  "password": "Password123!",
  "firstName": "Juan",
  "lastName": "Pérez"
}
```

**Respuesta:**
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "expiresIn": 86400000
}
```

---

##  Colección Postman

Se incluye una colección completa de Postman con todos los endpoints configurados.

### Variables de Entorno

Crear un environment con:
- `DEV`: http://localhost:8080

---

##  Docker

### Arquitectura de Contenedores

```
┌─────────────────────────────┐
│   shopping-cart-app:8080   │
│   (Spring Boot Application) │
└──────────────┬──────────────┘
               │
               ↓
┌─────────────────────────────┐
│      mysql_dev:3307        │
│   (MySQL 8.0 Database)      │
└─────────────────────────────┘
```

### Comandos Útiles

**Ver logs de la aplicación:**
```powershell
docker logs -f shopping-cart-app
```

**Ver logs de MySQL:**
```powershell
docker logs -f mysql_dev
```

**Detener servicios:**
```powershell
cd resources\db
docker-compose down
```

**Reiniciar servicios:**
```powershell
cd resources\db
docker-compose restart
```

**Ver contenedores en ejecución:**
```powershell
docker ps
```

**Limpiar todo (contenedores, imágenes, volúmenes):**
```powershell
cd resources\db
docker-compose down -v
docker rmi shopping-cart:latest
```
---

## 🔒 Seguridad

### JWT Configuration

- **Secret Key**: Almacenada en `application.yml`
- **Expiración**: 24 horas (86400000 ms)
- **Algoritmo**: HS512

### Endpoints Públicos

- `/auth/register`
- `/auth/login`
- `/swagger-ui.html`
- `/v3/api-docs`

### Endpoints Protegidos

Todos los demás endpoints requieren autenticación con JWT en el header:
```
Authorization: Bearer <token>
```

---

## 🌐 APIs Externas

### FakeStore API

**URL**: https://fakestoreapi.com

**Uso**: El módulo de Products consume esta API para obtener el catálogo de productos.

**Resiliencia**:
- Circuit Breaker con Resilience4j
- 3 reintentos con backoff exponencial
- Timeout de 5 segundos

---

## 👨‍💻 Autor

**Alejandro Aragón**

- Email: aragondru155@gmail.com
- GitHub: [@aragon-598](https://github.com/aragon-598)
- Repositorio: [shoppingcart](https://github.com/aragon-598/shoppingcart)

---