package fr.mathieu.rueduterroir.repository;

import fr.mathieu.rueduterroir.model.entity.Address;
import fr.mathieu.rueduterroir.model.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ITenantRepository extends JpaRepository<Tenant, UUID> {
    Optional<Tenant> findByEmail(String email);
    Optional<Tenant> findByAddress(Address address);
}
