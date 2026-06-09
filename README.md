# Libro Digital de Programación Orientada a Objetos - UPeU

Este repositorio publica el curso de Programación Orientada a Objetos como libro digital en Markdown. El material está organizado como un solo curso, con tres unidades y dieciséis sesiones alineadas al desarrollo incremental de **CoMarket**, un sistema comercial de escritorio construido con enfoque orientado a objetos.

## Qué encontrará el estudiante

- El sílabo actualizado del curso.
- Tres unidades organizadas por productos parciales.
- Dieciséis sesiones teórico-prácticas en formato de lectura y guía de trabajo.
- Un taller complementario para reforzar la configuración del proyecto.

## Enfoque del curso

El curso está centrado en diseñar e implementar una aplicación de escritorio aplicando modelado del dominio, encapsulamiento, herencia, polimorfismo, colecciones, arquitectura por capas, persistencia relacional, DAO e interfaz gráfica.

La herramienta base de trabajo es IntelliJ IDEA. El libro no depende de cuadernos Jupyter ni de ejecución embebida en el sitio.

## Ruta de aprendizaje

| Unidad | Sesiones | Producto esperado |
|---|---:|---|
| Fundamentos de POO | 1-6 | Aplicación funcional en memoria con clases, relaciones entre objetos, colecciones y operaciones principales del dominio. |
| Aplicación de escritorio con persistencia | 7-12 | Aplicación de escritorio con arquitectura por capas, GUI, DAO, JDBC y base de datos relacional. |
| Proyecto integrador CoMarket | 13-16 | Sistema comercial orientado a objetos documentado, probado y sustentado. |

## Ejecución local

### Con Docker

```powershell
docker compose up
```

Luego abra:

```text
http://127.0.0.1:8002/
```

### Con Python local

```powershell
python -m pip install mkdocs mkdocs-material pymdown-extensions
mkdocs serve
```

## Generación del sitio

```powershell
docker compose run --rm mkdocs mkdocs build
```

o bien:

```powershell
mkdocs build
```

## Estructura del libro

- Inicio y presentación del curso.
- Sílabo de POO.
- Unidad 1: fundamentos de modelado y programación orientada a objetos.
- Unidad 2: arquitectura, persistencia e interfaz gráfica.
- Unidad 3: integración, refinamiento, sustentación y evaluación final de CoMarket.
- Taller complementario.
