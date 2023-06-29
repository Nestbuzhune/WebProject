package com.imagecolectionsystem1.nest.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.imagecolectionsystem1.nest.model.Image;
import com.imagecolectionsystem1.nest.repository.ImageRepository;

@Service
public class ImageService {
    @Value("${file.upload-dir}")
    private String uploadDirectory;

    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public void saveImage(Image image, MultipartFile file) throws IOException {
        // Validate the file
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be empty");
        }

        // Save the file to the upload directory
        String fileName = file.getOriginalFilename();
        Path filePath = Path.of(uploadDirectory, fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Set the file path in the Image entity
        image.setFilePath(filePath.toString());

        // Save the Image entity to the database
        imageRepository.save(image);
    }

    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }
}