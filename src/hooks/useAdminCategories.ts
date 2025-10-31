import { useState, useEffect } from 'react';
import { adminApi, Category } from '../api/adminApi';

export const useAdminCategories = () => {
  const [categories, setCategories] = useState<Category[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    loadCategories();
  }, []);

  const loadCategories = async () => {
    try {
      setIsLoading(true);
      const data = await adminApi.getCategories();
      setCategories(data);
      setError(null);
    } catch (err) {
      console.error('Failed to load categories:', err);
      setError('Failed to load categories');
      setCategories([]);
    } finally {
      setIsLoading(false);
    }
  };

  const getCategory = async (id: string | number) => {
    try {
      return await adminApi.getCategory(id);
    } catch (err) {
      console.error('Failed to get category:', err);
      throw new Error('Failed to get category');
    }
  };

  return {
    categories,
    isLoading,
    error,
    reload: loadCategories,
    getCategory,
  };
};