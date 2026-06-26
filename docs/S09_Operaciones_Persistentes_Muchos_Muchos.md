# S9 - Operaciones persistentes con relaciÃ³n muchos a muchos

## 1. IntroducciÃ³n

Tiempo: 20 min.

### 1.1 PropÃ³sito

Implementar operaciones persistentes sobre un modelo con relaciÃ³n muchos a muchos usando una clase de detalle con atributos propios.

### 1.2 Resultado de aprendizaje

El estudiante modela una operaciÃ³n con cabecera y detalle, persiste datos relacionados mediante DAO y mantiene la separaciÃ³n entre controlador, servicio, entidades y persistencia.

### 1.3 Producto de sesiÃ³n

Registro persistente de una operaciÃ³n con detalles: cabecera, lista de detalles, entidad relacionada, cÃ¡lculo de subtotal y total.

### 1.4 MotivaciÃ³n de la sesiÃ³n

DespuÃ©s de persistir una tabla simple, el siguiente reto es registrar una operaciÃ³n real donde una cabecera contiene varios detalles y cada detalle referencia una entidad existente.

Pregunta guÃ­a:

```text
CÃ³mo guardamos una operaciÃ³n con varios detalles sin perder la separaciÃ³n por capas?
```

### 1.5 UbicaciÃ³n en el curso

- Unidad: U2.
- Carpeta de trabajo: `comarket-desk`.
- Avance de sesiÃ³n: persistencia de una relaciÃ³n avanzada muchos a muchos desde objetos.

## 2. Explica

Tiempo: 25 min.

### 2.1 Conceptos clave

- RelaciÃ³n muchos a muchos desde el modelo de objetos.
- Cabecera y detalle.
- Clase intermedia con atributos propios.
- ComposiciÃ³n entre cabecera y detalle.
- AsociaciÃ³n entre detalle y entidad relacionada.
- DAO para cabecera y DAO para detalle.
- TransacciÃ³n o secuencia controlada de guardado.
- Validaciones del flujo.

Regla metodolÃ³gica de la sesiÃ³n:

```text
La relaciÃ³n se entiende primero como relaciÃ³n entre objetos.
La base de datos persiste esa relaciÃ³n mediante tablas.
El detalle no es una pantalla CRUD independiente.
El detalle nace dentro del flujo de la cabecera.
Los DAO se ubican en `dao` y reutilizan `util/ConexionBD` para conectarse a SQLite.
```

### 2.2 Arquitectura de la sesiÃ³n

```mermaid
classDiagram
    class VentaController {
        onAgregarDetalle()
        onGuardarVenta()
        cargarProductos()
    }

    class VentaService {
        <<interface>>
        registrar(venta)
        calcularTotal(venta)
    }

    class VentaServiceImplDB {
        registrar(venta)
        calcularTotal(venta)
    }

    class VentaDAO {
        insertar(venta)
    }

    class DetalleVentaDAO {
        insertar(detalle, ventaId)
    }

    class ConexionBD {
        obtenerConexion()
    }

    class SQLite {
        venta
        detalle_venta
        producto
    }

    class Venta {
        -cliente
        -fecha
        -detalles
        calcularTotal()
    }

    class DetalleVenta {
        -cantidad
        -precioUnitario
        calcularSubtotal()
    }

    class Producto {
        -nombre
        -precio
        -stock
    }

    VentaController ..> VentaService : usa contrato
    VentaService <|.. VentaServiceImplDB : implements
    VentaServiceImplDB --> VentaDAO : usa
    VentaServiceImplDB --> DetalleVentaDAO : usa
    VentaDAO --> ConexionBD : usa
    DetalleVentaDAO --> ConexionBD : usa
    ConexionBD --> SQLite : JDBC
    VentaServiceImplDB ..> Venta : usa
    Venta "1" *-- "*" DetalleVenta : contiene
    DetalleVenta "*" --> "1" Producto : referencia
```

## 3. Aplica: actividad prÃ¡ctica guiada

Tiempo: 2h.

1. Crear o revisar entidades `Venta`, `DetalleVenta` y `Producto`.
2. DiseÃ±ar vista de registro de venta.
3. Cargar productos existentes desde la base de datos.
4. Seleccionar producto y cantidad.
5. Crear `DetalleVenta`.
6. Agregar detalles a la venta.
7. Calcular subtotal y total.
8. Crear `VentaDAO`.
9. Crear `DetalleVentaDAO`.
10. Reutilizar `ConexionBD` desde `util`.
11. Crear `VentaServiceImplDB`.
12. Guardar primero la cabecera y luego los detalles.
13. Validar cantidad, stock, venta sin detalles y errores de persistencia.

Tablas de referencia:

```sql
CREATE TABLE venta (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    cliente TEXT NOT NULL,
    fecha TEXT NOT NULL,
    total REAL NOT NULL
);

CREATE TABLE detalle_venta (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    venta_id INTEGER NOT NULL,
    producto_id INTEGER NOT NULL,
    cantidad INTEGER NOT NULL,
    precio_unitario REAL NOT NULL,
    subtotal REAL NOT NULL,
    FOREIGN KEY (venta_id) REFERENCES venta(id),
    FOREIGN KEY (producto_id) REFERENCES producto(id)
);
```

## 4. Crea: actividad autÃ³noma

Fuera del aula, cada estudiante consolida el registro persistente con detalle y prepara una evidencia individual.

Tiempo: 2h fuera del aula.

### 4.1 Plantilla de evidencia individual

Entrega un PDF con el siguiente nombre:

```text
S09_Equipo##_ApellidoNombre.pdf
```

#### 4.1.1 Datos del estudiante

- Nombre:
- Equipo:
- SesiÃ³n: S09 - Operaciones persistentes con relaciÃ³n muchos a muchos
- Rol o aporte realizado:
- Link de GitHub:

#### 4.1.2 Trabajo autÃ³nomo realizado

1. Completar registro de cabecera y detalle.
2. Evidenciar `Venta`, `DetalleVenta` y `Producto`.
3. Evidenciar DAO de cabecera y DAO de detalle.
4. Mostrar cÃ¡lculo de total.
5. Verificar registros en SQLite.
6. Documentar validaciones aplicadas.

#### 4.1.3 Evidencia tÃ©cnica

- Captura de la pantalla de registro.
- CÃ³digo o fragmento de `VentaServiceImplDB`.
- CÃ³digo o fragmento de `VentaDAO` y `DetalleVentaDAO`.
- Captura de tablas persistidas.
- Evidencia de validaciÃ³n de cantidad, stock o venta sin detalles.

#### 4.1.4 Error o hallazgo

Describe un problema encontrado al guardar cabecera y detalle.

#### 4.1.5 ReflexiÃ³n tÃ©cnica breve

Responde en 5 a 8 lÃ­neas:

```text
Por quÃ© DetalleVenta no debe manejarse como un CRUD independiente?
```

### 4.2 Criterios mÃ­nimos de aceptaciÃ³n

- PDF con nombre correcto.
- Registro de cabecera y detalles.
- CÃ¡lculo de subtotal y total.
- Persistencia en tablas relacionadas.
- Validaciones del flujo.
- Evidencia de separaciÃ³n por capas.

## 5. Cierre evaluativo

Tiempo: 20 min.

### 5.1 Resultados esperados

- El estudiante explica la relaciÃ³n cabecera-detalle.
- El detalle referencia una entidad existente.
- El servicio coordina el guardado.
- El DAO persiste cabecera y detalle.
- La GUI muestra detalles y total.
- Hay validaciones al cierre de la sesiÃ³n.

### 5.2 Evidencia del producto de sesiÃ³n

Cada estudiante entrega un PDF individual siguiendo la plantilla de la secciÃ³n 4.1.

### 5.3 Preguntas de defensa y reflexiÃ³n

1. QuÃ© representa la cabecera?
2. QuÃ© representa el detalle?
3. Por quÃ© el detalle tiene atributos propios?
4. QuÃ© DAO guarda la cabecera?
5. QuÃ© DAO guarda los detalles?
6. QuÃ© validaciÃ³n evita vender una cantidad invÃ¡lida?

### 5.4 RÃºbrica de evaluaciÃ³n

| DimensiÃ³n | Peso | 3 - Logro destacado | 2 - Logro | 1 - Proceso | 0 - Inicio | PuntuaciÃ³n obtenida |
|---|---:|---|---|---|---|---:|
| 1. Modelo cabecera-detalle | 2 | Modelo claro y coherente. | Modelo funcional. | Modelo parcial. | No evidencia modelo. | |
| 2. Persistencia relacionada | 2 | Guarda cabecera y detalles correctamente. | Persistencia principal funcional. | Persistencia parcial. | No persiste relaciÃ³n. | |
| 3. Servicio y DAO | 2 | Servicio coordina y DAO separa SQL. | SeparaciÃ³n funcional. | Mezcla responsabilidades. | No separa. | |
| 4. Validaciones | 2 | Valida cantidad, stock y venta sin detalles. | Validaciones bÃ¡sicas. | Validaciones parciales. | No valida. | |
| 5. Error o hallazgo | 1 | Analiza causa y soluciÃ³n. | Explica un problema. | Menciona un problema. | No presenta. | |
| 6. Orden y reflexiÃ³n | 1 | Evidencia clara y reflexiÃ³n precisa. | Evidencia suficiente. | Evidencia incompleta. | No sustenta. | |
