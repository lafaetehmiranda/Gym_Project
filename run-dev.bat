@echo off
setlocal

REM Set JAVA_HOME to Java 21
set "JAVA_HOME=C:\Program Files\Java\jdk-21"
set "PATH=%JAVA_HOME%\bin;%PATH%"

REM Verify Java version
echo Using Java from: %JAVA_HOME%
java -version

REM Run Maven wrapper with Quarkus dev mode
echo.
echo Starting Quarkus in dev mode...
call mvnw.cmd quarkus:dev

endlocal
