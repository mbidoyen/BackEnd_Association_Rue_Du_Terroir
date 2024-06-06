package fr.mathieu.rueduterroir.service;

import fr.mathieu.rueduterroir.model.entity.Role;
import fr.mathieu.rueduterroir.model.entity.Tenant;

import java.util.List;
import java.util.Optional;

public interface IRoleService {
    Role save(Role role);

    Role update(Role role);

    Boolean delete(Role role);

    Optional<Role> getRoleById(int id);

    Optional<Role> getRoleByName(String name);

    List<Role> getAll();
}
