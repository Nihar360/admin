import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { AdminAuthProvider } from './contexts/AdminAuthContext';
import { AdminDashboard } from './pages/AdminDashboard';
import { NotificationsPage } from './pages/NotificationsPage';
import { OrderList } from './pages/OrderList';
import { OrderDetails } from './pages/OrderDetails';
import { ProductList } from './pages/ProductList';
import { ProductForm } from './pages/ProductForm';
import { CouponList } from './pages/CouponList';
import { UserList } from './pages/UserList';
import { InventoryManagement } from './pages/InventoryManagement';
import { SettingsPage } from './pages/SettingsPage';
import { ReportsPage } from './pages/ReportsPage';
import { Toaster } from './components/ui/sonner';

export default function App() {
  return (
    <BrowserRouter>
      <AdminAuthProvider>
        <Routes>
          <Route path="/" element={<Navigate to="/admin/dashboard" replace />} />
          <Route path="/preview_page.html" element={<Navigate to="/admin/dashboard" replace />} />
          
          <Route path="/admin/dashboard" element={<AdminDashboard />} />
          <Route path="/admin/notifications" element={<NotificationsPage />} />
          <Route path="/admin/orders" element={<OrderList />} />
          <Route path="/admin/orders/:id" element={<OrderDetails />} />
          <Route path="/admin/products" element={<ProductList />} />
          <Route path="/admin/products/:id/edit" element={<ProductForm />} />
          <Route path="/admin/coupons" element={<CouponList />} />
          <Route path="/admin/users" element={<UserList />} />
          <Route path="/admin/inventory" element={<InventoryManagement />} />
          <Route path="/admin/settings" element={<SettingsPage />} />
          <Route path="/admin/reports" element={<ReportsPage />} />

          {/* Catch-all route for unmatched paths */}
          <Route path="*" element={<Navigate to="/admin/dashboard" replace />} />
        </Routes>
        <Toaster />
      </AdminAuthProvider>
    </BrowserRouter>
  );
}
