@echo off
REM E-Commerce Admin Backend - Startup Script for Windows
REM This script sets up environment variables and starts the Spring Boot backend

REM Database Configuration
set DB_HOST=localhost
set DB_PORT=3306
set DB_NAME=ecommerce_db
set DB_USERNAME=root
set DB_PASSWORD=nihar@360

REM Server Configuration  
set SERVER_PORT=8080

echo Starting E-Commerce Admin Backend...
echo Database: mysql://%DB_HOST%:%DB_PORT%/%DB_NAME%
echo Server will run on: http://localhost:%SERVER_PORT%/api
echo.

REM Navigate to backend directory
cd /d %~dp0

REM Build and run
mvn spring-boot:run
