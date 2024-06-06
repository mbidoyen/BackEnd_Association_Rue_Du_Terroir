package fr.mathieu.rueduterroir.model.mapper;

import fr.mathieu.rueduterroir.model.dto.tenant.TenantSubscription;
import fr.mathieu.rueduterroir.model.entity.Tenant;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface TenantMapper {
    Tenant tenantSubscriptiontoTenant(TenantSubscription tenantSubscription);
}
