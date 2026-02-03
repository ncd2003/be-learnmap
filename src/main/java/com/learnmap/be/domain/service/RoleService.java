package com.learnmap.be.domain.service;

import com.learnmap.be.domain.entities.Role;
import com.learnmap.be.domain.entities.type.RoleName;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService extends BaseService {
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role handleRoleByName(RoleName name) {
        return roleRepository.findByName(name).isEmpty() ? null : roleRepository.findByName(name).get();
    }

    public void createDefaultRoles() {
        if (roleRepository.findByName(RoleName.ADMIN).isEmpty()) {
            Role adminRole = new Role();
            adminRole.setName(RoleName.ADMIN);
            roleRepository.save(adminRole);
        }
        if (roleRepository.findByName(RoleName.STAFF).isEmpty()) {
            Role memberRole = new Role();
            memberRole.setName(RoleName.STAFF);
            roleRepository.save(memberRole);
        }
        if (roleRepository.findByName(RoleName.STUDENT).isEmpty()) {
            Role memberRole = new Role();
            memberRole.setName(RoleName.STUDENT);
            roleRepository.save(memberRole);
        }
    }
}
