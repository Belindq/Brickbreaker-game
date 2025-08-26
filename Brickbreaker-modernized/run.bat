@echo off
setlocal enabledelayedexpansion
set CMD=%1
if "%CMD%"=="" set CMD=run
set MAIN_CLASS=%2
if "%MAIN_CLASS%"=="" set MAIN_CLASS=Main
if not exist out mkdir out
dir /S /B src\main\java\*.java > sources.txt
javac -d out @sources.txt
if exist assets xcopy /E /Y /I assets out >nul
if "%CMD%"=="run" ( java -cp out %MAIN_CLASS% & goto :eof )
if "%CMD%"=="test" (
  set JUNIT_VER=1.10.2
  if not exist .tools mkdir .tools
  if not exist .tools\junit-platform-console-standalone-%JUNIT_VER%.jar ^
    powershell -Command "Invoke-WebRequest https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/%JUNIT_VER%/junit-platform-console-standalone-%JUNIT_VER%.jar -OutFile .tools/junit-platform-console-standalone-%JUNIT_VER%.jar"
  dir /S /B src\test\java\*.java > tests.txt 2>nul
  if exist tests.txt (
    javac -cp "out;.tools\junit-platform-console-standalone-%JUNIT_VER%.jar" -d out @tests.txt
    java -jar .tools\junit-platform-console-standalone-%JUNIT_VER%.jar -cp out --scan-classpath
  ) else ( echo No tests found in src\test\java )
  goto :eof
)
if "%CMD%"=="jar" (
  if not exist build mkdir build
  echo Main-Class: %MAIN_CLASS%> MANIFEST.MF
  jar cfm build\BrickBreaker.jar MANIFEST.MF -C out .
  if exist assets xcopy /E /Y /I assets build >nul
  echo Created build\BrickBreaker.jar
)