package com.pcn.playing_ground.seeder;

import com.pcn.playing_ground.entity.ERole;
import com.pcn.playing_ground.entity.Role;
import com.pcn.playing_ground.repository.RoleRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @pre Here to create such data seeder for inserting roles automaticaly, when not existing in the DB
 * */

@Component
public class RoleDataSeeder {
    @Autowired
    private RoleRepo roleRepository;

    @EventListener
    @Transactional
    public void LoadRoles(ContextRefreshedEvent event) {

        List<ERole> roles = Arrays.stream(ERole.values()).toList();

        for(ERole erole: roles) {
            if (roleRepository.findByRoleName(erole)==null) {
                roleRepository.save(new Role(erole));
            }
        }

    }
}
