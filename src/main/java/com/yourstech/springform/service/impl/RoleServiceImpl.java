package com.yourstech.springform.service.impl;

import com.yourstech.springform.model.Role;
import com.yourstech.springform.repository.RoleRepository;
import com.yourstech.springform.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role getRoleByName(String name) {
        return roleRepository.findByName(name).orElseThrow();
    }
}
