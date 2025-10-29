# E-Commerce Admin Backend

Spring Boot backend for the E-commerce Admin Dashboard with MySQL database integration.

## Technology Stack

- **Java**: 17
- **Spring Boot**: 3.2.0
- **Database**: MySQL
- **Build Tool**: Maven

## Prerequisites

- Java 17 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher

## Database Setup

1. Create a MySQL database:
```sql
CREATE DATABASE ecommerce_db;
```

2. Update database credentials in `src/main/resources/application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/ecommerce_db
    username: root
    password: your_password
```

## Running the Application

1. Build the project:
```bash
mvn clean install
```

2. Run the application:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080/api`

## API Endpoints

### Dashboard
- `GET /api/admin/dashboard/stats` - Get dashboard statistics
- `GET /api/admin/dashboard/sales?days=30` - Get sales data

### Orders
- `GET /api/admin/orders` - Get all orders (supports filtering)
- `GET /api/admin/orders/{id}` - Get order by ID
- `PATCH /api/admin/orders/{id}/status` - Update order status

### Products
- `GET /api/admin/products` - Get all products (supports filtering)
- `GET /api/admin/products/{id}` - Get product by ID
- `POST /api/admin/products` - Create new product
- `PUT /api/admin/products/{id}` - Update product
- `DELETE /api/admin/products/{id}` - Delete product

### Coupons
- `GET /api/admin/coupons` - Get all coupons
- `GET /api/admin/coupons/{id}` - Get coupon by ID
- `POST /api/admin/coupons` - Create new coupon
- `PUT /api/admin/coupons/{id}` - Update coupon
- `DELETE /api/admin/coupons/{id}` - Delete coupon

### Users
- `GET /api/admin/users` - Get all users (customers)
- `GET /api/admin/users/{id}` - Get user by ID

## Environment Variables

You can override database settings using environment variables:

```bash
export DB_PASSWORD=your_password
mvn spring-boot:run
```

## CORS Configuration

CORS is configured to allow requests from:
- `http://localhost:3000`
- `http://localhost:5000`
- `http://127.0.0.1:5000`

You can modify this in `src/main/java/com/ecommerce/admin/config/WebConfig.java`

## Project Structure

```
backend/
├── src/
│   ├── main/
│   │   ├── java/com/ecommerce/admin/
│   │   │   ├── config/          # Configuration classes
│   │   │   ├── controller/      # REST controllers
│   │   │   ├── dto/             # Data Transfer Objects
│   │   │   ├── exception/       # Exception handling
│   │   │   ├── model/           # Entity models
│   │   │   ├── repository/      # JPA repositories
│   │   │   ├── service/         # Business logic
│   │   │   └── AdminApplication.java
│   │   └── resources/
│   │       └── application.yml  # Application configuration
│   └── test/                    # Test classes
├── pom.xml
└── README.md
```

## Features

- ✅ RESTful API for admin dashboard
- ✅ MySQL database integration with JPA/Hibernate
- ✅ CORS configuration for frontend integration
- ✅ Global exception handling
- ✅ Input validation
- ✅ Transaction management
- ✅ Pagination support for large datasets

## Development

To enable debug logging:
```yaml
logging:
  level:
    com.ecommerce.admin: DEBUG
```

## Production Deployment

1. Build the JAR file:
```bash
mvn clean package
```

2. Run the JAR:
```bash
java -jar target/admin-backend-1.0.0.jar
```

## Notes

- Authentication is currently bypassed for MVP - will be added in future iterations
- All endpoints are accessible without authentication
- Make sure MySQL is running before starting the application
