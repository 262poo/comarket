# S13 - Integración del sistema

## 1. Introducción

Tiempo: 20 min.

### 1.1 Propósito

Integrar los módulos construidos en U1 y U2 en una versión coherente del producto final, eliminando duplicidades y dejando un flujo principal ejecutable.

### 1.2 Resultado de aprendizaje

El estudiante consolida pantallas, controladores, servicios, entidades, DAO, base de datos, recursos y dependencias en una sola aplicación.

### 1.3 Producto de sesión

Producto integrado con flujo principal funcional y preparación para empaquetado final.

### 1.4 Motivación de la sesión

Después de varias sesiónes, el proyecto puede tener clases duplicadas, nombres distintos, pantallas sueltas o servicios incompletos. Integrar significa dejar una sola versión funcional y defendible.

Pregunta guía:

```text
Qué debe quedar unido para que el producto funcione como aplicación final?
```

### 1.5 Ubicación en el curso

- Unidad: U3 - Proyecto integrador.
- Avance de sesión: ensamblaje del producto final.

## 2. Explica

Tiempo: 25 min.

### 2.1 Conceptos clave

- Integración de módulos.
- Consistencia de paquetes.
- Flujo principal.
- Dependencias Maven.
- Recursos FXML.
- Base de datos.
- Preparación para ejecutable nativo.

Regla métodológica de la sesión:

```text
Integrar no es agregar más clases.
Integrar es dejar una sola ruta funcional desde la GUI hasta la base de datos.
Si hay dos clases que hacen lo mismo, se decide una y se elimina la duplicidad.
```

### 2.2 Arquitectura integrada

```mermaid
flowchart TB
    Vista["Vistas FXML"]
    Controladores["Controladores"]

    subgraph Servicios["Servicios"]
        Contrato["Interface<br/>contrato de operaciones CRUD"]
        Implementación["Implementación persistente<br/>implements"]
        Validaciones["Validaciones/Excepciones"]
    end

    Entidades["Entidades"]

    subgraph Persistencia["Persistencia"]
        DAO["DAO"]
        SQLite[("SQLite / comarket.db")]
    end

    Vista --> Controladores
    Controladores --> Contrato
    Implementación -. implements .-> Contrato
    Contrato -.-> Entidades
    Implementación -.-> Entidades
    Implementación -.-> Validaciones
    Implementación --> DAO
    DAO --> Entidades
    DAO -->|"JDBC"| SQLite
```

## 3. Aplica: actividad práctica guiada

Tiempo: 2h.

1. Revisar estructura de paquetes.
2. Identificar clases duplicadas o con nombres inconsistentes.
3. Integrar pantallas y controladores.
4. Revisar interface de servicio e implementación persistente.
5. Revisar entidades usadas por GUI, servicio y DAO.
6. Verificar conexión con SQLite.
7. Ejecutar el flujo principal de punta a punta.
8. Revisar configuración Maven y recursos.
9. Registrar problemas encontrados y correcciones.

## 4. Crea: actividad autónoma

Fuera del aula, cada estudiante consolida el aprendizaje integrando una parte del producto y preparando una evidencia individual.

Tiempo: 3h fuera del aula.

### 4.1 Plantilla de evidencia individual

Entrega un PDF con el siguiente nombre:

```text
S13_Equipo##_ApellidoNombre.pdf
```

Ejemplo:

```text
S13_Equipo03_QuispeAna.pdf
```

El PDF debe usar esta estructura. La primera sección define el trabajo autónomo; completa las demás con tus evidencias.

#### 4.1.1 Datos del estudiante

- Nombre:
- Equipo:
- Sesión: S13 - Integración del sistema
- Rol o aporte realizado:
- Link de GitHub:

#### 4.1.2 Trabajo autónomo realizado

Completa y evidencia estas tareas:

1. Integrar una funcionalidad pendiente o corregir una inconsistencia del proyecto.
2. Revisar paquetes, nombres y clases duplicadas.
3. Verificar el flujo principal de punta a punta.
4. Confirmar conexión con SQLite.
5. Registrar archivos modificados.
6. Explicar antes/después del cambio.
7. Registrar observaciones pendientes para refinamiento.

#### 4.1.3 Evidencia técnica

Incluye capturas o salidas con una breve explicación debajo de cada una:

- Antes/después del cambio.
- Flujo probado.
- Archivos modificados.
- Error encontrado y solución.
- Captura de la aplicación integrada.
- Evidencia de persistencia operativa.

#### 4.1.4 Error o hallazgo

Describe al menos un error, diferencia o hallazgo técnico:

- Qué ocurrió.
- Cómo lo diagnosticaste.
- Cómo lo corregiste o qué aprendiste.

Ejemplos válidos:

- Había clases duplicadas.
- Un controlador apuntaba a una vista incorrecta.
- Una entidad no coincidía con el DAO.
- El flujo principal fallaba por una ruta o recurso FXML.

#### 4.1.5 Reflexión técnica breve

Responde en 5 a 8 líneas:

```text
Por qué integrar no significa agregar más clases, sino hacer que las partes existentes funcionen juntas?
```

### 4.2 Criterios mínimos de aceptación

La evidencia individual se considera completa si:

- El archivo respeta el nombre `S13_Equipo##_ApellidoNombre.pdf`.
- Incluye evidencias técnicas legibles.
- Muestra una integración o corrección real.
- Muestra flujo principal probado.
- Identifica archivos modificados.
- Registra al menos una observación pendiente.
- No contiene solo pantallazos: cada evidencia tiene una descripción breve.

## 5. Cierre evaluativo

Tiempo: 20 min.

Esta sección conecta el resultado de aprendizaje de la sesión con el producto que debe evidenciar cada estudiante.

### 5.1 Resultados esperados

Al finalizar la sesión, el estudiante debe demostrar que:

- El proyecto está integrado.
- El flujo principal es ejecutable.
- Paquetes y nombres son consistentes.
- La persistencia está operativa.
- Las observaciones quedan registradas para refinamiento.

### 5.2 Evidencia del producto de sesión

Cada estudiante entrega un PDF individual siguiendo la plantilla de la sección 4.1.

Nombre del archivo:

```text
S13_Equipo##_ApellidoNombre.pdf
```

La evidencia debe demostrar:

- Producto de sesión construido.
- Aporte individual verificable.
- Integración o corrección aplicada.
- Reflexión técnica breve.

La revisión se realiza con los criterios mínimos de aceptación de la sección 4.2 y la rúbrica de la sección 5.4.

### 5.3 Preguntas de defensa y reflexión

1. Qué módulo integraste?
2. Qué duplicidad o inconsistencia corregiste?
3. Cómo verificaste el flujo principal?
4. Qué falta estabilizar antes de sustentar?
5. Qué archivo consideras más crítico en tu módulo?
6. Qué riesgo técnico registraste para S14?

### 5.4 Rúbrica de evaluación

| Dimensión | Peso | 3 - Logro destacado | 2 - Logro | 1 - Proceso | 0 - Inicio | Puntuación obtenida |
|---|---:|---|---|---|---|---:|
| 1. Integración aplicada | 2 | Integra módulo o corrección real con evidencia clara. | Integración funcional. | Integración parcial. | No evidencia integración. | |
| 2. Flujo principal | 2 | Flujo completo probado de punta a punta. | Flujo principal probado. | Flujo parcial. | No prueba flujo. | |
| 3. Consistencia técnica | 2 | Paquetes, nombres, vistas, servicios y DAO coherentes. | Consistencia suficiente. | Inconsistencias pendientes. | No revisa consistencia. | |
| 4. Persistencia y recursos | 2 | Persistencia y recursos FXML verificados. | Verificación principal realizada. | Verificación parcial. | No verifica persistencia ni recursos. | |
| 5. Error o hallazgo | 1 | Analiza error/hallazgo, causa, solución y aprendizaje técnico. | Explica un problema y una solución. | Menciona un problema sin análisis. | No presenta error ni hallazgo. | |
| 6. Reflexión y orden | 1 | PDF ordenado, evidencias legibles y reflexión precisa. | Evidencias suficientes y reflexión clara. | Evidencias incompletas o reflexión superficial. | PDF desordenado o sin reflexión. | |

Puntuación acumulada = suma de (`Peso` * `Puntuación obtenida`) = ____.

Nota final = (`Puntuación acumulada` / 30) * 20 = ____.

Para usar la rúbrica con IA, solicita:

```text
Evalúa el PDF usando la rúbrica de la sesión.
Para cada dimensión selecciona la puntuación obtenida usando la escala Inicio=0, Proceso=1, Logro=2, Logro destacado=3.
Justifica brevemente cada puntuación.
Calcula la puntuación acumulada con la fórmula: suma de (Peso * Puntuación obtenida).
Calcula la nota final sobre 20 con la fórmula: (Puntuación acumulada / 30) * 20.
Indica 2 fortalezas y 2 recomendaciones.
```

