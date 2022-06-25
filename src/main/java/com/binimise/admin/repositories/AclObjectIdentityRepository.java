package com.binimise.admin.repositories;

import com.binimise.admin.models.AclObjectIdentity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AclObjectIdentityRepository extends JpaRepository<AclObjectIdentity, Integer> {

    Optional<AclObjectIdentity> findById(Integer id);
}
