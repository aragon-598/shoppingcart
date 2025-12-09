@echo off
echo ===============================================
echo Shopping Cart - Build and Deploy with Docker
echo ===============================================
echo.

REM Verificar si Maven está instalado
where mvn >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Maven no esta instalado o no esta en el PATH
    echo Por favor instala Maven y agrega al PATH del sistema
    pause
    exit /b 1
)

REM Verificar si Docker está instalado
where docker >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Docker no esta instalado o no esta en el PATH
    echo Por favor instala Docker Desktop y asegurate que este ejecutandose
    pause
    exit /b 1
)

echo [1/5] Limpiando proyecto anterior...
call mvn clean
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Fallo al limpiar el proyecto
    pause
    exit /b 1
)

echo.
echo [2/5] Compilando proyecto con Maven...
call mvn package -DskipTests
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Fallo la compilacion del proyecto
    pause
    exit /b 1
)

echo.
echo [3/5] Construyendo imagen Docker...
docker build -t shopping-cart:latest .
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Fallo la construccion de la imagen Docker
    pause
    exit /b 1
)

echo.
echo [4/5] Deteniendo contenedores existentes...
cd resources\db
docker-compose down

echo.
echo [5/5] Iniciando servicios con Docker Compose...
docker-compose up -d
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Fallo al iniciar los servicios
    cd ..\..
    pause
    exit /b 1
)

cd ..\..

echo.
echo ===============================================
echo Deploy completado exitosamente!
echo ===============================================
echo.
echo MySQL disponible en: localhost:3307
echo Shopping Cart API disponible en: http://localhost:8080
echo Swagger UI: http://localhost:8080/swagger-ui.html
echo.
echo Para ver los logs: docker-compose -f resources\db\docker-compose.yml logs -f
echo Para detener: docker-compose -f resources\db\docker-compose.yml down
echo.
pause
