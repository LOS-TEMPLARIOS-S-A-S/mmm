# 🛡️ SIRMOT — Sistema de Registro de Motos Robadas

> Proyecto universitario — Universidad de Remington  
> Aplicación web desarrollada con **Spring Boot** y **Thymeleaf** para el registro, consulta y gestión del estado de motos robadas, usando un **Árbol Binario de Búsqueda (ABB)** como estructura de datos principal.

---

## Tabla de Contenidos

1. [Descripción General](#descripción-general)
2. [Tecnologías Utilizadas](#tecnologías-utilizadas)
3. [Requisitos Previos](#requisitos-previos)
4. [Instalación y Ejecución](#instalación-y-ejecución)
5. [Estructura del Proyecto](#estructura-del-proyecto)
6. [Arquitectura](#arquitectura)
7. [Modelos](#modelos)
8. [Servicios](#servicios)
9. [Controladores](#controladores)
10. [Vistas](#vistas)
11. [Endpoints / Rutas](#endpoints--rutas)
12. [Funcionalidades](#funcionalidades)
13. [Flujo de la Aplicación](#flujo-de-la-aplicación)
14. [Configuración](#configuración)
15. [Guía de Commits](#guía-de-commits)

---

## 🌐 Descripción General

**SIRMOT** es un sistema web de tipo MVC orientado al sector de seguridad, que permite a agentes registrar y consultar el estado de motos en una base de datos gestionada mediante un Árbol Binario de Búsqueda. La búsqueda por placa tiene una complejidad de **O(log n)**, lo que permite respuestas rápidas incluso con grandes volúmenes de registros.

Las principales acciones del sistema son:

- Registrar motos con información completa del vehículo y propietario.
- Consultar si una placa figura en el sistema y en qué estado se encuentra.
- Marcar motos como **robadas** o **recuperadas** directamente desde la interfaz.
- Eliminar registros del árbol cuando ya no son necesarios.
- Visualizar estadísticas globales del sistema en tiempo real.
- Ver una ficha detallada de cada moto registrada.

> ⚠️ **Nota:** Los datos se almacenan en memoria (estructura ABB en tiempo de ejecución). Al reiniciar la aplicación, todos los registros se pierden. No se utiliza base de datos persistente.

---

## 🔧 Tecnologías Utilizadas

| Tecnología | Versión | Descripción |
|---|---|---|
| Java | 23 | Lenguaje de programación principal |
| Spring Boot | 3.4.2 | Framework backend MVC |
| Spring Web | — | Módulo para controladores y manejo de rutas HTTP |
| Spring Actuator | — | Monitoreo y métricas de la aplicación |
| Thymeleaf | — | Motor de plantillas HTML del lado del servidor |
| Spring Boot DevTools | — | Recarga automática en desarrollo |
| Bootstrap | 5.3 | Framework CSS para diseño responsivo |
| Bootstrap Icons | 1.11 | Iconografía del sistema |
| Maven | — | Gestor de dependencias y construcción del proyecto |

---

## ✅ Requisitos Previos

- Java JDK **23** o superior instalado
- Apache Maven **3.8+** (o usar el wrapper `mvnw` incluido en el proyecto)
- Un navegador web moderno (Chrome, Firefox, Edge)

---

## 🚀 Instalación y Ejecución

### 1. Clonar el repositorio

```bash
git clone <url-del-repositorio>
cd sirmot
```

### 2. Compilar el proyecto

```bash
# Con Maven instalado globalmente
mvn clean install

# Con el wrapper incluido (Linux / Mac)
./mvnw clean install

# Con el wrapper incluido (Windows)
.\mvnw.cmd clean install
```

### 3. Ejecutar la aplicación

```bash
# Con Maven
mvn spring-boot:run

# Con el wrapper (Linux / Mac)
./mvnw spring-boot:run

# Con el wrapper (Windows)
.\mvnw.cmd spring-boot:run
```

### 4. Acceder al sistema

Abrir el navegador en: **http://localhost:8080/motos**

> El puerto por defecto es `8080`, configurado en `application.properties`.

---

## 📁 Estructura del Proyecto

```
sirmot/
├── pom.xml                                  # Configuración Maven y dependencias
├── mvnw / mvnw.cmd                          # Maven Wrapper
├── README.md                                # Este archivo
│
└── src/
    ├── main/
    │   ├── java/co/edu/uniremington/kiarapuello/Moto/
    │   │   ├── MotoApplication.java                    # Clase principal (entry point)
    │   │   │
    │   │   ├── Model/                                  # Capa de Modelos
    │   │   │   ├── Moto.java                           # Entidad principal: moto y sus datos
    │   │   │   ├── NodoABB.java                        # Nodo del Árbol Binario de Búsqueda
    │   │   │   └── ArbolABB.java                       # Estructura ABB con todas las operaciones
    │   │   │
    │   │   ├── Service/                                # Capa de Servicios (Lógica de Negocio)
    │   │   │   └── MotoService.java                    # Operaciones sobre las motos (usa ArbolABB)
    │   │   │
    │   │   └── controller/                             # Capa de Controladores
    │   │       └── MotoController.java                 # Todos los endpoints HTTP del sistema
    │   │
    │   └── resources/
    │       ├── application.properties                  # Configuración de la aplicación
    │       └── templates/                              # Plantillas Thymeleaf
    │           ├── index.html                          # Página principal: registro y búsqueda
    │           ├── lista-motos.html                    # Tabla de registros con filtros y acciones
    │           ├── detalle-moto.html                   # Ficha individual de una moto
    │           └── estadisticas.html                   # Panel de métricas del sistema
    │
    └── test/                                           # Pruebas unitarias
```

---

## 🏗️ Arquitectura

La aplicación sigue el patrón **MVC (Modelo - Vista - Controlador)** de Spring Boot, con una capa de estructura de datos personalizada (ABB) que reemplaza el uso de colecciones estándar:

```
┌──────────────────────────────────────────────────────────┐
│                  NAVEGADOR (Cliente)                     │
│           Bootstrap 5 + Thymeleaf + Bootstrap Icons      │
└───────────────────────┬──────────────────────────────────┘
                        │ Peticiones HTTP
                        ▼
┌──────────────────────────────────────────────────────────┐
│                    CONTROLLER                            │
│               MotoController.java                        │
│  (registrar · buscar · listar · eliminar · marcar        │
│   robada · marcar recuperada · estadísticas · detalle)   │
└───────────────────────┬──────────────────────────────────┘
                        │ Inyección de dependencias
                        ▼
┌──────────────────────────────────────────────────────────┐
│                     SERVICE                              │
│                  MotoService.java                        │
│  (lógica de negocio: estados, filtros, estadísticas)     │
└───────────────────────┬──────────────────────────────────┘
                        │
                        ▼
┌──────────────────────────────────────────────────────────┐
│           ÁRBOL BINARIO DE BÚSQUEDA (ABB)                │
│                  ArbolABB.java                           │
│   insertar · buscar O(log n) · eliminar · listarInorden  │
│                        │                                 │
│              NodoABB.java  ←→  Moto.java                 │
└──────────────────────────────────────────────────────────┘
```

---

## 📦 Modelos

### `Moto.java`
Entidad que representa un vehículo registrado en el sistema.

| Atributo | Tipo | Descripción |
|---|---|---|
| `placa` | `String` | Identificador único del vehículo (clave del ABB) |
| `propietario` | `String` | Nombre completo del propietario |
| `cedulaPropietario` | `String` | Número de cédula del propietario |
| `marca` | `String` | Marca del vehículo (Honda, Yamaha, etc.) |
| `modelo` | `String` | Referencia o línea del vehículo |
| `color` | `String` | Color principal de la moto |
| `anio` | `int` | Año de fabricación |
| `estado` | `String` | Estado actual: `normal`, `robada` o `recuperada` |
| `fechaReporte` | `LocalDate` | Fecha en que se registró en el sistema |
| `descripcion` | `String` | Observaciones adicionales o señas particulares |

---

### `NodoABB.java`
Nodo interno del Árbol Binario de Búsqueda. Almacena una referencia a un objeto `Moto` y punteros a sus nodos hijo.

| Atributo | Tipo | Descripción |
|---|---|---|
| `moto` | `Moto` | Objeto moto almacenado en el nodo |
| `izquierda` | `NodoABB` | Referencia al subárbol izquierdo (placas menores) |
| `derecha` | `NodoABB` | Referencia al subárbol derecho (placas mayores) |

---

### `ArbolABB.java`
Implementación completa del Árbol Binario de Búsqueda. Gestiona el almacenamiento estructural de todos los registros, ordenados alfabéticamente por placa.

| Método | Retorno | Descripción |
|---|---|---|
| `insertar(Moto moto)` | `void` | Inserta o actualiza una moto en el árbol |
| `buscar(String placa)` | `Moto` | Búsqueda eficiente O(log n) por placa |
| `eliminar(String placa)` | `void` | Elimina el nodo correctamente (casos 0, 1 y 2 hijos) |
| `listarInorden()` | `List<Moto>` | Retorna todas las motos en orden alfabético por placa |
| `estaVacio()` | `boolean` | Indica si el árbol no tiene registros |
| `contarNodos()` | `int` | Cuenta el total de nodos en el árbol |

---

## ⚙️ Servicios

### `MotoService.java`
Capa de lógica de negocio. Delega las operaciones estructurales a `ArbolABB` y expone métodos orientados al dominio del problema.

| Método | Retorno | Descripción |
|---|---|---|
| `insertar(Moto moto)` | `void` | Registra una moto en el sistema |
| `buscar(String placa)` | `Moto` | Busca una moto por placa (insensible a mayúsculas) |
| `listar()` | `List<Moto>` | Lista todas las motos en orden alfabético |
| `eliminar(String placa)` | `void` | Elimina una moto del árbol |
| `marcarComoRobada(String placa)` | `boolean` | Cambia el estado a `robada`. Retorna `false` si no existe |
| `marcarComoRecuperada(String placa)` | `boolean` | Cambia el estado a `recuperada`. Retorna `false` si no existe |
| `listarRobadas()` | `List<Moto>` | Filtra y retorna únicamente las motos con estado `robada` |
| `filtrarPorEstado(String estado)` | `List<Moto>` | Filtra motos por cualquier estado dado |
| `obtenerEstadisticas()` | `Map<String, Integer>` | Retorna conteos: `total`, `robadas`, `normales`, `recuperadas` |

---

## 🎮 Controladores

### `MotoController.java`
Único controlador del sistema. Maneja todas las rutas bajo el prefijo `/motos`.

| Método HTTP | Ruta | Descripción |
|---|---|---|
| `GET` | `/motos` | Muestra la página principal con formulario de registro y búsqueda |
| `POST` | `/motos/registrar` | Procesa el formulario de registro de una nueva moto |
| `GET` | `/motos/buscar?placa=` | Busca una moto por placa y muestra el resultado |
| `GET` | `/motos/listar` | Muestra la tabla completa de registros |
| `GET` | `/motos/listar?filtro=` | Lista motos filtradas por estado (`robada`, `normal`, `recuperada`) |
| `POST` | `/motos/eliminar` | Elimina una moto del árbol por placa |
| `POST` | `/motos/marcarRobada` | Cambia el estado de una moto a `robada` |
| `POST` | `/motos/marcarRecuperada` | Cambia el estado de una moto a `recuperada` |
| `GET` | `/motos/estadisticas` | Muestra el panel de métricas y estadísticas globales |
| `GET` | `/motos/detalle?placa=` | Muestra la ficha individual completa de una moto |

**Validaciones aplicadas en `/motos/registrar`:**
- La placa se convierte automáticamente a mayúsculas antes de almacenarse.
- La fecha de reporte se asigna automáticamente con la fecha del sistema (`LocalDate.now()`).
- La descripción es opcional; si no se ingresa, se almacena como cadena vacía.

---

## 🖥️ Vistas

Todas las plantillas utilizan **Thymeleaf** como motor de renderizado del lado del servidor y **Bootstrap 5** para el diseño responsivo. El estilo visual sigue una temática policial con fondo oscuro azul y detalles dorados.

| Template | Descripción | Características destacadas |
|---|---|---|
| `index.html` | Página principal del sistema | Navbar sticky, tarjetas de estadísticas en tiempo real, formulario completo de registro, sección de búsqueda con resultado visual diferenciado por estado |
| `lista-motos.html` | Tabla de todos los registros | Barra de filtros por estado, filas coloreadas según estado, botones de acción por fila (marcar robada, recuperada, eliminar), contador de registros |
| `detalle-moto.html` | Ficha individual de una moto | Cabecera con color según estado, animación de pulso en motos robadas, todos los datos del vehículo y propietario, botones de acción directos |
| `estadisticas.html` | Panel de métricas globales | Números grandes por categoría, barras de progreso con porcentajes calculados, listado compacto de motos robadas activas |

**Diseño UI:**
- Fondo oscuro azul policial (`#0d1b2a`) con acentos dorados (`#f0a500`).
- Navbar sticky con bordes dorados y efecto de gradiente.
- Tarjetas glassmorphism con `backdrop-filter` para los formularios.
- Indicadores de estado con codificación de color: rojo (robada), verde (normal), naranja (recuperada).
- Animación de pulso (`@keyframes`) en registros de motos robadas para destacar la alerta.
- Conversión automática de placa a mayúsculas desde el frontend con JavaScript.

---

## 🔗 Endpoints / Rutas

### Resumen completo

| Método | Ruta | Controlador | Vista retornada |
|---|---|---|---|
| `GET` | `/motos` | `MotoController` | `index.html` |
| `POST` | `/motos/registrar` | `MotoController` | `redirect:/motos` |
| `GET` | `/motos/buscar` | `MotoController` | `index.html` |
| `GET` | `/motos/listar` | `MotoController` | `lista-motos.html` |
| `POST` | `/motos/eliminar` | `MotoController` | `redirect:/motos/listar` |
| `POST` | `/motos/marcarRobada` | `MotoController` | `redirect:/motos/listar` |
| `POST` | `/motos/marcarRecuperada` | `MotoController` | `redirect:/motos/listar` |
| `GET` | `/motos/estadisticas` | `MotoController` | `estadisticas.html` |
| `GET` | `/motos/detalle` | `MotoController` | `detalle-moto.html` |

---

## ✨ Funcionalidades

### 1. Registro de Motos
Formulario completo con campos: placa, marca, modelo, color, año, propietario, cédula, estado y descripción. La placa se convierte a mayúsculas automáticamente. El estado inicial puede ser `normal` o `robada` desde el momento del registro. La fecha de reporte se asigna automáticamente.

### 2. Búsqueda por Placa
Consulta eficiente O(log n) mediante el ABB. El resultado se muestra visualmente diferenciado: alerta roja con animación si la moto está robada, verde si está en estado normal, naranja si fue recuperada. Si la placa no existe en el sistema, se muestra un mensaje informativo.

### 3. Listado con Filtros
Tabla completa de todos los registros ordenados alfabéticamente por placa (recorrido inorden del ABB). Permite filtrar por estado usando botones de acceso rápido. Cada fila tiene color de borde según el estado de la moto y botones de acción directos.

### 4. Gestión de Estados
Desde la tabla o desde la ficha de detalle, se puede cambiar el estado de una moto entre `normal`, `robada` y `recuperada`. Cada cambio redirige con un mensaje flash de confirmación o error.

### 5. Eliminación de Registros
Elimina el nodo correspondiente del árbol manejando correctamente los tres casos del algoritmo de eliminación en un ABB (hoja, un hijo, dos hijos con sucesor inorden).

### 6. Ficha de Detalle
Vista individual que muestra toda la información de una moto en una tarjeta visual. El encabezado cambia de color y estilo según el estado actual. Si la moto está robada, se muestra un banner de alerta con animación de pulso.

### 7. Panel de Estadísticas
Muestra en tiempo real el total de registros y la distribución por estado. Incluye barras de progreso con porcentajes calculados dinámicamente y un listado compacto de todas las motos robadas activas.

---

## 🔄 Flujo de la Aplicación

```
              ┌──────────────────────────────┐
              │   INICIO — /motos            │
              │   index.html                 │
              │   Registro + Búsqueda rápida │
              └──────────────┬───────────────┘
                             │
          ┌──────────────────┼──────────────────┐
          ▼                  ▼                  ▼
┌─────────────────┐  ┌──────────────────┐  ┌──────────────────┐
│ POST /registrar │  │ GET /buscar      │  │ GET /listar      │
│                 │  │ ?placa=ABC123    │  │ ?filtro=robada   │
│ Nueva moto      │  │                  │  │                  │
│ → redirect      │  │ Resultado visual │  │ Tabla completa   │
└────────┬────────┘  │ por estado       │  │ con acciones     │
         │           └──────────────────┘  └────────┬─────────┘
         │                                          │
         ▼                                ┌─────────┴──────────┐
  Vuelve a /motos                         │                    │
  con mensaje                     POST /marcarRobada    POST /eliminar
  de éxito                        POST /marcarRecuperada
                                          │
                                          ▼
                                   redirect /listar
                                   con mensaje flash

                    ┌──────────────────────┐
                    │ GET /estadisticas    │
                    │                      │
                    │ Métricas globales    │
                    │ Lista de robadas     │
                    └──────────────────────┘

                    ┌──────────────────────┐
                    │ GET /detalle         │
                    │ ?placa=ABC123        │
                    │                      │
                    │ Ficha individual     │
                    │ + acciones directas  │
                    └──────────────────────┘
```

---

## 📄 Configuración

### `application.properties`

```properties
spring.application.name=Moto
```

> El puerto por defecto de Spring Boot es `8080`. Para cambiarlo, agregar: `server.port=XXXX`

### `pom.xml` — Dependencias principales

```xml
<dependencies>
    <!-- Motor de plantillas Thymeleaf -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>

    <!-- Spring Web MVC -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- Monitoreo y métricas -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>

    <!-- Recarga automática en desarrollo -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>

    <!-- Testing -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

---

## 📅 Guía de Commits

### 👤 Programador 1 — Backend (5 commits)

```bash
# Commit 1 — Entidad principal
git add src/main/java/.../Model/Moto.java
git commit -m "feat(model): definir entidad Moto con atributos placa, marca, color, anio, cedula, estado, fechaReporte y descripcion"

# Commit 2 — Nodo del árbol
git add src/main/java/.../Model/NodoABB.java
git commit -m "feat(model): implementar NodoABB con referencias izquierda y derecha para estructura ABB"

# Commit 3 — Árbol Binario de Búsqueda
git add src/main/java/.../Model/ArbolABB.java
git commit -m "feat(model): implementar ArbolABB con insertar, buscar O(log n), eliminar en 3 casos y listarInorden"

# Commit 4 — Servicio de negocio
git add src/main/java/.../Service/MotoService.java
git commit -m "feat(service): implementar MotoService con marcarRobada, marcarRecuperada, filtrarPorEstado y obtenerEstadisticas usando ArbolABB"

# Commit 5 — Controlador HTTP
git add src/main/java/.../controller/MotoController.java
git commit -m "feat(controller): agregar endpoints registrar, buscar, listar, eliminar, marcarRobada, marcarRecuperada, estadisticas y detalle"
```

### 👤 Programador 2 — Frontend (5 commits)

```bash
# Commit 1 — Página principal
git add src/main/resources/templates/index.html
git commit -m "feat(view): rediseñar index.html con navbar, tarjetas de estadisticas, formulario completo y resultado de busqueda con alertas visuales"

# Commit 2 — Listado de registros
git add src/main/resources/templates/lista-motos.html
git commit -m "feat(view): crear lista-motos.html con tabla de registros, filtros por estado, filas coloreadas y botones de accion por fila"

# Commit 3 — Ficha de detalle
git add src/main/resources/templates/detalle-moto.html
git commit -m "feat(view): implementar detalle-moto.html con ficha individual, encabezado dinamico por estado y animacion de alerta en motos robadas"

# Commit 4 — Panel de estadísticas
git add src/main/resources/templates/estadisticas.html
git commit -m "feat(view): crear estadisticas.html con panel de metricas, barras de progreso y listado de motos robadas activas"

# Commit 5 — Documentación
git add README.md
git commit -m "docs: agregar documentacion completa del proyecto con arquitectura, modelos, servicios, endpoints y guia de commits"
```

---

## 👥 Equipo

| Rol | Responsabilidad | Archivos |
|---|---|---|
| Programador 1 | Backend — Estructura de datos y lógica | `Moto.java`, `NodoABB.java`, `ArbolABB.java`, `MotoService.java`, `MotoController.java` |
| Programador 2 | Frontend — Vistas y diseño visual | `index.html`, `lista-motos.html`, `detalle-moto.html`, `estadisticas.html`, `README.md` |
| Director | Revisión de código, aprobación de Pull Requests, coordinación general | — |

---

*SIRMOT &copy; 2025 — Universidad de Remington*