# Admin Dashboard Project

## Overview
This is an admin dashboard application built with React, TypeScript, Vite, and Tailwind CSS. The project features a comprehensive admin panel for managing orders, products, users, inventory, and more.

## Tech Stack
- **Frontend Framework**: React 18
- **Build Tool**: Vite 6.3.5
- **Language**: TypeScript
- **Styling**: Tailwind CSS v4
- **UI Components**: Radix UI (comprehensive component library)
- **Routing**: React Router DOM
- **Charts**: Recharts
- **Forms**: React Hook Form
- **Icons**: Lucide React

## Project Structure
```
src/
├── api/              # API integration
├── components/       # React components
│   ├── admin/       # Admin-specific components
│   │   ├── dashboard/
│   │   ├── layout/
│   │   ├── orders/
│   │   ├── products/
│   │   └── shared/
│   ├── figma/       # Figma-related components
│   └── ui/          # Reusable UI components (Radix UI)
├── contexts/        # React contexts
├── hooks/           # Custom React hooks
├── pages/           # Page components
└── styles/          # Global styles
```

## Key Features
- Admin authentication and authorization
- Dashboard with analytics and charts
- Order management with status tracking
- Product catalog management
- User management
- Inventory tracking
- Coupon management
- Reports and analytics
- Settings configuration

## Development

### Running the Project
The project runs on port 5000 (configured for Replit environment):
```bash
npm run dev
```

### Building for Production
```bash
npm run build
```

## Configuration
- **Port**: 5000 (Replit compatible)
- **Host**: 0.0.0.0 (allows external connections)
- **HMR**: Configured for Replit's proxy environment

## Demo Credentials
The application includes demo credentials visible on the login page:
- Email: admin@example.com
- Password: admin123

## Recent Changes (October 29, 2025)

### Initial Setup (October 27, 2025)
- Imported from GitHub
- Configured for Replit environment
- Set up TypeScript configuration
- Updated Vite config for port 5000
- Configured HMR for Replit proxy
- Added .gitignore
- Set up deployment configuration

### Database Integration Update (October 29, 2025)
- ✅ **Removed all demo/mock data** - No more hardcoded placeholder data
- ✅ **API integration ready** - All endpoints now connect to Spring Boot backend
- ✅ **Admin login removed** - Authentication bypassed (to be re-added later)
- ✅ **MySQL backend ready** - Configured for local Spring Boot + MySQL setup
- ✅ **Environment configuration** - Added .env.example for API URL configuration

## Backend Integration

### API Configuration
The frontend is configured to connect to a Spring Boot backend with MySQL database. 

**To set up:**
1. Create a `.env` file in the project root:
   ```
   VITE_API_BASE_URL=http://localhost:8080/api
   ```
2. Start your Spring Boot backend on port 8080
3. Ensure CORS is enabled in the backend for the frontend URL

### Required Backend Endpoints
All API endpoints are documented in `README_SETUP.md`. The backend should implement:
- Dashboard stats and analytics
- Order management (CRUD operations)
- Product management (CRUD operations)
- Coupon management (CRUD operations)
- User management (view operations)

### Database Schema
The MySQL database schema is provided in `attached_assets/database_schema_*.csv` with tables for:
- Users, Orders, Products, Categories
- Coupons, Notifications, Admin Activity Logs
- Order Refunds, Order Status History
- And more...

## Current Status

### Authentication
- **Status**: Bypassed/Disabled
- **Access**: All pages are publicly accessible
- **Login Page**: Removed (to be re-added after backend is ready)
- **Future**: Will implement JWT authentication with Spring Boot backend

### Data Source
- **Current**: All API calls configured but will fail without backend
- **Expected**: Spring Boot REST API on `http://localhost:8080/api`
- **Database**: MySQL with schema as provided in documentation

## Notes
- ✅ No demo/mock data - all removed
- ✅ Ready for Spring Boot + MySQL backend integration
- ✅ Environment variables configured via .env file
- ⚠️ Backend must be running for the dashboard to work
- ⚠️ CORS must be configured in Spring Boot backend
- Development server automatically restarts on file changes

## Next Steps
1. Implement Spring Boot backend (see `SPRING_BOOT_BACKEND_COMPLETE_PLAN` document)
2. Set up MySQL database with provided schema
3. Configure `.env` file with correct API URL
4. Test all dashboard features with live data
5. Re-implement authentication once backend is ready
