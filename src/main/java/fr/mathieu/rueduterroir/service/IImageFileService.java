package fr.mathieu.rueduterroir.service;

import fr.mathieu.rueduterroir.dto.image_file.ImageUploadDTO;
import fr.mathieu.rueduterroir.model.entity.ImageFile;
import fr.mathieu.rueduterroir.model.entity.Tenant;

public interface IImageFileService {
    ImageFile save(ImageFile imageFile, Tenant tenant);

    ImageFile update(ImageFile imageFile);

    Boolean uploadTenantImage(ImageUploadDTO imageUploadDTO, String email);
}
