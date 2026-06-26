# S10 - Seguridad bÃ¡sica y relaciÃ³n uno a muchos

## 1. IntroducciÃ³n

Tiempo: 20 min.

### 1.1 PropÃ³sito

Incorporar seguridad bÃ¡sica mediante usuarios, autenticaciÃ³n simple y operaciones persistentes asociadas a una relaciÃ³n uno a muchos.

### 1.2 Resultado de aprendizaje

El estudiante crea una tabla de usuarios, implementa un login bÃ¡sico, mantiene una sesiÃ³n activa y asocia operaciones persistentes al usuario autenticado.

### 1.3 Producto de sesiÃ³n

AutenticaciÃ³n bÃ¡sica y registro de operaciones asociadas a un usuario, usando GUI, servicio, DAO, SQLite y validaciones de acceso.

### 1.4 MotivaciÃ³n de la sesiÃ³n

Una aplicaciÃ³n de escritorio no solo guarda datos; tambiÃ©n debe saber quiÃ©n realiza una operaciÃ³n. Esta sesiÃ³n agrega usuario y seguridad bÃ¡sica sin convertir el curso en seguridad avanzada.

Pregunta guÃ­a:

```text
CÃ³mo asociamos operaciones persistentes a un usuario autenticado sin consultar la base de datos en cada pantalla?
```

### 1.5 UbicaciÃ³n en el curso

- Unidad: U2.
- Carpeta de trabajo: `comarket-desk`.
- Avance de sesiÃ³n: seguridad bÃ¡sica y relaciÃ³n simple uno a muchos.

## 2. Explica

Tiempo: 25 min.

### 2.1 Conceptos clave

- Usuario.
- AutenticaciÃ³n bÃ¡sica.
- SesiÃ³n activa en aplicaciÃ³n de escritorio.
- RelaciÃ³n uno a muchos.
- Operaciones asociadas al usuario.
- ValidaciÃ³n de acceso.
- DAO para usuario.
- Manejo bÃ¡sico de errores.

Regla metodolÃ³gica de la sesiÃ³n:

```text
La seguridad se trabaja de forma bÃ¡sica.
Usuario no reemplaza al dominio principal.
Usuario permite asociar operaciones a quien las registra.
La relaciÃ³n uno a muchos se entiende como un usuario con varias operaciones.
Las validaciones de acceso se aplican antes de ejecutar la operaciÃ³n.
Sesion no es una sesiÃ³n web.
Sesion es un estado simple de la aplicaciÃ³n de escritorio.
Sesion evita consultar la base de datos cada vez que una pantalla necesita saber quÃ© usuario estÃ¡ autenticado.
`UsuarioDAO` se ubica en `dao` y reutiliza `util/ConexionBD`.
```

### 2.2 Arquitectura de la sesiÃ³n

```mermaid
classDiagram
    class LoginController {
        onIngresar()
    }

    class VentaController {
        onGuardarVenta()
    }

    class UsuarioService {
        <<interface>>
        autenticar(username, password)
    }

    class UsuarioServiceImplDB {
        autenticar(username, password)
    }

    class UsuarioDAO {
        buscarPorUsername(username)
    }

    class ConexionBD {
        obtenerConexion()
    }

    class SQLite {
        usuario
        venta
    }

    class Sesion {
        -usuarioActual
        iniciar(usuario)
        getUsuarioActual()
        estaActiva()
        cerrar()
    }

    class Usuario {
        -id
        -username
        -passwordHash
        -rol
    }

    class Venta {
        -cliente
        -fecha
        -usuario
    }

    LoginController ..> UsuarioService : usa contrato
    UsuarioService <|.. UsuarioServiceImplDB : implements
    UsuarioServiceImplDB --> UsuarioDAO : usa
    UsuarioDAO --> ConexionBD : usa
    ConexionBD --> SQLite : JDBC
    UsuarioDAO ..> Usuario : retorna
    LoginController --> Sesion : guarda usuario
    VentaController ..> Sesion : consulta
    Venta "*" --> "1" Usuario : registrada por
```

## 3. Aplica: actividad prÃ¡ctica guiada

Tiempo: 2h.

### 3.1 Crear tabla `usuario`

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
VALUES ('admin', '123456', 'ADMIN');
```

Nota metodolÃ³gica:

```text
Para la prÃ¡ctica se puede usar texto simple.
En una aplicaciÃ³n real la contraseÃ±a debe almacenarse usando hash seguro.
```

### 3.2 Crear entidad `Usuario`

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

### 3.3 Crear `UsuarioDAO`

`UsuarioDAO` solo conversa con la base de datos.

```java
public class UsuarioDAO {
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

### 3.5 Crear `UsuarioServiceImplDB`

El servicio decide si las credenciales son vÃ¡lidas. El controlador no debe comparar contraseÃ±as ni ejecutar SQL.

```java
public class UsuarioServiceImplDB implements UsuarioService {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    public Usuario autenticar(String username, String password) {
        Usuario usuario = usuarioDAO.buscarPorUsername(username);

        if (usuario == null) {
            return null;
        }

        if (!usuario.getPasswordHash().equals(password)) {
            return null;
        }

        return usuario;
    }
}
```

### 3.6 Crear la clase `Sesion`

`Sesion` no reemplaza a la base de datos y no es una sesiÃ³n web. Es una clase simple que conserva en memoria el usuario autenticado durante la ejecuciÃ³n de la aplicaciÃ³n.

Utilidad:

```text
1. Evita consultar la base de datos en cada pantalla para saber quiÃ©n estÃ¡ autenticado.
2. Centraliza el estado del usuario actual.
3. Permite asociar operaciones al usuario sin pasar username y password por todo el sistema.
4. Permite validar acceso antes de guardar una operaciÃ³n.
5. Optimiza recursos porque el usuario ya fue validado una vez al iniciar sesiÃ³n.
```

ImplementaciÃ³n simple:

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

    public static void cerrar() {
        usuarioActual = null;
    }
}
```

Regla de uso:

```text
LoginController escribe en Sesion despuÃ©s de autenticar.
Los demÃ¡s controladores solo consultan Sesion.
NingÃºn controlador debe volver a pedir username/password para cada operaciÃ³n.
```

### 3.7 DiseÃ±ar vista de login

Controles mÃ­nimos:

- `TextField` para usuario.
- `PasswordField` para contraseÃ±a.
- `Button` para ingresar.
- `Label` para mensajes.

### 3.8 Implementar `LoginController`

```java
public class LoginController {
    private UsuarioService usuarioService = new UsuarioServiceImplDB();

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private void onIngresar() {
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

### 3.9 Asociar una operaciÃ³n al usuario actual

La tabla de operaciÃ³n debe tener una referencia al usuario. En el flujo de venta:

```sql
ALTER TABLE venta ADD COLUMN usuario_id INTEGER REFERENCES usuario(id);
```

Antes de guardar:

```java
if (!Sesion.estaActiva()) {
    // Mostrar mensaje: debe iniciar sesiÃ³n.
    return;
}

Usuario usuario = Sesion.getUsuarioActual();
venta.setUsuario(usuario);
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

### 3.11 Validaciones de cierre de sesiÃ³n

Probar:

1. Login correcto.
2. Login incorrecto.
3. Guardar operaciÃ³n con sesiÃ³n activa.
4. Intentar guardar operaciÃ³n sin sesiÃ³n activa.
5. Cerrar sesiÃ³n y verificar que ya no se pueda operar.
6. Revisar en SQLite que la operaciÃ³n quedÃ³ asociada al usuario.

## 4. Crea: actividad autÃ³noma

Fuera del aula, cada estudiante consolida la autenticaciÃ³n y la relaciÃ³n con operaciones persistentes.

Tiempo: 2h fuera del aula.

### 4.1 Plantilla de evidencia individual

Entrega un PDF con el siguiente nombre:

```text
S10_Equipo##_ApellidoNombre.pdf
```

#### 4.1.1 Datos del estudiante

- Nombre:
- Equipo:
- SesiÃ³n: S10 - Seguridad bÃ¡sica y relaciÃ³n uno a muchos
- Rol o aporte realizado:
- Link de GitHub:

#### 4.1.2 Trabajo autÃ³nomo realizado

1. Crear usuario de prueba.
2. Implementar login bÃ¡sico.
3. Mantener sesiÃ³n activa.
4. Asociar una operaciÃ³n al usuario.
5. Evidenciar relaciÃ³n uno a muchos.
6. Validar credenciales incorrectas.
7. Validar operaciÃ³n sin sesiÃ³n.

#### 4.1.3 Evidencia tÃ©cnica

- Captura de login.
- CÃ³digo o fragmento de `UsuarioDAO`.
- CÃ³digo o fragmento de `UsuarioServiceImplDB`.
- CÃ³digo o fragmento de `Sesion`.
- Evidencia de usuario autenticado.
- Evidencia de operaciÃ³n asociada al usuario.
- ValidaciÃ³n de acceso o credenciales.

#### 4.1.4 Error o hallazgo

Describe un problema encontrado al controlar sesiÃ³n o acceso.

#### 4.1.5 ReflexiÃ³n tÃ©cnica breve

Responde en 5 a 8 lÃ­neas:

```text
Por quÃ© conviene guardar el usuario autenticado en Sesion en lugar de consultar la base de datos en cada operaciÃ³n?
```

### 4.2 Criterios mÃ­nimos de aceptaciÃ³n

- PDF con nombre correcto.
- Login bÃ¡sico funcional.
- Usuario persistido en SQLite.
- SesiÃ³n activa controlada.
- OperaciÃ³n asociada al usuario.
- ValidaciÃ³n de acceso.

## 5. Cierre evaluativo

Tiempo: 20 min.

### 5.1 Resultados esperados

- El estudiante explica autenticaciÃ³n bÃ¡sica.
- Usuario se persiste mediante DAO.
- La sesiÃ³n activa se consulta desde controladores.
- Las operaciones se asocian al usuario.
- Se evidencia relaciÃ³n uno a muchos.
- Se aplican validaciones de acceso.
- El estudiante explica por quÃ© `Sesion` evita consultas repetidas a la base de datos.

### 5.2 Evidencia del producto de sesiÃ³n

Cada estudiante entrega un PDF individual siguiendo la plantilla de la secciÃ³n 4.1.

### 5.3 Preguntas de defensa y reflexiÃ³n

1. QuÃ© responsabilidad tiene `UsuarioDAO`?
2. QuÃ© responsabilidad tiene `UsuarioService`?
3. DÃ³nde se guarda el usuario autenticado durante la ejecuciÃ³n?
4. Por quÃ© `Sesion` no es una sesiÃ³n web?
5. QuÃ© significa relaciÃ³n uno a muchos en esta sesiÃ³n?
6. QuÃ© validaciÃ³n evita operar sin sesiÃ³n?
7. Por quÃ© no debe guardarse contraseÃ±a en texto plano?

### 5.4 RÃºbrica de evaluaciÃ³n

| DimensiÃ³n | Peso | 3 - Logro destacado | 2 - Logro | 1 - Proceso | 0 - Inicio | PuntuaciÃ³n obtenida |
|---|---:|---|---|---|---|---:|
| 1. Usuario y login | 2 | Login funcional y usuario persistido correctamente. | Login funcional. | Login parcial. | No evidencia login. | |
| 2. SesiÃ³n activa | 2 | Controla sesiÃ³n y acceso con claridad. | SesiÃ³n funcional. | SesiÃ³n parcial. | No controla sesiÃ³n. | |
| 3. RelaciÃ³n uno a muchos | 2 | Operaciones asociadas al usuario correctamente. | AsociaciÃ³n funcional. | AsociaciÃ³n parcial. | No evidencia relaciÃ³n. | |
| 4. Capas | 2 | Controlador, servicio y DAO separados. | SeparaciÃ³n suficiente. | Mezcla responsabilidades. | No separa. | |
| 5. Error o hallazgo | 1 | Analiza causa y soluciÃ³n. | Explica un problema. | Menciona un problema. | No presenta. | |
| 6. Orden y reflexiÃ³n | 1 | Evidencia clara y reflexiÃ³n precisa. | Evidencia suficiente. | Evidencia incompleta. | No sustenta. | |
