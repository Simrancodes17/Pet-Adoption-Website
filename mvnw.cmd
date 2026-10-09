@REM ----------------------------------------------------------------------------
@REM Maven Wrapper Batch File for Windows
@REM ----------------------------------------------------------------------------
@echo off
setlocal

set "DIRNAME=%~dp0"
if "%DIRNAME%" == "" set "DIRNAME=."

@REM Prefer scratch tools maven if present
if exist "%DIRNAME%..\tools\apache-maven-3.9.6\bin\mvn.cmd" (
    call "%DIRNAME%..\tools\apache-maven-3.9.6\bin\mvn.cmd" %*
    exit /b %ERRORLEVEL%
)

@REM Fallback to system mvn
call mvn %*
exit /b %ERRORLEVEL%
