package fr.mathieu.rueduterroir.service.impl;

import fr.mathieu.rueduterroir.model.entity.Role;
import fr.mathieu.rueduterroir.repository.IRoleRepository;
import fr.mathieu.rueduterroir.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceIMPL implements IRoleService {
    private final IRoleRepository roleRepository;

    @Autowired
    public RoleServiceIMPL(IRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional
    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Transactional
    @Override
    public Role update(Role role) {
        return roleRepository.save(role);
    }

    @Transactional
    @Override
    public Boolean delete(Role role) {
        roleRepository.delete(role);
        Optional<Role> deletedRole = roleRepository.findById(role.getId());
        return deletedRole.isEmpty();
    }

    @Override
    public Optional<Role> getRoleById(int id) {
        return roleRepository.findById(id);
    }

    @Override
    public Optional<Role> getRoleByName(String name) {
        return roleRepository.findRoleByName(name);
    }

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

}
