#!/bin/bash

# E-Commerce Admin Backend - Startup Script
# This script starts the Spring Boot backend
# Database credentials must be set in environment or .env file

# Check if .env file exists and source it
if [ -f .env ]; then
    echo "Loading environment variables from .env file..."
    export $(cat .env | xargs)
fi

# Set defaults for optional variables
export DB_HOST="${DB_HOST:-localhost}"
export DB_PORT="${DB_PORT:-3306}"
export DB_NAME="${DB_NAME:-ecommerce_db}"
export DB_USERNAME="${DB_USERNAME:-root}"
export SERVER_PORT="${SERVER_PORT:-8080}"

# Check if password is set
if [ -z "$DB_PASSWORD" ]; then
    echo "ERROR: DB_PASSWORD environment variable is not set!"
    echo ""
    echo "Please set your database password using one of these methods:"
    echo ""
    echo "  Method 1: Create a .env file in the backend directory with:"
    echo "    DB_PASSWORD=your_password_here"
    echo ""
    echo "  Method 2: Export the variable before running this script:"
    echo "    export DB_PASSWORD=your_password_here"
    echo "    ./run.sh"
    echo ""
    echo "  Method 3: Run with inline variable:"
    echo "    DB_PASSWORD=your_password_here ./run.sh"
    echo ""
    exit 1
fi

echo "Starting E-Commerce Admin Backend..."
echo "Database: mysql://${DB_HOST}:${DB_PORT}/${DB_NAME}"
echo "User: ${DB_USERNAME}"
echo "Server will run on: http://localhost:${SERVER_PORT}/api"
echo ""

# Navigate to backend directory
cd "$(dirname "$0")"

# Build and run
mvn spring-boot:run
