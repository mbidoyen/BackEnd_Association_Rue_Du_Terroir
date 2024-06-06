package fr.mathieu.rueduterroir.repository;

import fr.mathieu.rueduterroir.model.entity.Role;
import fr.mathieu.rueduterroir.model.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findRoleByName(String name);
}
