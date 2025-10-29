#!/bin/bash

# E-Commerce Admin Backend - Startup Script
# This script sets up environment variables and starts the Spring Boot backend

# Database Configuration
export DB_HOST="localhost"
export DB_PORT="3306"
export DB_NAME="ecommerce_db"
export DB_USERNAME="root"
export DB_PASSWORD="nihar@360"

# Server Configuration
export SERVER_PORT="8080"

echo "Starting E-Commerce Admin Backend..."
echo "Database: mysql://${DB_HOST}:${DB_PORT}/${DB_NAME}"
echo "Server will run on: http://localhost:${SERVER_PORT}/api"
echo ""

# Navigate to backend directory
cd "$(dirname "$0")"

# Build and run
mvn spring-boot:run
