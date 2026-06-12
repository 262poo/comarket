# S3 - Asociación, agregación/composición y colecciones

## 1. Introducción

Tiempo: 20 min.

### 1.1 Propósito

Representar relaciones básicas entre objetos mediante asociación, agregación/composición y colecciones en memoria, antes de convertir esas ideas en operaciones CRUD.

### 1.2 Resultado de aprendizaje

El estudiante identifica entidades del dominio, representa asociaciónes, agregación y composición, y usa `ArrayList` para manejar grupos de objetos relacionados.

### 1.3 Producto de sesión

Modelo inicial con varias entidades relaciónadas, colecciones administradas desde un servicio inicial y pruebas desde `Main`.

### 1.4 Motivación de la sesión

En un sistema real no existe una sola clase. Una venta se relacióna con un cliente, un proveedor puede estar asociado a productos, y una venta puede estar compuestá por detalles. La Programación Orientada a Objetos ayuda a convertir esas relaciones del problema en clases conectadas.

Pregunta guía:

```text
Cómo pasamos de clases aisladas a un modelo de dominio con objetos relacionados?
```

### 1.5 Ubicación en el curso

- Unidad: U1.
- Producto de unidad: aplicación de consola en memoria con entidades, relaciones, colecciones y operaciones principales.
- Avance de sesión: se construye el mapa inicial del dominio y se prepara la base para herencia, polimorfismo y CRUD.

```mermaid
flowchart TB
    S1["S1<br/>Entidad simple<br/>clase y objetos"]
    S2["S2<br/>Entidad encapsulada<br/>constructores y validaciones"]
    S3["S3<br/>Asociación, agregación/composición<br/>y colecciones"]
    S4["S4<br/>Herencia y polimorfismo<br/>bloque aplicado"]
    S5["S5<br/>Servicio en memoria<br/>CRUD con ArrayList"]
    S6["S6<br/>Producto U1<br/>consola ejecutable"]

    S1 --> S2
    S2 --> S3
    S3 --> S5
    S3 -. refuerza modelo .-> S4
    S4 -. se aplica en .-> S5
    S5 --> S6

    classDef today fill:#ffe08a,stroke:#9a6b00,stroke-width:2px,color:#111;
    class S3 today;
```

Hoy no se busca terminar todo el sistema. Se busca qué el estudiante entienda qué el dominio se arma con varias clases, cada una con una responsabilidad, y qué las relaciones deben aparecer en el código de manera clara.

## 2. Explica

Tiempo: 25 min.

### 2.1 Conceptos clave

| Concepto | Idea central | Ejemplo |
|---|---|---|
| Entidad | Clase qué representa un elemento importante del dominio. | `Cliente`, `Proveedor`, `Producto`, `Venta` |
| Asociación | Un objeto conoce o usa a otro objeto. | `Venta` tiene un `Cliente` |
| Agregación | Un objeto agrupa otros, pero esos objetos pueden existir por separado. | `Proveedor` relaciónado con varios `Producto` |
| Composición | Un objeto contiene partes qué dependen de el. | `Venta` contiene `DetalleVenta` |
| Colección | Estructura para manejar varios objetos del mismo tipo. | `ArrayList<Producto>` |
| Servicio inicial | Clase qué administra una colección y evita cargar toda la lógica en `Main`. | `ProductoService`, `VentaService` |

Regla métodológica de la sesión:

```text
Las entidades representan información y comportamiento del dominio.
Las relaciones muestran cómo colaboran los objetos.
Las colecciones administran grupos de objetos.
Main solo crea escenarios de prueba.
```

### 2.2 Arquitectura de la sesión

```mermaid
classDiagram
    class Main {
        main(String[] args)
    }
    class ProductoService {
        -productos
        registrar(producto)
        listar()
    }
    class VentaService {
        -ventas
        registrar(venta)
        listar()
    }
    class Cliente {
        -nombre
        -dni
    }
    class Proveedor {
        -ruc
        -razonSocial
        -productos
        agregarProducto(producto)
    }
    class Producto {
        -codigo
        -nombre
        -precio
    }
    class Venta {
        -cliente
        -detalles
        agregarDetalle(detalle)
        calcularTotal()
    }
    class DetalleVenta {
        -producto
        -cantidad
        calcularSubtotal()
    }

    Main ..> ProductoService : prueba
    Main ..> VentaService : prueba
    ProductoService o-- "0..*" Producto : ArrayList
    VentaService o-- "0..*" Venta : ArrayList

    Proveedor "1" o-- "0..*" Producto : agrega
    Venta "1" --> "1" Cliente : asociación
    Venta "1" *-- "1..*" DetalleVenta : composición
    DetalleVenta "*" --> "1" Producto : asociación
```

Convencion del diagrama: `-->` representa asociación, `o--` representa agregación, `*--` representa composición y `..>` representa dependencia de prueba o uso temporal. En esta sesión no se implementa todavía la arquitectura completa de servicio; solo se prepara el dominio para qué S5 pueda convertir las operaciones en un CRUD en memoria.

### 2.3 Tipos de relación

Asociación:

```java
public class Venta {
    private Cliente cliente;
}
```

La venta se relacióna con un cliente. El cliente puede existir aunque no tenga ventas registradas.

Agregación:

```java
public class Proveedor {
    private ArrayList<Producto> productos;
}
```

El proveedor agrupa productos. Para la practica inicial se entiende cómo una relación de agrupacion: los productos son parte del modelo y pueden administrarse también desde un servicio.

Composición:

```java
public class Venta {
    private ArrayList<DetalleVenta> detalles;
}
```

Los detalles existen para explicar una venta. Si se elimina la venta, sus detalles ya no tienen sentido dentro del sistema.

### 2.4 Errores frecuentes

| Error | Corrección esperada |
|---|---|
| Poner todas las variables en `Main`. | Crear entidades con responsabilidades claras. |
| Usar solo una clase para todo el dominio. | Separar `Cliente`, `Producto`, `Venta`, `DetalleVenta` y otros conceptos. |
| Confundir una lista con una entidad. | La lista administra varios objetos; la entidad representa un objeto del dominio. |
| Crear relaciones sin sentido. | Cada relación debe responder a una regla del problema. |
| Hacer CRUD completo antes de modelar. | Primero se entiende el dominio; luego se agregan operaciones. |

## 3. Aplica: actividad practica guíada

Tiempo: 2h.

### 3.1 Identificar entidades del dominio

Parte de un caso simple de comercio:

```text
Un sistema registra clientes, proveedores, productos y ventas.
Cada venta pertenece a un cliente.
Cada venta tiene uno o más detalles.
Cada detalle indica un producto, cantidad y precio.
Un proveedor puede estar asociado a varios productos.
```

Entidades iniciales:

- `Cliente`
- `Proveedor`
- `Producto`
- `Venta`
- `DetalleVenta`

### 3.2 Crear entidades base

Ejemplo de `Producto`:

```java
public class Producto {
    private String codigo;
    private String nombre;
    private double precio;

    public Producto(String codigo, String nombre, double precio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }
}
```

Ejemplo de `Proveedor` con agregación:

```java
import java.util.ArrayList;

public class Proveedor {
    private String ruc;
    private String razonSocial;
    private ArrayList<Producto> productos;

    public Proveedor(String ruc, String razonSocial) {
        this.ruc = ruc;
        this.razonSocial = razonSocial;
        this.productos = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }
}
```

### 3.3 Representar una venta con composición

`DetalleVenta` representa una parte de la venta:

```java
public class DetalleVenta {
    private Producto producto;
    private int cantidad;

    public DetalleVenta(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public double calcularSubtotal() {
        return producto.getPrecio() * cantidad;
    }
}
```

`Venta` contiene sus detalles:

```java
import java.util.ArrayList;

public class Venta {
    private Cliente cliente;
    private ArrayList<DetalleVenta> detalles;

    public Venta(Cliente cliente) {
        this.cliente = cliente;
        this.detalles = new ArrayList<>();
    }

    public void agregarDetalle(DetalleVenta detalle) {
        detalles.add(detalle);
    }

    public double calcularTotal() {
        double total = 0;
        for (DetalleVenta detalle : detalles) {
            total += detalle.calcularSubtotal();
        }
        return total;
    }
}
```

### 3.4 Crear un servicio inicial

El servicio inicial administra una colección. Todavía no implementa un contrato CRUD formal; eso se completa en S5.

```java
import java.util.ArrayList;

public class ProductoService {
    private ArrayList<Producto> productos;

    public ProductoService() {
        this.productos = new ArrayList<>();
    }

    public void registrar(Producto producto) {
        productos.add(producto);
    }

    public void listar() {
        for (Producto producto : productos) {
            System.out.println(producto.getCodigo() + " - " + producto.getNombre());
        }
    }
}
```

### 3.5 Probar desde Main

```java
public class Main {
    public static void main(String[] args) {
        Cliente cliente = new Cliente(
                "Ana Torres",
                "71234567",
                "999888777",
                java.time.LocalDate.of(2000, 5, 10)
        );

        Producto teclado = new Producto("P001", "Teclado", 80.0);
        Producto mouse = new Producto("P002", "Mouse", 45.0);

        Proveedor proveedor = new Proveedor("20456789123", "Tecno Peru SAC");
        proveedor.agregarProducto(teclado);
        proveedor.agregarProducto(mouse);

        Venta venta = new Venta(cliente);
        venta.agregarDetalle(new DetalleVenta(teclado, 1));
        venta.agregarDetalle(new DetalleVenta(mouse, 2));

        System.out.println("Total: " + venta.calcularTotal());
    }
}
```

### 3.6 Preguntas durante la practica

1. Qué clases son entidades del dominio?
2. Qué relación existe entre `Venta` y `Cliente`?
3. Por qué `DetalleVenta` depende de `Venta`?
4. Qué clase administra una colección?
5. Qué lógica ya no deberia quedarse en `Main`?

## 4. Crea: actividad autónoma

Tiempo: 2h fuera del aula.

Amplia el modelo con una relación adicional. Puedes elegir una de estas opciones:

- `Categoria` relaciónada con varios `Producto`.
- `Empleado` relaciónado con varias `Venta`.
- `Proveedor` relaciónado con varios `Producto`.
- `Cliente` relaciónado con varias `Venta`.

Entrega evidencia breve con:

- Diagrama simple del modelo.
- Código de al menos tres entidades relaciónadas.
- Uso de una colección con `ArrayList`.
- Una clase de servicio inicial.
- Salida de consola mostrando objetos relacionados.

## 5. Cierre evaluativo

Tiempo: 20 min.

### 5.1 Resultados esperados

- El modelo tiene varias entidades del dominio.
- Las relaciones no están sueltas; aparecen representadas en atributos o colecciones.
- Hay al menos una relación de uno a muchos.
- Se usa `ArrayList` para administrar varios objetos.
- `Main` solo arma escenarios de prueba y no concentra toda la lógica.

### 5.2 Preguntas de defensa

1. Qué diferencia hay entre entidad y colección?
2. Qué relación modelaste cómo asociación?
3. Qué relación modelaste cómo agregación o composición?
4. Por qué una venta necesita detalles?
5. Qué parte de este modelo se podría convertir en CRUD en S5?
