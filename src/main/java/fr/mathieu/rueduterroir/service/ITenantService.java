package fr.mathieu.rueduterroir.service;

import fr.mathieu.rueduterroir.model.entity.Address;
import fr.mathieu.rueduterroir.model.entity.Tenant;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ITenantService {

    Tenant save(Tenant tenant);
    Tenant update(Tenant tenant);
    Boolean delete(Tenant tenant);
    Optional<Tenant> getByUuid(UUID uuid);
    Optional<Tenant> getByEmail(String email);
    List<Tenant> getAll();
    Optional<Tenant> findByAddress(Address address);
}
