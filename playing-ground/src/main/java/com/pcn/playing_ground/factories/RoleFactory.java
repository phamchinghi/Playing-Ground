package com.pcn.playing_ground.factories;

import com.pcn.playing_ground.common.exceptions.RoleNotFoundException;
import com.pcn.playing_ground.entity.ERole;
import com.pcn.playing_ground.entity.Role;
import com.pcn.playing_ground.repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class RoleFactory {
    @Autowired
    RoleRepo roleRepository;

    public Role getInstance(String role) throws RoleNotFoundException {
        switch (role) {
            case "ADMIN" -> {
                return roleRepository.findByRoleName(ERole.ADMIN);
            }
            case "USER" -> {
                return roleRepository.findByRoleName(ERole.USER);
            }
            default -> throw new RoleNotFoundException("No role found for " +  role);
        }
    }

    public Set<Role> determineRoles(Set<String> strRoles) throws RoleNotFoundException {
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            roles.add(getInstance("USER"));
        } else {
            for (String role : strRoles) {
                roles.add(getInstance(role));
            }
        }
        return roles;
    }
}
