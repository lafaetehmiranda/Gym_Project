# Script to set JAVA_HOME to Java 21 and run Quarkus dev mode
# Run this script instead of ./mvnw quarkus:dev

# Find Java 21 installation
$java21Path = Get-ChildItem "C:\Program Files\Java" -Directory -ErrorAction SilentlyContinue | Where-Object { $_.Name -eq "jdk-21" } | Select-Object -First 1

if (-not $java21Path) {
    # Try other common locations
    $possiblePaths = @(
        "C:\Program Files\Java\jdk-21",
        "C:\Program Files\Java\jdk21",
        "C:\Program Files\Java\jdk-21.0.8"
    )
    
    foreach ($path in $possiblePaths) {
        if (Test-Path $path) {
            $java21Path = Get-Item $path
            break
        }
    }
}

if ($java21Path) {
    Write-Host "Found Java 21 at: $($java21Path.FullName)" -ForegroundColor Green
    $env:JAVA_HOME = $java21Path.FullName
    Write-Host "JAVA_HOME set to: $env:JAVA_HOME" -ForegroundColor Green
    
    # Verify Java version
    & "$env:JAVA_HOME\bin\java.exe" -version
    
    Write-Host "`nStarting Quarkus in dev mode..." -ForegroundColor Cyan
    & .\mvnw.cmd quarkus:dev
} else {
    Write-Host "Java 21 not found!" -ForegroundColor Red
    Write-Host "Please install Java 21 or update the script with the correct path" -ForegroundColor Yellow
    
    # Show available Java installations
    Write-Host "`nAvailable Java installations:" -ForegroundColor Yellow
    Get-ChildItem "C:\Program Files\Java" -Directory -ErrorAction SilentlyContinue | ForEach-Object { Write-Host "  - $($_.Name)" }
}
