package fr.mathieu.rueduterroir.controller;

import fr.mathieu.rueduterroir.dto.address.AddressDTO;
import fr.mathieu.rueduterroir.model.entity.Address;
import fr.mathieu.rueduterroir.model.mapper.AddressMapper;
import fr.mathieu.rueduterroir.service.IAddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@Validated
@Slf4j
public class AddressController {
    private final IAddressService addressService;
    private final AddressMapper addressMapper;

    @Autowired
    public AddressController(IAddressService addressService, AddressMapper addressMapper) {
        this.addressService = addressService;
        this.addressMapper = addressMapper;
    }

    @GetMapping("/address")
    public ResponseEntity<List<AddressDTO>> getAllAddress() {
        List<Address> addressList = addressService.getAll();
        if (addressList.isEmpty()) {
            return ResponseEntity.internalServerError().body(new ArrayList<>());
        }
        List<AddressDTO> addressDTOS = addressMapper.AddressListToAddressDTOList(addressList);
        return ResponseEntity.ok(addressDTOS);
    }

    @GetMapping("/address/{id}")
    public ResponseEntity<AddressDTO> getAddress(@PathVariable("id") int id){
        Optional<Address> address = addressService.getAddressById(id);
        if(address.isPresent()) {
            AddressDTO addressDTO = addressMapper.AddressToAddressDTO(address.get());
            return ResponseEntity.ok(addressDTO);
        }
        return new ResponseEntity<>(new AddressDTO(), HttpStatus.BAD_REQUEST);
    }
}
