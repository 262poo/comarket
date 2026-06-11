# S15 - Sustentación del proyecto CoMarket

## 1. Introducción

Tiempo: 20 min.

### 1.1 Propósito

Sustentar CoMarket mediante una demostración funcional y una explicación técnica clara.

### 1.2 Resultado de aprendizaje

El estudiante presenta el producto, explica su arquitectura, defiende decisiones de diseño y demuestra su aporte individual.

### 1.3 Producto de sesión

Sustentación grupal del proyecto CoMarket con defensa técnica individual.

### 1.4 Motivación de la sesión

Construir software también implica explicarlo. La sustentación permite verificar que el estudiante entiende cómo funciona el sistema y qué aportó.

Pregunta guía:

```text
¿Puedes explicar y defender técnicamente el producto que construiste?
```

### 1.5 Ubicación en el curso

- Unidad: U3.
- Avance de sesión: defensa técnica del producto.

## 2. Explica

Tiempo: 20 min.

### 2.1 Elementos de la sustentación

- Demostración funcional.
- Arquitectura.
- Entidades.
- Controladores.
- DAO y persistencia.
- Validaciones.
- Ejecutable.
- Aporte individual.

### 2.2 Orden sugerido

```mermaid
flowchart TB
    Demo["Demostración"]
    Arquitectura["Arquitectura"]
    Codigo["Código clave"]
    Evidencias["Evidencias"]
    Defensa["Defensa individual"]

    Demo --> Arquitectura
    Arquitectura --> Codigo
    Codigo --> Evidencias
    Evidencias --> Defensa
```

## 3. Aplica: actividad práctica guiada

Tiempo: 2h.

1. Ejecutar CoMarket.
2. Mostrar el flujo principal.
3. Explicar arquitectura por capas.
4. Mostrar entidades y DAO.
5. Mostrar persistencia en SQLite.
6. Explicar validaciones.
7. Presentar evidencias.
8. Responder preguntas individuales.

## 4. Crea: preparación autónoma

Tiempo: 2h fuera del aula.

Prepara una sustentación breve con:

- Guion de demostración.
- Capturas o evidencias.
- Explicación de tu aporte.
- Posibles preguntas y respuestas.

## 5. Cierre evaluativo

Tiempo: según programación.

### 5.1 Resultados esperados

- Producto ejecutable.
- Demostración funcional.
- Explicación técnica clara.
- Aporte individual identificable.
- Respuestas coherentes a preguntas.

### 5.2 Preguntas de defensa

1. ¿Qué parte implementaste?
2. ¿Cómo fluye una operación desde la vista hasta la base de datos?
3. ¿Qué entidad es central en tu módulo?
4. ¿Qué error importante resolviste?
5. ¿Qué mejorarías con más tiempo?

