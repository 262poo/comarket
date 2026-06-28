# comarket-cli

Proyecto de consola para la Unidad 1.

Aqui se trabaja el producto base del curso: clases del dominio, encapsulamiento, relaciones entre objetos, herencia, servicios, colecciones y CRUD en memoria desde consola.

## Ejecutar

```bash
mvn compile
mvn exec:java
```

## Generar ejecutable nativo

Requisito: GraalVM JDK instalado y `native-image` disponible en la terminal.

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

Compilar el JAR ejecutable:

```bash
mvn clean package
java -jar target/comarket-cli-1.0-SNAPSHOT.jar
```

Generar el ejecutable nativo:

```bash
native-image -jar target/comarket-cli-1.0-SNAPSHOT.jar comarket-cli
```

Ejecutar en Windows:

```bash
.\comarket-cli.exe
```

Ejecutar en Linux/macOS:

```bash
./comarket-cli
```

La aplicacion de escritorio JavaFX para Unidad 2 y Unidad 3 se trabaja en `../comarket-desk`.
