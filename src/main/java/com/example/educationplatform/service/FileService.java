package com.example.educationplatform.service;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {
    public String saveFile(String name, MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();

        if (originalFileName == null) {
            originalFileName = "file.jpg";
        }

        String filename = name + originalFileName.substring(originalFileName.lastIndexOf("."));
        String imageDir = "src/main/resources/static/files";
        String projectPath = new File("").getAbsolutePath();

        Path path = Paths.get(projectPath, imageDir, filename);
        File newFile = path.toFile();
        FileUtils.writeByteArrayToFile(newFile, file.getBytes());

        String imageDir2 = "target/classes/static/files";

        Path path2 = Paths.get(projectPath, imageDir2, filename);
        File newFile2 = path2.toFile();
        FileUtils.writeByteArrayToFile(newFile2, file.getBytes());

        return "files/" + filename;
    }
}
