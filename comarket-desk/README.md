# comarket-desk

Proyecto de escritorio JavaFX para la Unidad 2 y Unidad 3.

Aqui se trabaja la evolucion de CoMarket hacia una aplicacion de escritorio: interfaz grafica con JavaFX, controladores, servicios, arquitectura por capas y preparacion para persistencia e integracion del proyecto final.

## Ejecutar

```powershell
.\mvnw.cmd clean javafx:run
```

Tambien puedes usar Maven instalado localmente:

```bash
mvn clean javafx:run
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

Generar configuracion de GraalVM con el agente, recorriendo las pantallas de la aplicacion:

```powershell
.\mvnw.cmd -DskipTests gluonfx:runagent
```

Generar el ejecutable nativo:

```powershell
.\mvnw.cmd -DskipTests gluonfx:build
```

Ejecutar el binario nativo:

```powershell
.\mvnw.cmd -DskipTests gluonfx:nativerun
```

Crear un instalador o paquete para el sistema operativo:

```powershell
.\mvnw.cmd -DskipTests gluonfx:package
```

La aplicacion de consola para Unidad 1 se trabaja en `../comarket-cli`.
