package fr.mathieu.rueduterroir.service.impl;

import fr.mathieu.rueduterroir.model.entity.Address;
import fr.mathieu.rueduterroir.model.entity.Tenant;
import fr.mathieu.rueduterroir.repository.ITenantRepository;
import fr.mathieu.rueduterroir.service.ITenantService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TenantServiceIMPL implements ITenantService {

    private final ITenantRepository tenantRepository;

    public TenantServiceIMPL(ITenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @Transactional
    @Override
    public Tenant save(Tenant tenant) {
        return tenantRepository.save(tenant);
    }

    @Transactional
    @Override
    public Tenant update(Tenant tenant) {
        return tenantRepository.save(tenant);
    }

    @Transactional
    @Override
    public Boolean delete(Tenant tenant) {
        tenantRepository.delete(tenant);
        Optional<Tenant> deletedTenant = tenantRepository.findById(tenant.getUuid());
        return deletedTenant.isEmpty();
    }

    @Override
    public Optional<Tenant> getByUuid(UUID uuid) {
        return tenantRepository.findById(uuid);
    }

    @Override
    public Optional<Tenant> getByEmail(String email) {
        return tenantRepository.findByEmail(email);
    }

    @Override
    public List<Tenant> getAll() {
        return tenantRepository.findAll();
    }

    @Override
    public Optional<Tenant> findByAddress(Address address) {
        return tenantRepository.findByAddress(address);
    }
}
