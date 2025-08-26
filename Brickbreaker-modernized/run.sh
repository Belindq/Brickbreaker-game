#!/usr/bin/env bash
set -euo pipefail
MAIN_CLASS="${2:-Main}"     # change to BrickBreakerGame if that's your entry
CMD="${1:-run}"             # run | test | jar
mkdir -p out
find src/main/java -name "*.java" > sources.txt
javac -d out @sources.txt
if [ -d assets ]; then cp -R assets/* out/ 2>/dev/null || true; fi
if [ "$CMD" = "run" ]; then exec java -cp out "$MAIN_CLASS"; fi
if [ "$CMD" = "test" ]; then
  JUNIT_VER="1.10.2"
  JAR=".tools/junit-platform-console-standalone-${JUNIT_VER}.jar"
  mkdir -p .tools
  [ -f "$JAR" ] || curl -L -o "$JAR" \
    "https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/${JUNIT_VER}/junit-platform-console-standalone-${JUNIT_VER}.jar"
  find src/test/java -name "*.java" > tests.txt || true
  if [ -s tests.txt ]; then
    javac -cp "out:$JAR" -d out @tests.txt
    exec java -jar "$JAR" -cp out --scan-classpath
  else
    echo "No tests in src/test/java"
  fi
fi
if [ "$CMD" = "jar" ]; then
  mkdir -p build
  echo "Main-Class: $MAIN_CLASS" > MANIFEST.MF
  jar cfm build/BrickBreaker.jar MANIFEST.MF -C out .
  if [ -d assets ]; then cp -R assets/* build/ 2>/dev/null || true; fi
  echo "Created build/BrickBreaker.jar"
fi
