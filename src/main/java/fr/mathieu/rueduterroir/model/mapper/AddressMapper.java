package fr.mathieu.rueduterroir.model.mapper;

import fr.mathieu.rueduterroir.dto.address.AddressDTO;
import fr.mathieu.rueduterroir.model.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface AddressMapper {
    AddressDTO AddressToAddressDTO(Address address);

    List<AddressDTO> AddressListToAddressDTOList(List<Address> address);
}
