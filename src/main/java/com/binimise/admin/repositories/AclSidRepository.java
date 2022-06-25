package com.binimise.admin.repositories;

import com.binimise.admin.models.AclSid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AclSidRepository extends JpaRepository<AclSid, Integer> {

    Optional<AclSid> findBySid(String id);
}
