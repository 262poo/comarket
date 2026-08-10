# Plan de trabajo POO

Este documento es el rastreador vivo de avance del curso POO
(`comarket/`). No confundir con el sílabo (contenido oficial, en
`silabo_poo_2026_1.md`/`silabo_poo_2026_2.md`) ni con `CLAUDE.md` (guía
operativa de dónde vive cada cosa en el repositorio).

## Estado actual

- **Guías de sesión**: las 16 (S01–S16) están escritas en **Java 21**, no
  hay migración de lenguaje pendiente como en FP. Sí hay una migración de
  **plantilla** en curso: S01 y S02 ya siguen la estructura ampliada de FP
  (Índice, Metodología, Motivación/Caso, Hoja de ruta, cierre con
  Metodología para resolver problemas). S03–S16 todavía usan la plantilla
  anterior, más breve.
- **Publicación**: por eso el menú (`mkdocs.yml`) solo muestra S01 y S02
  por ahora. S03–S16 siguen escritas y accesibles en `docs/`, pero se
  retiraron temporalmente de la navegación hasta alinearlas a la plantilla
  nueva, para no publicar sesiones con dos niveles de profundidad distintos
  al mismo tiempo.
- **Código de referencia**:
  - `comarket-cli/` — Unidad 1 (S1–S6): consola, sin persistencia.
  - `comarket-desk/` — Unidades 2 y 3 (S7–S16): JavaFX + FXML + SQLite,
    arquitectura por capas (`db`, `security` desde S13).
  - El código de referencia usa JavaFX. Java Swing es una alternativa
    igualmente válida para docentes o estudiantes que lo prefieran; no
    cambia el alcance de sesión ni la arquitectura por capas, solo el
    framework de interfaz gráfica.
- El detalle de qué implementa cada sesión en el código vive en
  `CLAUDE.md`, sección "Dónde está cada cosa" — este archivo no lo
  repite; se actualiza aquí solo cuando cambie el estado general del
  curso (p. ej. una unidad nueva, un rediseño de arquitectura).

## Hoja de ruta

| Sesión | Foco | Guía |
|---|---|---|
| S1 | Entorno, estructuras de control, métodos y estructuras de datos lineales | Publicada (plantilla FP) |
| S2 | Clases, objetos, constructores y comunicación entre objetos | Publicada (plantilla FP) |
| S3 | Encapsulamiento, separación de responsabilidades y relaciones entre objetos | Escrita, pendiente de alinear a la plantilla |
| S4 | Herencia, interfaces y polimorfismo | Escrita, pendiente de alinear a la plantilla |
| S5 | Operaciones CRUD, validaciones y responsabilidad única | Escrita, pendiente de alinear a la plantilla |
| S6 | Evaluación de la Unidad I | Escrita, pendiente de alinear a la plantilla |
| S7 | Interfaz gráfica y CRUD desde GUI en memoria | Escrita, pendiente de alinear a la plantilla |
| S8 | Arquitectura por capas, DAO y primer listado persistente desde GUI | Escrita, pendiente de alinear a la plantilla |
| S9 | CRUD persistente completo de Producto desde GUI | Escrita, pendiente de alinear a la plantilla |
| S10 | Gestión de objetos relacionados Usuario-Venta | Escrita, pendiente de alinear a la plantilla |
| S11 | Procesamiento de Venta-DetalleVenta | Escrita, pendiente de alinear a la plantilla |
| S12 | Evaluación de la Unidad II | Escrita, pendiente de alinear a la plantilla |
| S13 | Control de acceso y sesión desde la IGU | Escrita, pendiente de alinear a la plantilla |
| S14 | Consultas integradas y pruebas del flujo principal | Escrita, pendiente de alinear a la plantilla |
| S15 | Sistema orientado a objetos integrado — Proyecto Sello | Escrita, pendiente de alinear a la plantilla |
| S16 | Evaluación final individual | Escrita, pendiente de alinear a la plantilla |
