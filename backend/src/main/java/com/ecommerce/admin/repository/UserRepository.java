package com.ecommerce.admin.repository;

import com.ecommerce.admin.model.User;
import com.ecommerce.admin.model.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByRole(UserRole role);
    List<User> findByActive(Boolean active);
    
    @Query("SELECT u FROM User u WHERE u.role = 'CUSTOMER' AND (u.fullName LIKE %:search% OR u.email LIKE %:search%)")
    List<User> searchCustomers(String search);
    
    @Query("SELECT COUNT(u) FROM User u WHERE u.role = 'CUSTOMER'")
    Long countCustomers();
}
