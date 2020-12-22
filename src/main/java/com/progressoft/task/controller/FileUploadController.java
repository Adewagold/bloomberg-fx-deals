package com.progressoft.task.controller;

import com.progressoft.task.model.FileUploadResponse;
import com.progressoft.task.service.FileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.stream.Collectors;

@RestController
public class FileUploadController {

    private FileServiceImpl fileService;

    @Autowired
    public FileUploadController(FileServiceImpl fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/uploadFile")
    public ResponseEntity<FileUploadResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        FileUploadResponse response = new FileUploadResponse(fileService.store(file) ,"Uploaded Successfully!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
