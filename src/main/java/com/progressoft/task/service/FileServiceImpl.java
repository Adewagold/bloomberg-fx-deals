package com.progressoft.task.service;

import com.progressoft.task.config.StorageProperties;
import com.progressoft.task.exception.FileException;
import com.progressoft.task.exception.InvalidFileException;
import com.progressoft.task.exception.MissingFieldsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceImpl implements FileService{
    private final Path rootLocation;
    private final DealService dealService;

    private static final Logger LOGGER = LoggerFactory.getLogger(FileService.class);
    @Autowired
    public FileServiceImpl(StorageProperties properties, DealService dealRepository) {
        this.rootLocation = Paths.get(properties.getLocation());
        this.dealService = dealRepository;
    }

    @Override
    public String store(MultipartFile file) {
        if(file.getOriginalFilename().endsWith(".csv")
                || file.getOriginalFilename().endsWith(".xlsx")
                || file.getOriginalFilename().endsWith(".xls")
        ){
            try {
                if (file.isEmpty()) {
                    throw new FileException("Failed to store empty file.");
                }
                if(Files.exists(Paths.get(String.format("%s/%s", this.rootLocation, file.getOriginalFilename())))){
                    throw new FileException("File already exists.");
                }
                Path destinationFile = this.rootLocation.resolve(
                        Paths.get(file.getOriginalFilename()))
                        .normalize().toAbsolutePath();
                if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                    throw new FileException(
                            "Cannot store file outside current directory.");
                }

                List<String> lines = new ArrayList<>();
                try (InputStream inputStream = file.getInputStream()) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    int row = 1;
                    String line;
                    int columnLength = 0;

                    while((line = bufferedReader.readLine()) != null){
                        lines.add(line);
                        columnLength = line.split(",").length;
                        if(row==1){
                            LOGGER.info("Deals header - {}", line);

                        }else {
                            validateColumnLength(columnLength, line.split(",").length);
                            dealService.persistToDB(line);
                            LOGGER.info(line + " - Saved to DB");
                        }
                        row++;
                    }

                    Files.copy(inputStream, destinationFile,
                            StandardCopyOption.REPLACE_EXISTING);
                }
            }
            catch (IOException e) {
                LOGGER.error(e.getMessage());
                throw new FileException("Failed to store file.", e);
            }
            return file.getOriginalFilename();
        }
        else{
            throw new InvalidFileException("You have uploaded an invalid file.");
        }
    }


    private void validateColumnLength(Integer expected, Integer length){
        if(!length.equals(expected)){
            throw new MissingFieldsException("Invalid row");
        }
    }
}
