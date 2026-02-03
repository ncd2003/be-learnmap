package com.learnmap.be.domain.repositories;

import com.learnmap.be.domain.entities.Role;
import com.learnmap.be.domain.entities.type.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(RoleName name);
}
