package com.example.numberextractorservice.service;

import com.example.numberextractorservice.entity.NumberResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface NumberExtractorService {

    /**
     * Process multipart file.
     * @param file multipart file
     * @return NumberResponseEntity with id and List Of Objects
     * @throws IOException
     */
    NumberResponseEntity processFile(MultipartFile file) throws IOException;
}
