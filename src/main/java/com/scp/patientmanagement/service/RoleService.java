package com.scp.patientmanagement.service;

import com.scp.patientmanagement.repository.RoleRepository;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;


@ComponentScan
@Service
public class RoleService implements RoleServiceInterface {

    private final RoleRepository roleRepository;


    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     *
     * @return
     */
    public RoleRepository getRepository() {
        return this.roleRepository;
    }

}
