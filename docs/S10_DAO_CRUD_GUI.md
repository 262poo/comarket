# S10 - Patron DAO y operaciones CRUD persistentes desde GUI

## 1. Introduccion

Tiempo: 20 min.

### 1.1 Proposito

Implementar el patron DAO para ejecutar operaciones CRUD persistentes desde la interfaz grafica.

### 1.2 Resultado de aprendizaje

El estudiante separa el acceso a datos en DAO, mapea entidades a registros, ejecuta SQL basico y agrega una implementacion persistente del mismo contrato de servicio.

### 1.3 Producto de sesion

CRUD persistente funcional desde formularios y tablas JavaFX.

### 1.4 Motivacion de la sesion

La GUI ya funciona con memoria y la base de datos ya existe. Ahora corresponde guardar y recuperar datos desde SQLite sin poner SQL en el controlador.

Pregunta guia:

```text
Como guardamos y recuperamos datos desde la GUI sin mezclar SQL con la pantalla?
```

### 1.5 Ubicacion en el curso

- Unidad: U2.
- Avance de sesion: integracion de GUI con persistencia.

## 2. Explica

Tiempo: 25 min.

### 2.1 Conceptos clave

- Patron DAO.
- Servicio como coordinador entre controlador y DAO.
- Implementacion persistente del contrato CRUD.
- Mapeo objeto-relacional basico.
- `insert`, `select`, `update`, `delete`.
- Confirmacion de eliminacion.
- Excepciones de persistencia.
- Refresco de `TableView` desde base de datos.

Regla metodologica de la sesion:

```text
El controlador no escribe SQL.
El servicio valida y coordina.
El DAO ejecuta SQL.
JDBC conecta con SQLite.
La entidad sigue siendo una clase del dominio.
```

### 2.2 Arquitectura de la sesion

```mermaid
flowchart TB
    Vista["Vista JavaFX"]
    Controlador["Controlador"]

    subgraph Servicio["Servicio"]
        Contrato["ClienteService<br/>&lt;&lt;interface&gt;&gt;"]
        ServicioBD["ClienteServiceBD<br/>implements"]
        Validaciones["Validaciones/Excepciones"]
    end

    Entidades["Cliente"]

    subgraph Persistencia["Persistencia"]
        DAO["ClienteDAO"]
        SQLite[("SQLite / comarket.db")]
    end

    Vista --> Controlador
    Controlador --> Contrato
    ServicioBD -. implements .-> Contrato
    Contrato -.-> Entidades
    ServicioBD -.-> Entidades
    ServicioBD -.-> Validaciones
    ServicioBD --> DAO
    DAO --> Entidades
    DAO -->|"JDBC"| SQLite
```

## 3. Aplica: actividad practica guiada

Tiempo: 2h.

1. Crear `ClienteDAO`.
2. Implementar `registrar` con `insert`.
3. Implementar `listar` con `select`.
4. Implementar `actualizar` con `update`.
5. Implementar `eliminar` con `delete`.
6. Mapear cada fila de la base de datos a un objeto `Cliente`.
7. Crear o adaptar `ClienteServiceBD`.
8. Hacer que `ClienteServiceBD` use `ClienteDAO`.
9. Conectar botones de la GUI con `ClienteService`, no directamente con SQL.
10. Recargar la tabla desde la base de datos despues de cada operacion.
11. Confirmar eliminacion y manejar errores basicos.

## 4. Crea: actividad autonoma

Tiempo: 2h fuera del aula.

Completa el CRUD persistente para una entidad adicional o mejora el modulo principal.

Entrega evidencia breve con:

- Codigo de `ClienteService`, `ClienteServiceBD` y `ClienteDAO`.
- Capturas de GUI.
- Registros persistidos en SQLite.
- Explicacion del flujo Vista-Controlador-Servicio-Entidades-DAO.

## 5. Cierre evaluativo

Tiempo: 20 min.

### 5.1 Resultados esperados

- El DAO concentra las consultas SQL.
- El controlador no contiene SQL directo.
- El servicio coordina operaciones, validaciones y DAO.
- Las entidades se mantienen como clases del dominio.
- La GUI registra, lista, actualiza y elimina datos persistentes.

### 5.2 Preguntas de defensa

1. Que responsabilidad tiene el DAO?
2. Que responsabilidad tiene la interface del servicio?
3. Que responsabilidad tiene la implementacion persistente?
4. Por que no poner SQL en el controlador?
5. Como conviertes un registro en objeto?
6. Como verificas que el dato quedo guardado?
