?# Proyecto Sello de Programación Orientada a Objetos

## 1. Propósito

El Proyecto Sello integra las sesiones de **Programación Orientada a Objetos** alrededor de una misma aplicación que evoluciona desde consola hasta escritorio. Durante el semestre no se construyen ejercicios independientes; cada tema fortalece el mismo sistema hasta convertirlo en una solución orientada a objetos, persistente, integrada y sustentable.

```text
Dominio -> Clases -> Relaciones -> CRUD -> GUI -> Persistencia -> Integración -> Sustentación
```

## 2. El Proyecto

Durante el semestre desarrollarás una **aplicación de escritorio orientada a objetos** aplicada a un proceso transaccional de negocio.

El proyecto debe partir de un dominio claro, con entidades maestras y transaccionales, relaciones entre objetos, operaciones de gestión, interfaz gráfica, persistencia relacional y arquitectura por capas.

El proyecto debe cumplir estas condiciones:

- Modelar un dominio de negocio concreto.
- Definir entidades, atributos, comportamientos y relaciones.
- Aplicar encapsulamiento, responsabilidades, herencia, interfaces o polimorfismo cuando el dominio lo justifique.
- Evolucionar de consola en memoria hacia aplicación JavaFX con persistencia.
- Integrar arquitectura por capas, DAO, JDBC y SQLite.
- Ser sustentado técnicamente por todos los integrantes del equipo.

No se considera Proyecto Sello:

- Clases aisladas sin dominio común.
- Ejercicios de POO sin aplicación integrada.
- Un CRUD sin modelo de dominio ni relaciones.
- Una GUI desconectada de servicios, entidades o persistencia.
- Un proyecto que el estudiante no pueda explicar en código y ejecución.

## 3. Evolución del Proyecto

| Unidad | Temas principales | Evolución del proyecto |
|---|---|---|
| Unidad 1 | Clases, objetos, encapsulamiento, relaciones, herencia, interfaces, polimorfismo y colecciones. | Aplicación de consola orientada a objetos con gestión de datos en memoria. |
| Unidad 2 | JavaFX, arquitectura por capas, DAO, JDBC, SQLite, seguridad básica, relaciones y consultas. | Aplicación de escritorio por capas con interfaz gráfica y persistencia relacional. |
| Unidad 3 | Integración, validación, refinamiento, ejecutable y sustentación. | Sistema orientado a objetos integrado para un proceso transaccional de negocio. |

```mermaid
flowchart TB
    A[Dominio] --> B[Clases y objetos]
    B --> C[Encapsulamiento]
    C --> D[Relaciones]
    D --> E[CRUD en memoria]
    E --> F[GUI JavaFX]
    F --> G[DAO y persistencia]
    G --> H[Flujo transaccional]
    H --> I[Consultas y pruebas]
    I --> J[Sustentación]
```

### Alineamiento por sesiones

Este alineamiento muestra cómo el proyecto evoluciona desde la comprensión del dominio hasta una aplicación de escritorio orientada a objetos integrada.

| Sesiones | Contenido central | Avance del proyecto |
|---|---|---|
| S1-S2 | Estructuras base, métodos, clases, objetos y constructores. | Brief del dominio, entidades iniciales, objetos instanciados y comunicación básica. |
| S3-S4 | Encapsulamiento, responsabilidades, relaciones, herencia, interfaces y polimorfismo. | Modelo de dominio organizado, relaciones entre objetos y decisiones POO justificadas. |
| S5-S6 | CRUD en memoria, validaciones y evaluación U1. | Aplicación de consola orientada a objetos con colecciones, menú y evidencias. |
| S7-S8 | JavaFX, controladores, arquitectura por capas, DAO y persistencia. | Paso de consola a escritorio con GUI, servicio y base de datos local. |
| S9-S10 | Relaciones persistentes, flujo transaccional y seguridad básica. | Cabecera-detalle o relación equivalente, control de acceso y consistencia de datos. |
| S11-S12 | Consultas, pruebas y evaluación U2. | Aplicación de escritorio por capas validada con consultas y evidencias. |
| S13-S14 | Integración, validación, refinamiento y ejecutable. | Sistema ensamblado, corregido y preparado para sustentación. |
| S15-S16 | Sustentación y evaluación final individual. | Producto POO integrado sustentado y cierre académico. |

## 4. Cronograma

| Hito | Momento | Producto esperado |
|---|---|---|
| S2 | Aprobación del brief | Dominio, entidades iniciales, responsabilidades, operaciones y alcance. |
| S6 | Producto U1 | Aplicación de consola orientada a objetos con clases, relaciones, CRUD en memoria y validaciones. |
| S12 | Producto U2 | Aplicación JavaFX por capas con persistencia relacional, seguridad básica, relaciones, consultas y pruebas. |
| S15 | Producto final | Sistema orientado a objetos integrado, validado, documentado y sustentado técnicamente. |
| S16 | Cierre individual | Evaluación final, recuperación de sustentaciones pendientes y cierre académico. |

## 5. Producto Final

Al finalizar el curso, la aplicación debe incorporar como mínimo:

- Modelo de dominio con entidades coherentes.
- Encapsulamiento y separación de responsabilidades.
- Relaciones entre objetos.
- Uso justificado de herencia, interfaces o polimorfismo.
- CRUD en memoria y persistente.
- Interfaz gráfica JavaFX con formularios, tablas y eventos.
- Arquitectura por capas: vista, controlador, servicio, entidad y DAO.
- Persistencia con JDBC y SQLite.
- Flujo transaccional con cabecera, detalle o relación equivalente.
- Seguridad básica, consultas, validaciones y manejo de errores.
- Evidencias de pruebas funcionales.
- Ejecutable o evidencia de ejecución final.

Artefactos mínimos:

- Código fuente organizado.
- Base de datos local y scripts o evidencia de estructura.
- Diagrama o explicación del modelo de dominio.
- Evidencia de arquitectura por capas.
- Casos de prueba básicos.
- Demo funcional y sustentación técnica.

## 6. Evaluación

| Criterio | Qué se observa |
|---|---|
| Dominio y alcance | El sistema responde a un proceso de negocio claro y mantiene un alcance viable. |
| Modelado POO | Las clases, atributos, métodos y relaciones representan correctamente el dominio. |
| Aplicación de fundamentos POO | Se evidencia encapsulamiento, responsabilidades, herencia, interfaces o polimorfismo cuando corresponde. |
| Funcionalidad | El sistema permite gestionar datos, ejecutar el flujo principal y consultar información relevante. |
| Arquitectura | La solución separa vista, controlador, servicio, entidad y acceso a datos. |
| Persistencia | La aplicación guarda, recupera y consulta datos mediante DAO, JDBC y base relacional local. |
| Calidad del código | El código es legible, modular y consistente con buenas prácticas básicas. |
| Pruebas y evidencias | Se presentan casos de prueba, capturas, datos de prueba y resultados verificables. |
| Sustentación técnica | El estudiante explica diseño, código, decisiones, limitaciones y funcionamiento. |
| Sustentación profesional | El estudiante expone con orden, demuestra en vivo su aporte, responde preguntas y mantiene una presentación adecuada. |

## 7. Sustentación

La sustentación debe demostrar que el equipo comprende el sistema y que cada integrante domina su aporte.

| Momento | Tiempo sugerido | Propósito |
|---|---:|---|
| Exposición técnica | 10 minutos | Presentar dominio, arquitectura, modelo de objetos, persistencia y evidencias. |
| Demostración en vivo | 5 minutos | Ejecutar el flujo principal, CRUD, consultas, persistencia y validaciones. |

Cada integrante debe mostrar en vivo la parte que desarrolló o explicar una sección concreta del código. Las diapositivas apoyan la explicación, pero la evidencia principal es el sistema ejecutándose.

Se espera comunicación clara, puntualidad, vestimenta limpia y adecuada, cabello ordenado, higiene personal y actitud profesional.

## 8. Resultado Esperado

Al finalizar el curso, el estudiante debe demostrar que puede transformar un dominio de negocio en una aplicación de escritorio orientada a objetos, persistente y sustentable.

```text
Dominio -> Modelo POO -> CRUD -> GUI -> Persistencia -> Sistema integrado -> Sustentación
```

