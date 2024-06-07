package fr.mathieu.rueduterroir.service.impl;

import fr.mathieu.rueduterroir.dto.image_file.ImageUploadDTO;
import fr.mathieu.rueduterroir.model.entity.ImageFile;
import fr.mathieu.rueduterroir.model.entity.Tenant;
import fr.mathieu.rueduterroir.repository.IImageFileRepository;
import fr.mathieu.rueduterroir.service.IImageFileService;
import fr.mathieu.rueduterroir.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ImageFileServiceIMPL implements IImageFileService {
    private final IImageFileRepository imageFileRepository;
    @Value("${image.repository.base.url}")
    private String imageDirectoryUrl;

    @Autowired
    public ImageFileServiceIMPL(IImageFileRepository imageFileRepository) {
        this.imageFileRepository = imageFileRepository;
    }

    @Transactional
    @Override
    public ImageFile save(ImageFile imageFile, Tenant tenant) {
        imageFile.setTenant(tenant);
        return imageFileRepository.save(imageFile);
    }

    @Transactional
    @Override
    public ImageFile update(ImageFile imageFile) {
        return imageFileRepository.save(imageFile);
    }

    @Override
    public Boolean uploadTenantImage(ImageUploadDTO imageUploadDTO, String email) {
        String uploadDir = imageDirectoryUrl + "/user_profile/" + email;
        String uniqueFilename = UUID.randomUUID() + "_" + imageUploadDTO.getFilename();
        boolean isUploaded = FileUtil.uploadFile(uploadDir, uniqueFilename, imageUploadDTO.getData());
        if (isUploaded) {
            imageUploadDTO.setFilename(uniqueFilename);
        }
        return isUploaded;
    }
}
