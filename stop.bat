@echo off
echo ===============================================
echo Shopping Cart - Detener Servicios
echo ===============================================
echo.

cd resources\db

echo [1/2] Deteniendo contenedores...
docker-compose down

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ===============================================
    echo Servicios detenidos exitosamente!
    echo ===============================================
) else (
    echo.
    echo [ERROR] Fallo al detener los servicios
)

cd ..\..

echo.
echo Presiona cualquier tecla para salir...
pause >nul
