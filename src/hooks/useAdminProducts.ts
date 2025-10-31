import { useState, useEffect } from 'react';
import { adminApi, Product, PageResponse, ProductCreateRequest, ProductUpdateRequest } from '../api/adminApi';

export const useAdminProducts = (filters?: {
  categoryId?: number;
  search?: string;
  inStock?: boolean;
  page?: number;
  size?: number;
}) => {
  const [products, setProducts] = useState<Product[]>([]);
  const [pageData, setPageData] = useState<PageResponse<Product> | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    loadProducts();
  }, [filters?.categoryId, filters?.search, filters?.inStock, filters?.page, filters?.size]);

  const loadProducts = async () => {
    try {
      setIsLoading(true);
      const data = await adminApi.getProducts(filters);
      setProducts(data.content);
      setPageData(data);
      setError(null);
    } catch (err) {
      console.error('Failed to load products:', err);
      setError('Failed to load products');
      setProducts([]);
    } finally {
      setIsLoading(false);
    }
  };

  const createProduct = async (data: ProductCreateRequest) => {
    try {
      await adminApi.createProduct(data);
      await loadProducts();
    } catch (err) {
      console.error('Failed to create product:', err);
      throw new Error('Failed to create product');
    }
  };

  const updateProduct = async (id: string | number, data: ProductUpdateRequest) => {
    try {
      await adminApi.updateProduct(id, data);
      await loadProducts();
    } catch (err) {
      console.error('Failed to update product:', err);
      throw new Error('Failed to update product');
    }
  };

  const deleteProduct = async (id: string | number) => {
    try {
      await adminApi.deleteProduct(id);
      await loadProducts();
    } catch (err) {
      console.error('Failed to delete product:', err);
      throw new Error('Failed to delete product');
    }
  };

  const adjustStock = async (id: string | number, type: 'add' | 'remove', quantity: number) => {
    try {
      await adminApi.adjustStock(id, type, quantity);
      await loadProducts();
    } catch (err) {
      console.error('Failed to adjust stock:', err);
      throw new Error('Failed to adjust stock');
    }
  };

  return {
    products,
    pageData,
    isLoading,
    error,
    reload: loadProducts,
    createProduct,
    updateProduct,
    deleteProduct,
    adjustStock,
  };
};