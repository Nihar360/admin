package com.ecommerce.admin.repository;

import com.ecommerce.admin.model.AdminActivityLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminActivityLogRepository extends JpaRepository<AdminActivityLog, Long> {
    List<AdminActivityLog> findByAdminIdOrderByCreatedAtDesc(Long adminId);
    Page<AdminActivityLog> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
