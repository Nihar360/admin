import React, { createContext, useContext, useState } from 'react';

interface Admin {
  id: string;
  email: string;
  name: string;
  role: 'super_admin' | 'admin' | 'manager';
}

interface AdminAuthContextType {
  admin: Admin | null;
  isLoading: boolean;
  login: (email: string, password: string) => Promise<void>;
  logout: () => void;
  isAuthenticated: boolean;
}

const AdminAuthContext = createContext<AdminAuthContextType | undefined>(undefined);

export const AdminAuthProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  // Authentication bypassed - always authenticated for now
  const [admin] = useState<Admin>({
    id: '1',
    email: 'admin@example.com',
    name: 'Admin User',
    role: 'super_admin',
  });
  const [isLoading] = useState(false);

  const login = async (_email: string, _password: string) => {
    // Login bypassed - will be implemented with backend
    return Promise.resolve();
  };

  const logout = () => {
    // Logout functionality disabled for now
    console.log('Logout functionality will be implemented with backend');
  };

  return (
    <AdminAuthContext.Provider
      value={{
        admin,
        isLoading,
        login,
        logout,
        isAuthenticated: true, // Always authenticated for now
      }}
    >
      {children}
    </AdminAuthContext.Provider>
  );
};

export const useAdminAuth = () => {
  const context = useContext(AdminAuthContext);
  if (context === undefined) {
    throw new Error('useAdminAuth must be used within an AdminAuthProvider');
  }
  return context;
};
