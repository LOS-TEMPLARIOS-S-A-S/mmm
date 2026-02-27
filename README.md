# 🛵 SIRMOT — Sistema de Registro de Motos Robadas

> Proyecto universitario — Uniremington  
> **Spring Boot + Thymeleaf + Árbol Binario de Búsqueda (ABB)**

---

## 📌 Descripción

SIRMOT permite registrar, buscar, y gestionar el estado de motos robadas usando un **Árbol Binario de Búsqueda** como estructura de datos principal. La búsqueda por placa es O(log n).

---

## ✨ Funcionalidades

- 📋 Registrar moto (placa, marca, modelo, color, año, propietario, cédula, descripción)
- 🔍 Buscar por placa con resultado visual según estado
- 🚨 Marcar como robada / ✅ Marcar como recuperada
- 🗑️ Eliminar registro del árbol
- 📊 Estadísticas con barras de progreso
- 📄 Ficha de detalle individual por moto
- 🔎 Filtrar tabla por estado (robada / normal / recuperada)

---

## 🚀 Cómo ejecutar

```bash
git clone https://github.com/TU_USUARIO/sirmot.git
cd sirmot
./mvnw spring-boot:run
```

Abre: **http://localhost:8080/motos**

---

## 🏗️ Estructura del Proyecto

```
src/main/java/.../Moto/
├── Model/
│   ├── Moto.java          → Entidad completa de la moto
│   ├── NodoABB.java       → Nodo del árbol binario
│   └── ArbolABB.java      → Árbol Binario de Búsqueda (lógica estructural)
├── Service/
│   └── MotoService.java   → Lógica de negocio (usa ArbolABB)
└── controller/
    └── MotoController.java → Endpoints HTTP

src/main/resources/templates/
├── index.html             → Registro + búsqueda rápida
├── lista-motos.html       → Tabla con filtros y acciones
├── detalle-moto.html      → Ficha individual de cada moto
└── estadisticas.html      → Panel de métricas
```

---

## 📅 División de 10 Commits para GitHub

> Cada commit corresponde a **un archivo específico**.  
> Esto garantiza un historial limpio y trazable.

---

### 👤 Persona 1 — Backend (5 commits)

#### Commit 1 — Modelo de datos
```bash
git add src/main/java/.../Model/Moto.java
git commit -m "feat(model): ampliar entidad Moto con campos marca, color, anio, cedula, descripcion y fechaReporte"
```

#### Commit 2 — Nodo del árbol
```bash
git add src/main/java/.../Model/NodoABB.java
git commit -m "feat(model): definir NodoABB con referencias izquierda y derecha para el ABB"
```

#### Commit 3 — Árbol Binario de Búsqueda
```bash
git add src/main/java/.../Model/ArbolABB.java
git commit -m "feat(model): implementar clase ArbolABB con insertar, buscar, eliminar y listarInorden"
```

#### Commit 4 — Servicio de negocio
```bash
git add src/main/java/.../Service/MotoService.java
git commit -m "feat(service): implementar MotoService usando ArbolABB con marcarRobada, marcarRecuperada, filtros y estadisticas"
```

#### Commit 5 — Controlador HTTP
```bash
git add src/main/java/.../controller/MotoController.java
git commit -m "feat(controller): agregar endpoints registrar, buscar, listar, eliminar, marcarRobada, marcarRecuperada, estadisticas y detalle"
```

---

### 👤 Persona 2 — Frontend (5 commits)

#### Commit 1 — Página principal
```bash
git add src/main/resources/templates/index.html
git commit -m "feat(view): rediseñar index.html con navbar, tarjetas de estadisticas, formulario completo y resultado de busqueda con alertas"
```

#### Commit 2 — Listado con acciones
```bash
git add src/main/resources/templates/lista-motos.html
git commit -m "feat(view): rediseñar lista-motos.html con tabla profesional, filtros por estado, colores por fila y botones de accion"
```

#### Commit 3 — Ficha de detalle
```bash
git add src/main/resources/templates/detalle-moto.html
git commit -m "feat(view): crear detalle-moto.html con ficha individual, header por estado y acciones directas"
```

#### Commit 4 — Panel de estadísticas
```bash
git add src/main/resources/templates/estadisticas.html
git commit -m "feat(view): crear estadisticas.html con panel de metricas, barras de progreso y listado de motos robadas activas"
```

#### Commit 5 — Documentación
```bash
git add README.md
git commit -m "docs: agregar README con descripcion, estructura del proyecto y guia de 10 commits para trabajo en equipo"
```

---

## 👥 Autores

| Persona | Área | Archivos |
|---|---|---|
| Kiara Puello | Backend | Moto.java, NodoABB.java, ArbolABB.java, MotoService.java, MotoController.java |
| [Compañero/a] | Frontend | index.html, lista-motos.html, detalle-moto.html, estadisticas.html, README.md |

---

*Uniremington — 2025*
