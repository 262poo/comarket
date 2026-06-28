# S12 - Aplicaciones de escritorio por capas y gestión de datos persistentes (Evaluación U2)

## 1. Introducción

Tiempo: 20 min.

### 1.1 Propósito

Validar la aplicación de escritorio por capas con GUI, DAO, SQLite, operaciones persistentes, relaciones entre objetos, seguridad básica, consultas y pruebas.

### 1.2 Resultado de aprendizaje

El estudiante demuestra que puede construir, ejecutar, probar y defender una aplicación JavaFX organizada por capas, con persistencia relacional y operaciones sobre objetos relacionados.

### 1.3 Producto de sesión

Producto U2 integrado: GUI JavaFX, controladores, servicios, entidades, DAO, SQLite, relación muchos a muchos, relación uno a muchos, seguridad básica, consultas y evidencias de pruebas.

### 1.4 Motivación de la sesión

Una aplicación de escritorio se evalúa por el flujo completo: el usuario ingresa, opera pantallas, el controlador delega, el servicio coordina, el DAO persiste y las consultas muestran información consistente.

Preguntas para los estudiantes:

1. Qué evidencia demuestra que la GUI funciona integrada con SQLite?
2. Qué parte puedes defender individualmente?
3. Qué revisas cuando una operación no aparece en una consulta?

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
- Relación muchos a muchos mediante detalle.
- Relación uno a muchos asociada al usuario.
- Seguridad básica.
- Consultas integradas.
- Pruebas manuales.

### 2.2 Arquitectura del producto U2

```mermaid
flowchart TB
    Vista["view<br/>JavaFX/FXML"]
    Controlador["controller"]

    subgraph Servicio["service"]
        ProductoService["ProductoServiceImplDB"]
        VentaService["VentaServiceImplDB"]
        UsuarioService["UsuarioServiceImplDB"]
        ConsultaService["ConsultaService"]
        Validaciones["Validaciones/Excepciones"]
    end

    Entidades["entity<br/>Producto / Venta / DetalleVenta / Usuario"]

    subgraph Persistencia["dao"]
        ProductoDAO["ProductoDAO"]
        VentaDAO["VentaDAO"]
        DetalleDAO["DetalleVentaDAO"]
        UsuarioDAO["UsuarioDAO"]
        ConsultaDAO["ConsultaDAO"]
        SQLite[("SQLite")]
    end

    Vista --> Controlador
    Controlador --> ProductoService
    Controlador --> VentaService
    Controlador --> UsuarioService
    Controlador --> ConsultaService
    ProductoService -.-> Validaciones
    VentaService -.-> Validaciones
    UsuarioService -.-> Validaciones
    ProductoService --> ProductoDAO
    VentaService --> VentaDAO
    VentaService --> DetalleDAO
    UsuarioService --> UsuarioDAO
    ConsultaService --> ConsultaDAO
    ProductoDAO --> Entidades
    VentaDAO --> Entidades
    DetalleDAO --> Entidades
    UsuarioDAO --> Entidades
    ConsultaDAO --> Entidades
    ProductoDAO -->|"JDBC"| SQLite
    VentaDAO -->|"JDBC"| SQLite
    DetalleDAO -->|"JDBC"| SQLite
    UsuarioDAO -->|"JDBC"| SQLite
    ConsultaDAO -->|"JDBC"| SQLite

    classDef serviceImpl fill:#dbeafe,stroke:#2563eb,stroke-width:2px,color:#1e3a8a;
    class ProductoService,VentaService,UsuarioService serviceImpl;
```

### 2.3 Criterios mínimos de revisión

- GUI operativa.
- `controller` conectado con `service`.
- CRUD persistente de una tabla simple.
- Operación persistente con cabecera y detalle.
- Seguridad básica con usuario.
- Relación uno a muchos asociada al usuario.
- Consultas integradas.
- Validaciones por sesión.
- Pruebas del flujo principal.

## 3. Aplica: evaluación práctica

Tiempo: 3h.

### 3.1 Preparar demostración

Orden recomendado:

1. Abrir el proyecto.
2. Mostrar estructura de capas.
3. Ejecutar la aplicación JavaFX.
4. Iniciar sesión.
5. Demostrar CRUD persistente de una tabla simple.
6. Registrar una operación con detalles.
7. Verificar registros en SQLite.
8. Ejecutar una consulta integrada.
9. Mostrar matriz de pruebas.
10. Explicar una decisión técnica.

### 3.2 Ejecutar pruebas base

El estudiante demuestra:

1. Login correcto e incorrecto.
2. Registro persistente desde GUI.
3. Edición y eliminación de una tabla simple.
4. Registro de cabecera y detalle.
5. Validación de cantidad, stock o venta sin detalles.
6. Asociación de operación a usuario.
7. Consulta por filtro y vista de detalle.
8. Manejo básico de errores.

### 3.3 Demostración individual

Cada integrante debe poder responder:

- Qué parte implementó.
- Qué clase o archivo modificó.
- Qué prueba ejecutó.
- Qué error diagnosticó.
- Qué decisión técnica puede defender.

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
- Código o descripción de DAO.
- Código o descripción de servicios.
- Evidencia de seguridad básica.
- Evidencia de relación muchos a muchos.
- Evidencia de relación uno a muchos.
- Consulta integrada.
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
- Operación con detalle demostrada.
- Seguridad básica demostrada.
- Consulta integrada demostrada.
- Validaciones demostradas.
- Aporte individual verificable.

## 5. Cierre evaluativo

Tiempo: 20 min.

### 5.1 Resultados esperados

- Producto U2 ejecutado.
- Persistencia demostrada.
- Relaciones entre objetos explicadas.
- Seguridad básica operativa.
- Consultas integradas funcionando.
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
6. Qué relación se asocia al usuario?
7. Qué consulta integrada implementaste?
8. Qué mejorarás en U3?

### 5.4 Rúbrica de evaluación

| Dimensión | Peso | 3 - Logro destacado | 2 - Logro | 1 - Proceso | 0 - Inicio | Puntuación obtenida |
|---|---:|---|---|---|---|---:|
| 1. GUI funcional | 2 | GUI completa, clara y conectada al flujo principal. | GUI principal funcional. | GUI parcial o inestable. | No ejecuta GUI. | |
| 2. Capas y responsabilidades | 2 | `controller`, `service`, `entity` y `dao` bien separados. | Separación suficiente. | Mezclas importantes. | No separa. | |
| 3. Persistencia y relaciones | 2 | CRUD simple, detalle y relaciones persistentes funcionando. | Persistencia principal funcional. | Persistencia incompleta. | No persiste. | |
| 4. Seguridad y consultas | 2 | Login, usuario asociado y consultas integradas funcionando. | Funcionalidad principal presente. | Funcionalidad parcial. | No evidencia. | |
| 5. Evidencia individual | 1 | Evidencia clara, ordenada y verificable. | Evidencia suficiente. | Evidencia incompleta. | No entrega. | |
| 6. Defensa técnica | 1 | Responde con precisión y criterio. | Responde adecuadamente. | Responde parcialmente. | No sustenta. | |
