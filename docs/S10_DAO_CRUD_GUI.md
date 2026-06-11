# S10 - Patrón DAO y operaciones CRUD persistentes desde GUI

## 1. Introducción

Tiempo: 20 min.

### 1.1 Propósito

Implementar el patrón DAO para ejecutar CRUD persistente desde la interfaz gráfica.

### 1.2 Resultado de aprendizaje

El estudiante separa el acceso a datos en DAO, mapea entidades a registros, ejecuta consultas SQL y agrega una implementación persistente del mismo contrato de servicio.

### 1.3 Producto de sesión

CRUD persistente funcional desde formularios y tablas JavaFX.

### 1.4 Motivación de la sesión

La GUI ya funciona con memoria y la base de datos ya existe. Ahora toca agregar una implementación persistente del servicio para coordinar DAO y SQLite sin cambiar las entidades ni poner SQL en el controlador.

Pregunta guía:

```text
¿Cómo guardamos y recuperamos datos desde la GUI sin mezclar SQL con la pantalla?
```

### 1.5 Ubicación en el curso

- Unidad: U2.
- Avance de sesión: integración de GUI con persistencia.

## 2. Explica

Tiempo: 25 min.

### 2.1 Conceptos clave

- Patrón DAO.
- Servicio como coordinador entre controlador y DAO.
- Implementación persistente del contrato de operaciones CRUD.
- Mapeo objeto-relacional básico.
- `insert`, `select`, `update`, `delete`.
- Confirmación de eliminación.
- Excepciones de persistencia.
- Refresco de `TableView` desde base de datos.

### 2.2 Arquitectura de la sesión

```mermaid
flowchart TB
    Vista["Vista JavaFX"]
    Controlador["Controlador"]
    Contrato["Interface<br/>contrato de operaciones CRUD"]
    Servicio["Implementación persistente<br/>implements"]
    Entidades["Entidades"]
    DAO["DAO"]
    SQLite[("SQLite / comarket.db")]

    Vista --> Controlador
    Controlador --> Contrato
    Servicio -. implements .-> Contrato
    Servicio -.-> Entidades
    Servicio --> DAO
    DAO --> Entidades
    DAO -->|"JDBC"| SQLite
```

## 3. Aplica: actividad práctica guiada

Tiempo: 2h.

1. Crear una interfaz DAO o clase DAO.
2. Implementar `registrar`.
3. Implementar `listar`.
4. Implementar `actualizar`.
5. Implementar `eliminar`.
6. Cargar la tabla desde la base de datos.
7. Conectar botones de la GUI con el contrato del servicio, no directamente con SQL.
8. Crear o adaptar una implementación persistente del servicio.
9. Hacer que la implementación persistente use DAO para guardar y consultar.
10. Confirmar eliminación y manejar errores básicos.

## 4. Crea: actividad autónoma

Tiempo: 2h fuera del aula.

Completa el CRUD persistente para una entidad adicional o mejora el módulo principal.

Entrega evidencia breve con:

- Código de la interface del servicio, implementación persistente y DAO.
- Capturas de GUI.
- Registros persistidos en SQLite.
- Explicación del flujo Vista-Controlador-Servicio-Entidades-DAO.

## 5. Cierre evaluativo

Tiempo: 20 min.

### 5.1 Resultados esperados

- El DAO concentra las consultas SQL.
- El controlador no contiene SQL directo.
- El servicio coordina operaciones, validaciones y DAO.
- Las entidades se mantienen como las mismas clases del dominio.
- La GUI registra, lista, actualiza y elimina datos persistentes.
- La tabla se recarga desde SQLite.

### 5.2 Preguntas de defensa

1. ¿Qué responsabilidad tiene el DAO?
2. ¿Qué responsabilidad tiene la interface del servicio?
3. ¿Qué responsabilidad tiene la implementación persistente?
4. ¿Por qué no poner SQL en el controlador?
5. ¿Cómo conviertes un registro en objeto?
6. ¿Cómo verificas que el dato quedó guardado?
