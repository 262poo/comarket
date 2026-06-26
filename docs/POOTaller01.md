# Taller POO 01 - Construir el producto U1 en consola

Este taller guía la construcción del producto de la Unidad 1 en `comarket-cli`: una aplicación de consola orientada a objetos, con entidades del dominio, herencia, servicio CRUD, `ArrayList`, validaciones básicas y menú de ejecución.

## 1. Objetivo del taller

Al finalizar, el estudiante tendrá un producto U1 ejecutable desde consola con esta arquitectura:

```mermaid
flowchart TB
    Main["Main / Consola"]

    subgraph ServiceU1["service"]
        direction TB
        ProductoService["ProductoService<br/>&lt;&lt;interface&gt;&gt;"]
        ProductoServiceImplMemoria["ProductoServiceImplMemoria<br/>ArrayList&lt;Producto&gt;"]
    end

    subgraph EntityU1["entity"]
        direction TB
        Producto["Producto"]
        Persona["Persona<br/>clase base"]
        Cliente["Cliente<br/>extends Persona"]
        Usuario["Usuario<br/>extends Persona"]
    end

    Main --> ProductoService
    ProductoServiceImplMemoria -. implements .-> ProductoService
    ProductoService -.-> Producto
    ProductoServiceImplMemoria -. usa .-> Producto
    Main -. prueba .-> Cliente
    Main -. prueba .-> Usuario
    Cliente -- extends --> Persona
    Usuario -- extends --> Persona

    classDef serviceImpl fill:#dbeafe,stroke:#2563eb,stroke-width:2px,color:#1e3a8a;
    classDef personaRole fill:#fef3c7,stroke:#d97706,stroke-width:3px,color:#78350f;
    class ProductoServiceImplMemoria serviceImpl;
    class Cliente,Usuario personaRole;
```

## 2. Carpeta de trabajo

Trabajar dentro de:

```text
comarket-cli/
```

Estructura esperada:

```text
comarket-cli/
├── pom.xml
└── src/
    └── main/
        └── java/
            └── com/upeu/comarket/
                ├── app/Main.java
                ├── entity/Persona.java
                ├── entity/Cliente.java
                ├── entity/Usuario.java
                ├── entity/Producto.java
                ├── service/ProductoService.java
                └── service/ProductoServiceImplMemoria.java
```

## 3. Crear el proyecto Maven

El proyecto usa Java 17 y ejecuta `com.upeu.comarket.app.Main`.

Desde `comarket-cli`:

```bash
mvn compile
mvn exec:java
```

## 4. Entidades del dominio

El modelo mínimo tiene cuatro clases:

| Clase | Responsabilidad |
|---|---|
| `Persona` | Reúne datos comunes: DNI, nombre y email. |
| `Cliente` | Hereda de `Persona` y representa al cliente del negocio. |
| `Usuario` | Hereda de `Persona` y representa al usuario interno del sistema. |
| `Producto` | Entidad principal para registrar, listar, buscar, actualizar y eliminar. |

## 5. Servicio CRUD

`ProductoService` define el contrato:

```java
void registrar(Producto producto);
List<Producto> listar();
Producto buscarPorCodigo(String codigo);
boolean actualizar(Producto producto);
boolean eliminar(String codigo);
```

`ProductoServiceImplMemoria` implementa el contrato usando:

```java
private final List<Producto> productos = new ArrayList<>();
```

Reglas mínimas:

1. No registrar códigos repetidos.
2. No permitir nombre vacío.
3. No permitir precio negativo.
4. No permitir stock negativo.
5. Buscar, actualizar y eliminar por código.

## 6. Menú de consola

`Main` debe usar el contrato, no depender directamente de la implementación:

```java
ProductoService service = new ProductoServiceImplMemoria();
```

Opciones mínimas:

```text
1. Registrar producto
2. Listar productos
3. Buscar producto
4. Actualizar producto
5. Eliminar producto
6. Ver personas de prueba
0. Salir
```

## 7. Pruebas mínimas

Registrar:

```text
Codigo: P001
Nombre: Arroz
Precio: 4.50
Stock: 20
```

Probar:

1. Listar productos.
2. Buscar `P001`.
3. Actualizar precio y stock.
4. Eliminar `P001`.
5. Intentar registrar un código repetido.
6. Intentar registrar precio negativo.

## 8. Evidencia de entrega

Entregar un PDF con:

1. Captura de estructura de paquetes.
2. Captura de `Producto`, `Persona`, `Cliente` y `Usuario`.
3. Captura de `ProductoService`.
4. Captura de `ProductoServiceImplMemoria`.
5. Captura del menú ejecutándose.
6. Evidencia de registrar, listar, buscar, actualizar y eliminar.
7. Explicación breve de dónde se aplica encapsulamiento, herencia, interface, polimorfismo y `ArrayList`.

Nombre sugerido:

```text
Taller01_Equipo##_ApellidoNombre.pdf
```

## 9. Criterios de revisión

| Criterio | Logro esperado |
|---|---|
| Entidades | `Producto`, `Persona`, `Cliente` y `Usuario` están encapsuladas. |
| Herencia | `Cliente` y `Usuario` extienden de `Persona`. |
| Servicio | `ProductoService` define el contrato CRUD. |
| Polimorfismo | `Main` usa `ProductoService`, no la implementación directamente. |
| Memoria | `ProductoServiceImplMemoria` usa `ArrayList<Producto>`. |
| Menú | El CRUD se prueba desde consola. |
| Validación | Hay controles básicos de datos obligatorios, precio, stock y código repetido. |
