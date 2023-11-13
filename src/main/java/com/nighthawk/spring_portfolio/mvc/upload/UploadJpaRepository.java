package com.nighthawk.spring_portfolio.mvc.upload;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadJpaRepository extends JpaRepository<Upload, Long> {
    Optional<Upload> findByfileName(String fileName);

    void deleteByfileName(String fileName);
}