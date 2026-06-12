# S5 - CRUD en memoria con ArrayList

## 1. Introducción

Tiempo: 20 min.

### 1.1 Propósito

Implementar operaciones CRUD en memoria usando `ArrayList`, una interface de servicio y una implementacion concreta, sin cargar toda la lógica en `Main`.

### 1.2 Resultado de aprendizaje

El estudiante implementa registro, listado, busqueda, actualizacion y eliminación en memoria, separando `Main`, contrato de servicio, implementacion y entidades.

### 1.3 Producto de sesión

CRUD en consola con `ClienteService`, `ClienteServiceMemoria`, entidades encapsuladas, busqueda por documento y preparación de entrega con Maven/GraalVM.

### 1.4 Motivación de la sesión

Después de modelar clases, relaciones, herencia e interfaces, el producto necesita operaciones reales. El objetivo es qué el menu de consola use un servicio, y qué el servicio sea quien administre la colección.

Pregunta guía:

```text
Cómo hacemos un CRUD en memoria sin convertir Main en una clase gigante?
```

### 1.5 Ubicación en el curso

- Unidad: U1.
- Producto de unidad: aplicación de consola en memoria.
- Avance de sesión: versión funcional previa a la evaluación U1.

## 2. Explica

Tiempo: 25 min.

### 2.1 Conceptos clave

| Concepto | Idea central |
|---|---|
| CRUD | Crear, leer, actualizar y eliminar datos. |
| Interface de servicio | Contrato qué define las operaciones esperadas. |
| Implementacion en memoria | Clase qué cumple el contrato usando `ArrayList`. |
| Busqueda | Recorrido de la colección para ubicar un objeto. |
| Validaciones básicas | Reglas simples antes de registrar o actualizar. |
| Maven | Organiza compilación y estructura del proyecto. |
| GraalVM | Permite preparar un ejecutable nativo cómo cierre de U1. |

Regla métodológica de la sesión:

```text
Main muestra el menu y recibe opciones.
La interface declara operaciones CRUD.
La implementacion en memoria administra el ArrayList.
Las entidades representan datos y comportamiento del dominio.
Maven/GraalVM son parte de la entrega, no del flujo CRUD.
```

### 2.2 Arquitectura de la sesión

```mermaid
classDiagram
    class Main {
        main(String[] args)
        mostrarMenu()
    }
    class Cliente {
        -nombre
        -documento
        -telefono
        mostrarPerfil()
    }
    class ClienteService {
        <<interface>>
        registrar(cliente)
        listar()
        buscarPorDocumento(documento)
        actualizar(cliente)
        eliminar(documento)
    }
    class ClienteServiceMemoria {
        -clientes
        registrar(cliente)
        listar()
        buscarPorDocumento(documento)
        actualizar(cliente)
        eliminar(documento)
    }
    class ArrayListClientes {
        ArrayList de clientes
    }

    Main ..> ClienteService : usa contrato
    Main ..> Cliente : crea datos
    ClienteService <|.. ClienteServiceMemoria : implements
    ClienteService ..> Cliente : usa
    ClienteServiceMemoria ..> Cliente : usa
    ClienteServiceMemoria o-- ArrayListClientes : administra
```

## 3. Aplica: actividad practica guíada

Tiempo: 2h.

### 3.1 Definir contrato CRUD

```java
import java.util.ArrayList;

public interface ClienteService {
    void registrar(Cliente cliente);
    ArrayList<Cliente> listar();
    Cliente buscarPorDocumento(String documento);
    void actualizar(Cliente cliente);
    void eliminar(String documento);
}
```

### 3.2 Crear implementacion en memoria

```java
import java.util.ArrayList;

public class ClienteServiceMemoria implements ClienteService {
    private ArrayList<Cliente> clientes = new ArrayList<>();

    @Override
    public void registrar(Cliente cliente) {
        clientes.add(cliente);
    }

    @Override
    public ArrayList<Cliente> listar() {
        return clientes;
    }

    @Override
    public Cliente buscarPorDocumento(String documento) {
        for (Cliente cliente : clientes) {
            if (cliente.getDocumento().equals(documento)) {
                return cliente;
            }
        }
        return null;
    }

    @Override
    public void actualizar(Cliente clienteActualizado) {
        Cliente cliente = buscarPorDocumento(clienteActualizado.getDocumento());
        if (cliente != null) {
            cliente.setNombre(clienteActualizado.getNombre());
            cliente.setTelefono(clienteActualizado.getTelefono());
        }
    }

    @Override
    public void eliminar(String documento) {
        Cliente cliente = buscarPorDocumento(documento);
        if (cliente != null) {
            clientes.remove(cliente);
        }
    }
}
```

### 3.3 Probar operaciones desde Main

```java
public class Main {
    public static void main(String[] args) {
        ClienteService service = new ClienteServiceMemoria();

        service.registrar(new Cliente("Ana Torres", "71234567", "999888777"));
        service.registrar(new Cliente("Marco Ruiz", "72345678", "988777666"));

        Cliente encontrado = service.buscarPorDocumento("71234567");
        if (encontrado != null) {
            encontrado.mostrarPerfil();
        }

        service.eliminar("72345678");
        System.out.println("Total clientes: " + service.listar().size());
    }
}
```

### 3.4 Agregar menu de consola

El menu debe llamar al contrato `ClienteService`, no directamente al `ArrayList`.

Opciones mínimas:

1. Registrar cliente.
2. Listar clientes.
3. Buscar cliente.
4. Actualizar cliente.
5. Eliminar cliente.
6. Salir.

### 3.5 Organizar con Maven

La migracion a Maven se realiza al cierre de la unidad para preparar compilación ordenada.

Estructura mínima:

```text
src/main/java/
    Main.java
    entidad/Cliente.java
    servicio/ClienteService.java
    servicio/ClienteServiceMemoria.java
pom.xml
```

### 3.6 Preparar entrega con GraalVM

En esta sesión no se ensena GraalVM cómo arquitectura del sistema. Se usa cómo mecanismo de entrega para cerrar U1 con un ejecutable demostrable.

## 4. Crea: actividad autónoma

Tiempo: 3h fuera del aula.

Completa el CRUD de una entidad del dominio.

Entrega evidencia breve con:

- Interface CRUD.
- Implementacion en memoria.
- Menu de consola.
- Salida de registrar, listar, buscar, actualizar y eliminar.
- Evidencia de proyecto organizado con Maven.
- Evidencia de preparación o generacion de ejecutable nativo si corresponde.

## 5. Cierre evaluativo

Tiempo: 20 min.

### 5.1 Resultados esperados

- CRUD funcional en memoria.
- `Main` no contiene el `ArrayList` principal.
- La interface declara el contrato.
- La implementacion en memoria administra la colección.
- Las entidades se mantienen encapsuladas.
- El proyecto queda listo para evaluación U1.

### 5.2 Preguntas de defensa

1. Qué responsabilidad tiene `Main`?
2. Qué responsabilidad tiene `ClienteService`?
3. Dónde se almacena temporalmente la información?
4. Por qué `ArrayList` no debe estar cómo variable principal en `Main`?
5. Qué cambiaria cuándo el almacenamiento sea SQLite?
