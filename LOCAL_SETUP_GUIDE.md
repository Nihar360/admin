# E-Commerce Admin Dashboard - Local Setup Guide

This guide will help you set up and run the complete admin dashboard on your local machine with MySQL database connection.

## 📋 Prerequisites

Before you begin, make sure you have the following installed:

1. **Node.js** (v18 or higher)
   - Check: `node --version`
   - Download: https://nodejs.org/

2. **Java 17 or higher** (for Spring Boot backend)
   - Check: `java -version`
   - Download: https://adoptium.net/

3. **Maven** (for building Java backend)
   - Check: `mvn --version`
   - Download: https://maven.apache.org/download.cgi

4. **MySQL** (v8.0 or higher)
   - Check: `mysql --version`
   - Make sure MySQL is running on port 3306
   - Database name: `ecommerce_db`
   - Username: `root`
   - Password: `nihar@360`

## 🚀 Quick Start

### Step 1: Install Frontend Dependencies

```bash
# In the project root directory
npm install
```

### Step 2: Configure Backend Database Connection

The backend is already configured to connect to your MySQL database with the credentials:
- Host: `localhost:3306`
- Database: `ecommerce_db`
- Username: `root`
- Password: `nihar@360`

If you need to change these, edit `backend/run.sh` (Mac/Linux) or `backend/run.bat` (Windows).

### Step 3: Start the Backend

**For Mac/Linux:**
```bash
cd backend
./run.sh
```

**For Windows:**
```bash
cd backend
run.bat
```

The backend will:
1. Connect to your MySQL database
2. Automatically create/update tables based on your schema
3. Start on http://localhost:8080/api

**Wait until you see:** 
```
Started AdminApplication in X.XXX seconds
```

### Step 4: Start the Frontend

Open a **new terminal** and run:

```bash
# In the project root directory
npm run dev
```

The frontend will start on: http://localhost:5000

### Step 5: Access the Dashboard

Open your browser and go to:
```
http://localhost:5000
```

You should now see the admin dashboard fetching real data from your MySQL database!

## 📁 Project Structure

```
├── backend/                      # Spring Boot Backend
│   ├── src/main/java/           # Java source code
│   │   └── com/ecommerce/admin/
│   │       ├── controller/      # REST API endpoints
│   │       ├── service/         # Business logic
│   │       ├── repository/      # Database access
│   │       ├── model/           # Entity models
│   │       └── dto/             # Data Transfer Objects
│   ├── src/main/resources/      
│   │   └── application.yml      # Database configuration
│   ├── run.sh                   # Startup script (Mac/Linux)
│   ├── run.bat                  # Startup script (Windows)
│   └── pom.xml                  # Maven dependencies
│
├── src/                         # React Frontend
│   ├── api/                     # API client
│   ├── components/              # Reusable components
│   ├── hooks/                   # Custom React hooks
│   ├── pages/                   # Page components
│   └── config/                  # Configuration
│
├── .env.local                   # Frontend environment variables
└── package.json                 # Frontend dependencies
```

## 🔌 API Endpoints

Once the backend is running, these endpoints are available:

### Dashboard
- `GET /api/admin/dashboard/stats` - Get dashboard statistics
- `GET /api/admin/dashboard/sales?days=30` - Get sales data

### Orders
- `GET /api/admin/orders` - List all orders
- `GET /api/admin/orders/{id}` - Get order details
- `PATCH /api/admin/orders/{id}/status` - Update order status

### Products
- `GET /api/admin/products` - List all products
- `POST /api/admin/products` - Create new product
- `PUT /api/admin/products/{id}` - Update product
- `DELETE /api/admin/products/{id}` - Delete product

### Coupons
- `GET /api/admin/coupons` - List all coupons
- `POST /api/admin/coupons` - Create new coupon
- `PUT /api/admin/coupons/{id}` - Update coupon
- `DELETE /api/admin/coupons/{id}` - Delete coupon

### Users (Customers)
- `GET /api/admin/users` - List all customers
- `GET /api/admin/users/{id}` - Get customer details

### Notifications
- `GET /api/admin/notifications` - List all notifications
- `GET /api/admin/notifications/unread` - Get unread notifications
- `GET /api/admin/notifications/count` - Get unread count
- `PATCH /api/admin/notifications/{id}/read` - Mark as read

## 🐛 Troubleshooting

### Backend won't start

**Problem:** "Cannot connect to database"
**Solution:**
1. Make sure MySQL is running: `mysql.server start` (Mac) or `sudo service mysql start` (Linux)
2. Verify database exists in MySQL:
   ```sql
   mysql -u root -p
   SHOW DATABASES;
   ```
3. If `ecommerce_db` doesn't exist, create it:
   ```sql
   CREATE DATABASE ecommerce_db;
   ```

**Problem:** "Port 8080 already in use"
**Solution:**
1. Stop any process using port 8080
2. Or change the port in `backend/src/main/resources/application.yml`:
   ```yaml
   server:
     port: 8081
   ```
3. Then update `.env.local`:
   ```
   VITE_API_BASE_URL=http://localhost:8081/api
   ```

### Frontend won't connect to backend

**Problem:** API calls failing with CORS errors or network errors

**Solution:**
1. Make sure backend is running on http://localhost:8080
2. Check `.env.local` has: `VITE_API_BASE_URL=http://localhost:8080/api`
3. Restart the frontend: Stop the dev server and run `npm run dev` again

**Problem:** Data not showing up

**Solution:**
1. Open browser DevTools (F12) → Network tab
2. Check if API calls are returning data
3. Verify your MySQL database has data:
   ```sql
   mysql -u root -p
   USE ecommerce_db;
   SELECT COUNT(*) FROM products;
   SELECT COUNT(*) FROM orders;
   SELECT COUNT(*) FROM users;
   ```

### Maven build issues

**Problem:** Maven dependencies not downloading

**Solution:**
```bash
cd backend
mvn clean install -U
```

**Problem:** Java version mismatch

**Solution:**
Make sure you're using Java 17 or higher:
```bash
java -version
```

## 🔧 Configuration Files

### Backend Configuration (`backend/src/main/resources/application.yml`)
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/ecommerce_db
    username: root
    password: your_password
```

### Frontend Configuration (`.env.local`)
```
VITE_API_BASE_URL=http://localhost:8080/api
```

## 📊 Database Schema

Your database schema includes these main tables:
- `users` - User accounts and admins
- `products` - Product catalog
- `orders` - Customer orders
- `order_items` - Items in each order
- `categories` - Product categories
- `coupons` - Discount coupons
- `addresses` - Customer shipping addresses
- `notifications` - System notifications
- `admin_activity_logs` - Admin action logs
- `product_reviews` - Customer reviews
- `cart_items` - Shopping cart
- And more...

All tables are automatically mapped via JPA entities in the backend.

## 🎯 Features

✅ **Dashboard** - Real-time stats from your MySQL database
✅ **Order Management** - View, filter, and update order status
✅ **Product Management** - Full CRUD operations for products
✅ **Inventory Management** - Track stock levels and adjust inventory
✅ **Customer Management** - View customer information and activity
✅ **Coupon Management** - Create and manage discount coupons
✅ **Notifications** - System notifications for important events
✅ **Reports** - Sales analytics and charts

## 💡 Development Tips

1. **Backend Auto-reload**: Spring DevTools is enabled, so code changes will auto-reload
2. **Frontend HMR**: Vite provides Hot Module Replacement for instant updates
3. **Database Logs**: Check backend terminal to see SQL queries being executed
4. **API Testing**: Use browser DevTools or Postman to test API endpoints
5. **Error Logs**: Check both terminal windows for errors

## 🔒 Security Notes

⚠️ **Important**: This setup is for development only!

For production deployment, you must:
1. Add proper authentication (JWT tokens)
2. Secure API endpoints with role-based access control
3. Use environment variables for all sensitive data
4. Enable HTTPS
5. Implement rate limiting
6. Add input validation and sanitization
7. Use prepared statements (already done with JPA)

## 📞 Need Help?

If you encounter any issues:

1. Check both terminal windows for error messages
2. Verify MySQL is running and accessible
3. Ensure all dependencies are installed
4. Check that ports 5000 and 8080 are available
5. Review the troubleshooting section above

## 🎉 Success!

Once everything is running, you should see:
- ✅ Frontend running on http://localhost:5000
- ✅ Backend API running on http://localhost:8080/api
- ✅ Real data from your MySQL database displayed in the dashboard
- ✅ All CRUD operations working correctly

Enjoy your fully functional e-commerce admin dashboard!
