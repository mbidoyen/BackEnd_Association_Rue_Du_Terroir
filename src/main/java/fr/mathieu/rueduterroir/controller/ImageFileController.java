package fr.mathieu.rueduterroir.controller;

import fr.mathieu.rueduterroir.model.mapper.ImageFileMapper;
import fr.mathieu.rueduterroir.service.IImageFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class ImageFileController {
    private final IImageFileService imageFileService;
    ImageFileMapper imageFileMapper;

    @Autowired
    public ImageFileController(IImageFileService imageFileService) {
        this.imageFileService = imageFileService;
    }
}
