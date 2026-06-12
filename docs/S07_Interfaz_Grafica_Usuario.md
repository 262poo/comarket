# S7 - Interfaz grafica de usuario

## 1. Introduccion

Tiempo: 20 min.

### 1.1 Proposito

Iniciar la aplicacion de escritorio con JavaFX, FXML, Scene Builder y controladores, manteniendo la separacion entre vista y codigo Java.

### 1.2 Resultado de aprendizaje

El estudiante crea una ventana JavaFX, disena una vista FXML, conecta controles con un controlador y atiende eventos basicos.

### 1.3 Producto de sesion

Proyecto JavaFX con Maven, vista FXML, controlador y formulario inicial.

### 1.4 Motivacion de la sesion

El producto deja la consola y empieza a operar como aplicacion de escritorio. La GUI no reemplaza la POO construida en U1; solo cambia la forma de interactuar con el usuario.

Pregunta guia:

```text
Como conectamos una pantalla JavaFX con codigo Java sin mezclarlo todo?
```

### 1.5 Ubicacion en el curso

- Unidad: U2 - Aplicacion de escritorio con persistencia de datos.
- Avance de sesion: base visual del producto.

## 2. Explica

Tiempo: 25 min.

### 2.1 Conceptos clave

| Concepto | Idea central |
|---|---|
| JavaFX | Libreria para construir interfaces graficas en Java. |
| FXML | Archivo que describe la estructura visual de la pantalla. |
| Scene Builder | Herramienta visual para editar FXML. |
| Controller | Clase Java que recibe eventos de la vista. |
| `fx:id` | Nombre que permite conectar un control FXML con Java. |
| Evento | Accion del usuario, como presionar un boton. |
| `TableView` | Control para mostrar listas de objetos. |

Regla metodologica de la sesion:

```text
La vista muestra controles.
El controlador atiende eventos.
Las entidades siguen representando el dominio.
El CRUD completo se conecta en la siguiente sesion.
```

### 2.2 Arquitectura de la sesion

```mermaid
classDiagram
    class MainApp {
        start(stage)
    }
    class VistaFXML {
        TextField
        Button
        TableView
    }
    class ClienteController {
        initialize()
        onRegistrar()
        onLimpiar()
    }
    class Cliente {
        -nombre
        -documento
        -telefono
    }

    MainApp ..> VistaFXML : carga FXML
    VistaFXML ..> ClienteController : fx:controller
    ClienteController ..> Cliente : crea objeto
```

## 3. Aplica: actividad practica guiada

Tiempo: 2h.

### 3.1 Crear proyecto JavaFX con Maven

Usa el IDE que soporte JavaFX y Scene Builder. Para U2 se recomienda IntelliJ IDEA o un entorno equivalente con Maven configurado.

### 3.2 Crear vista FXML

Controles minimos:

- `TextField` para nombre.
- `TextField` para documento.
- `TextField` para telefono.
- `Button` para registrar.
- `Button` para limpiar.
- `TableView` como preparacion para S8.

### 3.3 Conectar controles con `fx:id`

Cada control que el controlador necesita manipular debe tener `fx:id`.

Ejemplo:

```xml
<TextField fx:id="txtNombre" />
<Button text="Registrar" onAction="#onRegistrar" />
```

### 3.4 Crear controlador

```java
public class ClienteController {
    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtDocumento;

    @FXML
    private TextField txtTelefono;

    @FXML
    private void onRegistrar() {
        System.out.println(txtNombre.getText());
    }
}
```

### 3.5 Probar eventos

La prueba de esta sesion no busca guardar datos todavia. Busca comprobar que:

- La ventana abre.
- El FXML carga.
- El boton ejecuta el metodo del controlador.
- Los textos escritos se pueden leer desde Java.

## 4. Crea: actividad autonoma

Tiempo: 2h fuera del aula.

Disena una vista inicial para una entidad del dominio.

Entrega evidencia breve con:

- Captura de Scene Builder.
- Captura de la aplicacion ejecutando.
- Codigo del controlador.
- Explicacion de un evento conectado desde FXML.

## 5. Cierre evaluativo

Tiempo: 20 min.

### 5.1 Resultados esperados

- Proyecto JavaFX ejecuta correctamente.
- La vista FXML abre sin errores.
- Los controles tienen `fx:id` cuando corresponde.
- El controlador recibe eventos.
- La GUI queda lista para conectarse al servicio en S8.

### 5.2 Preguntas de defensa

1. Que funcion cumple FXML?
2. Que funcion cumple Scene Builder?
3. Que funcion cumple el controlador?
4. Como se conecta un boton con un metodo Java?
5. Por que el controlador no debe contener todo el CRUD?
