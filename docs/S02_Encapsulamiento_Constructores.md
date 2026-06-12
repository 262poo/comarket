# S2 - Encapsulamiento, constructores y control del estado

## 1. Introduccion

Tiempo: 20 min.

### 1.1 Proposito

Proteger el estado de los objetos mediante atributos privados, constructores y getters/setters limpios, e introducir la separacion basica de responsabilidades con `ProductoService`.

### 1.2 Resultado de aprendizaje

El estudiante aplica encapsulamiento, crea constructores simples y sobrecargados, consulta datos mediante getters, usa setters limpios para asignaciones directas, separa operaciones en un servicio inicial y prueba el flujo desde `Main`.

### 1.3 Producto de sesion

Clase `Producto` encapsulada con constructores y getters/setters limpios, mas `ProductoService` inicial con operaciones sobre productos.

### 1.4 Motivacion de la sesion

#### 1.4.1 Caso: estado invalido en objetos del dominio

En S1 se creo una clase simple para entender clase, objeto, atributos, metodos, estado, comportamiento, responsabilidad inicial y abstraccion. Esa version permite aprender rapido, pero tambien permite que `Main` cambie los datos sin control.

Ejemplos de problemas:

- Un producto creado solo con los datos iniciales disponibles.
- Un producto al que luego se le completa precio y stock.
- Un precio negativo.
- Un stock negativo.
- Un cambio de precio sin ninguna regla.

Pregunta guia:

```text
Como hacemos que un objeto proteja su propio estado y no dependa de Main para corregir datos?
```

### 1.5 Ubicacion en el curso

- Unidad: U1 - Fundamentos de la Programacion Orientada a Objetos.
- Producto de unidad: aplicacion de consola en memoria con entidades, relaciones, colecciones y CRUD.
- Avance del producto en esta sesion: `Producto` deja de ser una clase con datos expuestos y empieza a controlar su estado.

Roadmap para elaborar el producto de la unidad:

```mermaid
flowchart TB
    S1["S1<br/>Coche, Persona y Producto<br/>clase y objetos"]
    S2["S2<br/>Producto encapsulado<br/>HOY"]
    S3["S3<br/>Asociacion, agregacion/composicion<br/>y colecciones"]
    S4["S4<br/>Herencia y polimorfismo<br/>bloque aplicado"]
    S5["S5<br/>Servicio en memoria<br/>CRUD con ArrayList"]
    S6["S6<br/>Producto U1<br/>consola ejecutable"]

    S1 --> S2
    S2 --> S3
    S3 --> S5
    S3 -. refuerza modelo .-> S4
    S4 -. se aplica en .-> S5
    S5 --> S6

    classDef done fill:#e8f5e9,stroke:#2e7d32,color:#111;
    classDef today fill:#ffe08a,stroke:#9a6b00,stroke-width:2px,color:#111;
    class S1 done;
    class S2 today;
```

## 2. Explica

Tiempo: 25 min.

### 2.1 Conceptos clave

El encapsulamiento evita que cualquier parte del programa modifique directamente el estado interno de un objeto. La clase controla como se crea, como cambia y que reglas debe cumplir.

En S1, "responsabilidad" se entendio como caracteristicas y acciones que corresponden a una clase. En S2 esa idea se mejora: la clase tambien debe proteger sus datos para no quedar en un estado invalido.

Conceptos de la sesion:

- `private` para proteger atributos.
- Constructor para inicializar objetos.
- Getters para consultar estado.
- Getters y setters limpios.
- Metodos de cambio con nombre de accion.
- Validaciones basicas.
- Invariantes simples.
- Metodo de comportamiento.

Nota metodologica:

```text
S1 permite ver estado y comportamiento de forma directa.
S2 empieza a controlar el estado con encapsulamiento.

Todavia no se trabajan interfaces como contrato.
Eso queda para S4.
```

Ejemplo de responsabilidad mejorada:

```text
Producto guarda codigo, nombre, precio y stock.
ProductoService agrupa operaciones sobre productos.
Main no debe modificar los atributos directamente ni concentrar las reglas.
```

Nota sobre getters/setters:

```text
Los getters y setters deben quedar simples.
Mas adelante, en U2 o U3, este codigo mecanico podria reemplazarse
con Lombok usando anotaciones como @Getter y @Setter.

Las reglas importantes no deben esconderse en getters/setters.
Esas reglas se expresan mejor en metodos con nombre de accion,
por ejemplo dentro de ProductoService.
```

### 2.2 S de SOLID: una responsabilidad principal

La S de SOLID se conoce como principio de responsabilidad unica. En esta sesion se aplica de forma basica:

```text
Producto sabe que datos tiene.
ProductoService sabe que operaciones se hacen sobre un producto.
Main solo prueba el flujo.
```

No se busca una arquitectura completa todavia. Solo se evita que `Producto` y `Main` hagan todo.

### 2.3 Arquitectura de la sesion

```mermaid
classDiagram
    class Main {
        main(String[] args)
    }
    class Producto {
        -codigo
        -nombre
        -precio
        -stock
        Producto(codigo, nombre, precio, stock)
        Producto(codigo, nombre)
        getCodigo()
        getNombre()
        getPrecio()
        getStock()
        setPrecio()
        setStock()
    }
    class ProductoService {
        actualizarPrecio()
        aumentarStock()
        disminuirStock()
        mostrarInformacion()
        validarCantidadPositiva()
    }

    Main ..> Producto : depende de
    Main ..> ProductoService : depende de
    ProductoService ..> Producto : usa
```

Convencion del diagrama: cada clase muestra sus atributos y metodos principales; `-` indica atributo privado y `..>` indica dependencia o uso temporal desde la prueba.

Regla practica:

- `Main` prueba escenarios.
- `Producto` protege su estado.
- El constructor inicializa el objeto.
- La sobrecarga de constructores permite crear objetos con distintos datos iniciales.
- Los atributos no se modifican directamente desde fuera.
- Los getters y setters se mantienen limpios.
- Los cambios con regla pasan por `ProductoService`.
- El precio y el stock no deben ser negativos.

### 2.4 Flujo de trabajo

1. Partir de la clase `Producto` creada en S1.
2. Cambiar atributos de acceso directo a `private`.
3. Crear constructores simples y sobrecargados.
4. Agregar getters y setters limpios.
5. Crear `ProductoService`.
6. Mover operaciones con regla hacia `ProductoService`.
7. Probar casos validos e invalidos desde `Main`.

### 2.5 Errores frecuentes y diagnostico

| Problema | Causa probable | Solucion |
|---|---|---|
| No se puede acceder al atributo | El atributo ahora es `private` | Usar getter o metodo de comportamiento |
| El objeto se crea con datos incompletos | No se eligio bien el constructor | Usar sobrecarga de constructores segun el caso |
| Precio negativo | No se valido el dato | Rechazar valores negativos |
| Stock negativo | No se valido el dato | Rechazar valores negativos |
| Setter contiene demasiada logica | Se mezclo codigo mecanico con reglas | Dejar setter limpio y mover la regla a `ProductoService` |
| `Main` contiene demasiadas reglas | No se separo la operacion | Llevar operaciones simples a `ProductoService` |

## 3. Aplica: actividad practica guiada

En el laboratorio, el docente guia la transformacion de `Producto` desde una clase con atributos expuestos hacia una clase encapsulada que controla su propio estado.

Tiempo: 2h.

### 3.1 Revisar la clase Producto creada en S1

**Producto del paso:** clase `Producto` inicial identificada.

Punto de partida esperado:

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

### 3.2 Encapsular atributos

**Producto del paso:** atributos protegidos con `private`.

```java
public class Producto {
    private String codigo;
    private String nombre;
    private double precio;
    private int stock;

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

### 3.3 Probar el codigo de S1 y observar el error

**Producto del paso:** evidencia de que `private` protege el acceso directo al estado.

Volver a probar el codigo usado en S1:

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

Resultado esperado:

```text
El codigo ya no compila porque codigo, nombre, precio y stock
ahora son atributos private.
```

Lectura metodologica:

```text
Eso es encapsulamiento:
el estado interno ya no se modifica directamente desde Main.

Para crear el objeto se usara constructor.
Para consultar datos se usaran getters.
Para asignar datos simples se usaran setters.
Para cambios con regla se usaran metodos con nombre de accion.
```

### 3.4 Crear constructores

**Producto del paso:** objetos creados con distintos datos iniciales.

El constructor sirve para inicializar el objeto. En la practica no siempre se reciben todos los campos al crear un objeto; por eso se puede usar sobrecarga de constructores y completar algunos datos despues con setters o metodos con nombre de accion.

```java
public Producto(String codigo, String nombre, double precio, int stock) {
    this.codigo = codigo;
    this.nombre = nombre;
    this.precio = precio;
    this.stock = stock;
}

public Producto(String codigo, String nombre) {
    this.codigo = codigo;
    this.nombre = nombre;
    this.precio = 0;
    this.stock = 0;
}
```

Nota metodologica:

```text
Esto se llama sobrecarga de constructores:
una misma clase puede tener varios constructores con parametros diferentes.

No es polimorfismo todavia; el polimorfismo se trabaja en S4.
```

### 3.5 Agregar getters y setters limpios

**Producto del paso:** consulta y asignacion controlada por acceso, sin logica pesada.

```java
public String getCodigo() {
    return codigo;
}

public String getNombre() {
    return nombre;
}

public double getPrecio() {
    return precio;
}

public int getStock() {
    return stock;
}

public void setPrecio(double precio) {
    this.precio = precio;
}

public void setStock(int stock) {
    this.stock = stock;
}
```

Regla metodologica:

```text
Getter devuelve.
Setter asigna.

Si hay una regla importante, se crea un metodo con nombre de accion.
```

### 3.6 Probar constructores, getters y setters limpios

**Producto del paso:** evidencia de que el objeto se crea con constructor y se consulta/modifica mediante metodos publicos.

Probar desde `Main`:

```java
public class Main {
    public static void main(String[] args) {
        Producto producto1 = new Producto("P001", "Teclado", 80.0, 10);
        Producto producto2 = new Producto("P002", "Mouse");

        producto2.setPrecio(45.0);
        producto2.setStock(20);

        System.out.println(producto1.getCodigo() + " - " + producto1.getNombre());
        System.out.println("Precio: " + producto1.getPrecio());
        System.out.println("Stock: " + producto1.getStock());

        System.out.println(producto2.getCodigo() + " - " + producto2.getNombre());
        System.out.println("Precio: " + producto2.getPrecio());
        System.out.println("Stock: " + producto2.getStock());
    }
}
```

Lectura metodologica:

```text
Ya no se usa producto.codigo ni producto.precio directamente.
El constructor inicializa.
Los getters consultan.
Los setters completan datos simples.
```

### 3.7 Segunda parte: crear ProductoService

**Producto del paso:** operaciones sobre productos separadas de la entidad.

Antes de crear el servicio, retirar de `Producto` las operaciones que ahora pasaran a otra responsabilidad:

```text
Salen de Producto:
- mostrarInformacion()
- actualizarPrecio()
- aumentarStock()
```

`Producto` queda como clase de datos encapsulada: atributos privados, constructores, getters y setters limpios. Las operaciones pasan a `ProductoService`.

Crear `ProductoService.java`:

```java
public class ProductoService {
    public void actualizarPrecio(Producto producto, double nuevoPrecio) {
        if (nuevoPrecio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
        producto.setPrecio(nuevoPrecio);
    }

    public void aumentarStock(Producto producto, int cantidad) {
        validarCantidadPositiva(cantidad);
        producto.setStock(producto.getStock() + cantidad);
    }

    public void disminuirStock(Producto producto, int cantidad) {
        validarCantidadPositiva(cantidad);
        if (cantidad > producto.getStock()) {
            throw new IllegalArgumentException("No hay stock suficiente");
        }
        producto.setStock(producto.getStock() - cantidad);
    }

    public void mostrarInformacion(Producto producto) {
        System.out.println(
                producto.getCodigo() + " - " +
                producto.getNombre() + " - S/ " +
                producto.getPrecio() + " - Stock: " +
                producto.getStock()
        );
    }

    private void validarCantidadPositiva(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser positiva");
        }
    }
}
```

Regla metodologica:

```text
Producto conserva sus datos.
ProductoService realiza operaciones sobre Producto.
Main coordina la prueba.

En esta sesion, Main no debe llamar setters para aplicar reglas del negocio.
Para eso usa ProductoService.
```

### 3.8 Agregar validaciones basicas en ProductoService

**Producto del paso:** reglas simples ubicadas fuera de getters/setters.

Ejemplo:

```java
public void actualizarPrecio(Producto producto, double nuevoPrecio) {
    if (nuevoPrecio < 0) {
        throw new IllegalArgumentException("El precio no puede ser negativo");
    }
    producto.setPrecio(nuevoPrecio);
}
```

### 3.9 Probar desde Main

**Producto del paso:** casos validos e invalidos ejecutados desde consola.

```java
public class Main {
    public static void main(String[] args) {
        Producto producto = new Producto("P001", "Teclado", 80.0, 10);
        Producto productoNuevo = new Producto("P002", "Mouse");
        ProductoService productoService = new ProductoService();

        productoNuevo.setPrecio(45.0);
        productoNuevo.setStock(20);

        productoService.mostrarInformacion(producto);
        productoService.actualizarPrecio(producto, 75.0);
        productoService.aumentarStock(producto, 5);
        productoService.disminuirStock(producto, 3);
        productoService.mostrarInformacion(producto);

        // Caso invalido para observar la validacion
        // productoService.actualizarPrecio(producto, -10.0);
    }
}
```

### 3.10 Registrar decisiones de encapsulamiento y responsabilidad

**Producto del paso:** explicacion breve de que sabe cada clase y que hace cada clase.

Completar una tabla simple:

| Elemento | Decision |
|---|---|
| `codigo` | Se inicializa en constructor porque identifica al producto |
| `nombre` | Se inicializa en constructor porque describe al producto |
| `Producto` | Sabe sus datos y expone getters/setters limpios |
| `ProductoService` | Realiza operaciones como actualizar precio y mover stock |
| `Main` | Crea objetos y prueba el flujo |

Reglas simples:

```text
El constructor inicializa.
Los getters consultan.
Los setters asignan datos de forma simple.
ProductoService contiene metodos con nombre de accion.
```

## 4. Crea: actividad autonoma

Tiempo: 2h fuera del aula.

Mejora otra entidad del dominio aplicando encapsulamiento, constructores sobrecargados, getters/setters limpios y una clase service inicial. Puede ser `Proveedor`, `Empleado`, `Usuario`, `Cliente` u otra entidad del proyecto elegido.

Entrega evidencia breve con:

- Clase encapsulada.
- Constructores sobrecargados.
- Getters o metodos necesarios.
- Setters limpios.
- Clase service con al menos dos operaciones.
- Prueba valida desde `Main`.
- Prueba invalida controlada.

## 5. Cierre evaluativo

Tiempo: 20 min.

### 5.1 Resultados esperados

- Las clases no exponen atributos publicos.
- Los constructores inicializan objetos.
- Hay sobrecarga de constructores cuando se necesita crear objetos con distintos datos iniciales.
- Los getters/setters se mantienen simples.
- Existe una clase `ProductoService` o equivalente.
- Las validaciones basicas estan en el service.
- Los cambios importantes de estado pasan por metodos del service.
- `Main` se usa para probar, no para controlar todas las reglas.
- El estudiante explica que reglas simples protege su clase mediante setters o metodos.

### 5.2 Preguntas de defensa

1. Por que los atributos deben ser privados?
2. Para que sirve la sobrecarga de constructores?
3. Por que los getters/setters deben quedar limpios?
4. Que responsabilidad tiene `Producto`?
5. Que responsabilidad tiene `ProductoService`?
6. Que responsabilidad no deberia tener `Main`?
