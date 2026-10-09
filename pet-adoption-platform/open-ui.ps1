# PawNest - Online Pet Adoption Platform PowerShell Launcher
$ErrorActionPreference = "SilentlyContinue"
$projectRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
$htmlFile = Join-Path $projectRoot "frontend\index.html"

Write-Host "==================================================================" -ForegroundColor Cyan
Write-Host "   PawNest — Online Pet Adoption Platform Launcher (PowerShell)   " -ForegroundColor Yellow
Write-Host "==================================================================" -ForegroundColor Cyan

if (Test-Path "$projectRoot\backend\bin") {
    Write-Host "[✔] Starting PetAdoptionServer in background..." -ForegroundColor Green
    Start-Process java -ArgumentList "-cp `"$projectRoot\backend\bin`" com.petadoption.PetAdoptionServer" -WindowStyle Hidden
    Start-Sleep -Seconds 1
    Start-Process "http://localhost:8080"
    Write-Host "[✔] Running at: http://localhost:8080" -ForegroundColor Green
} else {
    Write-Host "[✔] Opening direct frontend: $htmlFile" -ForegroundColor Green
    Start-Process $htmlFile
}

Write-Host "Done! The PawNest application has been opened in your default browser." -ForegroundColor White
