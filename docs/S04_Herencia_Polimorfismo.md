# S4 - Herencia, interfaces y polimorfismo

## 1. IntroducciÃ³n

Tiempo: 20 min.

### 1.1 PropÃ³sito

Diferenciar dos mecanismos de POO quÃ© suelen confundirse: herencia para especializar entidades del dominio y polimorfismo con interfaces para programar contra contratos.

### 1.2 Resultado de aprendizaje

El estudiante crea una clase base abstracta con subclases mediante `extends`, define una interface de servicio y crea dos implementaciones mediante `implements`.

### 1.3 Producto de sesiÃ³n

Modelo con `Persona`, `Cliente` y `Empleado` para herencia, mÃ¡s un contrato `ProductoService` con `ProductoServiceImplMemoria` y `ProductoServiceImplDB` cÃ³mo preparaciÃ³n para memoria y persistencia.

### 1.4 MotivaciÃ³n de la sesiÃ³n

En POO no toda reutilizaciÃ³n se resuelve con herencia. Una entidad puede especializarse porque existe una relaciÃ³n es-un, mientras que un servicio puede tener varias implementaciones porque se quiere conservar el mismo contrato aunque cambie la forma de ejecutar la operaciÃ³n.

Pregunta guÃ­a:

```text
CuÃ¡ndo usamos extends en entidades y cuÃ¡ndo usamos implements en servicios?
```

### 1.5 UbicaciÃ³n en el curso

- Unidad: U1.
- Producto de unidad: aplicaciÃ³n de consola en memoria.
- Carpeta de trabajo: `comarket-cli`.
- Avance de sesiÃ³n: se formaliza la diferencia entre entidades con herencia y servicios polimorficos.

## 2. Explica

Tiempo: 25 min.

### 2.1 Conceptos clave

| Concepto | Idea central | Ejemplo |
|---|---|---|
| Herencia | Una clase especializada hereda de una clase base. | `Cliente extends Persona` |
| Clase abstracta | Clase base quÃ© organiza atributos o comportamiento comun. | `abstract class Persona` |
| Sobrescritura | Una subclase redefine un comportamiento heredado. | `mostrarPerfil()` |
| Interface | Contrato de operaciones, sin decidir la implementaciÃ³n concreta. | `ProductoService` |
| Implements | Una clase cumple el contrato de una interface. | `ProductoServiceImplMemoria implements ProductoService` |
| Polimorfismo | Una misma referencia puede apuntar a implementaciones distintas. | `ProductoService service = new ProductoServiceImplMemoria()` |

Regla mÃ©todolÃ³gica de la sesiÃ³n:

```text
Herencia: se aplica en entidades cuando existe relaciÃ³n es-un.
En herencia, el trabajo funcional suele hacerse con clases concretas/hijas.
Si Persona es abstracta, no se registra "una Persona"; se trabaja con Cliente o Empleado.
La herencia no es una asociaciÃ³n normal uno a uno entre dos objetos separados.
En memoria, un Cliente es una Persona especializada; no son dos objetos independientes.
Interface: se aplica en servicios para declarar operaciones esperadas.
ImplementaciÃ³n: ejecuta el contrato, en memoria o con base de datos.
Las entidades no implementan contratos de servicio.
En polimorfismo, el cÃ³digo consumidor accede por el padre/contrato.
Si una implementaciÃ³n agrega mÃ©todos propios, esos mÃ©todos no forman parte del contrato y no deben ser necesarios para el flujo principal.
```

### 2.2 Arquitectura de la sesiÃ³n

```mermaid
classDiagram
    class Main {
        main(String[] args)
    }

    namespace entity {
        class Persona {
            <<abstract>>
            -nombre
            -documento
            mostrarPerfil()
        }
        class Cliente {
            -telefono
            mostrarPerfil()
        }
        class Empleado {
            -cargo
            mostrarPerfil()
        }
        class Producto {
            -codigo
            -nombre
            -precio
        }
    }

    namespace service {
        class ProductoService {
            <<interface>>
            registrar(producto)
            listar()
            buscarPorCodigo(codigo)
        }
        class ProductoServiceImplMemoria {
            -productos
            registrar(producto)
            listar()
            buscarPorCodigo(codigo)
        }
        class ProductoServiceImplDB {
            registrar(producto)
            listar()
            buscarPorCodigo(codigo)
        }
    }

    Main ..> Cliente : prueba
    Main ..> Empleado : prueba
    Main ..> ProductoService : prueba
    Persona <|-- Cliente : extends
    Persona <|-- Empleado : extends
    ProductoService <|.. ProductoServiceImplMemoria : implements
    ProductoService <|.. ProductoServiceImplDB : implements
    ProductoServiceImplMemoria ..> Producto : usa
    ProductoServiceImplDB ..> Producto : usa

    classDef serviceImpl fill:#dbeafe,stroke:#2563eb,stroke-width:2px,color:#1e3a8a;
    class ProductoServiceImplMemoria serviceImpl;
    class ProductoServiceImplDB serviceImpl;
```

Convencion del diagrama: flecha continua con triangulo representa `extends`; flecha punteada con triangulo representa `implements`; flecha punteada simple representa dependencia o uso.

Lectura importante del diagrama:

```text
Herencia:
Persona organiza lo comÃºn, pero el sistema crea objetos concretos: Cliente y Empleado.
Main prueba Cliente y Empleado; no instancia Persona cuando Persona es abstracta.
Si se hiciera CRUD de personas, normalmente se haria sobre las clases hijas o casos de uso concretos.
En base de datos puede mapearse parecido a una relaciÃ³n uno a uno, pero no significa lo mismo.
Si se usa tabla padre y tabla hija, la fila hija depende de la fila padre: al eliminar el hijo normalmente se elimina tambiÃ©n su parte padre.

Polimorfismo:
Main usa ProductoService, no ProductoServiceImplMemoria directamente.
El contrato define quÃ© operaciones se pueden llamar.
Las implementaciones pueden cambiar, pero el flujo principal no debe depender de mÃ©todos que no estÃ©n en la interface.
```

## 3. Aplica: actividad prÃ¡ctica guiada

Tiempo: 2h.

### 3.1 Identificar una relaciÃ³n es-un

Usa una relaciÃ³n natural del dominio:

```text
Cliente es una Persona.
Empleado es una Persona.
Producto no es una Persona.
Venta no es una Persona.
```

La herencia se usa solo cuÃ¡ndo la frase "es un/a" tiene sentido real.

Nota de diseÃ±o:

```text
Persona es una clase base para compartir datos y comportamiento comÃºn.
Cliente y Empleado son clases concretas.
El servicio se diseÃ±a para el mÃ³dulo que se va a operar. En esta ruta se usa ProductoService para mantener continuidad con el CRUD del curso.
```

Importante para no confundir con base de datos:

```text
Herencia en POO:
Cliente es una Persona especializada.
No representa dos objetos separados relacionados uno a uno.

AsociaciÃ³n uno a uno:
Dos objetos existen con identidad propia.
Si eliminas uno, el otro podrÃ­a seguir existiendo segÃºn la regla del negocio.

Herencia mapeada a BD con tabla padre + tabla hija:
persona(id, nombre, documento)
cliente(id_persona, telefono)

AquÃ­ la tabla hija extiende a la tabla padre.
Si eliminas el cliente, tambiÃ©n debe eliminarse la parte persona que le pertenece.
Por eso se parece a uno a uno en tablas, pero conceptualmente sigue siendo herencia.
```

### 3.2 Crear la clase base abstracta

```java
public abstract class Persona {
    private String nombre;
    private String documento;

    public Persona(String nombre, String documento) {
        this.nombre = nombre;
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public abstract void mostrarPerfil();
}
```

### 3.3 Crear subclases con extends

```java
public class Cliente extends Persona {
    private String telefono;

    public Cliente(String nombre, String documento, String telefono) {
        super(nombre, documento);
        this.telefono = telefono;
    }

    @Override
    public void mostrarPerfil() {
        System.out.println("Cliente: " + getNombre() + " - " + telefono);
    }
}
```

```java
public class Empleado extends Persona {
    private String cargo;

    public Empleado(String nombre, String documento, String cargo) {
        super(nombre, documento);
        this.cargo = cargo;
    }

    @Override
    public void mostrarPerfil() {
        System.out.println("Empleado: " + getNombre() + " - " + cargo);
    }
}
```

### 3.4 Probar herencia desde Main

```java
public class Main {
    public static void main(String[] args) {
        Persona cliente = new Cliente("Ana Torres", "71234567", "999888777");
        Persona empleado = new Empleado("Luis Ramos", "73456789", "Vendedor");

        cliente.mostrarPerfil();
        empleado.mostrarPerfil();
    }
}
```

### 3.5 Definir el contrato del servicio

```java
import java.util.ArrayList;

public interface ProductoService {
    void registrar(Producto producto);
    ArrayList<Producto> listar();
    Producto buscarPorCodigo(String codigo);
}
```

En polimorfismo se programa contra el contrato. Eso significa que el resto del sistema conoce `ProductoService` y no necesita saber si la implementaciÃ³n trabaja en memoria o con base de datos.

Regla prÃ¡ctica:

```text
Si un mÃ©todo no estÃ¡ declarado en ProductoService, Main no debe depender de ese mÃ©todo.
AsÃ­ se puede cambiar ProductoServiceImplMemoria por ProductoServiceImplDB sin romper el flujo principal.
```

### 3.6 Crear dos implementaciones

ImplementaciÃ³n en memoria:

```java
import java.util.ArrayList;

public class ProductoServiceImplMemoria implements ProductoService {
    private ArrayList<Producto> productos = new ArrayList<>();

    @Override
    public void registrar(Producto producto) {
        productos.add(producto);
    }

    @Override
    public ArrayList<Producto> listar() {
        return productos;
    }

    @Override
    public Producto buscarPorCodigo(String codigo) {
        for (Producto producto : productos) {
            if (producto.getCodigo().equals(codigo)) {
                return producto;
            }
        }
        return null;
    }
}
```

ImplementaciÃ³n con base de datos, aun cÃ³mo preparaciÃ³n conceptual:

```java
import java.util.ArrayList;

public class ProductoServiceImplDB implements ProductoService {
    @Override
    public void registrar(Producto producto) {
        System.out.println("Luego guardara usando DAO");
    }

    @Override
    public ArrayList<Producto> listar() {
        return new ArrayList<>();
    }

    @Override
    public Producto buscarPorCodigo(String codigo) {
        return null;
    }
}
```

### 3.7 Probar polimorfismo con interface

```java
public class Main {
    public static void main(String[] args) {
        ProductoService service = new ProductoServiceImplMemoria();

        service.registrar(new Producto("P001", "Teclado", 80.0, 10));
        service.registrar(new Producto("P002", "Mouse", 45.0, 15));

        for (Producto producto : service.listar()) {
            producto.mostrarInformacion();
        }
    }
}
```

Observa que la variable es de tipo `ProductoService`:

```java
ProductoService service = new ProductoServiceImplMemoria();
```

La implementaciÃ³n concreta es `ProductoServiceImplMemoria`, pero el acceso se hace por el contrato. Esta es la idea central del polimorfismo con interfaces.

## 4. Crea: actividad autÃ³noma

Fuera del aula, cada estudiante consolida el aprendizaje aplicando herencia e interfaces en una parte del dominio y preparando una evidencia individual.

Tiempo: 2h fuera del aula.

### 4.1 Plantilla de evidencia individual

Entrega un PDF con el siguiente nombre:

```text
S04_Equipo##_ApellidoNombre.pdf
```

Ejemplo:

```text
S04_Equipo03_QuispeAna.pdf
```

El PDF debe usar esta estructura. La primera secciÃ³n define el trabajo autÃ³nomo; completa las demÃ¡s con tus evidencias.

#### 4.1.1 Datos del estudiante

- Nombre:
- Equipo:
- SesiÃ³n: S04 - Herencia, interfaces y polimorfismo
- Rol o aporte realizado:
- Link de GitHub:

#### 4.1.2 Trabajo autÃ³nomo realizado

Completa y evidencia estas tareas:

1. Elegir una relaciÃ³n `es-un` del dominio.
2. Crear una clase base abstracta.
3. Crear dos subclases con `extends`.
4. Sobrescribir al menos un mÃ©todo con `@Override`.
5. Crear una interface de servicio.
6. Crear dos implementaciones con `implements`.
7. Probar desde `Main` usando una referencia de la clase base.
8. Probar desde `Main` usando una referencia de la interface.

#### 4.1.3 Evidencia tÃ©cnica

Incluye capturas o salidas de consola con una breve explicaciÃ³n debajo de cada una:

- Una clase base abstracta.
- Dos subclases con `extends`.
- Un mÃ©todo sobrescrito con `@Override`.
- Una interface de servicio.
- Dos implementaciones con `implements`.
- Prueba desde `Main` usando una referencia de la clase base y una referencia de la interface.
- ExplicaciÃ³n de por quÃ© `extends` e `implements` resuelven problemas distintos.

#### 4.1.4 Error o hallazgo

Describe al menos un error, diferencia o hallazgo tÃ©cnico:

- QuÃ© ocurriÃ³.
- CÃ³mo lo diagnosticaste.
- CÃ³mo lo corregiste o quÃ© aprendiste.

Ejemplos vÃ¡lidos:

- Se intentÃ³ usar herencia sin relaciÃ³n `es-un`.
- Se confundiÃ³ clase abstracta con interface.
- Una implementaciÃ³n no cumplÃ­a todos los mÃ©todos del contrato.
- La prueba no evidenciaba polimorfismo.

#### 4.1.5 ReflexiÃ³n tÃ©cnica breve

Responde en 5 a 8 lÃ­neas:

```text
Por quÃ© las entidades usan herencia con cuidado y los servicios pueden usar interfaces para cambiar implementaciÃ³n?
```

### 4.2 Criterios mÃ­nimos de aceptaciÃ³n

La evidencia individual se considera completa si:

- El archivo respeta el nombre `S04_Equipo##_ApellidoNombre.pdf`.
- Incluye evidencias tÃ©cnicas legibles.
- Muestra una jerarquÃ­a con `extends`.
- Muestra una interface y dos implementaciones con `implements`.
- Incluye prueba de herencia y prueba de polimorfismo.
- Justifica por quÃ© la herencia tiene sentido en el dominio.
- No contiene solo pantallazos: cada evidencia tiene una descripciÃ³n breve.

## 5. Cierre evaluativo

Tiempo: 20 min.

Esta secciÃ³n conecta el resultado de aprendizaje de la sesiÃ³n con el producto que debe evidenciar cada estudiante.

### 5.1 Resultados esperados

Al finalizar la sesiÃ³n, el estudiante debe demostrar que:

- La herencia responde a una relaciÃ³n `es-un`.
- La clase base no reemplaza a las entidades concretas.
- En herencia, los casos funcionales se trabajan sobre clases concretas cuÃ¡ndo la clase base es abstracta.
- El estudiante diferencia herencia de una asociaciÃ³n uno a uno.
- Comprende que la estrategia tabla padre + tabla hija es una forma de persistir herencia, no una asociaciÃ³n comÃºn.
- Hay sobrescritura de comportamiento cuando corresponde.
- La interface declara operaciones y no guarda datos.
- Las implementaciones cumplen el contrato con `implements`.
- En polimorfismo, el acceso se realiza por el contrato y no por mÃ©todos propios de una implementaciÃ³n.
- El estudiante diferencia `extends` de `implements`.

### 5.2 Evidencia del producto de sesiÃ³n

Cada estudiante entrega un PDF individual siguiendo la plantilla de la secciÃ³n 4.1.

Nombre del archivo:

```text
S04_Equipo##_ApellidoNombre.pdf
```

La evidencia debe demostrar:

- Producto de sesiÃ³n construido.
- Aporte individual verificable.
- Herencia aplicada con sentido.
- Interface con dos implementaciones.
- ReflexiÃ³n tÃ©cnica breve.

La revisiÃ³n se realiza con los criterios mÃ­nimos de aceptaciÃ³n de la secciÃ³n 4.2 y la rÃºbrica de la secciÃ³n 5.4.

### 5.3 Preguntas de defensa y reflexiÃ³n

1. Por quÃ© `Cliente` puede heredar de `Persona`?
2. Por quÃ© `ProductoService` debe ser interface?
3. QuÃ© clase implementa el contrato en memoria?
4. QuÃ© ventaja da declarar `ProductoService service = new ProductoServiceImplMemoria()`?
5. Por quÃ© no conviene que una entidad implemente un contrato de servicio?
6. Por quÃ© `Main` no debe depender de mÃ©todos que solo existan en `ProductoServiceImplMemoria`?
7. Por quÃ© herencia no es igual que una asociaciÃ³n uno a uno?
8. QuÃ© pasarÃ­a en BD si se elimina un `Cliente` guardado con tabla padre `persona` y tabla hija `cliente`?
9. CuÃ¡ndo no conviene usar herencia?

### 5.4 RÃºbrica de evaluaciÃ³n

| DimensiÃ³n | Peso | 3 - Logro destacado | 2 - Logro | 1 - Proceso | 0 - Inicio | PuntuaciÃ³n obtenida |
|---|---:|---|---|---|---|---:|
| 1. Herencia en entidades | 2 | Aplica herencia con relaciÃ³n `es-un` clara y clase base adecuada. | Herencia funcional y razonable. | Herencia parcial o forzada. | No evidencia herencia. | |
| 2. Sobrescritura | 1 | Usa `@Override` con comportamiento especializado. | Usa sobrescritura funcional. | Sobrescritura poco clara. | No evidencia sobrescritura. | |
| 3. Interface y contrato | 2 | Interface declara operaciones coherentes y no mezcla datos. | Interface funcional. | Contrato incompleto o confuso. | No evidencia interface. | |
| 4. Polimorfismo con implementaciones | 2 | Dos implementaciones probadas con referencia de interface. | ImplementaciÃ³n principal funcional. | Implementaciones incompletas. | No evidencia polimorfismo. | |
| 5. Error o hallazgo | 1 | Analiza error/hallazgo, causa, soluciÃ³n y aprendizaje tÃ©cnico. | Explica un problema y una soluciÃ³n. | Menciona un problema sin anÃ¡lisis. | No presenta error ni hallazgo. | |
| 6. ReflexiÃ³n y orden | 2 | PDF ordenado, evidencias legibles y reflexiÃ³n precisa sobre `extends` vs `implements`. | Evidencias suficientes y reflexiÃ³n clara. | Evidencias incompletas o reflexiÃ³n superficial. | PDF desordenado o sin reflexiÃ³n. | |

PuntuaciÃ³n acumulada = suma de (`Peso` * `PuntuaciÃ³n obtenida`) = ____.

Nota final = (`PuntuaciÃ³n acumulada` / 30) * 20 = ____.

Para usar la rÃºbrica con IA, solicita:

```text
EvalÃºa el PDF usando la rÃºbrica de la sesiÃ³n.
Para cada dimensiÃ³n selecciona la puntuaciÃ³n obtenida usando la escala Inicio=0, Proceso=1, Logro=2, Logro destacado=3.
Justifica brevemente cada puntuaciÃ³n.
Calcula la puntuaciÃ³n acumulada con la fÃ³rmula: suma de (Peso * PuntuaciÃ³n obtenida).
Calcula la nota final sobre 20 con la fÃ³rmula: (PuntuaciÃ³n acumulada / 30) * 20.
Indica 2 fortalezas y 2 recomendaciones.
```


