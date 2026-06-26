# S6 - Modelado orientado a objetos y gestiÃ³n de datos en memoria (EvaluaciÃ³n U1)

## 1. IntroducciÃ³n

Tiempo: 20 min.

### 1.1 PropÃ³sito

Validar el producto de la Unidad 1: aplicaciÃ³n de consola en memoria con modelo orientado a objetos, encapsulamiento, relaciones, herencia o interfaces, servicio CRUD, gestiÃ³n de datos en memoria y evidencia de ejecuciÃ³n.

### 1.2 Resultado de aprendizaje

El estudiante demuestra que puede construir, ejecutar, explicar y defender una aplicaciÃ³n de consola usando modelado orientado a objetos, separaciÃ³n de responsabilidades y gestiÃ³n de datos en memoria.

### 1.3 Producto de sesiÃ³n

Producto U1 integrado: entidades, relaciones, servicios, `ArrayList`, CRUD en memoria, menÃº de consola, proyecto Maven y evidencia de entrega ejecutable.

### 1.4 MotivaciÃ³n de la sesiÃ³n

La evaluaciÃ³n no revisa clases sueltas ni el repaso de S1 como tema central. Revisa si el estudiante puede explicar cÃ³mo las clases colaboran, dÃ³nde se guardan los datos en memoria, cÃ³mo se aplica la separaciÃ³n de responsabilidades y quÃ© evidencia demuestra que el producto funciona.

Preguntas para los estudiantes:

1. QuÃ© evidencia demuestra quÃ© tu producto U1 funciona?
2. QuÃ© parte puedes defender individualmente?
3. QuÃ© revisarias si una operaciÃ³n CRUD falla?

### 1.5 UbicaciÃ³n en el curso

- Unidad: U1 - Fundamentos de la ProgramaciÃ³n Orientada a Objetos.
- Producto de unidad: aplicaciÃ³n de consola funcional en memoria con clases, relaciones entre objetos, colecciones, operaciones principales del dominio y preparaciÃ³n para ejecutable nativo.
- Carpeta de trabajo: `comarket-cli`.
- Avance de sesiÃ³n: evaluaciÃ³n integradora antes de iniciar JavaFX.

## 2. Explica

Tiempo: 15 min.

### 2.1 Conceptos clave

- IntegraciÃ³n: las clases funcionan coordinadamente.
- Evidencia individual: prueba verificable del aporte de cada estudiante.
- DiagnÃ³stico: capacidad de ubicar fallos en `Main`, entidades, servicio o colecciÃ³n.
- Defensa tÃ©cnica: explicacion clara de decisiÃ³nes de modelado y separacion de responsabilidades.

### 2.2 Arquitectura del producto U1

```mermaid
flowchart TB
    Main["Main / Consola"]

    subgraph ServiceU1["service"]
        direction TB
        InterfaceU1["ProductoService<br/>(interface / contrato CRUD)"]
        ImplementacionU1["ProductoServiceImplMemoria<br/>(implementaciÃ³n en memoria)<br/>-productos: ArrayList&lt;Producto&gt;"]
    end

    subgraph EntitiesU1["entity"]
        direction TB
        ProductoU1["Producto<br/>(entidad principal)"]
        PersonaU1["Persona<br/>(clase base)"]
        ClienteU1["Cliente<br/>(extends Persona)"]
        UsuarioU1["Usuario<br/>(extends Persona)"]
    end

    Main --> InterfaceU1
    Main -. prueba .-> ProductoU1
    Main -. prueba .-> ClienteU1
    Main -. prueba .-> UsuarioU1
    InterfaceU1 ~~~ ImplementacionU1
    ImplementacionU1 -. implements .-> InterfaceU1
    InterfaceU1 -.-> ProductoU1
    ImplementacionU1 -. usa .-> ProductoU1
    PersonaU1 ~~~ ClienteU1
    PersonaU1 ~~~ UsuarioU1
    ClienteU1 -- extends --> PersonaU1
    UsuarioU1 -- extends --> PersonaU1

    classDef serviceImpl fill:#dbeafe,stroke:#2563eb,stroke-width:2px,color:#1e3a8a;
    classDef personaRole fill:#fef3c7,stroke:#d97706,stroke-width:3px,color:#78350f;
    class ImplementacionU1 serviceImpl;
    class ClienteU1,UsuarioU1 personaRole;
```

En la evaluaciÃ³n, cada equipo adapta esta arquitectura a su propio proyecto. Si el dominio no usa productos, reemplaza `Producto`, `ProductoService`, `ProductoServiceImplMemoria` y `ArrayList<Producto>` por la entidad principal de su sistema, por ejemplo `Libro`, `Reserva`, `Mascota`, `Paciente` u otra. Lo importante es evidenciar la separaciÃ³n entre consola, contrato de `service`, implementaciÃ³n en memoria, clases de `entity` y colecciÃ³n interna.

Las validaciones bÃ¡sicas se revisan dentro del flujo CRUD, ubicadas en el servicio o en los mÃ©todos de la entidad segÃºn la responsabilidad definida por el equipo.

### 2.3 Criterios mÃ­nimos de revisiÃ³n

- Clases de `entity` encapsuladas.
- Constructores y getters/setters limpios.
- Relaciones entre objetos cuÃ¡ndo corresponde.
- Herencia o interface aplicada con sentido.
- `service` separado de `Main`.
- CRUD en memoria completo.
- Validaciones bÃ¡sicas.
- Evidencia de ejecuciÃ³n por consola.
- Proyecto organizado para entrega.

## 3. Aplica: evaluaciÃ³n practica

Tiempo: 3h.

### 3.1 Preparar demostracion

Orden recomendado:

1. Abrir el proyecto.
2. Verificar que el producto U1 estÃ¡ en `comarket-cli`.
3. Mostrar estructura de paquetes.
4. Ejecutar el programa.
5. Demostrar flujo CRUD completo.
6. Mostrar cÃ³digo clave de entidades y servicio.
7. Explicar una decisiÃ³n tÃ©cnica.

### 3.2 Ejecutar pruebas base

El estudiante demuestra:

1. Registro de datos.
2. Listado de datos.
3. Busqueda por criterio.
4. Actualizacion.
5. EliminaciÃ³n.
6. ValidaciÃ³n bÃ¡sica.
7. Separacion entre `Main`, servicio, entidades y colecciÃ³n.

### 3.3 Demostracion individual

Cada integrante debe poder responder:

- QuÃ© parte implemento.
- QuÃ© clase modifico.
- QuÃ© prueba ejecuto.
- QuÃ© error encontro y cÃ³mo lo corrigio.

## 4. Crea: evidencia individual

Tiempo: 4h fuera del aula.

### 4.1 Plantilla de evidencia individual

Entrega un PDF con el siguiente nombre:

```text
S06_Equipo##_ApellidoNombre.pdf
```

#### 4.1.1 Datos del estudiante

- Nombre:
- Equipo:
- SesiÃ³n: S06 - Modelado orientado a objetos y gestiÃ³n de datos en memoria (EvaluaciÃ³n U1)
- Rol o aporte realizado:
- Link de GitHub:

#### 4.1.2 Trabajo autonomo realizado

1. Ordenar evidencias de U1.
2. Corregir observaciones detectadas.
3. Completar README o descripciÃ³n breve del producto.
4. Preparar defensa individual.
5. Registrar comandos, capturas o salidas de consola.

#### 4.1.3 Evidencia tÃ©cnica

- `entity`.
- Encapsulamiento.
- Relaciones, herencia o interfaces.
- `service` CRUD.
- `ArrayList`.
- Menu de consola.
- EjecuciÃ³n del producto.
- Aporte individual.

#### 4.1.4 Error o hallazgo

Describe un problema encontrado en U1 y cÃ³mo lo diagnosticaste.

#### 4.1.5 ReflexiÃ³n tÃ©cnica breve

Explica cÃ³mo las clases de tu producto forman una aplicaciÃ³n orientada a objetos y no solo un conjunto de variables en `Main`.

### 4.2 Criterios mÃ­nimos de aceptaciÃ³n

- PDF con nombre correcto.
- Evidencia del producto U1 funcionando.
- Evidencia de aporte individual.
- Pruebas por consola.
- Explicacion tÃ©cnica breve.

## 5. Cierre evaluativo

Tiempo: 20 min.

### 5.1 Resultados esperados

- Producto U1 ejecutado.
- CRUD en memoria demostrado.
- Separacion de responsabilidades explicada.
- Evidencia individual entregada.
- Base lista para iniciar JavaFX en U2.

### 5.2 Evidencia del producto de sesiÃ³n

Cada estudiante entrega un PDF individual siguiendo la plantilla de la seccion 4.1.

Nombre del archivo:

```text
S06_Equipo##_ApellidoNombre.pdf
```

### 5.3 Preguntas de defensa y reflexiÃ³n

1. CuÃ¡l fue tu aporte concreto en U1?
2. CÃ³mo se ejecuta el producto?
3. DÃ³nde se almacenan los datos en memoria?
4. CÃ³mo separaste `Main`, servicio y entidades?
5. QuÃ© cambiaria al pasar a GUI en U2?

### 5.4 RÃºbrica de evaluaciÃ³n

| Dimension | Peso | 3 - Logro destacado | 2 - Logro | 1 - Proceso | 0 - Inicio | Puntuacion obtenida |
|---|---:|---|---|---|---|---:|
| 1. Modelo orientado a objetos | 2 | Clases de `entity` claras, encapsuladas y coherentes con el dominio. | Entidades principales correctas. | Entidades incompletas o mezcladas. | No evidencia modelo OO. | |
| 2. Relaciones, herencia o interfaces | 2 | Aplica relaciones y contratos con criterio. | Aplica al menos un mecanismo correctamente. | AplicaciÃ³n parcial o forzada. | No aplica mecanismos OO. | |
| 3. CRUD en memoria | 2 | CRUD completo, probado y separado de `Main`. | CRUD principal funcional. | CRUD incompleto. | No hay CRUD funcional. | |
| 4. Separacion de responsabilidades | 2 | `Main`, servicio, entidades y colecciÃ³n tienen roles claros. | Separacion suficiente. | LÃ³gica mezclada en varias partes. | Todo estÃ¡ concentrado sin criterio. | |
| 5. Evidencia individual | 1 | Evidencia clara, ordenada y verificable. | Evidencia suficiente. | Evidencia incompleta. | No entrega evidencia. | |
| 6. Defensa tÃ©cnica | 1 | Responde con precision y criterio. | Responde adecuadamente. | Responde parcialmente. | No sustenta. | |

Puntuacion acumulada = suma de (`Peso` * `Puntuacion obtenida`) = ____.

Nota final = (`Puntuacion acumulada` / 30) * 20 = ____.

Para usar la rÃºbrica con IA, solicita:

```text
Evalua el PDF usando la rÃºbrica de la sesiÃ³n.
Para cada dimension selecciona la puntuacion obtenida usando la escala Inicio=0, Proceso=1, Logro=2, Logro destacado=3.
Justifica brevemente cada puntuacion.
Calcula la puntuacion acumulada con la formula: suma de (Peso * Puntuacion obtenida).
Calcula la nota final sobre 20 con la formula: (Puntuacion acumulada / 30) * 20.
Indica 2 fortalezas y 2 recomendaciones.
```

