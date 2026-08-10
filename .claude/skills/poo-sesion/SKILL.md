---
name: poo-sesion
description: Implementa el incremento de código de una sesión de POO (S1 a S16) sobre CoMarket (`comarket-cli` para S1-S6, `comarket-desk` para S7-S16), respetando el sílabo y sin adelantar alcance. Usar cuando se pida "avanza la sesión SXX de POO", "implementa S0X" o "continúa con la siguiente sesión de POO".
---

# Sesión de POO (CoMarket)

Este skill implementa **una sola sesión** del curso Programación Orientada a
Objetos sobre el código de `comarket/` (raíz de este repo). No mezcla el
trabajo de dos sesiones ni adelanta alcance de sesiones futuras.

## Antes de escribir código

1. Identifica el número de sesión (S1 a S16). Si no se especifica, revisa
   `comarket-cli/src` (S1-S6) y luego `comarket-desk/src` (S7-S16) para
   detectar la última sesión completada y continúa con la siguiente.
2. Determina el proyecto correspondiente:
   - **S1-S6 (Unidad 1)** → `comarket-cli/` (consola, en memoria).
   - **S7-S16 (Unidad 2 y 3)** → `comarket-desk/` (JavaFX, capas, SQLite).
3. Lee la fila de esa sesión en `docs/index.md` (raíz del repo) — ahí está
   la tabla con tema y "producto de sesión" por unidad, más los diagramas
   de arquitectura base U1 y U2-U3.
4. Lee el detalle completo y la rúbrica en `docs/S0X_*.md` (los archivos
   están directamente en `docs/`, no en una subcarpeta `sesiones/`). Por
   ejemplo, S11 es `docs/S11_Venta_DetalleVenta_Producto.md`.
5. Si la sesión es S1 o S2 de U1, o S7/S8 de U2, revisa también
   `docs/POOTaller01.md` / `docs/POOTaller02.md` — son guías paso a paso
   que suelen tener más detalle práctico que la rúbrica.
6. Inspecciona el código actual del proyecto correspondiente
   (`comarket-cli/src` o `comarket-desk/src`) para saber exactamente qué
   ya existe antes de agregar nada. No hay `docs/adr/` ni
   `docs/plan-trabajo.md` en este repo — no los inventes ni asumas que
   existen.

## Reglas al implementar

- Implementa **solo el incremento de la sesión pedida**, ni más ni menos.
  Ejemplo: en S9 se completa el CRUD persistente de `Producto` desde la
  GUI — no se toca `Venta` ni `Usuario` todavía (eso es S10-S11).
- Respeta la frontera de unidad: no introduzcas JavaFX/SQLite en
  `comarket-cli` (eso es exclusivo de `comarket-desk` desde S7), ni
  reescribas `comarket-desk` para volver a memoria.
- No implementes login, `Sesion` ni permisos antes de S13 — hasta S10 la
  relación `Usuario-Venta` es solo de dominio (selección manual de
  usuario), sin autenticación (ver `docs/index.md`, sección "De la
  asociación Usuario–Venta al control de sesión").
- Sigue el estilo ya presente en el proyecto:
  - `comarket-cli`: contrato `XxxService` (interface) +
    `XxxServiceImplMemoria` sobre `ArrayList`, entidades en `entity/`,
    herencia solo donde el dominio la justifica (`Persona` como base de
    `Cliente`/`Usuario`).
  - `comarket-desk`: capas `view` (FXML) → `controller` → `service`
    (interfaz + `...ImplSQLite`) → `dao` (JDBC) → `entity`. Sigue el
    patrón ya usado en `ProductoDao`/`ProductoServiceImplSQLite`.
- Si agregas un `Controller` o una `Entity` nueva en `comarket-desk`,
  agrégalo también a `<reflectionList>` del `gluonfx-maven-plugin` en
  `comarket-desk/pom.xml` (si no, la imagen nativa falla en tiempo de
  ejecución por reflexión no registrada).
- No pre-crees clases ni pantallas para sesiones futuras "por si acaso".
- No hay tests automatizados hoy en ninguno de los dos proyectos. No es
  requisito del sílabo agregarlos, pero si la sesión introduce lógica de
  negocio no trivial (cálculo de totales, validaciones de stock, etc.),
  es razonable proponer una prueba JUnit en `comarket-desk` (ya trae
  `junit-jupiter` como dependencia de test) — pregunta antes si no es
  obvio que la sesión lo pide.

## Verificación

El onboarding técnico completo (prerrequisitos, comando de compilación,
cómo ejecutar, cómo verificar sin tests automatizados) vive en el README de
cada proyecto — no lo dupliques aquí:

- `comarket-cli/README.md` (S1-S6): compilar con `mvn compile` (usa `mvn`
  del sistema, sin wrapper).
- `comarket-desk/README.md` (S7-S16): compilar con `mvnw.cmd compile`
  (Windows) / `mvnw compile` (macOS/Linux), **siempre con el wrapper**
  (Maven 3.8.5 — ver `CLAUDE.md`, sección "Maven wrapper").

Al cerrar una sesión, compila el proyecto tocado con el comando de su
README y trátalo como paso mínimo obligatorio. Si quieres validar el flujo
real de UI/persistencia en `comarket-desk`, el paso manual es
`mvnw.cmd clean javafx:run` e iniciar sesión con `admin` / `123456` (ver
README de `comarket-desk`) — indícalo como paso manual, no asumas que
puedes ejecutar una GUI ni verificar `data/comarket.db` de forma automática
en este entorno.

## Al terminar

Resume en 3-5 líneas qué se implementó, en qué proyecto
(`comarket-cli`/`comarket-desk`), qué clases/pantallas quedaron nuevas o
modificadas, y qué queda pendiente para la siguiente sesión.
