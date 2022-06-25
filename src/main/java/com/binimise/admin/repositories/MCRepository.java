package com.binimise.admin.repositories;

import com.binimise.admin.models.MC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MCRepository extends JpaRepository<MC, Long> {

    Optional<MC> findById(Long id);

    @PreAuthorize("#mc.id == null or  hasPermission(#mc, 'WRITE') or hasRole('ADMIN')")
    MC save(@Param("mc")MC mc);

    @Override
    List<MC> findAll();
}
