package fr.mathieu.rueduterroir.service;

import fr.mathieu.rueduterroir.model.entity.ImageFile;

public interface IImageFileService {
    ImageFile save(ImageFile imageFile);

    ImageFile update(ImageFile imageFile);
}
