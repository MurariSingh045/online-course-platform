package com.ms.Auth_Service.service;

import com.ms.Auth_Service.model.Role;
import com.ms.Auth_Service.repo.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    //This method ensures a role exists in the database. If not, it creates it.
    public Role getOrCreateRole(String roleName)
    {
        return roleRepository.findByName(roleName)
                .orElseGet(()->roleRepository.save(new Role()));
    }

    //This method is meant to be used when a user registers and should get default roles, like ROLE_USER.
    //It checks if ROLE_USER exists, creates it if not (using the method above)
    public Set<Role> getDefaultRoles()
    {
        Role userRole = getOrCreateRole("ROLE_USER");
        return Set.of(userRole);
    }
}
