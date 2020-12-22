package com.progressoft.task.service;

import com.progressoft.task.config.StorageProperties;
import com.progressoft.task.exception.FileException;
import com.progressoft.task.exception.FileNotFoundException;
import com.progressoft.task.exception.InvalidDateException;
import com.progressoft.task.model.Deal;
import com.progressoft.task.repository.DealRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Service
public class FileServiceImpl implements FileService{
    private final Path rootLocation;
    private final DealRepository dealRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(FileService.class);
    @Autowired
    public FileServiceImpl(StorageProperties properties, DealRepository dealRepository) {
        this.rootLocation = Paths.get(properties.getLocation());
        this.dealRepository = dealRepository;
    }

    @Override
    public String store(MultipartFile file) {
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
                while((line = bufferedReader.readLine()) != null){
                    lines.add(line);
                    if(row>1) {
                        persistToDB(line);
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

    private void persistToDB(String dealRows){
        String[] deals = dealRows.split(",");
        Deal deal = new Deal();
        deal.setId(Long.valueOf(deals[0].trim()));
        deal.setFromCurrencyIsoCode(deals[1].trim());
        deal.setToCurrencyIsoCode(deals[2].trim());
        deal.setTimestamp(formatDate(deals[3].trim()));
        deal.setAmount(new BigDecimal(deals[4].trim()));
        dealRepository.save(deal);
    }


    private Date formatDate(String dateString){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm/DD/yyyy hh:mm:ss");
        try {
            Date date = simpleDateFormat.parse(dateString);
            return date;
        } catch (ParseException e) {
            LOGGER.error(e.getMessage());
            throw new InvalidDateException("Invalid date", e);
        }
    }
}
