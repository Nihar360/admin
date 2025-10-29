# E-Commerce Admin Dashboard

A full-stack admin dashboard for managing e-commerce operations with React frontend and Spring Boot backend connected to MySQL database.

## 📊 Project Overview

This is a comprehensive admin dashboard that provides real-time management of:
- **Dashboard Analytics** - Revenue, orders, customers, and sales trends
- **Order Management** - View, filter, and update order statuses
- **Product Management** - Full CRUD operations for product catalog
- **Inventory Management** - Stock tracking and adjustments
- **Customer Management** - User profiles and activity tracking
- **Coupon Management** - Discount codes and promotions
- **Notifications** - System notifications and alerts
- **Reports** - Sales analytics and performance metrics

## 🏗️ Architecture

### Frontend (React + TypeScript)
- **Framework**: React 18 with TypeScript
- **Build Tool**: Vite 6.3.5
- **UI Library**: Radix UI components with Tailwind CSS
- **State Management**: React Hooks
- **Routing**: React Router DOM
- **Charts**: Recharts for data visualization

### Backend (Spring Boot + Java)
- **Framework**: Spring Boot 3.2.0
- **Language**: Java 17
- **Database**: MySQL 8.0 via JPA/Hibernate
- **API**: RESTful API with JSON responses
- **Build Tool**: Maven

### Database Schema
The MySQL database includes 20+ tables:
- Core: users, products, orders, categories
- Orders: order_items, order_status_history, order_refunds
- Products: product_images, product_features, product_sizes, product_reviews
- Marketing: coupons, notifications
- Admin: admin_activity_logs
- Customer: addresses, cart_items, carts

## 📁 Project Structure

```
├── src/                          # React Frontend Source
│   ├── api/                      # API client & endpoints
│   │   └── adminApi.ts          # Backend API integration
│   ├── components/               # Reusable components
│   │   ├── ui/                  # UI components (buttons, inputs, etc.)
│   │   └── admin/               # Admin-specific components
│   ├── hooks/                    # Custom React hooks
│   │   ├── useAdminDashboard.ts
│   │   ├── useAdminOrders.ts
│   │   └── useAdminProducts.ts
│   ├── pages/                    # Page components
│   │   ├── AdminDashboard.tsx
│   │   ├── OrderList.tsx
│   │   ├── ProductList.tsx
│   │   ├── UserList.tsx
│   │   ├── CouponList.tsx
│   │   └── InventoryManagement.tsx
│   ├── config/                   # Configuration
│   │   └── api.ts               # API endpoint definitions
│   └── App.tsx                   # Main app component
│
├── backend/                      # Spring Boot Backend
│   ├── src/main/java/com/ecommerce/admin/
│   │   ├── controller/          # REST API Controllers
│   │   │   ├── DashboardController.java
│   │   │   ├── OrderController.java
│   │   │   ├── ProductController.java
│   │   │   ├── CouponController.java
│   │   │   ├── UserController.java
│   │   │   └── NotificationController.java
│   │   ├── service/             # Business Logic Layer
│   │   │   ├── DashboardService.java
│   │   │   ├── OrderService.java
│   │   │   ├── ProductService.java
│   │   │   ├── CouponService.java
│   │   │   └── UserService.java
│   │   ├── repository/          # Data Access Layer (JPA)
│   │   │   ├── OrderRepository.java
│   │   │   ├── ProductRepository.java
│   │   │   ├── UserRepository.java
│   │   │   └── ... (all entity repositories)
│   │   ├── model/               # JPA Entity Models
│   │   │   ├── Order.java
│   │   │   ├── Product.java
│   │   │   ├── User.java
│   │   │   ├── Coupon.java
│   │   │   └── enums/          # Enumerations
│   │   ├── dto/                 # Data Transfer Objects
│   │   │   ├── request/        # API request DTOs
│   │   │   └── response/       # API response DTOs
│   │   └── exception/          # Error Handling
│   ├── src/main/resources/
│   │   └── application.yml     # Spring Boot configuration
│   ├── run.sh                  # Linux/Mac startup script
│   ├── run.bat                 # Windows startup script
│   └── pom.xml                 # Maven dependencies
│
├── .env.local                   # Frontend environment variables
├── vite.config.ts              # Vite configuration
├── package.json                # Node.js dependencies
└── LOCAL_SETUP_GUIDE.md        # Detailed setup instructions
```

## 🔌 API Endpoints

All API endpoints are prefixed with `/api` and return JSON in this format:
```json
{
  "success": true,
  "message": "Success message",
  "data": { ... }
}
```

### Available Endpoints

#### Dashboard
- `GET /admin/dashboard/stats` - Dashboard statistics
- `GET /admin/dashboard/sales?days=30` - Sales data for charts

#### Orders
- `GET /admin/orders` - List orders (supports pagination & filters)
- `GET /admin/orders/{id}` - Get order details
- `PATCH /admin/orders/{id}/status` - Update order status

#### Products
- `GET /admin/products` - List products (supports filters)
- `GET /admin/products/{id}` - Get product details
- `POST /admin/products` - Create new product
- `PUT /admin/products/{id}` - Update product
- `DELETE /admin/products/{id}` - Delete product

#### Coupons
- `GET /admin/coupons` - List all coupons
- `POST /admin/coupons` - Create coupon
- `PUT /admin/coupons/{id}` - Update coupon
- `DELETE /admin/coupons/{id}` - Delete coupon

#### Users
- `GET /admin/users` - List customers
- `GET /admin/users/{id}` - Get customer details

#### Notifications
- `GET /admin/notifications` - List all notifications
- `GET /admin/notifications/unread` - Unread notifications
- `GET /admin/notifications/count` - Unread count
- `PATCH /admin/notifications/{id}/read` - Mark as read
- `PATCH /admin/notifications/mark-all-read` - Mark all as read

## 🚀 Running Locally

### Prerequisites
- Node.js 18+
- Java 17+
- Maven
- MySQL 8.0+

### Quick Start

1. **Install frontend dependencies:**
   ```bash
   npm install
   ```

2. **Start the backend:**
   ```bash
   cd backend
   ./run.sh          # Mac/Linux
   # OR
   run.bat           # Windows
   ```

3. **Start the frontend (in a new terminal):**
   ```bash
   npm run dev
   ```

4. **Access the dashboard:**
   Open http://localhost:5000

**Full setup instructions**: See `LOCAL_SETUP_GUIDE.md`

## 🔧 Configuration

### Database Connection
Edit `backend/src/main/resources/application.yml` or use environment variables:
- `DB_HOST` - MySQL host (default: localhost)
- `DB_PORT` - MySQL port (default: 3306)
- `DB_NAME` - Database name (default: ecommerce_db)
- `DB_USERNAME` - MySQL username (default: root)
- `DB_PASSWORD` - MySQL password

### Frontend API URL
The frontend connects to the backend via environment variable in `.env.local`:
```
VITE_API_BASE_URL=http://localhost:8080/api
```

## 🎨 Features

### ✅ Implemented Features
- Real-time dashboard with statistics from MySQL database
- Order management with status updates
- Product CRUD operations with image support
- Inventory tracking and stock management
- Customer management and activity tracking
- Coupon/discount code management
- Notification system
- Responsive UI with Tailwind CSS
- Data visualization with charts
- Advanced filtering and search
- Pagination support
- Error handling and loading states

### 🔒 Security Notes
⚠️ **Current Status**: Development mode - No authentication enabled

**For Production**, implement:
- JWT-based authentication
- Role-based access control (RBAC)
- API rate limiting
- Input validation & sanitization
- HTTPS encryption
- CORS restrictions
- SQL injection protection (already handled by JPA)

## 📚 Technology Stack

### Frontend
- React 18.3.1
- TypeScript 5.9.3
- Vite 6.3.5
- Tailwind CSS 4.1.16
- Radix UI Components
- Recharts (data visualization)
- React Router DOM
- React Hook Form
- Lucide React (icons)

### Backend
- Spring Boot 3.2.0
- Java 17
- Spring Data JPA
- MySQL Connector
- Lombok
- Maven

## 📝 Development Notes

### Frontend Development
- Hot Module Replacement (HMR) enabled via Vite
- TypeScript strict mode for type safety
- Custom hooks for data fetching
- Reusable UI components based on Radix UI
- Responsive design with mobile support

### Backend Development
- Spring DevTools for auto-reload
- JPA for ORM (no raw SQL needed)
- Automatic table creation from entities
- SQL query logging enabled for debugging
- Global exception handling
- CORS enabled for frontend access

## 🐛 Troubleshooting

See `LOCAL_SETUP_GUIDE.md` for comprehensive troubleshooting guide.

Common issues:
- Backend not connecting: Check MySQL is running
- CORS errors: Verify backend URL in `.env.local`
- Port conflicts: Change ports in config files
- Build errors: Run `mvn clean install -U` and `npm install`

## 📞 Support

For detailed setup instructions and troubleshooting:
- **Local Setup**: See `LOCAL_SETUP_GUIDE.md`
- **Backend Setup**: See `BACKEND_SETUP.md`
- **Complete Guide**: See `COMPLETE_SETUP_GUIDE.md`

## 🎯 Project Status

**Current Version**: 1.0.0  
**Status**: Development Ready  
**Last Updated**: October 29, 2025

All core features are implemented and ready for local development. The application successfully connects to MySQL database and provides full admin functionality for e-commerce management.
