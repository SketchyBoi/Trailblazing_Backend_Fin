package com.nighthawk.spring_portfolio.mvc.upload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UploadApiAppl {
    @Autowired
    private UploadJpaRepository uploadFileRepository;

    public ResponseEntity<String> saveImage(Upload uploadFile) {
        try {
            uploadFileRepository.save(uploadFile);
            return ResponseEntity.ok("Image has been uploaded");
        } catch (Exception e) {
            // handle exception and return error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
        }
    }
}