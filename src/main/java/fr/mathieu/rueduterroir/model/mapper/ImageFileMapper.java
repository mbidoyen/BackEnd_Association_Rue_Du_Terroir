package fr.mathieu.rueduterroir.model.mapper;

import fr.mathieu.rueduterroir.model.dto.image_file.RegistrationImageUploadDTO;
import fr.mathieu.rueduterroir.model.entity.ImageFile;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ImageFileMapper {
    ImageFile RegistrationImageUploadDTOToImageFile(RegistrationImageUploadDTO registrationImageUploadDTO);
    RegistrationImageUploadDTO ImageFileToRegistrationImageUploadDTO(ImageFile imageFile);
}
