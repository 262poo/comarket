# S12 - Aplicaciones de escritorio por capas y gestiÃ³n de datos persistentes (EvaluaciÃ³n U2)

## 1. IntroducciÃ³n

Tiempo: 20 min.

### 1.1 PropÃ³sito

Validar la aplicaciÃ³n de escritorio por capas con GUI, DAO, SQLite, operaciones persistentes, relaciones entre objetos, seguridad bÃ¡sica, consultas y pruebas.

### 1.2 Resultado de aprendizaje

El estudiante demuestra que puede construir, ejecutar, probar y defender una aplicaciÃ³n JavaFX organizada por capas, con persistencia relacional y operaciones sobre objetos relacionados.

### 1.3 Producto de sesiÃ³n

Producto U2 integrado: GUI JavaFX, controladores, servicios, entidades, DAO, SQLite, relaciÃ³n muchos a muchos, relaciÃ³n uno a muchos, seguridad bÃ¡sica, consultas y evidencias de pruebas.

### 1.4 MotivaciÃ³n de la sesiÃ³n

Una aplicaciÃ³n de escritorio se evalÃºa por el flujo completo: el usuario ingresa, opera pantallas, el controlador delega, el servicio coordina, el DAO persiste y las consultas muestran informaciÃ³n consistente.

Preguntas para los estudiantes:

1. QuÃ© evidencia demuestra que la GUI funciona integrada con SQLite?
2. QuÃ© parte puedes defender individualmente?
3. QuÃ© revisas cuando una operaciÃ³n no aparece en una consulta?

### 1.5 UbicaciÃ³n en el curso

- Unidad: U2 - AplicaciÃ³n de escritorio con persistencia de datos.
- Producto de unidad: aplicaciÃ³n de escritorio funcional con arquitectura por capas, interfaz grÃ¡fica y persistencia en base de datos relacional.
- Carpeta de trabajo: `comarket-desk`.
- Avance de sesiÃ³n: evaluaciÃ³n integradora antes de la integraciÃ³n final en U3.

## 2. Explica

Tiempo: 15 min.

### 2.1 Conceptos clave

- IntegraciÃ³n GUI-persistencia.
- Arquitectura por capas.
- DAO y JDBC.
- RelaciÃ³n muchos a muchos mediante detalle.
- RelaciÃ³n uno a muchos asociada al usuario.
- Seguridad bÃ¡sica.
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

### 2.3 Criterios mÃ­nimos de revisiÃ³n

- GUI operativa.
- `controller` conectado con `service`.
- CRUD persistente de una tabla simple.
- OperaciÃ³n persistente con cabecera y detalle.
- Seguridad bÃ¡sica con usuario.
- RelaciÃ³n uno a muchos asociada al usuario.
- Consultas integradas.
- Validaciones por sesiÃ³n.
- Pruebas del flujo principal.

## 3. Aplica: evaluaciÃ³n prÃ¡ctica

Tiempo: 3h.

### 3.1 Preparar demostraciÃ³n

Orden recomendado:

1. Abrir el proyecto.
2. Mostrar estructura de capas.
3. Ejecutar la aplicaciÃ³n JavaFX.
4. Iniciar sesiÃ³n.
5. Demostrar CRUD persistente de una tabla simple.
6. Registrar una operaciÃ³n con detalles.
7. Verificar registros en SQLite.
8. Ejecutar una consulta integrada.
9. Mostrar matriz de pruebas.
10. Explicar una decisiÃ³n tÃ©cnica.

### 3.2 Ejecutar pruebas base

El estudiante demuestra:

1. Login correcto e incorrecto.
2. Registro persistente desde GUI.
3. EdiciÃ³n y eliminaciÃ³n de una tabla simple.
4. Registro de cabecera y detalle.
5. ValidaciÃ³n de cantidad, stock o venta sin detalles.
6. AsociaciÃ³n de operaciÃ³n a usuario.
7. Consulta por filtro y vista de detalle.
8. Manejo bÃ¡sico de errores.

### 3.3 DemostraciÃ³n individual

Cada integrante debe poder responder:

- QuÃ© parte implementÃ³.
- QuÃ© clase o archivo modificÃ³.
- QuÃ© prueba ejecutÃ³.
- QuÃ© error diagnosticÃ³.
- QuÃ© decisiÃ³n tÃ©cnica puede defender.

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
- SesiÃ³n: S12 - Aplicaciones de escritorio por capas y gestiÃ³n de datos persistentes (EvaluaciÃ³n U2)
- Rol o aporte realizado:
- Link de GitHub:

#### 4.1.2 Trabajo autÃ³nomo realizado

1. Ordenar evidencias de U2.
2. Registrar aporte individual.
3. Corregir observaciones.
4. Preparar defensa tÃ©cnica.
5. Documentar flujo integrado.

#### 4.1.3 Evidencia tÃ©cnica

- Capturas de GUI.
- Evidencia de registros en SQLite.
- CÃ³digo o descripciÃ³n de DAO.
- CÃ³digo o descripciÃ³n de servicios.
- Evidencia de seguridad bÃ¡sica.
- Evidencia de relaciÃ³n muchos a muchos.
- Evidencia de relaciÃ³n uno a muchos.
- Consulta integrada.
- Matriz mÃ­nima de pruebas.
- Aporte individual.

#### 4.1.4 Error o hallazgo

Describe un problema de integraciÃ³n y cÃ³mo lo diagnosticaste.

#### 4.1.5 ReflexiÃ³n tÃ©cnica breve

Explica cÃ³mo fluye una operaciÃ³n desde la vista hasta SQLite.

### 4.2 Criterios mÃ­nimos de aceptaciÃ³n

- PDF con nombre correcto.
- Evidencia de aplicaciÃ³n JavaFX funcionando.
- CRUD persistente demostrado.
- OperaciÃ³n con detalle demostrada.
- Seguridad bÃ¡sica demostrada.
- Consulta integrada demostrada.
- Validaciones demostradas.
- Aporte individual verificable.

## 5. Cierre evaluativo

Tiempo: 20 min.

### 5.1 Resultados esperados

- Producto U2 ejecutado.
- Persistencia demostrada.
- Relaciones entre objetos explicadas.
- Seguridad bÃ¡sica operativa.
- Consultas integradas funcionando.
- Validaciones y pruebas documentadas.
- Evidencia individual entregada.

### 5.2 Evidencia del producto de sesiÃ³n

Cada estudiante entrega un PDF individual siguiendo la plantilla de la secciÃ³n 4.1.

### 5.3 Preguntas de defensa y reflexiÃ³n

1. CÃ³mo fluye una operaciÃ³n desde la vista hasta SQLite?
2. QuÃ© responsabilidad tiene el controlador?
3. QuÃ© responsabilidad tiene el servicio?
4. QuÃ© responsabilidad tiene el DAO?
5. CÃ³mo se guarda una operaciÃ³n con detalles?
6. QuÃ© relaciÃ³n se asocia al usuario?
7. QuÃ© consulta integrada implementaste?
8. QuÃ© mejorarÃ¡s en U3?

### 5.4 RÃºbrica de evaluaciÃ³n

| DimensiÃ³n | Peso | 3 - Logro destacado | 2 - Logro | 1 - Proceso | 0 - Inicio | PuntuaciÃ³n obtenida |
|---|---:|---|---|---|---|---:|
| 1. GUI funcional | 2 | GUI completa, clara y conectada al flujo principal. | GUI principal funcional. | GUI parcial o inestable. | No ejecuta GUI. | |
| 2. Capas y responsabilidades | 2 | `controller`, `service`, `entity` y `dao` bien separados. | SeparaciÃ³n suficiente. | Mezclas importantes. | No separa. | |
| 3. Persistencia y relaciones | 2 | CRUD simple, detalle y relaciones persistentes funcionando. | Persistencia principal funcional. | Persistencia incompleta. | No persiste. | |
| 4. Seguridad y consultas | 2 | Login, usuario asociado y consultas integradas funcionando. | Funcionalidad principal presente. | Funcionalidad parcial. | No evidencia. | |
| 5. Evidencia individual | 1 | Evidencia clara, ordenada y verificable. | Evidencia suficiente. | Evidencia incompleta. | No entrega. | |
| 6. Defensa tÃ©cnica | 1 | Responde con precisiÃ³n y criterio. | Responde adecuadamente. | Responde parcialmente. | No sustenta. | |
