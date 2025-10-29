# Spring Boot Backend - Complete Setup Guide

## 🎉 What's Built

I've created a complete Spring Boot backend with MySQL integration for your e-commerce admin dashboard!

### ✅ Backend Components Created

1. **Entity Models** - All database tables mapped as JPA entities:
   - User, Order, OrderItem, Product, Category, Address
   - Coupon, Notification, AdminActivityLog
   - OrderRefund, OrderStatusHistory
   - ProductImage, ProductFeature, ProductSize, ProductReview
   - All enums (OrderStatus, PaymentMethod, UserRole, CouponType, etc.)

2. **JPA Repositories** - Data access layer with custom queries:
   - UserRepository, OrderRepository, ProductRepository
   - CouponRepository, NotificationRepository
   - And repositories for all other entities

3. **Service Layer** - Business logic implementation:
   - DashboardService (stats and analytics)
   - OrderService (order management)
   - ProductService (product CRUD)
   - CouponService (coupon management)
   - UserService (customer management)

4. **REST API Controllers** - All endpoints your frontend needs:
   - DashboardController - `/api/admin/dashboard/*`
   - OrderController - `/api/admin/orders/*`
   - ProductController - `/api/admin/products/*`
   - CouponController - `/api/admin/coupons/*`
   - UserController - `/api/admin/users/*`

5. **Configuration**:
   - CORS enabled for frontend (`localhost:3000`, `localhost:5000`)
   - Global exception handling
   - MySQL database configuration
   - Input validation

## 🚀 How to Run the Backend

### Prerequisites

1. **Java 17 or higher**
   - Check: `java -version`
   - Download: https://adoptium.net/

2. **Maven**
   - Check: `mvn -version`
   - Download: https://maven.apache.org/download.cgi

3. **MySQL 8.0 or higher**
   - Check: `mysql --version`
   - Download: https://dev.mysql.com/downloads/mysql/

### Step 1: Set Up MySQL Database

Open MySQL and create the database:

```sql
CREATE DATABASE ecommerce_db;
```

**Important**: Your database should already have tables from the schema CSV you provided. If not, you'll need to create them or import your existing database.

### Step 2: Configure Database Connection

Edit `backend/src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/ecommerce_db
    username: root
    password: YOUR_MYSQL_PASSWORD  # Change this!
```

Or set environment variable:
```bash
export DB_PASSWORD=your_mysql_password
```

### Step 3: Build and Run

```bash
# Navigate to backend directory
cd backend

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The backend will start on: `http://localhost:8080/api`

### Step 4: Test the API

Open your browser or use curl:

```bash
# Test dashboard stats
curl http://localhost:8080/api/admin/dashboard/stats

# Test products
curl http://localhost:8080/api/admin/products

# Test orders
curl http://localhost:8080/api/admin/orders
```

## 📊 API Endpoints Reference

### Dashboard APIs
```
GET  /api/admin/dashboard/stats       - Dashboard statistics
GET  /api/admin/dashboard/sales?days=30  - Sales data
```

### Order Management
```
GET    /api/admin/orders                    - List all orders (with filters)
GET    /api/admin/orders/{id}               - Get order details
PATCH  /api/admin/orders/{id}/status        - Update order status

Query params for /orders:
  - status: pending, processing, shipped, delivered, cancelled
  - search: search by order number or customer name
  - page: page number (default: 0)
  - limit: items per page (default: 10)
```

### Product Management
```
GET    /api/admin/products           - List all products (with filters)
GET    /api/admin/products/{id}      - Get product details
POST   /api/admin/products           - Create new product
PUT    /api/admin/products/{id}      - Update product
DELETE /api/admin/products/{id}      - Delete product

Query params for /products:
  - category: filter by category
  - search: search in name/description
  - inStock: true/false
```

### Coupon Management
```
GET    /api/admin/coupons            - List all coupons
GET    /api/admin/coupons/{id}       - Get coupon details
POST   /api/admin/coupons            - Create new coupon
PUT    /api/admin/coupons/{id}       - Update coupon
DELETE /api/admin/coupons/{id}       - Delete coupon
```

### User Management
```
GET  /api/admin/users           - List all customers (with filters)
GET  /api/admin/users/{id}      - Get user details

Query params for /users:
  - search: search by name/email
  - status: active, blocked
```

## 🔗 Connect Frontend to Backend

1. **Update Frontend `.env` file**:
```
VITE_API_BASE_URL=http://localhost:8080/api
```

2. **Start Frontend** (in the root directory):
```bash
npm run dev
```

3. **Access the dashboard**:
   - Frontend: `http://localhost:5000`
   - Backend API: `http://localhost:8080/api`

## 📁 Backend Project Structure

```
backend/
├── src/main/java/com/ecommerce/admin/
│   ├── AdminApplication.java          # Main Spring Boot app
│   ├── config/
│   │   └── WebConfig.java             # CORS configuration
│   ├── controller/                    # REST API endpoints
│   │   ├── DashboardController.java
│   │   ├── OrderController.java
│   │   ├── ProductController.java
│   │   ├── CouponController.java
│   │   └── UserController.java
│   ├── service/                       # Business logic
│   │   ├── DashboardService.java
│   │   ├── OrderService.java
│   │   ├── ProductService.java
│   │   ├── CouponService.java
│   │   └── UserService.java
│   ├── repository/                    # Database access
│   │   ├── UserRepository.java
│   │   ├── OrderRepository.java
│   │   ├── ProductRepository.java
│   │   └── ... (all other repos)
│   ├── model/                         # Entity models
│   │   ├── User.java
│   │   ├── Order.java
│   │   ├── Product.java
│   │   ├── Coupon.java
│   │   └── enums/
│   │       ├── OrderStatus.java
│   │       ├── UserRole.java
│   │       └── ... (all enums)
│   ├── dto/                           # Data Transfer Objects
│   │   ├── request/                   # Request DTOs
│   │   └── response/                  # Response DTOs
│   └── exception/                     # Error handling
│       └── GlobalExceptionHandler.java
├── src/main/resources/
│   └── application.yml                # Configuration
├── pom.xml                            # Maven dependencies
└── README.md
```

## 🐛 Troubleshooting

### Issue: "Cannot connect to database"
**Solution**: 
- Make sure MySQL is running: `mysql.server start` (Mac) or `sudo service mysql start` (Linux)
- Check database credentials in `application.yml`
- Verify database exists: `SHOW DATABASES;` in MySQL

### Issue: "Port 8080 already in use"
**Solution**:
Change the port in `application.yml`:
```yaml
server:
  port: 8081  # or any other port
```
Then update frontend `.env`: `VITE_API_BASE_URL=http://localhost:8081/api`

### Issue: "CORS error in browser"
**Solution**:
The CORS configuration in `WebConfig.java` already allows `localhost:3000` and `localhost:5000`. If you're using a different port, add it there.

### Issue: "Compilation errors"
**Solution**:
- Make sure you're using Java 17: `java -version`
- Clean and rebuild: `mvn clean install -U`

## 📝 Example API Requests

### Create a Product
```bash
curl -X POST http://localhost:8080/api/admin/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "New Product",
    "description": "Product description",
    "price": 99.99,
    "category": "Electronics",
    "stock": 100,
    "sku": "PROD001",
    "isActive": true
  }'
```

### Update Order Status
```bash
curl -X PATCH http://localhost:8080/api/admin/orders/1/status \
  -H "Content-Type: application/json" \
  -d '{"status": "shipped"}'
```

### Create a Coupon
```bash
curl -X POST http://localhost:8080/api/admin/coupons \
  -H "Content-Type: application/json" \
  -d '{
    "code": "SAVE20",
    "type": "percentage",
    "value": 20,
    "minPurchase": 100,
    "usageLimit": 100,
    "expiresAt": "2025-12-31T23:59:59",
    "isActive": true
  }'
```

## 🔒 Security Note

Authentication is currently **bypassed** for MVP development. All endpoints are publicly accessible. You'll need to add JWT authentication later for production use.

## 🎯 Next Steps

1. ✅ Start MySQL database
2. ✅ Configure database credentials
3. ✅ Run Spring Boot backend
4. ✅ Start frontend dev server
5. ✅ Test the integration

Your full-stack admin dashboard should now be working with real MySQL data!

## 💡 Tips

- Check backend logs in the terminal where you ran `mvn spring-boot:run`
- SQL queries are logged (see `application.yml` logging configuration)
- Use browser DevTools Network tab to debug API calls
- Backend auto-reloads on code changes (Spring DevTools enabled)

## 📞 Need Help?

- Check the logs in the terminal
- Verify MySQL connection
- Make sure all dependencies are installed
- Ensure frontend `.env` points to correct backend URL
