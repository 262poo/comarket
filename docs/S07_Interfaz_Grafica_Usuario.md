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

## 3. Aplica: actividad práctica guiada

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

Fuera del aula, cada estudiante consolida el aprendizaje diseñando una vista JavaFX propia y preparando una evidencia individual.

Tiempo: 2h fuera del aula.

### 4.1 Plantilla de evidencia individual

Entrega un PDF con el siguiente nombre:

```text
S07_Equipo##_ApellidoNombre.pdf
```

Ejemplo:

```text
S07_Equipo03_QuispeAna.pdf
```

El PDF debe usar esta estructura. La primera sección define el trabajo autónomo; completa las demás con tus evidencias.

#### 4.1.1 Datos del estudiante

- Nombre:
- Equipo:
- Sesión: S07 - Interfaz gráfica de usuario
- Rol o aporte realizado:
- Link de GitHub:

#### 4.1.2 Trabajo autónomo realizado

Completa y evidencia estas tareas:

1. Diseñar una vista inicial para una entidad del dominio.
2. Crear controles con `fx:id`.
3. Conectar al menos un botón con un método del controlador.
4. Leer datos escritos en la vista desde el controlador.
5. Ejecutar la aplicación JavaFX.
6. Explicar qué responsabilidad tiene FXML y qué responsabilidad tiene el controlador.

#### 4.1.3 Evidencia técnica

Incluye capturas o salidas con una breve explicación debajo de cada una:

- Captura de Scene Builder.
- Captura de la aplicación ejecutando.
- Código del controlador.
- Explicación de un evento conectado desde FXML.
- Fragmento FXML con `fx:id` y `onAction`.

#### 4.1.4 Error o hallazgo

Describe al menos un error, diferencia o hallazgo técnico:

- Qué ocurrió.
- Cómo lo diagnosticaste.
- Cómo lo corregiste o qué aprendiste.

Ejemplos válidos:

- El `fx:id` no coincidía con el atributo del controlador.
- El método `onAction` no existía o tenía mal nombre.
- La vista no cargaba por error de ruta FXML.
- El controlador no estaba declarado en el FXML.

#### 4.1.5 Reflexión técnica breve

Responde en 5 a 8 líneas:

```text
Por qué FXML y Controller deben tener responsabilidades diferentes?
```

### 4.2 Criterios mínimos de aceptación

La evidencia individual se considera completa si:

- El archivo respeta el nombre `S07_Equipo##_ApellidoNombre.pdf`.
- Incluye evidencias técnicas legibles.
- Muestra la vista en Scene Builder.
- Muestra la aplicación ejecutando.
- Muestra controles conectados con `fx:id`.
- Muestra al menos un evento funcionando.
- Explica la separación entre vista y controlador.
- No contiene solo pantallazos: cada evidencia tiene una descripción breve.

## 5. Cierre evaluativo

Tiempo: 20 min.

Esta sección conecta el resultado de aprendizaje de la sesión con el producto que debe evidenciar cada estudiante.

### 5.1 Resultados esperados

Al finalizar la sesión, el estudiante debe demostrar que:

- Proyecto JavaFX ejecuta correctamente.
- La vista FXML abre sin errores.
- Los controles tienen `fx:id` cuando corresponde.
- El controlador recibe eventos.
- La GUI queda lista para conectarse al servicio en S8.

### 5.2 Evidencia del producto de sesión

Cada estudiante entrega un PDF individual siguiendo la plantilla de la sección 4.1.

Nombre del archivo:

```text
S07_Equipo##_ApellidoNombre.pdf
```

La evidencia debe demostrar:

- Producto de sesión construido.
- Aporte individual verificable.
- Vista y controlador conectados.
- Reflexión técnica breve.

La revisión se realiza con los criterios mínimos de aceptación de la sección 4.2 y la rúbrica de la sección 5.4.

### 5.3 Preguntas de defensa y reflexión

1. Qué función cumple FXML?
2. Qué función cumple Scene Builder?
3. Qué función cumple el controlador?
4. Cómo se conecta un botón con un método Java?
5. Por qué el controlador no debe contener todo el CRUD?
6. Qué error tuviste al conectar la vista con el controlador?

### 5.4 Rúbrica de evaluación

| Dimensión | Peso | 3 - Logro destacado | 2 - Logro | 1 - Proceso | 0 - Inicio | Puntuación obtenida |
|---|---:|---|---|---|---|---:|
| 1. Vista FXML | 2 | Vista clara, controles adecuados y estructura coherente. | Vista funcional. | Vista incompleta. | No evidencia vista. | |
| 2. Conexión con controlador | 2 | `fx:id` y eventos conectados correctamente. | Conexión principal funcional. | Conexión parcial. | No conecta controlador. | |
| 3. Ejecución JavaFX | 2 | Aplicación ejecuta y demuestra interacción. | Aplicación abre correctamente. | Ejecución parcial o inestable. | No ejecuta. | |
| 4. Separación de responsabilidades | 2 | Explica con claridad vista, controlador y entidad. | Explicación suficiente. | Explicación confusa. | No explica responsabilidades. | |
| 5. Error o hallazgo | 1 | Analiza error/hallazgo, causa, solución y aprendizaje técnico. | Explica un problema y una solución. | Menciona un problema sin análisis. | No presenta error ni hallazgo. | |
| 6. Reflexión y orden | 1 | PDF ordenado, evidencias legibles y reflexión precisa. | Evidencias suficientes y reflexión clara. | Evidencias incompletas o reflexión superficial. | PDF desordenado o sin reflexión. | |

Puntuación acumulada = suma de (`Peso` * `Puntuación obtenida`) = ____.

Nota final = (`Puntuación acumulada` / 30) * 20 = ____.

Para usar la rúbrica con IA, solicita:

```text
Evalúa el PDF usando la rúbrica de la sesión.
Para cada dimensión selecciona la puntuación obtenida usando la escala Inicio=0, Proceso=1, Logro=2, Logro destacado=3.
Justifica brevemente cada puntuación.
Calcula la puntuación acumulada con la fórmula: suma de (Peso * Puntuación obtenida).
Calcula la nota final sobre 20 con la fórmula: (Puntuación acumulada / 30) * 20.
Indica 2 fortalezas y 2 recomendaciones.
```

