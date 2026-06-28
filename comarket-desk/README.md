# comarket-desk

Proyecto de escritorio JavaFX para la Unidad 2 y Unidad 3.

Tag sugerido: `sesion-10`

Aqui se trabaja la evolucion de CoMarket hacia una aplicacion de escritorio con arquitectura por capas, JavaFX, FXML, controladores, servicios, DAO, JDBC y SQLite.

En este hito, `Producto` se mantiene como catalogo persistente, `Venta` conserva cabecera y detalles, y se agrega seguridad basica con usuario autenticado. Las ventas nuevas quedan asociadas al usuario activo mediante una relacion uno a muchos.

La aplicacion incluye tres pestanas:

- `Productos`: CRUD persistente del catalogo.
- `Ventas`: registro de cabecera y detalles asociado al usuario autenticado.
- `Consulta de ventas`: listado de ventas registradas, detalle de la venta seleccionada y anulacion con reposicion de stock.

Antes de ingresar a esas pestanas, la aplicacion muestra un login. El usuario de prueba se crea automaticamente:

```text
usuario: admin
clave: 123456
rol: ADMIN
```

La version en memoria de productos queda como referencia en `ProductoServiceImplMemoria`. Las versiones activas usan SQLite mediante `ProductoServiceImplSQLite`, `VentaServiceImplSQLite` y `UsuarioServiceImplSQLite`.

## Ejecutar

```powershell
.\mvnw.cmd clean javafx:run
```

Tambien puedes usar Maven instalado localmente:

```bash
mvn clean javafx:run
```

Al ejecutar desde IntelliJ o Maven, la base de datos local se crea automaticamente en:

```text
data/comarket.db
```

Al ejecutar el binario nativo en Windows, la base queda al lado del `.exe`:

```text
carpeta-del-ejecutable/
    comarket-desk.exe
    data/
        comarket.db
```

Las tablas creadas por la aplicacion son:

```sql
producto(codigo, nombre, precio, stock)
usuario(id, username, password_hash, rol)
venta(id, cliente, fecha, total, estado, usuario_id)
detalle_venta(id, venta_id, producto_codigo, cantidad, precio_unitario, subtotal)
```

Para revisar los datos con `sqlite3`:

```powershell
sqlite3 data\comarket.db "SELECT * FROM producto;"
sqlite3 data\comarket.db "SELECT * FROM usuario;"
sqlite3 data\comarket.db "SELECT * FROM venta;"
sqlite3 data\comarket.db "SELECT * FROM detalle_venta;"
```

## Generar ejecutable nativo

Requisito: GraalVM JDK instalado, `native-image` disponible en la terminal y herramientas de compilacion C++ del sistema operativo.

### Instalar GraalVM

En Windows no necesitas SDKMAN. Puedes instalar GraalVM descargando el ZIP oficial y configurando `JAVA_HOME`:

```powershell
$version="17"
$zip="$env:TEMP\graalvm-jdk-$version.zip"
$dest="C:\java"
New-Item -ItemType Directory -Force -Path $dest
Invoke-WebRequest -Uri "https://download.oracle.com/graalvm/$version/latest/graalvm-jdk-$version`_windows-x64_bin.zip" -OutFile $zip
Expand-Archive -Path $zip -DestinationPath $dest -Force
$graalHome=(Get-ChildItem $dest -Directory | Where-Object Name -Like "graalvm-jdk-$version*").FullName
[Environment]::SetEnvironmentVariable("JAVA_HOME", $graalHome, "User")
[Environment]::SetEnvironmentVariable("Path", "$graalHome\bin;" + [Environment]::GetEnvironmentVariable("Path", "User"), "User")
```

Cierra y abre una terminal nueva.

En Windows, `native-image` tambien necesita las herramientas de compilacion C++ de Visual Studio. Si aparece un error relacionado con compilador C/C++, instala **Build Tools for Visual Studio** con la carga **Desktop development with C++** y vuelve a abrir la terminal.

En Linux/macOS o WSL, SDKMAN si es una opcion comoda:

```bash
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk list java
sdk install java <version>-graal
sdk default java <version>-graal
```

Reemplaza `<version>-graal` por una version listada por `sdk list java`.

Verificar:

```bash
java -version
native-image --version
```

Si `native-image` no se reconoce en Windows, instala GraalVM JDK y configura la terminal para usarlo:

```powershell
$env:JAVA_HOME="C:\ruta\a\graalvm-jdk-17"
$env:Path="$env:JAVA_HOME\bin;$env:Path"
java -version
native-image --version
```

Para dejarlo permanente, agrega `JAVA_HOME` en las variables de entorno de Windows y coloca `%JAVA_HOME%\bin` al inicio de `Path`. Luego abre una terminal nueva.

Antes de compilar a nativo, ejecuta primero la aplicacion en JVM y prueba login, CRUD de `Producto`, registro de `Venta`, consulta, anulacion y cierre de sesion:

```powershell
.\mvnw.cmd clean javafx:run
```

Generar configuracion de GraalVM con el agente. Mientras la app este abierta, ingresa con `admin`, registra productos, crea una venta con varios detalles, consulta ventas, anula una venta y cierra sesion para que el agente detecte el uso de JavaFX, FXML, JDBC, SQLite y login:

```powershell
.\mvnw.cmd -DskipTests gluonfx:runagent
```

Generar el ejecutable nativo para el hito `sesion-10`:

```powershell
.\mvnw.cmd -DskipTests gluonfx:build
```

Ejecutar el binario nativo y verificar que cree/use `data/comarket.db` junto al `.exe`:

```powershell
.\mvnw.cmd -DskipTests gluonfx:nativerun
```

Crear un instalador o paquete para el sistema operativo:

```powershell
.\mvnw.cmd -DskipTests gluonfx:package
```

La aplicacion de consola para Unidad 1 se trabaja en `../comarket-cli`.
