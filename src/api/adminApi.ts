// API functions for admin operations
// These will connect to your Spring Boot backend with MySQL database

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api';

export interface Order {
  id: string;
  orderNumber: string;
  customer: {
    name: string;
    email: string;
    phone: string;
  };
  items: Array<{
    id: string;
    name: string;
    quantity: number;
    price: number;
    image: string;
  }>;
  total: number;
  status: 'pending' | 'processing' | 'shipped' | 'delivered' | 'cancelled';
  paymentStatus: 'pending' | 'paid' | 'refunded';
  shippingAddress: {
    street: string;
    city: string;
    state: string;
    zipCode: string;
    country: string;
  };
  createdAt: string;
  updatedAt: string;
}

export interface Product {
  id: string;
  name: string;
  description: string;
  price: number;
  category: string;
  stock: number;
  sku: string;
  images: string[];
  isActive: boolean;
  createdAt: string;
}

export interface Coupon {
  id: string;
  code: string;
  type: 'percentage' | 'fixed';
  value: number;
  minPurchase: number;
  maxDiscount?: number;
  usageLimit: number;
  usageCount: number;
  expiresAt: string;
  isActive: boolean;
}

export interface User {
  id: string;
  name: string;
  email: string;
  phone: string;
  totalOrders: number;
  totalSpent: number;
  joinedAt: string;
  status: 'active' | 'blocked';
}

export interface DashboardStats {
  totalRevenue: number;
  totalOrders: number;
  totalCustomers: number;
  averageOrderValue: number;
  revenueChange: number;
  ordersChange: number;
  customersChange: number;
}

export interface SalesData {
  date: string;
  revenue: number;
  orders: number;
}

// API Response wrapper from Spring Boot backend
interface ApiResponse<T> {
  success: boolean;
  message: string | null;
  data: T;
}

// Helper function for API calls
async function apiCall<T>(endpoint: string, options?: RequestInit): Promise<T> {
  const response = await fetch(`${API_BASE_URL}${endpoint}`, {
    ...options,
    headers: {
      'Content-Type': 'application/json',
      ...options?.headers,
    },
  });

  if (!response.ok) {
    throw new Error(`API Error: ${response.statusText}`);
  }

  const result: ApiResponse<T> = await response.json();
  
  if (!result.success) {
    throw new Error(result.message || 'API request failed');
  }

  return result.data;
}

// API functions
export const adminApi = {
  // Dashboard
  getDashboardStats: async (): Promise<DashboardStats> => {
    return apiCall<DashboardStats>('/admin/dashboard/stats');
  },

  getSalesData: async (days: number = 30): Promise<SalesData[]> => {
    return apiCall<SalesData[]>(`/admin/dashboard/sales?days=${days}`);
  },

  // Orders
  getOrders: async (filters?: {
    status?: string;
    search?: string;
    page?: number;
    limit?: number;
  }): Promise<{ orders: Order[]; total: number }> => {
    const params = new URLSearchParams();
    if (filters?.status && filters.status !== 'all') params.append('status', filters.status);
    if (filters?.search) params.append('search', filters.search);
    if (filters?.page) params.append('page', filters.page.toString());
    if (filters?.limit) params.append('limit', filters.limit.toString());
    
    return apiCall<{ orders: Order[]; total: number }>(`/admin/orders?${params}`);
  },

  getOrder: async (id: string): Promise<Order | null> => {
    return apiCall<Order>(`/admin/orders/${id}`);
  },

  updateOrderStatus: async (id: string, status: Order['status']): Promise<Order> => {
    return apiCall<Order>(`/admin/orders/${id}/status`, {
      method: 'PATCH',
      body: JSON.stringify({ status }),
    });
  },

  // Products
  getProducts: async (filters?: {
    category?: string;
    search?: string;
    inStock?: boolean;
  }): Promise<Product[]> => {
    const params = new URLSearchParams();
    if (filters?.category && filters.category !== 'all') params.append('category', filters.category);
    if (filters?.search) params.append('search', filters.search);
    if (filters?.inStock !== undefined) params.append('inStock', filters.inStock.toString());
    
    return apiCall<Product[]>(`/admin/products?${params}`);
  },

  getProduct: async (id: string): Promise<Product | null> => {
    return apiCall<Product>(`/admin/products/${id}`);
  },

  createProduct: async (data: Omit<Product, 'id' | 'createdAt'>): Promise<Product> => {
    return apiCall<Product>('/admin/products', {
      method: 'POST',
      body: JSON.stringify(data),
    });
  },

  updateProduct: async (id: string, data: Partial<Product>): Promise<Product> => {
    return apiCall<Product>(`/admin/products/${id}`, {
      method: 'PUT',
      body: JSON.stringify(data),
    });
  },

  deleteProduct: async (id: string): Promise<void> => {
    return apiCall<void>(`/admin/products/${id}`, {
      method: 'DELETE',
    });
  },

  // Coupons
  getCoupons: async (): Promise<Coupon[]> => {
    return apiCall<Coupon[]>('/admin/coupons');
  },

  createCoupon: async (data: Omit<Coupon, 'id' | 'usageCount'>): Promise<Coupon> => {
    return apiCall<Coupon>('/admin/coupons', {
      method: 'POST',
      body: JSON.stringify(data),
    });
  },

  updateCoupon: async (id: string, data: Partial<Coupon>): Promise<Coupon> => {
    return apiCall<Coupon>(`/admin/coupons/${id}`, {
      method: 'PUT',
      body: JSON.stringify(data),
    });
  },

  deleteCoupon: async (id: string): Promise<void> => {
    return apiCall<void>(`/admin/coupons/${id}`, {
      method: 'DELETE',
    });
  },

  // Users
  getUsers: async (filters?: { search?: string; status?: string }): Promise<User[]> => {
    const params = new URLSearchParams();
    if (filters?.search) params.append('search', filters.search);
    if (filters?.status && filters.status !== 'all') params.append('status', filters.status);
    
    return apiCall<User[]>(`/admin/users?${params}`);
  },

  getUser: async (id: string): Promise<User | null> => {
    return apiCall<User>(`/admin/users/${id}`);
  },
};
