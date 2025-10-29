@echo off
REM E-Commerce Admin Backend - Startup Script for Windows
REM This script starts the Spring Boot backend
REM Database credentials must be set in environment or .env file

REM Check if .env file exists and load it
if exist .env (
    echo Loading environment variables from .env file...
    for /f "delims== tokens=1,2" %%a in (.env) do (
        set %%a=%%b
    )
)

REM Set defaults for optional variables
if not defined DB_HOST set DB_HOST=localhost
if not defined DB_PORT set DB_PORT=3306
if not defined DB_NAME set DB_NAME=ecommerce_db
if not defined DB_USERNAME set DB_USERNAME=root
if not defined SERVER_PORT set SERVER_PORT=8080

REM Check if password is set
if not defined DB_PASSWORD (
    echo ERROR: DB_PASSWORD environment variable is not set!
    echo.
    echo Please set your database password using one of these methods:
    echo.
    echo   Method 1: Create a .env file in the backend directory with:
    echo     DB_PASSWORD=your_password_here
    echo.
    echo   Method 2: Set as system environment variable
    echo.
    echo   Method 3: Run with inline variable:
    echo     set DB_PASSWORD=your_password_here ^&^& run.bat
    echo.
    exit /b 1
)

echo Starting E-Commerce Admin Backend...
echo Database: mysql://%DB_HOST%:%DB_PORT%/%DB_NAME%
echo User: %DB_USERNAME%
echo Server will run on: http://localhost:%SERVER_PORT%/api
echo.

REM Navigate to backend directory
cd /d %~dp0

REM Build and run
mvn spring-boot:run
