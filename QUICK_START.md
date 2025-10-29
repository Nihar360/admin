# Quick Start Guide

## ✅ What's Done

1. **All demo/mock data removed** - No hardcoded values anywhere
2. **Admin login removed** - Direct access to dashboard (will re-add later)
3. **Backend integration ready** - All API calls configured for Spring Boot + MySQL

## 🚀 To Run on Your Local Machine

### Step 1: Create Environment File

Create a `.env` file in the project root:

```env
VITE_API_BASE_URL=http://localhost:8080/api
```

### Step 2: Install & Run

```bash
npm install
npm run dev
```

The frontend will run on `http://localhost:5000` (Replit) or `http://localhost:3000` (local machine).

## 🔌 Connect to Your MySQL Backend

### Your Spring Boot Backend Should:

1. **Run on port 8080** (or update the .env file)
2. **Have CORS enabled** for your frontend URL
3. **Implement these API endpoints:**

```
GET  /api/admin/dashboard/stats
GET  /api/admin/dashboard/sales?days=30
GET  /api/admin/orders
GET  /api/admin/orders/:id
PATCH /api/admin/orders/:id/status
GET  /api/admin/products
POST /api/admin/products
PUT  /api/admin/products/:id
DELETE /api/admin/products/:id
GET  /api/admin/coupons
POST /api/admin/coupons
PUT  /api/admin/coupons/:id
DELETE /api/admin/coupons/:id
GET  /api/admin/users
```

### CORS Configuration (Spring Boot)

Add this to your Spring Boot application:

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins(
                    "http://localhost:5000",
                    "http://localhost:3000",
                    "https://your-replit-domain.replit.dev"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
```

## 📊 Database Setup

Your MySQL database should have all the tables from the schema CSV:
- users
- orders
- order_items
- products
- categories
- coupons
- notifications
- admin_activity_logs
- order_refunds
- order_status_history
- etc.

Refer to `database_schema_*.csv` for complete table structures.

## 🔍 How It Works

1. **Dashboard** → Calls `/api/admin/dashboard/stats` to get total orders, customers, revenue
2. **Orders Page** → Calls `/api/admin/orders` to fetch all orders from MySQL
3. **Products Page** → Calls `/api/admin/products` to fetch products
4. **Coupons Page** → Calls `/api/admin/coupons` to manage discount codes
5. **Users Page** → Calls `/api/admin/users` to view customers

All data comes from your MySQL database through Spring Boot APIs!

## ❌ What's NOT Included (You'll Add Later)

- ❌ Admin authentication/login (bypassed for now)
- ❌ JWT token handling (implement with Spring Security)
- ❌ User sessions (will add back after backend is ready)

## 📝 Files You Need to Create

1. **`.env`** - API configuration (see Step 1 above)
2. **Spring Boot Backend** - See `SPRING_BOOT_BACKEND_COMPLETE_PLAN_*.md`

## 🐛 Troubleshooting

**Q: Dashboard shows all zeros?**  
A: This is normal! The backend isn't connected yet. Once your Spring Boot server runs, data will appear.

**Q: Getting CORS errors?**  
A: Add CORS configuration to your Spring Boot backend (see above).

**Q: API calls failing?**  
A: Check that:
- Spring Boot backend is running on port 8080
- `.env` file has correct API URL
- Database is set up with correct schema

## ✨ Testing Without Backend

The app will load and work, but you'll see:
- Zeros on dashboard cards
- Empty tables on all pages
- Error messages in browser console (normal - API not available yet)

Once you connect the backend, all data will populate automatically!

## 📚 More Information

- **Complete setup guide**: `README_SETUP.md`
- **Backend implementation plan**: `attached_assets/SPRING_BOOT_BACKEND_COMPLETE_PLAN_*.md`
- **Database schema**: `attached_assets/database_schema_*.csv`
- **Project documentation**: `replit.md`
