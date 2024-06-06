package fr.mathieu.rueduterroir.repository;

import fr.mathieu.rueduterroir.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAddressRepository extends JpaRepository<Address, Integer> {
}
