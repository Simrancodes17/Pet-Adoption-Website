@echo off
echo ====================================================
echo   PawNest - Starting Pet Adoption Platform Server
echo ====================================================
cd /d "%~dp0"

echo Compiling Java source files...
if not exist "backend\bin" mkdir "backend\bin"
javac -d backend\bin backend\src\main\java\com\petadoption\model\*.java backend\src\main\java\com\petadoption\PetAdoptionServer.java

if %ERRORLEVEL% neq 0 (
    echo [ERROR] Compilation failed. Please ensure Java JDK is installed.
    pause
    exit /b %ERRORLEVEL%
)

echo Starting PetAdoptionServer on http://localhost:8080...
start http://localhost:8080
java -cp backend\bin com.petadoption.PetAdoptionServer
pause
