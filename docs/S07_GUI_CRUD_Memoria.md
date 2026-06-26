# S7 - Interfaz grÃ¡fica y CRUD desde GUI en memoria

## 1. IntroducciÃ³n

Tiempo: 20 min.

### 1.1 PropÃ³sito

Iniciar una aplicaciÃ³n de escritorio con JavaFX, FXML, Scene Builder y controladores, conectando un CRUD en memoria desde la GUI.

### 1.2 Resultado de aprendizaje

El estudiante crea una ventana JavaFX, diseÃ±a una vista FXML, conecta controles con un controlador y ejecuta operaciones CRUD en memoria usando el servicio trabajado en U1.

### 1.3 Producto de sesiÃ³n

Proyecto JavaFX con Maven, vista FXML, controlador, formulario, tabla y CRUD en memoria de una entidad simple.

### 1.4 MotivaciÃ³n de la sesiÃ³n

La aplicaciÃ³n deja la consola y empieza a operar como aplicaciÃ³n de escritorio. La GUI no reemplaza la POO construida en U1; reutiliza el contrato de servicio y la implementaciÃ³n en memoria desde una pantalla.

Pregunta guÃ­a:

```text
CÃ³mo conectamos una pantalla JavaFX con el servicio en memoria sin duplicar el CRUD en el controlador?
```

### 1.5 UbicaciÃ³n en el curso

- Unidad: U2.
- Carpeta de trabajo: `comarket-desk`.
- Avance de sesiÃ³n: transiciÃ³n de consola a GUI con CRUD en memoria.

## 2. Explica

Tiempo: 25 min.

### 2.1 Conceptos clave

| Concepto | Idea central |
|---|---|
| JavaFX | LibrerÃ­a para construir interfaces grÃ¡ficas en Java. |
| FXML | Archivo quÃ© describe la estructura visual de la pantalla. |
| Scene Builder | Herramienta visual para editar FXML. |
| Controller | Clase Java quÃ© recibe eventos de la vista. |
| `fx:id` | Nombre quÃ© permite conectar un control FXML con Java. |
| `TableView` | Control para mostrar listas de objetos. |
| Servicio en memoria | ImplementaciÃ³n que conserva datos temporalmente durante la ejecuciÃ³n. |

Regla metodolÃ³gica de la sesiÃ³n:

```text
La vista muestra controles.
El controlador atiende eventos.
El controlador delega operaciones al contrato del servicio.
La implementaciÃ³n en memoria conserva los datos durante la ejecuciÃ³n.
El ArrayList no va en el controlador; vive dentro de la implementaciÃ³n en memoria.
La persistencia con DAO y SQLite se trabaja en S8.
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

    class ProductoServiceImplMemoria {
        -productos: ArrayList
        CRUD sobre ArrayList
    }

    class Producto {
        -codigo
        -nombre
        -precio
        -stock
    }

    ProductoController ..> ProductoService : usa contrato
    ProductoController ..> Producto : crea/lee
    ProductoService <|.. ProductoServiceImplMemoria : implements
    ProductoService ..> Producto : usa
    ProductoServiceImplMemoria ..> Producto : usa

    classDef serviceImpl fill:#dbeafe,stroke:#2563eb,stroke-width:2px,color:#1e3a8a;
    class ProductoServiceImplMemoria serviceImpl;
```

## 3. Aplica: actividad prÃ¡ctica guiada

Tiempo: 2h.

### 3.1 Crear proyecto JavaFX con Maven

Usa un IDE que soporte JavaFX, Maven y Scene Builder. Para U2 se recomienda IntelliJ IDEA o un entorno equivalente con Maven configurado. En este repositorio el proyecto de escritorio se trabaja en `comarket-desk`.

Producto del paso: proyecto JavaFX/Maven creado o verificado en `comarket-desk`, con estructura inicial, carpetas base y clase `Main` preparada para cargar una vista FXML.

Estructura base:

```text
src/main/java/
    app/
        ProductoApplication.java
    controller/
        ProductoController.java
    entity/
        Producto.java
    service/
        ProductoService.java
        ProductoServiceImplMemoria.java

src/main/resources/
    view/
        ProductoView.fxml
```

La persistencia (`repository`, `util`, SQLite) se trabajarÃ¡ en S8. En S7 el foco es abrir una ventana, cargar FXML, conectar eventos y ejecutar CRUD en memoria desde la GUI.

### 3.2 Crear vista FXML

Controles mÃ­nimos:

- `TextField` para cÃ³digo.
- `TextField` para nombre.
- `TextField` para precio.
- `TextField` para stock.
- `Button` para registrar.
- `Button` para actualizar.
- `Button` para eliminar.
- `Button` para limpiar.
- `TableView` para listar productos.

### 3.3 Conectar controles con `fx:id`

Cada control que el controlador necesita manipular debe tener `fx:id`.

Ejemplo:

```xml
<TextField fx:id="txtNombre" />
<Button text="Registrar" onAction="#onRegistrar" />
```

### 3.4 Crear controlador

```java
public class ProductoController {
    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtStock;

    private ProductoService productoService = new ProductoServiceImplMemoria();

    @FXML
    private void onRegistrar() {
        // Leer formulario, crear Producto y delegar al servicio.
    }
}
```

### 3.5 Conectar CRUD en memoria

Flujo mÃ­nimo:

1. Leer datos del formulario.
2. Crear un objeto `Producto`.
3. Llamar a `productoService.registrar(producto)`.
4. Refrescar la tabla con `productoService.listar()`.
5. Cargar el producto seleccionado al formulario.
6. Actualizar usando el servicio.
7. Eliminar con confirmaciÃ³n.

### 3.6 Probar eventos y validaciones

La prueba de esta sesiÃ³n busca comprobar que:

- La ventana abre.
- El FXML carga.
- El botÃ³n ejecuta el mÃ©todo del controlador.
- El controlador delega el CRUD al servicio.
- La tabla se refresca despuÃ©s de registrar, editar o eliminar.
- Se validan campos obligatorios, precio y stock.

## 4. Crea: actividad autÃ³noma

Fuera del aula, cada estudiante consolida el CRUD desde GUI en memoria y prepara una evidencia individual.

Tiempo: 2h fuera del aula.

### 4.1 Plantilla de evidencia individual

Entrega un PDF con el siguiente nombre:

```text
S07_Equipo##_ApellidoNombre.pdf
```

#### 4.1.1 Datos del estudiante

- Nombre:
- Equipo:
- SesiÃ³n: S07 - Interfaz grÃ¡fica y CRUD desde GUI en memoria
- Rol o aporte realizado:
- Link de GitHub:

#### 4.1.2 Trabajo autÃ³nomo realizado

1. DiseÃ±ar una vista inicial para una entidad del dominio.
2. Crear controles con `fx:id`.
3. Conectar botones con mÃ©todos del controlador.
4. Registrar, listar, actualizar y eliminar desde GUI.
5. Usar el contrato de servicio.
6. Mantener el `ArrayList` dentro de la implementaciÃ³n en memoria.
7. Validar campos obligatorios y datos numÃ©ricos.
8. Explicar quÃ© responsabilidad tiene FXML, controlador y servicio.

#### 4.1.3 Evidencia tÃ©cnica

- Captura de Scene Builder.
- Captura de la aplicaciÃ³n ejecutando.
- CÃ³digo del controlador.
- CÃ³digo o referencia del servicio en memoria.
- Capturas de registrar, listar, actualizar y eliminar.
- Evidencia de validaciÃ³n.
- Fragmento FXML con `fx:id` y `onAction`.

#### 4.1.4 Error o hallazgo

Describe un error tÃ©cnico y cÃ³mo lo corregiste.

#### 4.1.5 ReflexiÃ³n tÃ©cnica breve

Responde en 5 a 8 lÃ­neas:

```text
Por quÃ© el controlador debe delegar el CRUD al servicio aunque la aplicaciÃ³n todavÃ­a use memoria?
```

### 4.2 Criterios mÃ­nimos de aceptaciÃ³n

- PDF con nombre correcto.
- Vista en Scene Builder.
- AplicaciÃ³n ejecutando.
- Registro, listado, ediciÃ³n y eliminaciÃ³n desde GUI.
- Servicio en memoria usado desde el controlador.
- Validaciones bÃ¡sicas.
- SeparaciÃ³n entre vista, controlador, servicio y entidad.

## 5. Cierre evaluativo

Tiempo: 20 min.

### 5.1 Resultados esperados

- Proyecto JavaFX ejecuta correctamente.
- La vista FXML abre sin errores.
- Los controles tienen `fx:id`.
- El controlador recibe eventos.
- El CRUD en memoria funciona desde la GUI.
- La GUI queda lista para reemplazar memoria por DAO y SQLite en S8.

### 5.2 Evidencia del producto de sesiÃ³n

Cada estudiante entrega un PDF individual siguiendo la plantilla de la secciÃ³n 4.1.

### 5.3 Preguntas de defensa y reflexiÃ³n

1. QuÃ© funciÃ³n cumple FXML?
2. QuÃ© funciÃ³n cumple Scene Builder?
3. QuÃ© funciÃ³n cumple el controlador?
4. QuÃ© responsabilidad tiene el servicio?
5. Por quÃ© el controlador no debe contener todo el CRUD?
6. DÃ³nde se almacena temporalmente la informaciÃ³n?
7. QuÃ© cambiarÃ¡ en S8 cuando se use DAO y SQLite?

### 5.4 RÃºbrica de evaluaciÃ³n

| DimensiÃ³n | Peso | 3 - Logro destacado | 2 - Logro | 1 - Proceso | 0 - Inicio | PuntuaciÃ³n obtenida |
|---|---:|---|---|---|---|---:|
| 1. Vista FXML | 2 | Vista clara, controles adecuados y estructura coherente. | Vista funcional. | Vista incompleta. | No evidencia vista. | |
| 2. ConexiÃ³n con controlador | 2 | `fx:id` y eventos conectados correctamente. | ConexiÃ³n principal funcional. | ConexiÃ³n parcial. | No conecta controlador. | |
| 3. CRUD en memoria | 2 | Registro, listado, ediciÃ³n y eliminaciÃ³n funcionan desde GUI. | CRUD principal funcional. | CRUD parcial. | No evidencia CRUD. | |
| 4. SeparaciÃ³n de responsabilidades | 2 | Explica vista, controlador, servicio y entidad. | ExplicaciÃ³n suficiente. | ExplicaciÃ³n confusa. | No explica responsabilidades. | |
| 5. Error o hallazgo | 1 | Analiza causa y soluciÃ³n. | Explica un problema. | Menciona un problema. | No presenta. | |
| 6. Orden y reflexiÃ³n | 1 | Evidencia clara y reflexiÃ³n precisa. | Evidencia suficiente. | Evidencia incompleta. | No sustenta. | |
