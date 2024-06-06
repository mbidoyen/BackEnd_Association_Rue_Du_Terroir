package fr.mathieu.rueduterroir.service.impl;

import fr.mathieu.rueduterroir.model.entity.ImageFile;
import fr.mathieu.rueduterroir.repository.IImageFileRepository;
import fr.mathieu.rueduterroir.service.IImageFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ImageFileServiceIMPL implements IImageFileService {

    private final IImageFileRepository imageFileRepository;

    @Autowired
    public ImageFileServiceIMPL(IImageFileRepository imageFileRepository) {
        this.imageFileRepository = imageFileRepository;
    }

    @Transactional
    @Override
    public ImageFile save(ImageFile imageFile) {
        return imageFileRepository.save(imageFile);
    }

    @Transactional
    @Override
    public ImageFile update(ImageFile imageFile) {
        return imageFileRepository.save(imageFile);
    }
}
