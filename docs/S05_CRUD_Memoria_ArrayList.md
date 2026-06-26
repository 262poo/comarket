# S5 - Operaciones CRUD, validaciones y responsabilidad Ãºnica

## 1. IntroducciÃ³n

Tiempo: 20 min.

### 1.1 PropÃ³sito

Implementar operaciones CRUD en memoria usando `ArrayList`, validaciones de flujo y responsabilidad Ãºnica, mediante una interface de servicio y una implementaciÃ³n concreta, sin cargar toda la lÃ³gica en `Main`.

### 1.2 Resultado de aprendizaje

El estudiante implementa registro, listado, bÃºsqueda, actualizaciÃ³n y eliminaciÃ³n en memoria, aplica validaciones bÃ¡sicas y consolida la separaciÃ³n de responsabilidades entre `Main`, contrato de servicio, implementaciÃ³n y entidades.

### 1.3 Producto de sesiÃ³n

CRUD en consola con `ProductoService`, `ProductoServiceImplMemoria`, entidades encapsuladas, validaciones, bÃºsqueda por cÃ³digo, menÃº de consola y preparaciÃ³n de entrega con Maven/GraalVM.

### 1.4 MotivaciÃ³n de la sesiÃ³n

DespuÃ©s de modelar clases, relaciones, herencia e interfaces, el producto necesita operaciones reales. El objetivo es que el menÃº de consola use un servicio, que el servicio administre la colecciÃ³n y que cada clase mantenga una responsabilidad principal.

Pregunta guÃ­a:

```text
CÃ³mo hacemos un CRUD en memoria sin convertir Main en una clase gigante?
```

### 1.5 UbicaciÃ³n en el curso

- Unidad: U1.
- Producto de unidad: aplicaciÃ³n de consola en memoria.
- Carpeta de trabajo: `comarket-cli`.
- Avance de sesiÃ³n: versiÃ³n funcional previa a la evaluaciÃ³n U1.

## 2. Explica

Tiempo: 25 min.

### 2.1 Conceptos clave

| Concepto | Idea central |
|---|---|
| CRUD | Crear, leer, actualizar y eliminar datos. |
| Interface de servicio | Contrato quÃ© define las operaciones esperadas. |
| ImplementaciÃ³n en memoria | Clase quÃ© cumple el contrato usando `ArrayList`. |
| Busqueda | Recorrido de la colecciÃ³n para ubicar un objeto. |
| Validaciones bÃ¡sicas | Reglas simples antes de registrar o actualizar. |
| Responsabilidad Ãºnica | Cada clase debe tener un motivo principal para cambiar. |
| Maven | Organiza compilaciÃ³n y estructura del proyecto. |
| GraalVM | Permite preparar un ejecutable nativo cÃ³mo cierre de U1. |

Regla mÃ©todolÃ³gica de la sesiÃ³n:

```text
Main muestra el menu y recibe opciones.
La interface declara operaciones CRUD.
La implementaciÃ³n en memoria administra el ArrayList.
Las entidades representan datos y comportamiento del dominio.
La responsabilidad Ãºnica se consolida evitando que Main, service y entidades hagan el mismo trabajo.
Maven/GraalVM son parte de la entrega, no del flujo CRUD.
```

### 2.2 Arquitectura de la sesiÃ³n

```mermaid
classDiagram
    class Main {
        main(String[] args)
        mostrarMenu()
    }
    class Producto {
        -codigo
        -nombre
        -precio
        -stock
        mostrarInformacion()
    }
    class ProductoService {
        <<interface>>
        registrar(producto)
        listar()
        buscarPorCodigo(codigo)
        actualizar(producto)
        eliminar(codigo)
    }
    class ProductoServiceImplMemoria {
        -productos: ArrayList
        registrar(producto)
        listar()
        buscarPorCodigo(codigo)
        actualizar(producto)
        eliminar(codigo)
    }

    Main ..> ProductoService : usa contrato
    Main ..> Producto : crea datos
    ProductoService <|.. ProductoServiceImplMemoria : implements
    ProductoService ..> Producto : usa
    ProductoServiceImplMemoria ..> Producto : usa

    classDef serviceImpl fill:#dbeafe,stroke:#2563eb,stroke-width:2px,color:#1e3a8a;
    class ProductoServiceImplMemoria serviceImpl;
```

En S5, la arquitectura U1 se concreta con `Producto`, `ProductoService` y `ProductoServiceImplMemoria`. Este ejemplo guiado sirve como patrÃ³n para que luego cada equipo adapte el CRUD a la entidad principal de su propio proyecto.

## 3. Aplica: actividad prÃ¡ctica guiada

Tiempo: 2h.

### 3.1 Definir contrato CRUD

```java
import java.util.ArrayList;

public interface ProductoService {
    void registrar(Producto producto);
    ArrayList<Producto> listar();
    Producto buscarPorCodigo(String codigo);
    void actualizar(Producto producto);
    void eliminar(String codigo);
}
```

### 3.2 Crear implementaciÃ³n en memoria

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

    @Override
    public void actualizar(Producto productoActualizado) {
        Producto producto = buscarPorCodigo(productoActualizado.getCodigo());
        if (producto != null) {
            producto.setNombre(productoActualizado.getNombre());
            producto.setPrecio(productoActualizado.getPrecio());
            producto.setStock(productoActualizado.getStock());
        }
    }

    @Override
    public void eliminar(String codigo) {
        Producto producto = buscarPorCodigo(codigo);
        if (producto != null) {
            productos.remove(producto);
        }
    }
}
```

### 3.3 Probar operaciones desde Main

```java
public class Main {
    public static void main(String[] args) {
        ProductoService service = new ProductoServiceImplMemoria();

        service.registrar(new Producto("P001", "Teclado", 80.0, 10));
        service.registrar(new Producto("P002", "Mouse", 45.0, 15));

        Producto encontrado = service.buscarPorCodigo("P001");
        if (encontrado != null) {
            encontrado.mostrarInformacion();
        }

        service.eliminar("P002");
        System.out.println("Total productos: " + service.listar().size());
    }
}
```

### 3.4 Agregar menu de consola

El menu debe llamar al contrato `ProductoService`, no directamente al `ArrayList`.

Opciones mÃ­nimas:

1. Registrar producto.
2. Listar productos.
3. Buscar producto.
4. Actualizar producto.
5. Eliminar producto.
6. Salir.

### 3.5 Organizar con Maven

La migraciÃ³n a Maven se realiza al cierre de la unidad para preparar compilaciÃ³n ordenada.

Estructura mÃ­nima:

```text
src/main/java/
    Main.java
    entity/Producto.java
    service/ProductoService.java
    service/ProductoServiceImplMemoria.java
pom.xml
```

### 3.6 Preparar entrega con GraalVM

En esta sesiÃ³n no se ensena GraalVM cÃ³mo arquitectura del sistema. Se usa cÃ³mo mecanismo de entrega para cerrar U1 con un ejecutable demostrable.

## 4. Crea: actividad autÃ³noma

Fuera del aula, cada estudiante consolida el aprendizaje completando un CRUD en memoria y preparando una evidencia individual.

Tiempo: 3h fuera del aula.

### 4.1 Plantilla de evidencia individual

Entrega un PDF con el siguiente nombre:

```text
S05_Equipo##_ApellidoNombre.pdf
```

Ejemplo:

```text
S05_Equipo03_QuispeAna.pdf
```

El PDF debe usar esta estructura. La primera secciÃ³n define el trabajo autÃ³nomo; completa las demÃ¡s con tus evidencias.

#### 4.1.1 Datos del estudiante

- Nombre:
- Equipo:
- SesiÃ³n: S05 - Operaciones CRUD, validaciones y responsabilidad Ãºnica
- Rol o aporte realizado:
- Link de GitHub:

#### 4.1.2 Trabajo autÃ³nomo realizado

Completa y evidencia estas tareas:

1. Completar el CRUD de una entidad del dominio.
2. Definir o ajustar una interface CRUD.
3. Implementar el CRUD en memoria con `ArrayList`.
4. Probar registro, listado, bÃºsqueda, actualizaciÃ³n y eliminaciÃ³n.
5. Agregar al menos una validaciÃ³n bÃ¡sica.
6. Mostrar un menÃº de consola o flujo equivalente desde `Main`.
7. Organizar el proyecto para la evaluaciÃ³n de U1.

#### 4.1.3 Evidencia tÃ©cnica

Incluye capturas o salidas de consola con una breve explicaciÃ³n debajo de cada una:

- Interface CRUD.
- ImplementaciÃ³n en memoria.
- MenÃº de consola.
- Salida de registrar, listar, buscar, actualizar y eliminar.
- Evidencia de proyecto organizado con Maven.
- Evidencia de preparaciÃ³n o generaciÃ³n de ejecutable nativo si corresponde.
- ExplicaciÃ³n del flujo `Main -> Interface -> ImplementaciÃ³n en memoria -> Entidades`, indicando que el `ArrayList` es un atributo interno de la implementaciÃ³n.

#### 4.1.4 Error o hallazgo

Describe al menos un error, diferencia o hallazgo tÃ©cnico:

- QuÃ© ocurriÃ³.
- CÃ³mo lo diagnosticaste.
- CÃ³mo lo corregiste o quÃ© aprendiste.

Ejemplos vÃ¡lidos:

- La bÃºsqueda no encontraba objetos por usar mal el criterio.
- La actualizaciÃ³n modificaba el objeto incorrecto.
- La eliminaciÃ³n fallaba al recorrer la lista.
- `Main` terminÃ³ concentrando lÃ³gica que debÃ­a estar en el service.

#### 4.1.5 ReflexiÃ³n tÃ©cnica breve

Responde en 5 a 8 lÃ­neas:

```text
Por quÃ© un CRUD en memoria debe estar separado de Main aunque todavÃ­a no exista base de datos?
```

### 4.2 Criterios mÃ­nimos de aceptaciÃ³n

La evidencia individual se considera completa si:

- El archivo respeta el nombre `S05_Equipo##_ApellidoNombre.pdf`.
- Incluye evidencias tÃ©cnicas legibles.
- Muestra una interface CRUD.
- Muestra una implementaciÃ³n en memoria con `ArrayList`.
- Demuestra registrar, listar, buscar, actualizar y eliminar.
- Muestra al menos una validaciÃ³n bÃ¡sica.
- Explica el flujo de responsabilidades.
- No contiene solo pantallazos: cada evidencia tiene una descripciÃ³n breve.

## 5. Cierre evaluativo

Tiempo: 20 min.

Esta secciÃ³n conecta el resultado de aprendizaje de la sesiÃ³n con el producto que debe evidenciar cada estudiante.

### 5.1 Resultados esperados

Al finalizar la sesiÃ³n, el estudiante debe demostrar que:

- Existe CRUD funcional en memoria.
- `Main` no contiene el `ArrayList` principal.
- La interface declara el contrato.
- La implementaciÃ³n en memoria administra la colecciÃ³n.
- Las entidades se mantienen encapsuladas.
- El proyecto queda listo para evaluaciÃ³n U1.

### 5.2 Evidencia del producto de sesiÃ³n

Cada estudiante entrega un PDF individual siguiendo la plantilla de la secciÃ³n 4.1.

Nombre del archivo:

```text
S05_Equipo##_ApellidoNombre.pdf
```

La evidencia debe demostrar:

- Producto de sesiÃ³n construido.
- Aporte individual verificable.
- CRUD en memoria probado.
- ReflexiÃ³n tÃ©cnica breve.

La revisiÃ³n se realiza con los criterios mÃ­nimos de aceptaciÃ³n de la secciÃ³n 4.2 y la rÃºbrica de la secciÃ³n 5.4.

### 5.3 Preguntas de defensa y reflexiÃ³n

1. QuÃ© responsabilidad tiene `Main`?
2. QuÃ© responsabilidad tiene `ProductoService`?
3. DÃ³nde se almacena temporalmente la informaciÃ³n?
4. Por quÃ© `ArrayList` no debe estar como variable principal en `Main`?
5. QuÃ© cambiarÃ­a cuando el almacenamiento sea SQLite?
6. QuÃ© evidencia demuestra que el CRUD estÃ¡ completo?

### 5.4 RÃºbrica de evaluaciÃ³n

| DimensiÃ³n | Peso | 3 - Logro destacado | 2 - Logro | 1 - Proceso | 0 - Inicio | PuntuaciÃ³n obtenida |
|---|---:|---|---|---|---|---:|
| 1. Contrato CRUD | 2 | Interface clara, completa y coherente con la entidad. | Interface funcional. | Interface incompleta o poco clara. | No evidencia contrato. | |
| 2. ImplementaciÃ³n en memoria | 2 | `ArrayList` bien encapsulado en la implementaciÃ³n. | ImplementaciÃ³n funcional. | ImplementaciÃ³n parcial. | No evidencia implementaciÃ³n. | |
| 3. Operaciones CRUD | 2 | Registrar, listar, buscar, actualizar y eliminar funcionan y estÃ¡n evidenciados. | Operaciones principales funcionan. | CRUD incompleto. | No evidencia CRUD. | |
| 4. SeparaciÃ³n de responsabilidades | 2 | `Main`, interface, implementaciÃ³n y entidades tienen roles claros. | SeparaciÃ³n suficiente. | LÃ³gica mezclada. | No separa responsabilidades. | |
| 5. Error o hallazgo | 1 | Analiza error/hallazgo, causa, soluciÃ³n y aprendizaje tÃ©cnico. | Explica un problema y una soluciÃ³n. | Menciona un problema sin anÃ¡lisis. | No presenta error ni hallazgo. | |
| 6. ReflexiÃ³n y orden | 1 | PDF ordenado, evidencias legibles y reflexiÃ³n precisa. | Evidencias suficientes y reflexiÃ³n clara. | Evidencias incompletas o reflexiÃ³n superficial. | PDF desordenado o sin reflexiÃ³n. | |

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

