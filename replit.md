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

## Recent Changes (October 27, 2025)
- Imported from GitHub
- Configured for Replit environment
- Set up TypeScript configuration
- Updated Vite config for port 5000
- Configured HMR for Replit proxy
- Added .gitignore
- Set up deployment configuration

## Notes
- The app uses mock/local data (no backend API currently connected)
- All dependencies are installed and configured
- Development server automatically restarts on file changes
