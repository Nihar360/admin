const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080';

// Types
export interface Product {
  id: number;
  name: string;
  sku: string;
  description: string;
  price: number;
  discountPrice?: number;
  categoryId: number;
  categoryName: string;
  stockQuantity: number;
  inStock: boolean;
  thumbnail?: string;
  isActive: boolean;
  metaDescription?: string;
  metaKeywords?: string;
  createdAt: string;
  updatedAt: string;
}

export interface Category {
  id: number;
  name: string;
  description?: string;
  image?: string;
  itemCount?: string;
  createdAt: string;
  updatedAt: string;
}

export interface ProductCreateRequest {
  name: string;
  description: string;
  price: number;
  discountPrice?: number;
  categoryId: number;
  stockQuantity: number;
  sku?: string;
  thumbnail?: string;
  isActive?: boolean;
  metaDescription?: string;
  metaKeywords?: string;
}

export interface ProductUpdateRequest {
  name?: string;
  description?: string;
  price?: number;
  discountPrice?: number;
  categoryId?: number;
  stockQuantity?: number;
  thumbnail?: string;
  isActive?: boolean;
  metaDescription?: string;
  metaKeywords?: string;
}

export interface PageResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  currentPage: number;
  pageSize: number;
  first: boolean;
  last: boolean;
}

export interface ApiResponse<T> {
  success: boolean;
  message: string;
  data: T;
}

// API Helper
async function fetchApi<T>(endpoint: string, options?: RequestInit): Promise<T> {
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
  return result.data;
}

// Product API
export const adminApi = {
  // Products
  async getProducts(filters?: {
    categoryId?: number;
    search?: string;
    inStock?: boolean;
    page?: number;
    size?: number;
  }): Promise<PageResponse<Product>> {
    const params = new URLSearchParams();
    if (filters?.categoryId) params.append('categoryId', filters.categoryId.toString());
    if (filters?.search) params.append('search', filters.search);
    if (filters?.inStock !== undefined) params.append('inStock', filters.inStock.toString());
    params.append('page', (filters?.page ?? 0).toString());
    params.append('size', (filters?.size ?? 10).toString());

    return fetchApi<PageResponse<Product>>(`/admin/products?${params.toString()}`);
  },

  async getProduct(id: string | number): Promise<Product> {
    return fetchApi<Product>(`/admin/products/${id}`);
  },

  async createProduct(data: ProductCreateRequest): Promise<Product> {
    return fetchApi<Product>('/admin/products', {
      method: 'POST',
      body: JSON.stringify(data),
    });
  },

  async updateProduct(id: string | number, data: ProductUpdateRequest): Promise<Product> {
    return fetchApi<Product>(`/admin/products/${id}`, {
      method: 'PUT',
      body: JSON.stringify(data),
    });
  },

  async deleteProduct(id: string | number): Promise<void> {
    return fetchApi<void>(`/admin/products/${id}`, {
      method: 'DELETE',
    });
  },

  async adjustStock(id: string | number, type: 'add' | 'remove', quantity: number): Promise<Product> {
    return fetchApi<Product>(`/admin/products/${id}/stock`, {
      method: 'PUT',
      body: JSON.stringify({ type, quantity }),
    });
  },

  // Categories
  async getCategories(): Promise<Category[]> {
    return fetchApi<Category[]>('/admin/categories');
  },

  async getCategory(id: string | number): Promise<Category> {
    return fetchApi<Category>(`/admin/categories/${id}`);
  },

  // Dashboard
  async getDashboardStats(): Promise<any> {
    return fetchApi('/admin/dashboard/stats');
  },

  async getSalesData(days: number = 30): Promise<any> {
    return fetchApi(`/admin/dashboard/sales?days=${days}`);
  },

  // Orders
  async getOrders(filters?: { status?: string; search?: string; page?: number; limit?: number }): Promise<any> {
    const params = new URLSearchParams();
    if (filters?.status) params.append('status', filters.status);
    if (filters?.search) params.append('search', filters.search);
    if (filters?.page !== undefined) params.append('page', filters.page.toString());
    if (filters?.limit) params.append('limit', filters.limit.toString());

    return fetchApi(`/admin/orders?${params.toString()}`);
  },

  async getOrder(id: string | number): Promise<any> {
    return fetchApi(`/admin/orders/${id}`);
  },

  async updateOrderStatus(id: string | number, status: string): Promise<any> {
    return fetchApi(`/admin/orders/${id}/status`, {
      method: 'PATCH',
      body: JSON.stringify({ status }),
    });
  },

  // Users
  async getUsers(filters?: { search?: string; status?: string }): Promise<any> {
    const params = new URLSearchParams();
    if (filters?.search) params.append('search', filters.search);
    if (filters?.status) params.append('status', filters.status);

    return fetchApi(`/admin/users?${params.toString()}`);
  },

  async getUser(id: string | number): Promise<any> {
    return fetchApi(`/admin/users/${id}`);
  },

  // Coupons
  async getCoupons(): Promise<any> {
    return fetchApi('/admin/coupons');
  },

  async getCoupon(id: string | number): Promise<any> {
    return fetchApi(`/admin/coupons/${id}`);
  },

  async createCoupon(data: any): Promise<any> {
    return fetchApi('/admin/coupons', {
      method: 'POST',
      body: JSON.stringify(data),
    });
  },

  async updateCoupon(id: string | number, data: any): Promise<any> {
    return fetchApi(`/admin/coupons/${id}`, {
      method: 'PUT',
      body: JSON.stringify(data),
    });
  },

  async deleteCoupon(id: string | number): Promise<void> {
    return fetchApi(`/admin/coupons/${id}`, {
      method: 'DELETE',
    });
  },

  // Notifications
  async getNotifications(): Promise<any> {
    return fetchApi('/admin/notifications');
  },

  async markNotificationAsRead(id: string | number): Promise<any> {
    return fetchApi(`/admin/notifications/${id}/read`, {
      method: 'PATCH',
    });
  },

  async markAllNotificationsAsRead(): Promise<any> {
    return fetchApi('/admin/notifications/mark-all-read', {
      method: 'PATCH',
    });
  },
};