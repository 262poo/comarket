# comarket-cli

Proyecto de consola de la Unidad 1 de POO (S1-S6). Sin Maven ni framework:
se compila y ejecuta directo con `javac`/`java` (Java 21), tal como indica
`docs/sesiones/S01_Fundamentos_Estructuras_Datos.md`.

## Estado

- **S1**: hecho. `src/Main.java` — menú con estructuras de control (`do-while`
  + `switch`) y operaciones sobre `ArrayList<String>` organizadas en
  métodos (`registrar`, `listar`, `buscar`, `actualizar`, `eliminar`).
  `src/ComparacionArrayArrayList.java` y `src/ProductosListasParalelas.java`
  son demos independientes de la sección "Explica"/"Aplica" (array de
  tamaño fijo vs. `ArrayList`, y listas paralelas para representar un
  producto sin clases todavía).
- **S2 en adelante**: pendiente. Desde S2 se introducen clases y objetos
  propios del dominio (`Coche`, `Persona`, `Producto`), reemplazando el
  enfoque de listas paralelas de S1.

## Compilar y ejecutar

```bash
cd comarket-cli/src
javac *.java
java Main
```

(`ComparacionArrayArrayList` y `ProductosListasParalelas` se ejecutan igual,
cambiando el nombre de la clase.)
