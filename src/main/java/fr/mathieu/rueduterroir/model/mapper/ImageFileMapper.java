package fr.mathieu.rueduterroir.model.mapper;

import fr.mathieu.rueduterroir.dto.image_file.ImageUploadDTO;
import fr.mathieu.rueduterroir.model.entity.ImageFile;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ImageFileMapper {
    ImageFile imageUploadDTOToImageFile(ImageUploadDTO imageUploadDTO);

    ImageUploadDTO imageFileToImageUploadDTO(ImageFile imageFile);
}
