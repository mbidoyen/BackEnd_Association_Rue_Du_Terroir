package fr.mathieu.rueduterroir.repository;

import fr.mathieu.rueduterroir.model.entity.ImageFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IImageFileRepository extends JpaRepository<ImageFile, Integer> {
}
