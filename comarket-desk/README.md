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

Esta es la ruta principal para Windows. Ejecuta los comandos desde la raiz de `comarket-desk`.

### 1. Instalar GraalVM JDK 21

```powershell
$version="21"
$zip="$env:TEMP\graalvm-jdk-$version.zip"
$dest="C:\java"
New-Item -ItemType Directory -Force -Path $dest
Invoke-WebRequest -Uri "https://download.oracle.com/graalvm/$version/latest/graalvm-jdk-$version`_windows-x64_bin.zip" -OutFile $zip
Expand-Archive -Path $zip -DestinationPath $dest -Force
$graalHome=(Get-ChildItem $dest -Directory | Where-Object Name -Like "graalvm-jdk-$version*").FullName
[Environment]::SetEnvironmentVariable("JAVA_HOME", $graalHome, "User")
[Environment]::SetEnvironmentVariable("GRAALVM_HOME", $graalHome, "User")
[Environment]::SetEnvironmentVariable("Path", "$graalHome\bin;" + [Environment]::GetEnvironmentVariable("Path", "User"), "User")
```

Cierra y abre una terminal nueva. Si `java -version` todavia muestra otro JDK como Temurin 17, o si GluonFX muestra `GraalVM installation directory not found`, fuerza GraalVM solo para la terminal actual:

```powershell
$env:JAVA_HOME="C:\java\graalvm-jdk-21.0.11+9.1"
$env:GRAALVM_HOME=$env:JAVA_HOME
$env:Path="$env:JAVA_HOME\bin;$env:Path"
```

Verifica que GraalVM y Native Image esten disponibles:

```powershell
java -version
native-image --version
```

El proyecto sigue compilando para Java 17 porque `pom.xml` usa `maven.compiler.release=17`.

### 2. Instalar Visual Studio Build Tools

- Descarga **Visual Studio Build Tools 2022 or later** desde la pagina de descargas de Visual Studio, seccion **Todas las descargas**.
- En el instalador selecciona la carga **Desktop development with C++**.
- Confirma que incluya **MSVC** y **Windows SDK**.
- Finaliza la instalacion y abre una terminal nueva.

Verifica que el compilador este disponible:

```powershell
where cl
```

La salida debe mostrar una ruta con `Hostx64\x64`, por ejemplo:

```text
C:\Program Files (x86)\Microsoft Visual Studio\2022\BuildTools\VC\Tools\MSVC\14.42.34433\bin\Hostx64\x64\cl.exe
```

Si `where cl` no muestra nada, o si la ruta muestra `HostX86\x86`, no continues. Abre **x64 Native Tools Command Prompt for VS 2022** y ejecuta desde alli los comandos de Maven.

### 3. Ejecutar la app normal

```powershell
.\mvnw.cmd clean javafx:run
```

Prueba login, CRUD de `Producto`, registro de `Venta`, anular ventas, filtros de reporte, detalle, totales y cierre de sesion.

### 4. Generar configuracion con el agente de GluonFX

Desde este paso usa **x64 Native Tools Command Prompt for VS 2022**. No uses PowerShell normal. Configura GraalVM en esa terminal con `set`:

```bat
cd C:\262\262poo\comarket\comarket-desk
set JAVA_HOME=C:\java\graalvm-jdk-21.0.11+9.1
set GRAALVM_HOME=%JAVA_HOME%
set Path=%JAVA_HOME%\bin;%Path%
echo %GRAALVM_HOME%
```

Mientras la app este abierta, recorre login, productos, ventas, anular ventas, reporte y cierre de sesion:

```bat
.\mvnw.cmd -DskipTests gluonfx:runagent
```

### 5. Construir el `.exe` nativo

Continua en **x64 Native Tools Command Prompt for VS 2022** y usa comandos `set`, no comandos `$env:` de PowerShell.

Para construir el `.exe`, usa una terminal con el compilador de Visual Studio cargado en x64. En Windows Terminal, abre una nueva pestaña con **x64 Native Tools Command Prompt for VS 2022**. Si usas **Developer PowerShell for VS 2022**, asegúrate de que este inicializado para x64; si el error muestra `HostX86\x86\cl.exe` o `version ... para x86`, estas usando el compilador x86 y debes cambiar a la terminal x64.

Si aparece `Cannot run program "cl"` o `CreateProcess error=2`, significa que Maven se ejecuto desde una terminal que no encuentra el compilador.

En esa terminal, vuelve a entrar al proyecto y ejecuta:

```bat
cd C:\262\262poo\comarket\comarket-desk
set JAVA_HOME=C:\java\graalvm-jdk-21.0.11+9.1
set GRAALVM_HOME=%JAVA_HOME%
set Path=%JAVA_HOME%\bin;%Path%
where cl
.\mvnw.cmd -DskipTests gluonfx:build
```

### 6. Probar el ejecutable nativo

```bat
.\mvnw.cmd -DskipTests gluonfx:nativerun
```

### 7. Opcional: generar paquete instalable

```bat
.\mvnw.cmd -DskipTests gluonfx:package
```

Si falla por compilador C/C++, revisa el paso 2 y vuelve a ejecutar desde `gluonfx:build`.

### Alternativa: SDKMAN en Linux, macOS o WSL

No ejecutes esta seccion si ya instalaste GraalVM en Windows con los pasos anteriores. Esta es una alternativa para Linux, macOS o WSL:

```bash
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk list java
sdk install java <version>-graal
sdk default java <version>-graal
```

Reemplaza `<version>-graal` por una version listada por `sdk list java`.

Verifica:

```bash
java -version
native-image --version
```

La aplicación de consola para Unidad 1 se trabaja en `../comarket-cli`.
