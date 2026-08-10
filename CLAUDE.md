# comarket

Monorepo del curso **Programación Orientada a Objetos** con dos módulos:
`comarket-cli` (aplicación de consola) y `comarket-desk` (aplicación de
escritorio JavaFX + SQLite).

## Restricciones de versión — NO modificar sin autorización explícita

### Java: fijado en **Java 21**

- El proyecto usa **GraalVM JDK 21.0.11** (`C:\java\graalvm-jdk-21.0.11+9.1`).
- El `gluonfx-maven-plugin` requiere GraalVM para generar imágenes nativas;
  no es compatible con JDK > 21 en la instalación actual.
- **No actualizar** `maven.compiler.source`, `maven.compiler.target` ni
  `maven.compiler.release` a ninguna versión superior a 21.
- **No proponer ni ejecutar** upgrades de Java sin confirmación explícita del
  usuario y verificación previa de compatibilidad con la versión de GraalVM
  instalada.

### Maven wrapper: fijado en **3.8.5**

- El `.mvn/wrapper/maven-wrapper.properties` apunta a Maven 3.8.5; no
  cambiar la `distributionUrl` sin pedido explícito.
- Nota real de este repo: ese `.mvn/` vive en la **raíz** del repo, no
  dentro de `comarket-desk/` (que es donde están `mvnw`/`mvnw.cmd`).
  `comarket-cli` no tiene wrapper propio; su README usa `mvn` del sistema
  directamente. No "corregir" esta asimetría sin pedido explícito — es el
  estado real del repo.

## Ambientes

A diferencia de un backend con `application-{local,prod}.yml`, aquí no hay
una noción formal de "ambientes". `comarket-cli` es un programa de consola
sin configuración externa. `comarket-desk` crea su base SQLite en
`comarket-desk/data/comarket.db` de forma automática al ejecutarse (o junto
al `.exe` nativo); no hay `.env`, no hay `docker-compose`, no hay ambiente
de producción distinto del ambiente de laptop del estudiante. El único
ejecutable "de producción" es el binario nativo generado con GraalVM +
`gluonfx-maven-plugin` (ver README de `comarket-desk`), que sigue siendo
para uso local/demo, no un despliegue real.

## Dónde está cada cosa

- **Código U1 (S1-S6)**: `comarket-cli/` — consola, Maven simple
  (`comarket-cli/pom.xml`), sin wrapper, sin tests. Paquete
  `com.upeu.comarket.app` (`Main`), `com.upeu.comarket.entity`
  (`Producto`, `Persona`, `Cliente extends Persona`,
  `Usuario extends Persona`), `com.upeu.comarket.service`
  (`ProductoService` + `ProductoServiceImplMemoria` sobre `ArrayList`).
- **Código U2-U3 (S7-S16)**: `comarket-desk/` — JavaFX + FXML + SQLite,
  Maven con wrapper (`comarket-desk/mvnw`, `comarket-desk/mvnw.cmd`).
  Capas reales bajo `com.upeu.comarket`: `view` (FXML en
  `src/main/resources/.../view/`), `controller`, `service` (interfaz +
  `...ImplMemoria` legado de referencia + `...ImplSQLite` activa), `entity`
  (`Producto`, `Usuario`, `Venta`, `DetalleVenta`), `dao` (JDBC),
  `db` (`ConexionSQLite`), `security` (`Sesion`, desde S13). Usuario de
  prueba: `admin` / `123456`.
- **Sin tests automatizados todavía** en ninguno de los dos proyectos.
  `comarket-desk/pom.xml` ya trae `junit-jupiter` (scope test) pero no
  existe `src/test` en ningún módulo — si una sesión agrega lógica de
  negocio no trivial, considerar agregar pruebas, pero no es requisito
  actual del sílabo.
- **Documentación** ("libro digital", sílabo, sesiones): siempre en
  `docs/` en la raíz del repo (no `docs/poo/`, y sin subcarpeta
  `sesiones/`):
  - `docs/silabo_poo_2026_2.md` — sílabo oficial vigente (no editar salvo
    pedido explícito); `docs/silabo_poo_2026_1.md` queda como versión
    anterior de referencia.
  - `docs/index.md` — libro digital: alcance por sesión (tabla S1-S16 por
    unidad), arquitectura base U1 y U2-U3 con diagramas Mermaid, stack
    tecnológico por unidad. Es el análogo más cercano al `index.md` de
    LP2, aunque más orientado a libro publicado (MkDocs) que a hoja de
    ruta operativa.
  - `docs/S01_*.md` a `docs/S16_*.md` — rúbrica y detalle por sesión,
    directamente en `docs/` (no hay subcarpeta `sesiones/` como en LP2).
  - `docs/POOTaller01.md`, `docs/POOTaller02.md` — guías paso a paso para
    construir U1 y U2 respectivamente.
  - `docs/proyecto-sello/index.md` — especificación del "Proyecto Sello"
    (producto integrador transversal de la carrera), con competencias
    (CE022/CE023/CE024) y condiciones de qué sí/no cuenta como proyecto
    válido.
  - **No existen** `docs/adr/`, `docs/plan-trabajo.md` ni `docs/sesiones/`
    en este repo (a diferencia de LP2) — no inventar esas rutas; ver
    "Fuera de alcance" y el resumen final de huecos detectados.
  - `mkdocs.yml` + `.github/workflows/deploy.yml` publican `docs/` como
    sitio en `https://262poo.github.io/comarket/`.
- **Skills**: `.claude/skills/` en la raíz del repo (p. ej. `poo-sesion`),
  scoped a todo `comarket/` porque hay dos proyectos de código, no uno
  solo como en `lp2/`.
- `comarket-desk/.junie/plans/` existe pero está vacío — carpeta de otra
  herramienta (JetBrains Junie), sin contenido útil hoy.

## Arquitectura y convenciones por producto

- **U1 (`comarket-cli`)**: sin arquitectura por capas todavía — el sílabo
  la introduce recién en U2. Contrato de servicio (`XxxService` interface)
  + implementación en memoria (`XxxServiceImplMemoria` sobre
  `ArrayList`), igual patrón que `ProductoService` /
  `ProductoServiceImplMemoria`. Herencia solo cuando el dominio la
  justifica (`Persona` como clase base de `Cliente`/`Usuario`). Ejecutar
  con `mvn compile` + `mvn exec:java` (sin wrapper, ver README de
  `comarket-cli`).
- **U2-U3 (`comarket-desk`)**: arquitectura por capas
  `view/controller/service/entity/dao`, con `service` manteniendo el
  mismo contrato de U1 pero implementado contra SQLite vía DAO/JDBC en vez
  de `ArrayList`. Login y sesión activa (`Sesion`) se agregan recién en
  S13 — no antes (ver `docs/index.md`, sección "De la asociación
  Usuario–Venta al control de sesión"). Si se agrega un `Controller` o
  `Entity` nuevo, también debe añadirse a `reflectionList` del
  `gluonfx-maven-plugin` en `comarket-desk/pom.xml`, o la imagen nativa
  fallará en tiempo de ejecución por reflexión no registrada.
- El dominio real de este repo es CoMarket (`Producto`, `Usuario`,
  `Venta`, `DetalleVenta`); `docs/index.md` aclara que el nombre puede
  adaptarse por filial/docente, pero en este repo concreto se mantiene
  ese dominio — no renombrar sin pedido explícito.

## Cómo avanzar sesión a sesión

1. Identificar la sesión (S1-S16) y leer su fila en `docs/index.md` +
   el detalle completo en `docs/S0X_*.md`. S1-S6 tocan `comarket-cli`;
   S7-S16 tocan `comarket-desk`.
2. Revisar el código actual del proyecto correspondiente antes de
   implementar, para no duplicar ni adelantar alcance.
3. Implementar **solo** el incremento de esa sesión.
4. Verificar que compila:
   - `comarket-cli`: `mvn -f comarket-cli/pom.xml compile` (usa `mvn` del
     sistema, este proyecto no trae wrapper).
   - `comarket-desk`: `comarket-desk\mvnw.cmd -f comarket-desk\pom.xml compile`
     (Windows) / `comarket-desk/mvnw -f comarket-desk/pom.xml compile`
     (macOS/Linux).
   No hay suite de tests que correr todavía (ver "Dónde está cada cosa").
5. No hay `plan-trabajo.md` que actualizar en este repo — si se necesita
   seguimiento de avance, es un hueco de documentación, no algo a inventar
   silenciosamente (avisar al usuario).
6. No hay carpeta `adr/`: si una sesión implica una decisión de
   arquitectura real (no solo implementación dentro de lo ya decidido),
   usar modo plan y preguntar al usuario cómo registrarla antes de crear
   estructura nueva por cuenta propia.

## Fuera de alcance salvo pedido explícito

- No modificar `docs/S0X_*.md`, `docs/index.md`, `docs/silabo_poo_*.md`,
  `docs/POOTaller0X.md` ni `docs/proyecto-sello/index.md` — son contenido
  pedagógico publicado del curso (libro digital vía MkDocs).
- No adelantar alcance de sesiones futuras (p. ej. no implementar login ni
  `Sesion` antes de S13, no paginar/optimizar antes de que el sílabo lo
  pida).
- No modificar las restricciones de versión de Java/GraalVM/Maven wrapper
  de la sección de arriba, ni "corregir" la ubicación atípica de `.mvn/`,
  sin autorización explícita.
- No crear `docs/adr/`, `docs/plan-trabajo.md` ni `docs/sesiones/` por
  iniciativa propia solo para imitar la estructura de LP2 — si se quieren,
  pedirlo explícitamente primero.
