# S1 - Clases, objetos y responsabilidad de clase

## 1. Introducción

Tiempo: 20 min.

### 1.1 Propósito

Iniciar una aplicación de consola mediante clases simples del dominio, objetos creados desde `Main` y pruebas por salida de texto.

### 1.2 Resultado de aprendizaje

El estudiante diferencia clase y objeto, define atributos y métodos, crea instancias y explica la responsabilidad básica de una clase del dominio.

### 1.3 Producto de sesión

Proyecto Java simple en VS Code con una primera clase del dominio, objetos instanciados desde `Main` y salida por consola.

### 1.4 Motivación de la sesión

#### 1.4.1 Caso: sistema de dominio inicial

Una organización necesita ordenar la información de un proceso de negocio. Puede tratarse de ventas, biblioteca, reservas, inventario, matriculas, atencion de clientes u otro contexto definido por el docente.

Antes de construir pantallas, base de datos o reportes, el sistema necesita representar objetos del dominio. En POO, esos objetos nacen a partir de clases.

Preguntas para los estudiantes:

1. Qué objetos reales aparecen en el dominio elegido?
2. Qué datos necesita guardar uno de esos objetos?
3. Qué comportamiento podría tener ese objeto?
4. Por qué no conviene escribir todo directamente en `Main`?

En esta sesión se inicia el proyecto creando el primer objeto del dominio y probandolo desde consola.

### 1.5 Ubicación en el curso

- Unidad: U1 - Fundamentos de la Programación Orientada a Objetos.
- Producto de unidad: aplicación de consola en memoria con entidades, relaciones, colecciones y CRUD.
- Avance del producto en esta sesión: primeras clases del dominio probadas desde `Main`.

Roadmap para elaborar el producto de la unidad:

```mermaid
flowchart TB
    S1["S1<br/>Coche, Persona y Producto<br/>clase y objetos"]
    S2["S2<br/>Producto encapsulado<br/>constructores y validaciones"]
    S3["S3<br/>Asociación, agregación/composición<br/>y colecciones"]
    S4["S4<br/>Producto, ProductoPerecible<br/>herencia y polimorfismo<br/>bloque aplicado"]
    S5["S5<br/>Servicio en memoria<br/>CRUD con ArrayList"]
    S6["S6<br/>Producto U1<br/>consola ejecutable"]

    S1 --> S2
    S2 --> S3
    S3 --> S5
    S3 -. refuerza modelo .-> S4
    S4 -. se aplica en .-> S5
    S5 --> S6

    classDef today fill:#ffe08a,stroke:#9a6b00,stroke-width:2px,color:#111;
    class S1 today;
```

Hoy se inicia con objetos tangibles del mundo real: `Coche` y `Persona`. Luego se trabaja `Producto` cómo segundo ejemplo preparatorio para S2. La ruta principal avanza hacia encapsulamiento, servicios con colecciones y CRUD en memoria. La herencia y el polimorfismo se trabajan cómo bloque aplicado entre S3 y S5: refuerzan el modelo y preparan el contrato de servicio, pero no deben sentirse cómo un adorno aislado.

## 2. Explica

Tiempo: 25 min.

### 2.1 Conceptos clave

Una clase es un molde para crear objetos. Un objeto es una instancia concreta qué tiene estado y comportamiento.

Ejemplo base: `Coche` y `Persona` permiten iniciar desde objetos tangibles. Un coche tiene marca y velocidad; una persona tiene nombre y puede conducir. Luego se usa `Producto` para observar cambios naturales de estado cómo precio y stock, preparando la S2.

Conceptos de la sesión:

- Clase cómo molde.
- Objeto cómo instancia.
- Atributos cómo estado.
- Métodos cómo comportamiento.
- Abstracción inicial del dominio.
- Responsabilidad de clase.
- `Main` cómo punto de prueba inicial.
- Salida por consola cómo evidencia de ejecución.

Alcance métodologico de S1:

```text
En S1 se llega hasta clase, objeto, atributos, métodos, estado,
comportamiento, abstracción inicial y responsabilidad de clase.

El constructor no se desarrolla como tema fuerte en esta sesión.
Se puede mencionar que existe, pero su uso formal queda para S2,
cuando se trabaje encapsulamiento y control del estado.
```

### 2.2 Arquitectura de la sesión

```mermaid
classDiagram
    class Main {
        main(String[] args)
    }
    class Coche {
        marca
        velocidad
        acelerar()
        frenar()
        mostrarEstado()
    }
    class Persona {
        nombre
        conducir(coche)
    }
    class Producto {
        codigo
        nombre
        precio
        stock
        mostrarInformacion()
        actualizarPrecio()
        aumentarStock()
    }

    Main ..> Coche : crea/prueba
    Main ..> Persona : crea/prueba
    Main ..> Producto : crea/prueba
    Persona ..> Coche : usa
```

Convencion del diagrama: cada clase muestra sus atributos y métodos principales; `..>` indica dependencia o uso temporal desde la prueba.

Regla practica:

- `Main` se usa para probar.
- La clase representa caracteristicas y acciones de un objeto del mundo real.
- Los objetos son instancias concretas de la clase.
- Los atributos guardan estado.
- Los métodos muestran o procesan comportamiento propio del objeto.
- La abstracción consiste en elegir solo los datos y comportamientos necesarios para está primera versión.

### 2.3 Flujo de trabajo

1. Preparar VS Code y Java.
2. Crear un proyecto Java simple.
3. Abstraer objetos tangibles del mundo real.
4. Definir la responsabilidad inicial de la clase.
5. Elegir atributos y métodos coherentes con sus caracteristicas y acciones.
6. Crear `Coche` y `Persona` para observar colaboracion simple.
7. Crear `Producto` cómo ejemplo puente hacia S2.
8. Ejecutar el programa por consola.
9. Registrar evidencia y explicar responsabilidades.

### 2.4 Errores frecuentes y diagnóstico

| Problema | Causa probable | Solución |
|---|---|---|
| No ejecuta `Main` | Falta método `public static void main` | Revisar firma del método |
| No reconoce la clase | Archivo, clase o paquete no coincide | Revisar nombre de archivo y paquete |
| Los datos salen en cero o `null` | No se asignaron valores al objeto | Inicializar atributos antes de imprimir |
| Todo está en `Main` | No se separo la responsabilidad | Mover datos y comportamiento a una clase |
| Salida poco clara | `Main` no imprime datos suficientes | Mejorar la salida desde `Main` sin meter consola en la entidad |
| La clase tiene métodos de muchas cosas | No se identificaron bien sus caracteristicas y acciones | Volver a la abstracción inicial del objeto |
| Se usan constructores o `private` antes de tiempo | Se adelanto contenido de S2 | En S1 usar clases simples; el control del estado queda para S2 |

## 3. Aplica: actividad práctica guiada

En el laboratorio, el docente guía la creacion del primer objeto del dominio y los estudiantes verifican el resultado ejecutando el programa desde VS Code.

Tiempo: 2h.

### 3.1 Preparar ambiente local: Java 17, Maven y VS Code

**Producto del paso:** ambiente local con Java 17, Maven y VS Code verificados, listo para crear y ejecutar clases Java desde consola.

Herramientas necesarias:

- Java 17.
- Maven 3.x.
- VS Code.
- Extension Pack for Java.
- Terminal integrada de VS Code.

En esta sesión se usa un proyecto Java simple. Maven se verifica desde el inicio porque sera necesario para organizar la entrega de la U1 en sesiónes posteriores.

#### 3.1.1 Instalar gestor de paquetes, si hace falta

Windows PowerShell, si no tienes Chocolatey:

```powershell
Set-ExecutionPolicy Bypass -Scope Process -Force; [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072; iex ((New-Object System.Net.WebClient).DownloadString('https://community.chocolatey.org/install.ps1'))
```

Luego cierra y vuelve a abrir PowerShell.

macOS bash/zsh, si no tienes Homebrew:

```bash
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
```

Luego cierra y vuelve a abrir Terminal.

#### 3.1.2 Instalar Java 17

Windows PowerShell con Chocolatey:

```powershell
choco install temurin17 -y
```

macOS bash/zsh con Homebrew:

```bash
brew install --cask temurin@17
```

Linux Debian/Ubuntu bash:

```bash
sudo apt update
sudo apt install -y openjdk-17-jdk
```

#### 3.1.3 Instalar Maven 3.x

Windows PowerShell con Chocolatey:

```powershell
choco install maven -y
```

macOS bash/zsh con Homebrew:

```bash
brew install maven
```

Linux Debian/Ubuntu bash:

```bash
sudo apt update
sudo apt install -y maven
```

#### 3.1.4 Instalar VS Code y Extension Pack for Java

Windows PowerShell con Chocolatey:

```powershell
choco install vscode -y
```

macOS bash/zsh con Homebrew:

```bash
brew install --cask visual-studio-code
```

Linux Debian/Ubuntu bash:

```bash
sudo snap install code --classic
```

En VS Code, instalar la extension:

```text
Extension Pack for Java
```

#### 3.1.5 Verificar instalacion

Verificar Java 17:

```bash
java -version
```

Resultado esperado:

```text
version 17
```

Verificar Maven:

```bash
mvn -version
```

Resultado esperado:

```text
Apache Maven 3.x
```

### 3.2 Crear proyecto Java simple

**Producto del paso:** carpeta de trabajo con estructura inicial.

1. Crear una carpeta para el proyecto.
2. Abrir la carpeta en VS Code.
3. Crear una carpeta `src`.
4. Crear el archivo `Main.java`.
5. Ejecutar un mensaje simple para comprobar el entorno.

Ejemplo:

```java
public class Main {
    public static void main(String[] args) {
        System.out.println("Proyecto iniciado");
    }
}
```

### 3.3 Abstraer objetos tangibles: Coche y Persona

**Producto del paso:** dos clases candidatas identificadas desde el mundo real.

Antes de escribir código, observar objetos tangibles. Para iniciar, se usan `Coche` y `Persona` porque permiten distinguir caracteristicas, acciones y colaboracion entre objetos.

Completar una tabla de abstracción inicial:

| Clase | Caracteristicas | Acciones |
|---|---|---|
| `Coche` | marca, velocidad | acelerar, frenar, mostrar estado |
| `Persona` | nombre | conducir |

En S1, responsabilidad de clase no significa responsabilidad legal, vial o moral. Significa identificar qué caracteristicas y qué acciones le corresponden a una clase dentro del programa.

Ejemplo:

```text
La Persona decide conducir.
El Coche ejecuta acelerar o frenar y cambia su propia velocidad.
```

Nota métodológica:

```text
En S1 todavía no se aplica SOLID de manera formal.
Tampoco se trabaja encapsulamiento ni constructores como tema fuerte.

El objetivo es entender clase, objeto, atributos, métodos, estado,
comportamiento, responsabilidad inicial y abstracción.
```

### 3.4 Crear la clase Coche

**Producto del paso:** clase tangible con atributos, estado y métodos.

Crear `Coche.java`:

```java
public class Coche {
    String marca;
    int velocidad;

    void acelerar() {
        velocidad = velocidad + 10;
    }

    void frenar() {
        velocidad = velocidad - 10;
    }

    void mostrarEstado() {
        System.out.println(marca + " - Velocidad: " + velocidad);
    }
}
```

En este punto ya aparecen los primeros conceptos:

| Elemento del código | Concepto POO |
|---|---|
| `public class Coche` | Clase |
| `marca`, `velocidad` | Atributos |
| Valor actual de `velocidad` | Estado |
| `acelerar()` y `frenar()` | Métodos |
| Cambiar la velocidad | Comportamiento |

### 3.5 Crear la clase Persona

**Producto del paso:** segunda clase tangible qué usa un objeto `Coche`.

Crear `Persona.java`:

```java
public class Persona {
    String nombre;

    void conducir(Coche coche) {
        System.out.println(nombre + " conduce el coche");
        coche.acelerar();
        coche.frenar();
    }
}
```

Lectura métodológica:

```text
Persona no cambia directamente la velocidad.
Persona usa acciones disponibles del Coche.
Coche modifica su propio estado.
```

La idea de pedales o volante puede usarse cómo analogia: la persona no manipula todo el motor; interactua mediante acciones visibles. La interface formal de Java se trabajara después, en S4.

### 3.6 Crear objetos desde Main

**Producto del paso:** objetos `coche1` y `persona1` instanciados y visibles por consola.

Actualizar `Main.java`:

```java
public class Main {
    public static void main(String[] args) {
        Coche coche1 = new Coche();
        coche1.marca = "Toyota";
        coche1.velocidad = 0;

        Persona persona1 = new Persona();
        persona1.nombre = "Ana";

        coche1.mostrarEstado();
        persona1.conducir(coche1);
        coche1.mostrarEstado();
    }
}
```

En este punto se observa la diferencia entre clase y objeto:

| Elemento | Explicacion |
|---|---|
| `Coche` | Molde o definicion general |
| `coche1` | Objeto creado desde la clase `Coche` |
| `Persona` | Molde o definicion general |
| `persona1` | Objeto creado desde la clase `Persona` |
| Estado de `coche1` | Toyota, velocidad actual |

### 3.7 Identificar estado, comportamiento y responsabilidad inicial

**Producto del paso:** explicacion de cómo los objetos guardan datos, ejecutan acciones y colaboran.

Analizar el código creado:

```text
El estado de coche1 cambia cuando se ejecuta acelerar o frenar.
El comportamiento está en los métodos de cada clase.
La responsabilidad inicial se entiende como características y acciones
que le corresponden a cada clase.
```

Completar:

| Clase | Sabe | Puede |
|---|---|---|
| `Coche` | marca, velocidad | acelerar, frenar, mostrar estado |
| `Persona` | nombre | conducir un coche |

### 3.8 Ejemplo 2: Producto cómo preparación para S2

**Producto del paso:** clase `Producto` simple con estado cambiante.

Ahora se usa `Producto` cómo segundo ejemplo porque en S2 se convertira en una clase encapsulada.

Crear `Producto.java`:

```java
public class Producto {
    String codigo;
    String nombre;
    double precio;
    int stock;

    void mostrarInformacion() {
        System.out.println(codigo + " - " + nombre + " - S/ " + precio + " - Stock: " + stock);
    }

    void actualizarPrecio(double nuevoPrecio) {
        precio = nuevoPrecio;
    }

    void aumentarStock(int cantidad) {
        stock = stock + cantidad;
    }
}
```

Probar desde `Main`:

```java
Producto producto1 = new Producto();
producto1.codigo = "P001";
producto1.nombre = "Teclado";
producto1.precio = 80.0;
producto1.stock = 10;

producto1.mostrarInformacion();
producto1.actualizarPrecio(75.0);
producto1.aumentarStock(5);
producto1.mostrarInformacion();
```

Lectura esperada:

```text
producto1 sigue siendo el mismo objeto.
Lo que cambió fue su estado: precio y stock.
En S2 se controlara mejor este cambio con encapsulamiento,
constructor, validaciones e invariantes simples.
```

## 4. Crea: actividad autónoma

Fuera del aula, cada estudiante consolida el aprendizaje creando clases propias del dominio y preparando una evidencia individual.

Tiempo: 2h fuera del aula.

### 4.1 Plantilla de evidencia individual

Entrega un PDF con el siguiente nombre:

```text
S01_Equipo##_ApellidoNombre.pdf
```

Ejemplo:

```text
S01_Equipo03_QuispeAna.pdf
```

El PDF debe usar esta estructura. La primera sección define el trabajo autónomo; completa las demás con tus evidencias.

#### 4.1.1 Datos del estudiante

- Nombre:
- Equipo:
- Sesión: S01 - Clases, objetos y responsabilidad de clase
- Rol o aporte realizado:
- Link de GitHub:

#### 4.1.2 Trabajo autónomo realizado

Completa y evidencia estas tareas:

1. Crear otro par de clases tangibles que colaboren entre sí, por ejemplo `Estudiante` y `Cuaderno`, `Jugador` y `Pelota`, o `Vendedor` y `Pedido`.
2. Crear una clase simple similar a `Producto` que pueda prepararse para encapsulamiento en S2.
3. Instanciar objetos desde `Main`.
4. Mostrar por consola el estado inicial de al menos un objeto.
5. Ejecutar métodos que cambien o muestren comportamiento.
6. Explicar qué datos y acciones pertenecen a cada clase.
7. Explicar qué parte del código demuestra abstracción inicial.

#### 4.1.3 Evidencia técnica

Incluye capturas o salidas de consola con una breve explicación debajo de cada una:

- Código de dos clases tangibles.
- Código de una clase puente similar a `Producto`.
- Código de prueba desde `Main`.
- Salida de consola antes y después de ejecutar un método.
- Tabla breve con clase, atributos, métodos y responsabilidad inicial.

#### 4.1.4 Error o hallazgo

Describe al menos un error, diferencia o hallazgo técnico:

- Qué ocurrió.
- Cómo lo diagnosticaste.
- Cómo lo corregiste o qué aprendiste.

Ejemplos válidos:

- El archivo no coincidía con el nombre de la clase.
- `Main` no encontraba una clase.
- Un atributo salía `null` o `0` porque no fue inicializado.
- Un método no cambiaba el estado esperado.

#### 4.1.5 Reflexión técnica breve

Responde en 5 a 8 líneas:

```text
Por qué una clase no debe ser solo una lista de variables?
```

### 4.2 Criterios mínimos de aceptación

La evidencia individual se considera completa si:

- El archivo respeta el nombre `S01_Equipo##_ApellidoNombre.pdf`.
- Incluye evidencias técnicas legibles.
- Muestra al menos dos clases tangibles que colaboran.
- Muestra una clase puente preparada para S2.
- Muestra objetos creados desde `Main`.
- Muestra salida de consola.
- Explica responsabilidad inicial, estado y comportamiento.
- No contiene solo pantallazos: cada evidencia tiene una descripción breve.

## 5. Cierre evaluativo

Tiempo: 20 min.

Esta sección conecta el resultado de aprendizaje de la sesión con el producto que debe evidenciar cada estudiante.

### 5.1 Resultados esperados

Al finalizar la sesión, el estudiante debe demostrar que:

- El proyecto ejecuta desde VS Code.
- Existen clases tangibles como `Coche` y `Persona`, o equivalentes del dominio elegido.
- Existe una clase puente como `Producto`, preparada para S2.
- Se crean objetos desde `Main`.
- La clase tiene atributos que representan estado.
- La clase tiene métodos que representan comportamiento básico.
- La salida por consola demuestra estado y comportamiento del objeto.
- El estudiante explica qué datos y comportamientos fueron elegidos por abstracción inicial.
- El estudiante explica qué responsabilidad tiene cada clase.
- Los métodos implementados corresponden a las acciones iniciales de la clase.
- No se usan constructores ni atributos `private` como tema central; eso queda para S2.

### 5.2 Evidencia del producto de sesión

Cada estudiante entrega un PDF individual siguiendo la plantilla de la sección 4.1.

Nombre del archivo:

```text
S01_Equipo##_ApellidoNombre.pdf
```

La evidencia debe demostrar:

- Producto de sesión construido.
- Aporte individual verificable.
- Pruebas por consola realizadas.
- Reflexión técnica breve.

La revisión se realiza con los criterios mínimos de aceptación de la sección 4.2 y la rúbrica de la sección 5.4.

### 5.3 Preguntas de defensa y reflexión

1. Cuál es la diferencia entre clase y objeto?
2. Qué representa el estado de un objeto?
3. Qué significa responsabilidad de clase en S1?
4. Qué método representa comportamiento en tu clase?
5. Qué datos dejaste fuera por abstracción inicial?
6. Qué características y acciones identificaste en tu clase?
7. Qué responsabilidad tiene `Main` en esta primera sesión?
8. Qué cambiará en S2 cuando aparezca encapsulamiento?

### 5.4 Rúbrica de evaluación

| Dimensión | Peso | 3 - Logro destacado | 2 - Logro | 1 - Proceso | 0 - Inicio | Puntuación obtenida |
|---|---:|---|---|---|---|---:|
| 1. Clases y objetos | 2 | Define clases claras, crea objetos y explica la diferencia con precisión. | Define clases y crea objetos funcionales. | Presenta clases incompletas o confunde clase con objeto. | No evidencia clases y objetos funcionales. | |
| 2. Estado y comportamiento | 2 | Atributos y métodos representan correctamente estado y comportamiento. | Atributos y métodos principales son coherentes. | Hay atributos o métodos poco claros. | No evidencia estado ni comportamiento. | |
| 3. Responsabilidad y abstracción | 2 | Explica qué pertenece a cada clase y qué se dejó fuera por abstracción. | Explica responsabilidad básica de las clases. | Explicación parcial o confusa. | No explica responsabilidad ni abstracción. | |
| 4. Prueba desde `Main` | 2 | `Main` crea objetos, ejecuta métodos y muestra salida clara. | `Main` prueba el flujo principal. | Prueba incompleta o salida poco clara. | No hay prueba desde `Main`. | |
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

