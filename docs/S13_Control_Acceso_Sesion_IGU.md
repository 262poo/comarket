# S13 - Control de acceso y sesión desde la IGU

## 1. Introducción

Tiempo: 20 min.

### 1.1 Propósito

Implementar el acceso desde la interfaz gráfica sobre el modelo `Usuario–Venta` ya persistido, usando login, clase `Sesion`, roles, permisos básicos y protección de operaciones.

### 1.2 Resultado de aprendizaje

El estudiante implementa login, conserva el usuario activo, adapta menús y pantallas según permisos y asocia automáticamente cada nueva venta al usuario autenticado.

### 1.3 Producto de sesión

IGU con login, sesión activa, roles y permisos básicos, cierre de sesión, pantallas protegidas y ventas asociadas automáticamente al usuario activo.

### 1.4 Motivación de la sesión

En S10 las ventas se asociaron a un usuario seleccionado manualmente. En S13 esa selección desaparece: la aplicación identifica al usuario desde el login y lo conserva durante la ejecución.

Pregunta guía:

```text
Cómo asociamos una venta persistente a un usuario autenticado sin consultar la base de datos en cada pantalla?
```

### 1.5 Ubicación en el curso

- Unidad: U3.
- Carpeta de trabajo: `comarket-desk`.
- Avance de sesión: seguridad desde la IGU sobre la asociación `Usuario–Venta` existente.

## 2. Explica

Tiempo: 25 min.

### 2.1 Conceptos clave

- Usuario.
- Autenticación básica.
- Sesión activa en aplicación de escritorio.
- Clase `Sesion` como contexto del usuario activo.
- Roles y permisos básicos.
- Menús y pantallas protegidas.
- Ventas asociadas al usuario.
- Validación de acceso.
- DAO para usuario.
- Manejo básico de errores.

Regla metodológica de la sesión:

```text
La seguridad se trabaja de forma básica.
Usuario no reemplaza al dominio principal.
Usuario permite asociar ventas a quien las registra.
La asociación `Usuario–Venta` ya fue implementada en S10 y no se vuelve a enseñar.
Las validaciones de acceso se aplican antes de registrar o anular una venta.
Sesion no es una sesión web.
Sesion es un estado simple de la aplicación de escritorio.
Sesion evita consultar la base de datos cada vez que una pantalla necesita saber qué usuario está autenticado.
`UsuarioDao` se ubica en `dao` y reutiliza `db/ConexionSQLite`.
Ocultar o deshabilitar controles mejora la experiencia, pero el servicio debe volver a validar las acciones sensibles.
La contraseña no se almacena dentro de `Sesion` ni se transporta a otras pantallas.
```

### 2.2 Arquitectura de la sesión

```mermaid
%%{init: {'flowchart': {'rankSpacing': 80, 'nodeSpacing': 35, 'curve': 'basis'}} }%%
flowchart TD
    LoginView["view<br/>LoginView.fxml<br/>txtUsername / txtPassword / Ingresar"]
    MainView["view<br/>MainView.fxml<br/>lblUsuario"]
    VentaView["view<br/>VentaView.fxml<br/>registro de venta"]
    AnularVentasView["view<br/>AnularVentasView.fxml<br/>colUsuario / Anular venta"]
    LoginController["controller<br/>LoginController<br/>onIngresarClick()"]
    MainController["controller<br/>MainController<br/>initialize()<br/>onCerrarSesionClick()"]
    VentaController["controller<br/>VentaController<br/>onGuardarVentaClick()"]
    AnularVentasController["controller<br/>AnularVentasController<br/>colUsuario<br/>onAnularVentaClick()"]
    UsuarioService["service<br/>UsuarioService<br/>autenticar(username, password)"]
    UsuarioServiceImplSQLite["service impl<br/>UsuarioServiceImplSQLite<br/>validar vacios / comparar password"]
    VentaService["service<br/>VentaService<br/>registrar(venta) / listar() / anular(ventaId)"]
    VentaServiceImplSQLite["service impl<br/>VentaServiceImplSQLite<br/>validar usuario en venta"]
    UsuarioDao["dao<br/>UsuarioDao<br/>buscarPorUsername(username)"]
    VentaDao["dao<br/>VentaDao<br/>insertar venta con usuario_id<br/>listar con LEFT JOIN usuario"]
    ConexionSQLite["db<br/>ConexionSQLite<br/>obtenerConexion()"]
    Sesion["security<br/>Sesion<br/>iniciar(usuario)<br/>getUsuarioActual()<br/>estaActiva()<br/>cerrar()"]
    Usuario["entity<br/>Usuario<br/>id / username / passwordHash / rol"]
    Venta["entity<br/>Venta<br/>cliente / fecha / estado / usuario"]
    SQLite[("data/comarket.db<br/>usuario<br/>venta.usuario_id")]

    LoginView --> LoginController
    LoginController --> UsuarioService
    UsuarioService --> UsuarioServiceImplSQLite
    UsuarioServiceImplSQLite --> UsuarioDao
    UsuarioDao --> ConexionSQLite
    ConexionSQLite --> SQLite
    UsuarioDao --> Usuario
    LoginController --> Sesion
    LoginController --> MainView
    MainView --> MainController
    MainController --> Sesion
    VentaView --> VentaController
    VentaController --> Sesion
    VentaController --> VentaService
    VentaService --> VentaServiceImplSQLite
    VentaServiceImplSQLite --> VentaDao
    VentaDao --> ConexionSQLite
    VentaController --> Venta
    Venta --> Usuario
    AnularVentasView --> AnularVentasController
    AnularVentasController --> VentaService
    AnularVentasController --> Sesion
    VentaDao --> Venta

    classDef serviceImpl fill:#dbeafe,stroke:#2563eb,stroke-width:2px,color:#1e3a8a;
    class UsuarioServiceImplSQLite,VentaServiceImplSQLite serviceImpl;
```

Nombres reales del proyecto guía:

```text
com.upeu.comarket.controller.LoginController
com.upeu.comarket.controller.MainController
com.upeu.comarket.controller.VentaController
com.upeu.comarket.controller.AnularVentasController
com.upeu.comarket.security.Sesion
com.upeu.comarket.entity.Usuario
com.upeu.comarket.entity.Venta
com.upeu.comarket.service.UsuarioService
com.upeu.comarket.service.UsuarioServiceImplSQLite
com.upeu.comarket.service.VentaServiceImplSQLite
com.upeu.comarket.dao.UsuarioDao
com.upeu.comarket.dao.VentaDao
com.upeu.comarket.db.ConexionSQLite
src/main/resources/com/upeu/comarket/view/LoginView.fxml
src/main/resources/com/upeu/comarket/view/MainView.fxml
src/main/resources/com/upeu/comarket/view/VentaView.fxml
src/main/resources/com/upeu/comarket/view/AnularVentasView.fxml
```

## 3. Aplica: actividad práctica guiada

Tiempo: 4 h.

### 3.1 Reutilizar la persistencia de `Usuario`

La estructura de `Usuario` y su asociación con `Venta` provienen de S10. El docente proporciona el script y un usuario de prueba; el estudiante no diseña nuevas tablas en S13.

```sql
CREATE TABLE usuario (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT NOT NULL UNIQUE,
    password_hash TEXT NOT NULL,
    rol TEXT NOT NULL
);
```

Usuario de prueba:

```sql
INSERT INTO usuario (username, password_hash, rol)
VALUES ('admin', '<hash-proporcionado>', 'ADMIN');
```

Nota metodológica:

```text
No se guarda la contraseña en texto plano.
El docente puede proporcionar una utilidad de hash para concentrar la sesión en la IGU, la sesión activa y los permisos.
```

### 3.2 Reutilizar y completar la entidad `Usuario`

```java
public class Usuario {
    private int id;
    private String username;
    private String passwordHash;
    private String rol;

    public Usuario(int id, String username, String passwordHash, String rol) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getRol() {
        return rol;
    }
}
```

### 3.3 Reutilizar `UsuarioDao`

`UsuarioDao` ya persiste usuarios desde S10. En S13 se agrega o verifica únicamente la búsqueda necesaria para autenticar.

```java
public class UsuarioDao {
    public Usuario buscarPorUsername(String username) {
        // SELECT id, username, password_hash, rol
        // FROM usuario
        // WHERE username = ?
        return null;
    }
}
```

### 3.4 Crear `UsuarioService`

```java
public interface UsuarioService {
    Usuario autenticar(String username, String password);
}
```

### 3.5 Crear `UsuarioServiceImplSQLite`

El servicio decide si las credenciales son válidas. El controlador no debe comparar contraseñas ni ejecutar SQL.

```java
public class UsuarioServiceImplSQLite implements UsuarioService {
    private final UsuarioDao usuarioDao = new UsuarioDao();

    @Override
    public Usuario autenticar(String username, String password) {
        if (estaVacio(username) || estaVacio(password)) {
            return null;
        }

        Usuario usuario = usuarioDao.buscarPorUsername(username.trim());

        if (usuario == null) {
            return null;
        }

        if (!PasswordHasher.verificar(password, usuario.getPasswordHash())) {
            return null;
        }

        return usuario;
    }
}
```

### 3.6 Crear la clase `Sesion`

`Sesion` no reemplaza a la base de datos y no es una sesión web. Es una clase simple que conserva en memoria el usuario autenticado durante la ejecución de la aplicación.

Utilidad:

```text
1. Evita consultar la base de datos en cada pantalla para saber quién está autenticado.
2. Centraliza el estado del usuario actual.
3. Permite asociar ventas al usuario sin pasar username y password por todo el sistema.
4. Permite validar acceso antes de registrar o anular una venta.
5. Optimiza recursos porque el usuario ya fue validado una vez al iniciar sesión.
```

Implementación simple:

```java
public class Sesion {
    private static Usuario usuarioActual;

    public static void iniciar(Usuario usuario) {
        usuarioActual = usuario;
    }

    public static Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public static boolean estaActiva() {
        return usuarioActual != null;
    }

    public static boolean tieneRol(String rol) {
        return estaActiva() && usuarioActual.getRol().equals(rol);
    }

    public static void cerrar() {
        usuarioActual = null;
    }
}
```

Regla de uso:

```text
LoginController escribe en Sesion después de autenticar.
Los demás controladores solo consultan Sesion.
Ningún controlador debe volver a pedir username/password para cada venta.
```

### 3.7 Diseñar vista de login

Controles mínimos:

- `TextField` para usuario.
- `PasswordField` para contraseña.
- `Button` para ingresar.
- `Label` para mensajes.

### 3.8 Implementar `LoginController`

```java
public class LoginController {
    private final UsuarioService usuarioService = new UsuarioServiceImplSQLite();

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private void onIngresarClick() {
        Usuario usuario = usuarioService.autenticar(
                txtUsername.getText(),
                txtPassword.getText()
        );

        if (usuario == null) {
            // Mostrar mensaje: credenciales incorrectas.
            return;
        }

        Sesion.iniciar(usuario);
        // Abrir ventana principal.
    }
}
```

### 3.9 Asociar automáticamente la venta al usuario actual

La referencia de `Venta` a `Usuario` ya existe desde S10. En S13 se elimina el selector manual de usuario y se obtiene el usuario autenticado desde `Sesion`.

Antes de guardar:

```java
if (!Sesion.estaActiva()) {
    // Mostrar mensaje: debe iniciar sesión.
    return;
}

Usuario usuario = Sesion.getUsuarioActual();
venta.setUsuario(usuario);
```

En `VentaDao.insertar(...)`, la venta se guarda con `usuario_id`:

```java
INSERT INTO venta (cliente, fecha, total, estado, usuario_id)
VALUES (?, ?, ?, ?, ?)
```

En `VentaDao.listar()`, las ventas se consultan junto con el usuario:

```sql
SELECT v.id, v.cliente, v.fecha, v.total, v.estado,
       u.id AS usuario_id, u.username, u.password_hash, u.rol
FROM venta v
LEFT JOIN usuario u ON u.id = v.usuario_id
ORDER BY v.id DESC;
```

### 3.10 Usar `Sesion` desde `VentaController`

```java
public class VentaController {
    @FXML
    private void onGuardarVenta() {
        if (!Sesion.estaActiva()) {
            // Mostrar alerta: acceso denegado.
            return;
        }

        Usuario usuario = Sesion.getUsuarioActual();

        Venta venta = new Venta();
        venta.setUsuario(usuario);

        // Completar datos de venta y delegar al servicio.
    }
}
```

En el proyecto guía, `AnularVentasController` también muestra el usuario que registró la venta mediante la columna `colUsuario`. Si una venta antigua no tiene usuario, la tabla muestra `Sin usuario`.

```java
colUsuario.setCellValueFactory(data ->
        new SimpleStringProperty(obtenerUsername(data.getValue()))
);
```

### 3.11 Aplicar roles y permisos básicos

La IGU puede ocultar o deshabilitar opciones no autorizadas:

```java
btnAnular.setDisable(!Sesion.tieneRol("ADMIN"));
```

La validación visual no es suficiente. El servicio también protege la acción:

```java
if (!Sesion.tieneRol("ADMIN")) {
    throw new AccesoDenegadoException("No tiene permiso para anular ventas");
}
```

### 3.12 Validaciones de cierre de sesión

Probar:

1. Login correcto.
2. Login incorrecto.
3. Guardar venta con sesión activa.
4. Intentar guardar venta sin sesión activa.
5. Cerrar sesión y verificar que ya no se pueda operar.
6. Revisar en SQLite que la venta quedó asociada al usuario.

## 4. Crea: actividad autónoma

Fuera del aula, cada estudiante consolida la autenticación y la relación usuario-ventas.

Tiempo: 2h fuera del aula.

### 4.1 Plantilla de evidencia individual

Entrega un PDF con el siguiente nombre:

```text
S13_Equipo##_ApellidoNombre.pdf
```

#### 4.1.1 Datos del estudiante

- Nombre:
- Equipo:
- Sesión: S13 - Control de acceso y sesión desde la IGU
- Rol o aporte realizado:
- Link de GitHub:

#### 4.1.2 Trabajo autónomo realizado

1. Crear usuario de prueba.
2. Implementar login básico.
3. Mantener sesión activa.
4. Asociar una venta al usuario.
5. Evidenciar roles y permisos en la IGU y el servicio.
6. Validar credenciales incorrectas.
7. Validar venta sin sesión.

#### 4.1.3 Evidencia técnica

- Captura de login.
- Código o fragmento de `UsuarioDao`.
- Código o fragmento de `UsuarioServiceImplSQLite`.
- Código o fragmento de `Sesion`.
- Evidencia de usuario autenticado.
- Evidencia de venta asociada al usuario.
- Validación de acceso o credenciales.
- Evidencia de participación en el concurso de programación y presentación del código desarrollado.

#### 4.1.4 Error o hallazgo

Describe un problema encontrado al controlar sesión o acceso.

#### 4.1.5 Reflexión técnica breve

Responde en 5 a 8 líneas:

```text
Por qué conviene guardar el usuario autenticado en Sesion en lugar de consultar la base de datos cada vez que se registra una venta?
```

### 4.2 Criterios mínimos de aceptación

- PDF con nombre correcto.
- Login básico funcional.
- Usuario persistido en SQLite.
- Sesión activa controlada.
- Venta asociada al usuario.
- Validación de acceso.

## 5. Cierre evaluativo

Tiempo: 20 min.

### 5.1 Resultados esperados

- El estudiante explica autenticación básica.
- Usuario se persiste mediante DAO.
- La sesión activa se consulta desde controladores.
- Las ventas se asocian al usuario.
- Se reutiliza la asociación `Usuario–Venta` sin selección manual.
- Se aplican roles o permisos básicos en la IGU y el servicio.
- Se aplican validaciones de acceso.
- El estudiante explica por qué `Sesion` evita consultas repetidas a la base de datos.

### 5.2 Evidencia del producto de sesión

Cada estudiante entrega un PDF individual siguiendo la plantilla de la sección 4.1.

### 5.3 Preguntas de defensa y reflexión

1. Qué responsabilidad tiene `UsuarioDao`?
2. Qué responsabilidad tiene `UsuarioService`?
3. Dónde se guarda el usuario autenticado durante la ejecución?
4. Por qué `Sesion` no es una sesión web?
5. Por qué ocultar un botón no reemplaza la validación del servicio?
6. Qué validación evita operar sin sesión?
7. Por qué no debe guardarse contraseña en texto plano?

### 5.4 Rúbrica de evaluación

| Dimensión | Peso | 3 - Logro destacado | 2 - Logro | 1 - Proceso | 0 - Inicio | Puntuación obtenida |
|---|---:|---|---|---|---|---:|
| 1. Usuario y login | 2 | Login funcional y usuario persistido correctamente. | Login funcional. | Login parcial. | No evidencia login. | |
| 2. Sesión activa | 2 | Controla sesión y acceso con claridad. | Sesión funcional. | Sesión parcial. | No controla sesión. | |
| 3. Permisos y asociación | 2 | Aplica permisos en IGU y servicio; asocia la venta al usuario activo. | Permisos y asociación funcionales. | Implementación parcial. | No controla acceso. | |
| 4. Capas | 2 | Controlador, servicio y DAO separados. | Separación suficiente. | Mezcla responsabilidades. | No separa. | |
| 5. Error o hallazgo | 1 | Analiza causa y solución. | Explica un problema. | Menciona un problema. | No presenta. | |
| 6. Orden y reflexión | 1 | Evidencia clara y reflexión precisa. | Evidencia suficiente. | Evidencia incompleta. | No sustenta. | |
