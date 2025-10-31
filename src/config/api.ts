const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080';

export const API_ENDPOINTS = {
  dashboard: {
    stats: `${API_BASE_URL}/admin/dashboard/stats`,
    sales: (days: number = 30) => `${API_BASE_URL}/admin/dashboard/sales?days=${days}`,
  },
  orders: {
    list: `${API_BASE_URL}/admin/orders`,
    detail: (id: number) => `${API_BASE_URL}/admin/orders/${id}`,
    updateStatus: (id: number) => `${API_BASE_URL}/admin/orders/${id}/status`,
  },
  products: {
    list: `${API_BASE_URL}/admin/products`,
    detail: (id: number) => `${API_BASE_URL}/admin/products/${id}`,
    create: `${API_BASE_URL}/admin/products`,
    update: (id: number) => `${API_BASE_URL}/admin/products/${id}`,
    delete: (id: number) => `${API_BASE_URL}/admin/products/${id}`,
    adjustStock: (id: number) => `${API_BASE_URL}/admin/products/${id}/stock`,
  },
  categories: {
    list: `${API_BASE_URL}/admin/categories`,
    detail: (id: number) => `${API_BASE_URL}/admin/categories/${id}`,
  },
  coupons: {
    list: `${API_BASE_URL}/admin/coupons`,
    detail: (id: number) => `${API_BASE_URL}/admin/coupons/${id}`,
    create: `${API_BASE_URL}/admin/coupons`,
    update: (id: number) => `${API_BASE_URL}/admin/coupons/${id}`,
    delete: (id: number) => `${API_BASE_URL}/admin/coupons/${id}`,
  },
  users: {
    list: `${API_BASE_URL}/admin/users`,
    detail: (id: number) => `${API_BASE_URL}/admin/users/${id}`,
  },
  notifications: {
    list: `${API_BASE_URL}/admin/notifications`,
    unread: `${API_BASE_URL}/admin/notifications/unread`,
    count: `${API_BASE_URL}/admin/notifications/count`,
    markAsRead: (id: number) => `${API_BASE_URL}/admin/notifications/${id}/read`,
    markAllAsRead: `${API_BASE_URL}/admin/notifications/mark-all-read`,
  },
};

export default API_BASE_URL;