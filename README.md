# Libro Digital de Programación Orientada a Objetos - UPeU

Este repositorio pública el curso de Programación Orientada a Objetos cómo libro digital en Markdown. El material está organizado cómo un solo curso, con tres unidades y dieciseis sesiónes alineadas al desarrollo incremental de **CoMarket**, un sistema comercial de escritorio construido con enfoque orientado a objetos.

## Qué encontrara el estudiante

- Presentación actualizada del curso en `docs/index.md`.
- Tres unidades organizadas por productos parciales.
- Dieciseis sesiónes teórico-practicas en formato de lectura y guía de trabajo.
- Un taller complementario para reforzar la configuración del proyecto.

## Enfoque del curso

El curso está centrado en diseñar e implementar una aplicación de escritorio aplicando modelado del dominio, encapsulamiento, herencia, polimorfismo, colecciones, arquitectura por capas, persistencia relacional, DAO e interfaz gráfica.

La Unidad 1 inicia con Java, VS Code, consola y almacenamiento en memoria. Desde la Unidad 2 se trabaja con JavaFX, Scene Builder, Maven, JDBC y SQLite en un IDE adecuado para aplicaciónes de escritorio, cómo IntelliJ IDEA.

Este repositorio no busca funciónar cómo un entorno de desarrollo embebido. Su propósito es servir cómo libro digital, guía clonable y referencia de estructura para qué cada estudiante mantenga su propio proyecto Java.

## Ruta de aprendizaje

| Unidad | Sesiónes | Producto esperado |
|---|---:|---|
| Fundamentos de POO | 1-6 | Aplicación funcional en memoria con clases, relaciones entre objetos, colecciones y operaciones principales del dominio. |
| Aplicación de escritorio con persistencia | 7-12 | Aplicación de escritorio con arquitectura por capas, GUI, DAO, JDBC y base de datos relacional. |
| Proyecto integrador CoMarket | 13-16 | Sistema comercial orientado a objetos documentado, probado y sustentado. |

## Ejecución local

### Con Python local

```powershell
python -m pip install mkdocs mkdocs-material pymdown-extensions
mkdocs serve
```

## Generacion del sitio

```powershell
mkdocs build
```

El despliegue a GitHub Pages se realiza con `.github/workflows/deploy.yml`, qué instala Python 3.12, MkDocs, MkDocs Material y `pymdown-extensions`.

## Estructura del libro

- Inicio y presentacion del curso.
- Unidad 1: fundamentos de modelado y programación orientada a objetos.
- Unidad 2: arquitectura, persistencia e interfaz gráfica.
- Unidad 3: integración, refinamiento, sustentación y evaluación final de CoMarket.
- Taller complementario.
