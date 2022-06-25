package com.binimise.admin.repositories;

import com.binimise.admin.models.AclEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AclEntryRepository extends JpaRepository<AclEntry, Integer> {

    Optional<AclEntry> findById(Integer id);
}
