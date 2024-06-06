package fr.mathieu.rueduterroir.service;

import fr.mathieu.rueduterroir.model.entity.Address;

import java.util.List;
import java.util.Optional;

public interface IAddressService {
    Optional<Address> getAddressById(int id);
    List<Address> getAll();
}
