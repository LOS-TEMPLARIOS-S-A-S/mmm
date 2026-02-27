🛡️ SIRMOT — Sistema de Registro de Motos Robadas

Proyecto universitario — Universidad de Remington
Aplicación web desarrollada con Spring Boot y Thymeleaf para el registro, consulta y gestión del estado de motos robadas, usando un Árbol Binario de Búsqueda (ABB) como estructura de datos principal.


Tabla de Contenidos

Descripción General
Tecnologías Utilizadas
Requisitos Previos
Instalación y Ejecución
Estructura del Proyecto
Arquitectura
Modelos
Servicios
Controladores
Vistas
Endpoints / Rutas
Funcionalidades
Flujo de la Aplicación
Configuración
Guía de Commits


🌐 Descripción General
SIRMOT es un sistema web de tipo MVC orientado al sector de seguridad, que permite a agentes registrar y consultar el estado de motos en una base de datos gestionada mediante un Árbol Binario de Búsqueda. La búsqueda por placa tiene una complejidad de O(log n), lo que permite respuestas rápidas incluso con grandes volúmenes de registros.
Las principales acciones del sistema son:

Registrar motos con información completa del vehículo y propietario.
Consultar si una placa figura en el sistema y en qué estado se encuentra.
Marcar motos como robadas o recuperadas directamente desde la interfaz.
Eliminar registros del árbol cuando ya no son necesarios.
Visualizar estadísticas globales del sistema en tiempo real.
Ver una ficha detallada de cada moto registrada.


⚠️ Nota: Los datos se almacenan en memoria (estructura ABB en tiempo de ejecución). Al reiniciar la aplicación, todos los registros se pierden. No se utiliza base de datos persistente.


🔧 Tecnologías Utilizadas
TecnologíaVersiónDescripciónJava23Lenguaje de programación principalSpring Boot3.4.2Framework backend MVCSpring Web—Módulo para controladores y manejo de rutas HTTPSpring Actuator—Monitoreo y métricas de la aplicaciónThymeleaf—Motor de plantillas HTML del lado del servidorSpring Boot DevTools—Recarga automática en desarrolloBootstrap5.3Framework CSS para diseño responsivoBootstrap Icons1.11Iconografía del sistemaPlus Jakarta Sans—Tipografía principal cargada desde Google FontsMaven—Gestor de dependencias y construcción del proyecto

✅ Requisitos Previos

Java JDK 23 o superior instalado
Apache Maven 3.8+ (o usar el wrapper mvnw incluido en el proyecto)
Un navegador web moderno (Chrome, Firefox, Edge)
Conexión a internet para cargar Google Fonts, Bootstrap y Bootstrap Icons desde CDN



🚀 Instalación y Ejecución
1. Clonar el repositorio
bashgit clone <url-del-repositorio>
cd MotoMejorado

### 4. Acceder al sistema

Abrir el navegador en: **http://localhost:9090/motos**

> El puerto por defecto es `9090`, configurado en `application.properties`.

---

## 📁 Estructura del Proyecto
```
MotoMejorado/
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
│     Bootstrap 5 + Thymeleaf + Plus Jakarta Sans          │
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

Todas las plantillas utilizan **Thymeleaf** como motor de renderizado del lado del servidor y **Bootstrap 5** para el diseño responsivo. El estilo visual sigue una estética moderna tipo **glassmorphism** con fondo degradado en tonos morado–azul–celeste, sidebar translúcido fijo de 230px, tarjetas con efecto de cristal y tipografía **Plus Jakarta Sans** importada desde Google Fonts.

| Template | Descripción | Características destacadas |
|---|---|---|
| `index.html` | Página principal del sistema | Sidebar fijo translúcido, 4 stat cards con íconos en degradado de color, formulario de registro con inputs glass, panel de búsqueda con resultado visual diferenciado por estado |
| `lista-motos.html` | Tabla de todos los registros | Sidebar fijo, filtros tipo pill semitransparentes con backdrop-filter, tabla glass con borde de color por estado en cada fila, placa en badge morado, botones de acción compactos de 30×30px |
| `detalle-moto.html` | Ficha individual de una moto | Banner superior en pastel que cambia según el estado, placa en tipografía monoespaciada grande con letter-spacing, banner de alerta con animación de pulso si está robada, botones de acción con colores diferenciados |
| `estadisticas.html` | Panel de métricas globales | 4 stat cards grandes con íconos degradado, barras de progreso con gradientes de color por categoría, barra extra de tasa de recuperación, listado scrollable de motos robadas activas con efecto hover |

**Diseño UI — Glassmorphism:**
- Fondo: gradiente `#e8d5ff → #c9d8ff → #b8eaff → #d4f0ff` con dos blobs de color difuminados en las esquinas usando `filter: blur(80px)`.
- Sidebar fijo de 230px con `background: rgba(255,255,255,0.35)`, `backdrop-filter: blur(24px)` y borde `rgba(255,255,255,0.7)`.
- Tarjetas con `background: rgba(255,255,255,0.45)`, `backdrop-filter: blur(20px)` y `border: 1px solid rgba(255,255,255,0.7)`.
- Logo del sidebar con gradiente `#7B5CE7 → #E879C8` aplicado al texto mediante `-webkit-background-clip: text`.
- Stat cards con íconos en gradientes: morado (`#a78bfa → #7B5CE7`), rosa (`#f472b6 → #E879C8`), verde (`#34d399 → #10b981`), azul (`#60a5fa → #38bdf8`).
- Botón principal de registro con gradiente `#7B5CE7 → #E879C8` y sombra de color ampliada al hacer hover.
- Codificación de color por estado: rojo semitransparente (robada), verde (normal), ámbar (recuperada).
- Animación `@keyframes pulse-r` en registros de motos robadas para destacar visualmente la alerta.
- Diseño responsive: el sidebar se oculta automáticamente en pantallas menores a 768px.
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
Formulario completo con campos: placa, marca, modelo, color, año, propietario, cédula, estado y descripción. Los inputs tienen fondo semitransparente con borde glass y glow morado al hacer foco. La placa se convierte a mayúsculas automáticamente via JavaScript. El estado inicial puede ser `normal` o `robada` desde el momento del registro. La fecha de reporte se asigna automáticamente con `LocalDate.now()`.

### 2. Búsqueda por Placa
Consulta eficiente **O(log n)** mediante el ABB. El resultado se muestra en una tarjeta visual diferenciada: roja con animación de pulso si la moto está robada, verde si está en estado normal, ámbar si fue recuperada. Si la placa no existe en el sistema, se muestra un estado vacío con ícono circular.

### 3. Listado con Filtros
Tabla glass con todos los registros ordenados alfabéticamente por placa (recorrido inorden del ABB). Los botones de filtro tienen estilo pill semitransparente con `backdrop-filter`. Cada fila tiene borde de color a la izquierda según el estado del vehículo. Las placas se muestran en un badge con fondo morado. Botones de acción compactos de 30×30px con color semántico por función.

### 4. Gestión de Estados
Desde la tabla o desde la ficha de detalle, se puede cambiar el estado de una moto entre `normal`, `robada` y `recuperada`. Cada cambio redirige con un mensaje flash visual diferenciado por color en la parte superior de la pantalla.

### 5. Eliminación de Registros
Elimina el nodo correspondiente del árbol manejando correctamente los tres casos del algoritmo de eliminación en un ABB: nodo hoja, nodo con un solo hijo, y nodo con dos hijos mediante el sucesor inorden.

### 6. Ficha de Detalle
Vista individual con tarjeta glass que muestra toda la información del vehículo. El banner superior cambia de color pastel según el estado actual. La placa se muestra en tipografía monoespaciada grande con letter-spacing amplio. Si la moto está robada, aparece un banner de alerta rojo con animación de pulso y botón directo para marcarla como recuperada.

### 7. Panel de Estadísticas
Muestra en tiempo real el total de registros y la distribución por estado en 4 stat cards con íconos en degradado. Incluye barras de progreso con gradientes de color y porcentajes calculados dinámicamente. Agrega una barra adicional de **Tasa de Recuperación** (recuperadas ÷ robadas × 100). Lista scrollable de motos robadas activas con efecto hover de deslizamiento lateral.

---

## 🔄 Flujo de la Aplicación
```
              ┌──────────────────────────────┐
              │   INICIO — /motos            │
              │   index.html                 │
              │   Sidebar + Stats + Registro │
              │   + Búsqueda rápida          │
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
  con mensaje flash               POST /marcarRobada    POST /eliminar
                                  POST /marcarRecuperada
                                          │
                                          ▼
                                   redirect /listar
                                   con mensaje flash

                    ┌──────────────────────┐
                    │ GET /estadisticas    │
                    │                      │
                    │ Métricas + barras    │
                    │ Tasa recuperación    │
                    │ Lista de robadas     │
                    └──────────────────────┘

                    ┌──────────────────────┐
                    │ GET /detalle         │
                    │ ?placa=ABC123        │
                    │                      │
                    │ Ficha glass          │
                    │ Banner por estado    │
                    │ + acciones directas  │
                    └──────────────────────┘

📄 Configuración
application.properties
propertiesspring.application.name=Moto

El puerto por defecto de Spring Boot es 9090. Para cambiarlo, agregar: server.port=XXXX

pom.xml — Dependencias principales
xml<dependencies>
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

📅 Guía de Commits
👤 Programador 1 — Backend (5 commits)
bash# Commit 1 — Entidad principal
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
👤 Programador 2 — Frontend (5 commits)
bash# Commit 1 — Página principal
git add src/main/resources/templates/index.html
git commit -m "feat(view): implementar index.html con sidebar glass, stat cards con iconos degradado, formulario registro y busqueda con resultado visual por estado - closes #7"

# Commit 2 — Listado de registros
git add src/main/resources/templates/lista-motos.html
git commit -m "feat(view): crear lista-motos.html con sidebar, tabla glass, filtros pill semitransparentes, filas con borde de color y botones accion compactos - closes #8"

# Commit 3 — Ficha de detalle
git add src/main/resources/templates/detalle-moto.html
git commit -m "feat(view): implementar detalle-moto.html con banner pastel por estado, placa monoespaciada grande y animacion de pulso en motos robadas - closes #9"

# Commit 4 — Panel de estadísticas
git add src/main/resources/templates/estadisticas.html
git commit -m "feat(view): crear estadisticas.html con stat cards, barras de progreso degradado, tasa de recuperacion y lista scrollable de robadas activas - closes #10"

# Commit 5 — Documentación
git add README.md
git commit -m "docs: actualizar README con arquitectura MVC, tecnologias, modelos, endpoints, diseno glassmorphism y guia de commits - closes #11"

👥 Equipo
RolResponsabilidadArchivosProgramador 1Backend — Estructura de datos y lógicaMoto.java, NodoABB.java, ArbolABB.java, MotoService.java, MotoController.javaProgramador 2Frontend — Vistas y diseño visualindex.html, lista-motos.html, detalle-moto.html, estadisticas.html, README.mdDirectorRevisión de código, aprobación de Pull Requests, coordinación general—

SIRMOT © 2025 — Universidad de Remington