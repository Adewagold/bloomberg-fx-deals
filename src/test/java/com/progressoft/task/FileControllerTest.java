package com.progressoft.task;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(classes=ProgressoftTaskApplication.class)
@WebAppConfiguration
public class FileControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileControllerTest.class);
   @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    static String fileName = "upload-dir/test_file.csv";

    @BeforeClass
    public static void deleteTestFiles(){
        try {
            Files.delete(Paths.get(fileName));
            Files.delete(Paths.get("upload-dir/test_file_empty.csv"));
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    public void invalidDate() throws FileNotFoundException, IOException {
        Paths.get(fileName);
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "test_file_invalidDate.csv",
                MediaType.TEXT_PLAIN_VALUE,
                ("ID, FROM_CURRENCY_CODE, TO_CURRENCY_CODE, TIMESTAMP, AMOUNT\n" +
                        "1, NGN, USD, 15/10/2020s 10:00:00, 143.3").getBytes()
        );

        try {
            mockMvc.perform(MockMvcRequestBuilders.fileUpload("/uploadFile")
                    .file(file))
                    .andExpect(MockMvcResultMatchers.status().is(400))
                    .andExpect(content().json("{\"statusCode\":\"08\",\"statusMessage\":\"Invalid date\"}"))
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void invalidFileFormat() throws FileNotFoundException, IOException {
        Paths.get(fileName);
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "test_file_invalidDate.txt",
                MediaType.TEXT_PLAIN_VALUE,
                ("ID, FROM_CURRENCY_CODE, TO_CURRENCY_CODE, TIMESTAMP, AMOUNT\n" +
                        "1, NGN2, USDs, 15/10/2020s 10:00:00, 143.3").getBytes()
        );

        try {
            mockMvc.perform(MockMvcRequestBuilders.fileUpload("/uploadFile")
                    .file(file))
                    .andExpect(MockMvcResultMatchers.status().is(400))
                    .andExpect(content().json("{\"statusCode\":\"10\",\"statusMessage\":\"You have uploaded an invalid file.\"}"))
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void newFileUpload() throws FileNotFoundException, IOException {
        Paths.get(fileName);
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "test_file.csv",
                MediaType.TEXT_PLAIN_VALUE,
                ("ID, FROM_CURRENCY_CODE, TO_CURRENCY_CODE, TIMESTAMP, AMOUNT \n" +
                        "").getBytes()
        );

        try {
            mockMvc.perform(MockMvcRequestBuilders.fileUpload("/uploadFile")
                    .file(file))
                    .andExpect(MockMvcResultMatchers.status().is(200))
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void fileareadyexists() throws FileNotFoundException, IOException {

        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "test_file.csv",
                MediaType.TEXT_PLAIN_VALUE,
                ("ID, FROM_CURRENCY_CODE, TO_CURRENCY_CODE, TIMESTAMP, AMOUNT \n" +
                        "").getBytes()
        );

        try {
            mockMvc.perform(MockMvcRequestBuilders.fileUpload("/uploadFile")
                    .file(file))
                    .andExpect(content().json("{\"statusCode\":\"06\",\"statusMessage\":\"File already exists.\"}"))
                    .andExpect(MockMvcResultMatchers.status().is(400))
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void emptyFileException() throws FileNotFoundException, IOException {

        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "test_file_empty.csv",
                MediaType.TEXT_PLAIN_VALUE,
                ("").getBytes()
        );

        try {
            mockMvc.perform(MockMvcRequestBuilders.fileUpload("/uploadFile")
                    .file(file))
                    .andExpect(MockMvcResultMatchers.status().is(400))
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
