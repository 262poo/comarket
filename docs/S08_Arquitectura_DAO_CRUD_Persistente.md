# S8 - Arquitectura por capas, DAO y CRUD persistente desde GUI

## 1. IntroducciÃ³n

Tiempo: 20 min.

### 1.1 PropÃ³sito

Implementar una primera versiÃ³n persistente de la aplicaciÃ³n de escritorio usando arquitectura por capas, patrÃ³n DAO, JDBC, SQLite y CRUD desde JavaFX.

### 1.2 Resultado de aprendizaje

El estudiante separa vista, controlador, servicio, entidades y persistencia; crea un DAO para una tabla simple y conecta el CRUD persistente con la interfaz grÃ¡fica.

### 1.3 Producto de sesiÃ³n

CRUD persistente de una entidad simple desde GUI, usando `ProductoServiceImplDB`, `ProductoDAO`, SQLite y validaciones bÃ¡sicas.

### 1.4 MotivaciÃ³n de la sesiÃ³n

En S7 el CRUD funcionÃ³ en memoria. En esta sesiÃ³n se reemplaza la implementaciÃ³n en memoria por una implementaciÃ³n persistente sin cambiar la responsabilidad del controlador ni de la entidad.

Pregunta guÃ­a:

```text
CÃ³mo guardamos datos desde la GUI sin poner SQL en el controlador?
```

### 1.5 UbicaciÃ³n en el curso

- Unidad: U2.
- Carpeta de trabajo: `comarket-desk`.
- Avance de sesiÃ³n: primera persistencia real con DAO y una tabla simple.

## 2. Explica

Tiempo: 25 min.

### 2.1 Conceptos clave

- Arquitectura por capas.
- Vista FXML y controlador JavaFX.
- Servicio como contrato de operaciones.
- ImplementaciÃ³n persistente del servicio.
- PatrÃ³n DAO.
- JDBC como conector.
- SQLite como base de datos local.
- Validaciones bÃ¡sicas y manejo inicial de errores.

Regla metodolÃ³gica de la sesiÃ³n:

```text
El controlador no escribe SQL.
El controlador llama al contrato del servicio.
La implementaciÃ³n persistente coordina validaciones y DAO.
El DAO ejecuta SQL y mapea filas a objetos.
JDBC conecta Java con SQLite.
La entidad sigue siendo clase del dominio.
No se usa JPA ni ORM.
La conexiÃ³n a la base de datos se centraliza en util/ConexionBD.
`dao` cumple el rol de capa de acceso a datos en este curso.
```

### 2.2 Arquitectura de la sesiÃ³n

```mermaid
classDiagram
    class ProductoController {
        onRegistrar()
        onActualizar()
        onEliminar()
        cargarTabla()
    }

    class ProductoService {
        <<interface>>
        registrar(producto)
        listar()
        actualizar(producto)
        eliminar(codigo)
    }

    class ProductoServiceImplDB {
        -dao: ProductoDAO
        CRUD con DAO
    }

    class ProductoDAO {
        insertar(producto)
        listar()
        actualizar(producto)
        eliminar(codigo)
    }

    class ConexionBD {
        obtenerConexion()
    }

    class SQLite {
        producto
    }

    class Producto {
        -codigo
        -nombre
        -precio
        -stock
    }

    ProductoController ..> ProductoService : usa contrato
    ProductoController ..> Producto : crea/lee
    ProductoService <|.. ProductoServiceImplDB : implements
    ProductoService ..> Producto : usa
    ProductoServiceImplDB ..> Producto : usa
    ProductoServiceImplDB --> ProductoDAO : usa
    ProductoDAO ..> Producto : mapea
    ProductoDAO --> ConexionBD : usa
    ConexionBD --> SQLite : JDBC

    classDef serviceImpl fill:#dbeafe,stroke:#2563eb,stroke-width:2px,color:#1e3a8a;
    class ProductoServiceImplDB serviceImpl;
```

## 3. Aplica: actividad prÃ¡ctica guiada

Tiempo: 2h.

1. Revisar el proyecto JavaFX/Maven ubicado en `comarket-desk`.
2. Agregar dependencia SQLite JDBC.
3. Crear o verificar las carpetas de capas.
4. Crear `ConexionBD` dentro de `util`.
5. Crear tabla `producto`.
6. Crear `ProductoDAO` dentro de `dao`.
7. Implementar `insert`, `select`, `update` y `delete`.
8. Crear `ProductoServiceImplDB implements ProductoService`.
9. Hacer que `ProductoController` use `ProductoService`, no el DAO directamente.
10. Cargar `TableView` desde SQLite.
11. Validar campos obligatorios, precio y stock.
12. Mostrar mensajes de error claros.

Estructura sugerida:

```text
src/main/java/
    app/
        ProductoApplication.java
    controller/
        ProductoController.java
    entity/
        Producto.java
    exception/
        ValidacionException.java
        PersistenciaException.java
    dao/
        ProductoDAO.java
    service/
        ProductoService.java
        ProductoServiceImplDB.java
    util/
        ConexionBD.java
src/main/resources/
    view/
        ProductoView.fxml
```

Nota metodolÃ³gica:

```text
`dao` se usa como carpeta para las clases DAO.
util contiene clases tÃ©cnicas compartidas, como ConexionBD.
No se agrega mapper, dto ni filter todavÃ­a porque no aportan al nivel de esta sesiÃ³n.
```

Tabla mÃ­nima:

```sql
CREATE TABLE producto (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    codigo TEXT NOT NULL UNIQUE,
    nombre TEXT NOT NULL,
    precio REAL NOT NULL,
    stock INTEGER NOT NULL
);
```

## 4. Crea: actividad autÃ³noma

Fuera del aula, cada estudiante consolida el CRUD persistente y prepara una evidencia individual.

Tiempo: 2h fuera del aula.

### 4.1 Plantilla de evidencia individual

Entrega un PDF con el siguiente nombre:

```text
S08_Equipo##_ApellidoNombre.pdf
```

#### 4.1.1 Datos del estudiante

- Nombre:
- Equipo:
- SesiÃ³n: S08 - Arquitectura por capas, DAO y CRUD persistente desde GUI
- Rol o aporte realizado:
- Link de GitHub:

#### 4.1.2 Trabajo autÃ³nomo realizado

1. Completar CRUD persistente de producto.
2. Evidenciar estructura por capas.
3. Mostrar `ProductoDAO`.
4. Mostrar `ProductoServiceImplDB`.
5. Ejecutar la GUI y registrar datos.
6. Verificar registros en SQLite.
7. Documentar una validaciÃ³n aplicada.

#### 4.1.3 Evidencia tÃ©cnica

- Captura de la GUI.
- CÃ³digo o fragmento de DAO.
- CÃ³digo o fragmento de servicio persistente.
- Captura de registros en SQLite.
- Evidencia de validaciÃ³n o mensaje de error.
- ExplicaciÃ³n del flujo `Vista -> Controlador -> Servicio -> DAO -> SQLite`.

#### 4.1.4 Error o hallazgo

Describe un problema tÃ©cnico y cÃ³mo lo corregiste.

#### 4.1.5 ReflexiÃ³n tÃ©cnica breve

Responde en 5 a 8 lÃ­neas:

```text
Por quÃ© el controlador no debe ejecutar SQL directamente?
```

### 4.2 Criterios mÃ­nimos de aceptaciÃ³n

- PDF con nombre correcto.
- CRUD persistente funcional desde GUI.
- DAO separado del controlador.
- Servicio persistente usando el DAO.
- SQLite con datos verificables.
- Validaciones bÃ¡sicas evidenciadas.

## 5. Cierre evaluativo

Tiempo: 20 min.

### 5.1 Resultados esperados

- El estudiante explica las capas principales.
- El controlador delega al servicio.
- El servicio persistente usa DAO.
- El DAO concentra SQL.
- La tabla se refresca desde SQLite.
- Hay validaciones bÃ¡sicas al registrar o actualizar.

### 5.2 Evidencia del producto de sesiÃ³n

Cada estudiante entrega un PDF individual siguiendo la plantilla de la secciÃ³n 4.1.

### 5.3 Preguntas de defensa y reflexiÃ³n

1. QuÃ© responsabilidad tiene el controlador?
2. QuÃ© responsabilidad tiene el servicio?
3. QuÃ© responsabilidad tiene el DAO?
4. CÃ³mo se conecta Java con SQLite?
5. QuÃ© validaciÃ³n implementaste?
6. CÃ³mo verificas que el dato quedÃ³ persistido?

### 5.4 RÃºbrica de evaluaciÃ³n

| DimensiÃ³n | Peso | 3 - Logro destacado | 2 - Logro | 1 - Proceso | 0 - Inicio | PuntuaciÃ³n obtenida |
|---|---:|---|---|---|---|---:|
| 1. Capas | 2 | Vista, controlador, servicio, entidad y DAO claramente separados. | Capas funcionales. | SeparaciÃ³n parcial. | No separa capas. | |
| 2. DAO | 2 | DAO ejecuta SQL y mapea objetos correctamente. | DAO funcional. | DAO incompleto. | No evidencia DAO. | |
| 3. CRUD persistente | 2 | CRUD completo desde GUI y verificado en SQLite. | CRUD principal funcional. | CRUD parcial. | No persiste. | |
| 4. Validaciones | 2 | Valida datos y muestra mensajes claros. | Validaciones bÃ¡sicas. | ValidaciÃ³n parcial. | No valida. | |
| 5. Error o hallazgo | 1 | Analiza causa y soluciÃ³n. | Explica un problema. | Menciona un problema. | No presenta. | |
| 6. Orden y reflexiÃ³n | 1 | Evidencia clara y reflexiÃ³n precisa. | Evidencia suficiente. | Evidencia incompleta. | No sustenta. | |
