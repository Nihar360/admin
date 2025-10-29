# Admin Dashboard - Setup Guide

## Overview
This is the frontend admin dashboard for your e-commerce platform. It connects to a Spring Boot backend with MySQL database.

## What Was Changed

### 1. Removed Demo/Mock Data
- ✅ All hardcoded mock data has been removed from `src/api/adminApi.ts`
- ✅ API calls now connect to your backend (Spring Boot + MySQL)
- ✅ No more placeholder/temporary data

### 2. Removed Admin Login
- ✅ Login page has been removed (you can add it back later)
- ✅ Authentication is bypassed - direct access to dashboard
- ✅ All routes are now public (no auth guards)

### 3. Backend Integration Ready
- ✅ All API endpoints configured to connect to your MySQL backend
- ✅ Environment variables set up for API configuration

## Setup Instructions

### Step 1: Configure Backend API URL

Create a `.env` file in the project root:

```bash
# .env
VITE_API_BASE_URL=http://localhost:8080/api
```

**For local development with Spring Boot:**
- If your Spring Boot backend runs on port 8080, use: `http://localhost:8080/api`
- Adjust the port number if your backend uses a different port

**For production:**
```bash
VITE_API_BASE_URL=https://your-backend-domain.com/api
```

### Step 2: Install Dependencies

```bash
npm install
```

### Step 3: Run the Development Server

```bash
npm run dev
```

The app will start on `http://localhost:5000` (Replit) or `http://localhost:3000` (local).

### Step 4: Build for Production

```bash
npm run build
```

This creates an optimized production build in the `build/` directory.

## Backend API Endpoints

Your Spring Boot backend should implement these endpoints:

### Dashboard
- `GET /api/admin/dashboard/stats` - Get dashboard statistics
- `GET /api/admin/dashboard/sales?days=30` - Get sales data

### Orders
- `GET /api/admin/orders?status=&search=&page=&limit=` - Get all orders with filters
- `GET /api/admin/orders/:id` - Get single order
- `PATCH /api/admin/orders/:id/status` - Update order status

### Products
- `GET /api/admin/products?category=&search=&inStock=` - Get all products
- `GET /api/admin/products/:id` - Get single product
- `POST /api/admin/products` - Create product
- `PUT /api/admin/products/:id` - Update product
- `DELETE /api/admin/products/:id` - Delete product

### Coupons
- `GET /api/admin/coupons` - Get all coupons
- `POST /api/admin/coupons` - Create coupon
- `PUT /api/admin/coupons/:id` - Update coupon
- `DELETE /api/admin/coupons/:id` - Delete coupon

### Users
- `GET /api/admin/users?search=&status=` - Get all users
- `GET /api/admin/users/:id` - Get single user

## Database Schema

Your MySQL database should have these tables (as provided in your schema):
- `users` - User accounts
- `orders` - Customer orders
- `order_items` - Order line items
- `products` - Product catalog
- `categories` - Product categories
- `coupons` - Discount coupons
- `notifications` - Admin notifications
- `admin_activity_logs` - Audit trail
- `order_refunds` - Refund requests
- `order_status_history` - Order status changes
- And more (see database schema CSV)

## Next Steps

1. **Set up Spring Boot Backend**
   - Follow the Spring Boot implementation plan provided
   - Configure MySQL database connection
   - Implement the API endpoints listed above

2. **Test API Integration**
   - Start your Spring Boot backend
   - Update `.env` with correct API URL
   - Test each page in the admin dashboard

3. **Add Authentication (Later)**
   - Implement JWT authentication in Spring Boot
   - Re-enable the login page in the frontend
   - Add auth guards back to routes

## Project Structure

```
src/
├── api/
│   └── adminApi.ts          # All API calls (now connects to backend)
├── components/
│   ├── admin/               # Admin-specific components
│   └── ui/                  # Reusable UI components
├── contexts/
│   └── AdminAuthContext.tsx # Auth context (currently bypassed)
├── hooks/                   # Custom React hooks
├── pages/                   # Page components
└── styles/                  # Global styles
```

## Running on Local Machine

1. Clone this repository
2. Install dependencies: `npm install`
3. Create `.env` file with your API URL
4. Start development server: `npm run dev`
5. Ensure your Spring Boot backend is running

## Troubleshooting

### API Calls Failing?
- Check that your Spring Boot backend is running
- Verify the `VITE_API_BASE_URL` in `.env` is correct
- Check browser console for CORS errors
- Add CORS configuration to your Spring Boot backend

### CORS Configuration (Spring Boot)
Add this to your Spring Boot backend:

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:5000", "http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
                .allowedHeaders("*");
    }
}
```

## Support

For issues or questions:
1. Check the database schema CSV for table structures
2. Review the Spring Boot implementation plan
3. Verify all API endpoints are implemented correctly
