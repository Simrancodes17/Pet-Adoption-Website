@echo off
setlocal enabledelayedexpansion
title PawNest - Online Pet Adoption Platform Launcher
color 0E
cls

echo ======================================================================
echo    PawNest — Online Pet Adoption Platform
echo ======================================================================
echo.
echo    [1/3] Verifying project files...

set "ROOT_DIR=%~dp0"
set "FRONTEND_DIR=%ROOT_DIR%frontend"
set "INDEX_HTML=%FRONTEND_DIR%\index.html"

if not exist "%INDEX_HTML%" (
    echo    [ERROR] Could not find "%INDEX_HTML%".
    echo    Please ensure this script is located in the project root folder.
    pause
    exit /b 1
)
echo          Frontend verified at: "%INDEX_HTML%"

echo.
echo    [2/3] Checking Java runtime environment...
where java >nul 2>&1
if %ERRORLEVEL% equ 0 (
    echo          Java runtime detected.
    echo          Compiling and starting background Java server...
    
    if not exist "%ROOT_DIR%backend\bin" mkdir "%ROOT_DIR%backend\bin"
    javac -d "%ROOT_DIR%backend\bin" "%ROOT_DIR%backend\src\main\java\com\petadoption\model\*.java" "%ROOT_DIR%backend\src\main\java\com\petadoption\PetAdoptionServer.java" >nul 2>&1
    
    start "PawNest Server" /min java -cp "%ROOT_DIR%backend\bin" com.petadoption.PetAdoptionServer
    
    echo.
    echo    [3/3] Opening browser at http://localhost:8080...
    timeout /t 1 >nul 2>&1
    start http://localhost:8080
    set "LAUNCH_MODE=http://localhost:8080"
) else (
    echo          Java not found in PATH.
    echo.
    echo    [3/3] Launching frontend directly via file:// protocol...
    start "" "%INDEX_HTML%"
    set "LAUNCH_MODE=%INDEX_HTML%"
)

color 0A
cls
echo ======================================================================
echo    PawNest Platform is Running!
echo ======================================================================
echo.
echo    Access URL : !LAUNCH_MODE!
echo.
echo    Active Features:
echo    - 1-Click Interactive Demo Tour (Floating button bottom-left)
echo    - Adopter Dashboard (Browse, Favorites, Applications, Messages, Certs)
echo    - Shelter Dashboard (Pet Listings, Quick Presets, Approvals, Chat)
echo    - Admin Dashboard   (User Management, Review Queue, System Settings)
echo.
echo ======================================================================
echo    Options:
echo    [1] Open http://localhost:8080 in browser
echo    [2] Open frontend\index.html directly
echo    [3] Exit launcher
echo ======================================================================
echo.

:MENU
set /p "CHOICE=Enter your choice (1, 2, or 3): "
if "%CHOICE%"=="1" (
    start http://localhost:8080
    goto MENU
)
if "%CHOICE%"=="2" (
    start "" "%INDEX_HTML%"
    goto MENU
)
if "%CHOICE%"=="3" (
    exit /b 0
)
echo Invalid choice. Please enter 1, 2, or 3.
goto MENU
