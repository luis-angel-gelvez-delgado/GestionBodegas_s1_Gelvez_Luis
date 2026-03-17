# LogiTrack - Sistema de Gestión de Bodegas

Sistema backend en Spring Boot para gestión y auditoría de bodegas, productos y movimientos de inventario de LogiTrack S.A.

---

## Tecnologías

- Java 21
- Spring Boot 3.4.1
- Spring Security + JWT
- Spring Data JPA + Hibernate
- MySQL 8+
- Lombok
- Swagger UI (Springdoc OpenAPI)
- Frontend: HTML / CSS / JavaScript

---

## Requisitos previos

- Java 21
- MySQL 8 o superior
- Git

---

## Instalación

### 1. Clonar el repositorio
```bash
git clone https://github.com/luis-angel-gelvez-delgado/GestionBodegas_s1_Gelvez_Luis.git
cd GestionBodegas_s1_Gelvez_Luis
```

### 2. Configurar credenciales en application.properties
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/logitrackgelvezluis?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=TU_PASSWORD
```

### 3. Ejecutar el proyecto

Al arrancar, Spring crea las tablas automaticamente e inserta los datos iniciales desde data.sql.

---

## Credenciales iniciales

| Usuario | Password    | Rol      |
|---------|-------------|----------|
| admin   | admin1234   | ADMIN    |
| carlos  | carlos1234  | EMPLEADO |
| maria   | maria1234   | EMPLEADO |

---

## Documentacion Swagger
```
http://localhost:8080/swagger-ui/index.html
```

Para probar endpoints protegidos:
1. Hacer POST /auth/login con usuario y password
2. Copiar el token de la respuesta
3. Hacer clic en Authorize en Swagger
4. Pegar el token sin la palabra Bearer

---

## Endpoints principales

### Autenticacion
| Método | Endpoint        | Acceso  |
|--------|----------------|---------|
| POST   | /auth/register | Público |
| POST   | /auth/login    | Público |

### Bodegas
| Método | Endpoint                      | Acceso      |
|--------|------------------------------|-------------|
| GET    | /api/bodegas                 | Autenticado |
| POST   | /api/bodegas                 | Autenticado |
| PUT    | /api/bodegas/{id}            | Autenticado |
| DELETE | /api/bodegas/{id}            | Solo ADMIN  |
| GET    | /api/bodegas/ubicacion/{u}   | Autenticado |
| GET    | /api/bodegas/encargado/{e}   | Autenticado |

### Productos
| Método | Endpoint                        | Acceso      |
|--------|---------------------------------|-------------|
| GET    | /api/productos                  | Autenticado |
| POST   | /api/productos                  | Autenticado |
| PUT    | /api/productos/{id}             | Autenticado |
| DELETE | /api/productos/{id}             | Solo ADMIN  |
| GET    | /api/productos/stock-bajo       | Autenticado |
| GET    | /api/productos/categoria/{cat}  | Autenticado |

### Movimientos
| Método | Endpoint                       | Acceso      |
|--------|-------------------------------|-------------|
| GET    | /api/movimientos               | Autenticado |
| POST   | /api/movimientos               | Autenticado |
| GET    | /api/movimientos/{id}          | Autenticado |
| GET    | /api/movimientos/fechas        | Autenticado |
| GET    | /api/movimientos/tipo/{tipo}   | Autenticado |
| GET    | /api/movimientos/usuario/{id}  | Autenticado |

### Auditorias
| Método | Endpoint                            | Acceso      |
|--------|-------------------------------------|-------------|
| GET    | /api/auditorias                     | Autenticado |
| GET    | /api/auditorias/usuario/{usuario}   | Autenticado |
| GET    | /api/auditorias/operacion/{tipo}    | Autenticado |
| GET    | /api/auditorias/entidad/{entidad}   | Autenticado |

### Reportes
| Método | Endpoint               | Acceso      |
|--------|----------------------|-------------|
| GET    | /api/reportes/resumen | Autenticado |

---

## Seguridad

- Autenticacion con JWT (expiracion 24 horas)
- Roles: ADMIN y EMPLEADO
- Solo ADMIN puede eliminar registros y gestionar usuarios
- Swagger accesible sin token
- CSRF desactivado (API REST stateless)
- Passwords encriptadas con BCrypt

---

## Auditoria automatica

El sistema registra automaticamente en la tabla auditoria cada INSERT, UPDATE y DELETE realizado sobre las entidades principales usando JPA EntityListeners. No requiere ninguna llamada manual.

---

## Stock por Bodega

Cada producto tiene stock independiente por bodega en la tabla producto_bodega:
- ENTRADA: suma stock en bodega destino
- SALIDA: resta stock en bodega origen
- TRANSFERENCIA: resta en bodega origen y suma en bodega destino

---

## Frontend

Carpeta: `frontend-logitrack/`

Para usarlo localmente:
1. Abrir la carpeta en VSCode
2. Instalar extension Live Server de Ritwick Dey
3. Click derecho en index.html → Open with Live Server
4. Abrir: http://127.0.0.1:5500/index.html
5. Login con admin / admin1234

Funcionalidades:
- Login con JWT
- CRUD de Bodegas
- CRUD de Productos
- Registro y consulta de Movimientos
- Reporte de stock por bodega y productos mas movidos
- Consulta de Auditorias

---

## Estructura del proyecto
```
GestionBodegas_s1_Gelvez_Luis/
├── src/
│   ├── main/
│   │   ├── java/com/s1/logitrack/
│   │   │   ├── auth/
│   │   │   ├── config/
│   │   │   ├── controller/
│   │   │   ├── dto/
│   │   │   ├── enums/
│   │   │   ├── exception/
│   │   │   ├── mapper/
│   │   │   ├── model/
│   │   │   ├── repository/
│   │   │   └── service/
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── schema.sql
│   │       └── data.sql
├── frontend-logitrack/
│   ├── index.html
│   ├── dashboard.html
│   ├── css/
│   └── js/
├── pom.xml
└── README.md
```

---

## Base de datos

Tablas creadas automaticamente por Hibernate:
- usuario
- bodega
- producto
- movimiento_inventario
- detalle_movimiento
- producto_bodega
- auditoria

Scripts en src/main/resources/:
- schema.sql → estructura de las tablas
- data.sql → datos iniciales de prueba

---

## Repositorio

https://github.com/luis-angel-gelvez-delgado/GestionBodegas_s1_Gelvez_Luis.git

---

## Autor

Luis Gelvez - S1 Cajasan - Ruta JAVA
Docente: David Dominguez
