# S12 - Aplicaciones de escritorio por capas y gestión de datos persistentes (Evaluación U2)

## 1. Introducción

Tiempo: 20 min.

### 1.1 Propósito

Validar la aplicación de escritorio por capas con GUI, DAO, SQLite, listado persistente, CRUD, asociación `Usuario–Venta` y flujo `Venta–DetalleVenta–Producto`.

### 1.2 Resultado de aprendizaje

El estudiante demuestra que puede construir, ejecutar, probar y defender una aplicación JavaFX organizada por capas, con persistencia relacional y operaciones sobre objetos relacionados.

### 1.3 Producto de sesión

Producto U2 integrado: GUI JavaFX, controladores, servicios, entidades, DAO, SQLite, CRUD de `Producto`, asociación `Usuario–Venta` y flujo `Venta–DetalleVenta–Producto`, todavía sin autenticación ni reportes.

### 1.4 Motivación de la sesión

Una aplicación de escritorio se evalúa por el flujo completo: la vista recibe acciones, el controlador delega, el servicio coordina objetos y reglas, el DAO persiste y la GUI recupera información consistente.

Preguntas para los estudiantes:

1. Qué evidencia demuestra que la GUI funciona integrada con SQLite?
2. Qué parte puedes defender individualmente?
3. Qué revisas cuando una operación no se recupera después de reiniciar la aplicación?

### 1.5 Ubicación en el curso

- Unidad: U2 - Aplicación de escritorio con persistencia de datos.
- Producto de unidad: aplicación de escritorio funcional con arquitectura por capas, interfaz gráfica y persistencia en base de datos relacional.
- Carpeta de trabajo: `comarket-desk`.
- Avance de sesión: evaluación integradora antes de la integración final en U3.

## 2. Explica

Tiempo: 15 min.

### 2.1 Conceptos clave

- Integración GUI-persistencia.
- Arquitectura por capas.
- DAO y JDBC.
- Asociación `Usuario–Venta` sin autenticación.
- Composición `Venta–DetalleVenta` y referencia a `Producto`.
- Persistencia atómica y consistencia del flujo.
- Validaciones y errores controlados.
- Pruebas manuales.

### 2.2 Arquitectura real del producto U2

```mermaid
flowchart TB
    ProductoView["view<br/>ProductoView.fxml"]
    VentaView["view<br/>VentaView.fxml"]
    ProductoController["controller<br/>ProductoController"]
    VentaController["controller<br/>VentaController"]
    ProductoService["service<br/>ProductoServiceImplSQLite"]
    VentaService["service<br/>VentaServiceImplSQLite"]

    subgraph Persistencia["dao + db"]
        ProductoDAO["dao<br/>ProductoDao"]
        VentaDAO["dao<br/>VentaDao"]
        DetalleDAO["dao<br/>DetalleVentaDao"]
        UsuarioDAO["dao<br/>UsuarioDao"]
        Conexion["db<br/>ConexionSQLite"]
        SQLite[("data/comarket.db")]
    end

    Entidades["entity<br/>Producto / Venta / DetalleVenta / Usuario"]

    ProductoView --> ProductoController
    VentaView --> VentaController
    ProductoController --> ProductoService
    VentaController --> VentaService
    ProductoService --> ProductoDAO
    VentaService --> VentaDAO
    VentaService --> DetalleDAO
    VentaService --> UsuarioDAO

    ProductoDAO --> Entidades
    VentaDAO --> Entidades
    DetalleDAO --> Entidades
    UsuarioDAO --> Entidades

    UsuarioDAO --> Conexion
    ProductoDAO --> Conexion
    VentaDAO --> Conexion
    DetalleDAO --> Conexion
    Conexion -->|"JDBC"| SQLite
```

### 2.3 Criterios mínimos de cierre U2

- Proyecto organizado por capas y conectado.
- Listado persistente de productos desde la GUI.
- CRUD persistente de productos en SQLite.
- Venta asociada a un usuario seleccionado, sin login.
- Registro de venta con cabecera y detalle.
- Recuperación de ventas con usuario y detalles.
- Anulación con cambio de estado y reposición de stock.
- Consistencia total cabecera vs detalle validada.
- Validaciones y errores controlados.

## 3. Aplica: evaluación práctica

Tiempo: 3h.

### 3.1 Demostrar el listado persistente por capas

1. Ejecutar comarket-desk.
2. Abrir la vista de productos.
3. Explicar el flujo base de datos–DAO–servicio–controlador–tabla.
4. Confirmar que los productos se recuperan después de reiniciar.

### 3.2 Demostrar CRUD persistente de productos

1. Registrar un producto nuevo.
2. Editar el producto.
3. Eliminar un producto según el flujo disponible.
4. Confirmar que los cambios se mantienen al recargar la pantalla.

### 3.3 Demostrar venta con cabecera y detalle

1. Registrar una venta con al menos dos detalles.
2. Verificar validaciones (cantidad mayor a cero y stock suficiente).
3. Seleccionar un usuario de prueba y confirmar que la venta queda asociada.

### 3.4 Demostrar anulación de ventas

1. Abrir Anular ventas.
2. Seleccionar una venta y mostrar su detalle.
3. Verificar que se muestra el usuario asociado a la venta.
4. Anular una venta activa.
5. Verificar cambio de estado a ANULADA y reposición de stock.

### 3.5 Demostrar objetos relacionados y consistencia

1. Listar las ventas asociadas a un usuario de prueba.
2. Seleccionar una venta y mostrar sus detalles.
3. Verificar que cada detalle referencia un producto existente.
4. Comparar el total de la venta con la suma de subtotales.

### 3.6 Ejecutar matriz final de pruebas U2

| Caso | Evidencia esperada | Resultado obtenido |
|---|---|---|
| Listado persistente | Productos recuperados desde la base de datos | |
| CRUD de productos | Persistencia correcta en GUI | |
| Registro de venta | Cabecera y detalle guardados | |
| Usuario en venta | Venta asociada al usuario seleccionado | |
| Anular ventas | Maestro-detalle operativo, usuario visible y anulación | |
| Anulación | Estado ANULADA y stock repuesto | |
| Consistencia | Total cabecera coincide con detalle | |

Nota metodológica:

```text
En el estado actual del proyecto, el cierre U2 se sustenta con pruebas funcionales manuales.
No hay suite automatizada en src/test para este producto.
```

## 4. Crea: evidencia individual

Tiempo: 4h fuera del aula.

### 4.1 Plantilla de evidencia individual

Entrega un PDF con el siguiente nombre:

```text
S12_Equipo##_ApellidoNombre.pdf
```

#### 4.1.1 Datos del estudiante

- Nombre:
- Equipo:
- Sesión: S12 - Aplicaciones de escritorio por capas y gestión de datos persistentes (Evaluación U2)
- Rol o aporte realizado:
- Link de GitHub:

#### 4.1.2 Trabajo autónomo realizado

1. Ordenar evidencias de U2.
2. Registrar aporte individual.
3. Corregir observaciones.
4. Preparar defensa técnica.
5. Documentar flujo integrado.

#### 4.1.3 Evidencia técnica

- Capturas de GUI.
- Evidencia de registros en SQLite.
- Evidencia del listado persistente por capas.
- Evidencia del CRUD persistente de `Producto`.
- Evidencia de la asociación `Usuario–Venta` mediante selección manual.
- Evidencia del flujo `Venta–DetalleVenta–Producto`.
- Evidencia de anulación, reposición de stock y consistencia de totales.
- Matriz mínima de pruebas.
- Aporte individual.

#### 4.1.4 Error o hallazgo

Describe un problema de integración y cómo lo diagnosticaste.

#### 4.1.5 Reflexión técnica breve

Explica cómo fluye una operación desde la vista hasta SQLite.

### 4.2 Criterios mínimos de aceptación

- PDF con nombre correcto.
- Evidencia de aplicación JavaFX funcionando.
- CRUD persistente demostrado.
- Asociación `Usuario–Venta` demostrada sin autenticación.
- Operación `Venta–DetalleVenta–Producto` demostrada.
- Anulación y consistencia del flujo demostradas.
- Validaciones demostradas.
- Aporte individual verificable.

## 5. Cierre evaluativo

Tiempo: 20 min.

### 5.1 Resultados esperados

- Producto U2 ejecutado.
- Persistencia demostrada.
- Relaciones entre objetos explicadas.
- Flujo cabecera–detalle funcionando.
- Stock y totales consistentes.
- Validaciones y pruebas documentadas.
- Evidencia individual entregada.

### 5.2 Evidencia del producto de sesión

Cada estudiante entrega un PDF individual siguiendo la plantilla de la sección 4.1.

### 5.3 Preguntas de defensa y reflexión

1. Cómo fluye una operación desde la vista hasta SQLite?
2. Qué responsabilidad tiene el controlador?
3. Qué responsabilidad tiene el servicio?
4. Qué responsabilidad tiene el DAO?
5. Cómo se guarda una operación con detalles?
6. Cómo se asocia una venta con el usuario seleccionado sin iniciar sesión?
7. Cómo garantizas la consistencia entre cabecera, detalles, total y stock?
8. Qué cambiará en S13 cuando el usuario provenga de una sesión autenticada?

### 5.4 Rúbrica de evaluación

| Dimensión | Peso | 3 - Logro destacado | 2 - Logro | 1 - Proceso | 0 - Inicio | Puntuación obtenida |
|---|---:|---|---|---|---|---:|
| 1. GUI funcional | 2 | GUI completa, clara y conectada al flujo principal. | GUI principal funcional. | GUI parcial o inestable. | No ejecuta GUI. | |
| 2. Capas y responsabilidades | 2 | `controller`, `service`, `entity` y `dao` bien separados. | Separación suficiente. | Mezclas importantes. | No separa. | |
| 3. Persistencia y relaciones | 2 | CRUD simple, detalle y relaciones persistentes funcionando. | Persistencia principal funcional. | Persistencia incompleta. | No persiste. | |
| 4. Flujo y consistencia | 2 | Venta con usuario, detalles, total, stock y anulación consistentes. | Flujo principal funcional y consistente. | Flujo parcial o con inconsistencias. | No evidencia el flujo. | |
| 5. Evidencia individual | 1 | Evidencia clara, ordenada y verificable. | Evidencia suficiente. | Evidencia incompleta. | No entrega. | |
| 6. Defensa técnica | 1 | Responde con precisión y criterio. | Responde adecuadamente. | Responde parcialmente. | No sustenta. | |
