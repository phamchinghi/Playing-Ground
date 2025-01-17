package com.pcn.playing_ground.factories;

import com.pcn.playing_ground.common.exceptions.RoleNotFoundException;
import com.pcn.playing_ground.entity.ERole;
import com.pcn.playing_ground.entity.Role;
import com.pcn.playing_ground.repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
}
