package fr.mathieu.rueduterroir.service.impl;

import fr.mathieu.rueduterroir.model.entity.Address;
import fr.mathieu.rueduterroir.repository.IAddressRepository;
import fr.mathieu.rueduterroir.service.IAddressService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceIMPL implements IAddressService {

    private final IAddressRepository addressRepository;

    public AddressServiceIMPL(IAddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Optional<Address> getAddressById(int id) {
        return addressRepository.findById(id);
    }

    @Override
    public List<Address> getAll() {
        return addressRepository.findAll();
    }
}
