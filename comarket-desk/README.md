# comarket-desk

Proyecto de escritorio JavaFX para la Unidad 2 y Unidad 3.

Tag sugerido: `sesion-08`

Aqui se trabaja la evolucion de CoMarket hacia una aplicacion de escritorio con arquitectura por capas, JavaFX, FXML, controladores, servicios, DAO, JDBC y SQLite.

En este hito, el CRUD de `Producto` ya persiste datos en SQLite. La pantalla usa la interfaz `ProductoService`; la version en memoria queda como referencia en `ProductoServiceImplMemoria`, y la version activa usa `ProductoServiceImplSQLite`.

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

La tabla creada por la aplicacion es:

```sql
producto(codigo, nombre, precio, stock)
```

Para revisar los datos con `sqlite3`:

```powershell
sqlite3 data\comarket.db "SELECT * FROM producto;"
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

Antes de compilar a nativo, ejecuta primero la aplicacion en JVM y prueba el CRUD de `Producto`:

```powershell
.\mvnw.cmd clean javafx:run
```

Generar configuracion de GraalVM con el agente. Mientras la app este abierta, registra, edita, elimina y lista productos para que el agente detecte el uso de JavaFX, FXML, JDBC y SQLite:

```powershell
.\mvnw.cmd -DskipTests gluonfx:runagent
```

Generar el ejecutable nativo para el hito `sesion-08`:

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
