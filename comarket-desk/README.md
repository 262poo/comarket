# comarket-desk

Proyecto de escritorio JavaFX para la Unidad 2 y Unidad 3.

Tag sugerido: `sesion-11`

Aquí se trabaja la evolución de CoMarket hacia una aplicación de escritorio con arquitectura por capas, JavaFX, FXML, controladores, servicios, DAO, JDBC y SQLite.

En este hito, `Producto` se mantiene como catálogo persistente, `Venta` conserva cabecera y detalles, y se consolidan consultas integradas con filtros, vista maestro-detalle, totales y validaciones del flujo principal.

La aplicación incluye cuatro pestañas:

- `Productos`: CRUD persistente del catálogo.
- `Ventas`: registro de cabecera y detalles asociado al usuario autenticado.
- `Anular ventas`: listado de ventas registradas, detalle de la venta seleccionada y anulación con reposición de stock.
- `Reporte de ventas`: filtros por cliente, fecha, usuario y estado; listado de ventas, detalle de la venta seleccionada, total mostrado y verificación de total contra detalle.

Antes de ingresar a esas pestañas, la aplicación muestra un login. El usuario de prueba se crea automáticamente:

```text
usuario: admin
clave: 123456
rol: ADMIN
```

La versión en memoria de productos queda como referencia en `ProductoServiceImplMemoria`. Las versiones activas usan SQLite mediante `ProductoServiceImplSQLite`, `VentaServiceImplSQLite` y `UsuarioServiceImplSQLite`.

## Flujo final de demo

1. Iniciar sesión con `admin` / `123456`.
2. Abrir `Productos` y registrar o editar productos.
3. Abrir `Ventas` y registrar una venta con cabecera y detalles.
4. Abrir `Anular ventas`, seleccionar una venta activa y anularla.
5. Abrir `Reporte de ventas` y aplicar filtros por cliente, fecha, usuario o estado.
6. Verificar persistencia revisando que los datos sigan disponibles al recargar la aplicación o consultando `data/comarket.db`.

## Matriz de pruebas S11

| Caso | Datos | Resultado esperado |
|---|---|---|
| Login correcto | `admin` / `123456` | Abre la ventana principal |
| Login incorrecto | Usuario o clave inválida | Muestra mensaje de credenciales incorrectas |
| Consulta por fecha | Rango con registros | Lista ventas dentro del rango |
| Consulta sin resultados | Filtro sin coincidencias | Muestra tabla vacía y resumen en cero |
| Ver detalle | Venta seleccionada | Muestra productos, cantidades, precios y subtotales |
| Total | Venta con detalles | Total de cabecera coincide con total de detalle |
| Anular venta | Venta activa seleccionada | Cambia estado a `ANULADA` y repone stock |
| Sin selección | Anular sin fila seleccionada | Muestra mensaje claro |

## Ejecutar

```powershell
.\mvnw.cmd clean javafx:run
```

También puedes usar Maven instalado localmente:

```bash
mvn clean javafx:run
```

Al ejecutar desde IntelliJ o Maven, la base de datos local se crea automáticamente en:

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

Las tablas creadas por la aplicación son:

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

### Ruta rapida Windows para S14

Ejecuta estos comandos desde la raiz de `comarket-desk`.

1. Preparar GraalVM JDK 17 en `C:\java` y dejar `JAVA_HOME` configurado para el usuario:

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

Cierra y abre una terminal nueva. Luego verifica:

```powershell
java -version
native-image --version
```

2. Ejecutar la app normal y validar el flujo principal:

```powershell
.\mvnw.cmd clean javafx:run
```

3. Generar configuracion con el agente de GluonFX. Mientras la app este abierta, recorre login, productos, ventas, anular ventas, reporte y cierre de sesion:

```powershell
.\mvnw.cmd -DskipTests gluonfx:runagent
```

4. Construir el `.exe` nativo:

```powershell
.\mvnw.cmd -DskipTests gluonfx:build
```

5. Probar el ejecutable nativo:

```powershell
.\mvnw.cmd -DskipTests gluonfx:nativerun
```

6. Opcional: generar paquete instalable:

```powershell
.\mvnw.cmd -DskipTests gluonfx:package
```

Si falla por compilador C/C++, instala **Build Tools for Visual Studio** con la carga **Desktop development with C++**, reinicia la terminal y vuelve a ejecutar desde el paso 4.

Requisito: GraalVM JDK instalado, `native-image` disponible en la terminal y herramientas de compilación C++ del sistema operativo.

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

En Windows, `native-image` también necesita las herramientas de compilación C++ de Visual Studio. Si aparece un error relacionado con compilador C/C++, instala **Build Tools for Visual Studio** con la carga **Desktop development with C++** y vuelve a abrir la terminal.

En Linux/macOS o WSL, SDKMAN sí es una opción cómoda:

```bash
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk list java
sdk install java <version>-graal
sdk default java <version>-graal
```

Reemplaza `<version>-graal` por una versión listada por `sdk list java`.

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

Antes de compilar a nativo, ejecuta primero la aplicación en JVM y prueba login, CRUD de `Producto`, registro de `Venta`, filtros de consulta, detalle, totales, anulación y cierre de sesión:

```powershell
.\mvnw.cmd clean javafx:run
```

Generar configuración de GraalVM con el agente. Mientras la app esté abierta, ingresa con `admin`, registra productos, crea una venta con varios detalles, consulta ventas usando filtros, selecciona una venta para ver su detalle, anula una venta y cierra sesión para que el agente detecte el uso de JavaFX, FXML, JDBC, SQLite y login:

```powershell
.\mvnw.cmd -DskipTests gluonfx:runagent
```

Generar el ejecutable nativo para el hito `sesion-11`:

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

La aplicación de consola para Unidad 1 se trabaja en `../comarket-cli`.
