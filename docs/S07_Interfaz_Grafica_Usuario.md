# S7 - Interfaz gráfica de usuario

## 1. Introducción

Tiempo: 20 min.

### 1.1 Propósito

Iniciar la aplicación de escritorio con JavaFX, FXML, Scene Builder y controladores, manteniendo la separacion entre vista y código Java.

### 1.2 Resultado de aprendizaje

El estudiante crea una ventana JavaFX, diseña una vista FXML, conecta controles con un controlador y atiende eventos básicos.

### 1.3 Producto de sesión

Proyecto JavaFX con Maven, vista FXML, controlador y formulario inicial.

### 1.4 Motivación de la sesión

El producto deja la consola y empieza a operar como aplicación de escritorio. La GUI no reemplaza la POO construida en U1; solo cambia la forma de interactuar con el usuario.

Pregunta guía:

```text
Cómo conectamos una pantalla JavaFX con código Java sin mezclarlo todo?
```

### 1.5 Ubicación en el curso

- Unidad: U2 - Aplicación de escritorio con persistencia de datos.
- Avance de sesión: base visual del producto.

## 2. Explica

Tiempo: 25 min.

### 2.1 Conceptos clave

| Concepto | Idea central |
|---|---|
| JavaFX | Libreria para construir interfaces gráficas en Java. |
| FXML | Archivo qué describe la estructura visual de la pantalla. |
| Scene Builder | Herramienta visual para editar FXML. |
| Controller | Clase Java qué recibe eventos de la vista. |
| `fx:id` | Nombre qué permite conectar un control FXML con Java. |
| Evento | Accion del usuario, cómo presionar un boton. |
| `TableView` | Control para mostrar listas de objetos. |

Regla métodológica de la sesión:

```text
La vista muestra controles.
El controlador atiende eventos.
Las entidades siguen representando el dominio.
El CRUD completo se conecta en la siguiente sesión.
```

### 2.2 Arquitectura de la sesión

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

## 3. Aplica: actividad practica guíada

Tiempo: 2h.

### 3.1 Crear proyecto JavaFX con Maven

Usa el IDE qué soporte JavaFX y Scene Builder. Para U2 se recomienda IntelliJ IDEA o un entorno equivalente con Maven configurado.

### 3.2 Crear vista FXML

Controles mínimos:

- `TextField` para nombre.
- `TextField` para documento.
- `TextField` para telefono.
- `Button` para registrar.
- `Button` para limpiar.
- `TableView` cómo preparación para S8.

### 3.3 Conectar controles con `fx:id`

Cada control qué el controlador necesita manipular debe tener `fx:id`.

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

La prueba de esta sesión no busca guardar datos todavía. Busca comprobar que:

- La ventana abre.
- El FXML carga.
- El boton ejecuta el método del controlador.
- Los textos escritos se pueden leer desde Java.

## 4. Crea: actividad autónoma

Tiempo: 2h fuera del aula.

Diseña una vista inicial para una entidad del dominio.

Entrega evidencia breve con:

- Captura de Scene Builder.
- Captura de la aplicación ejecutando.
- Código del controlador.
- Explicacion de un evento conectado desde FXML.

## 5. Cierre evaluativo

Tiempo: 20 min.

### 5.1 Resultados esperados

- Proyecto JavaFX ejecuta correctamente.
- La vista FXML abre sin errores.
- Los controles tienen `fx:id` cuándo corresponde.
- El controlador recibe eventos.
- La GUI queda lista para conectarse al servicio en S8.

### 5.2 Preguntas de defensa

1. Qué función cumple FXML?
2. Qué función cumple Scene Builder?
3. Qué función cumple el controlador?
4. Cómo se conecta un boton con un método Java?
5. Por qué el controlador no debe contener todo el CRUD?
