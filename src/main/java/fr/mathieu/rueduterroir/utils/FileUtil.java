package fr.mathieu.rueduterroir.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
public class FileUtil {

    public static boolean saveFile(String uploadDir, String fileName, byte[] imageData) {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                log.error("Erreur lors de la création du répertoire : " + uploadDir, e);
                return false;
            }
        }

        Path filePath = uploadPath.resolve(fileName);
        try (InputStream in = new ByteArrayInputStream(imageData)) {
            Files.copy(in, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("Erreur lors de l'écriture du fichier : " + fileName, e);
            return false;
        }
        return true;
    }
}
