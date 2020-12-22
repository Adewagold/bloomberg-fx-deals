package com.progressoft.task;

import com.progressoft.task.exception.InvalidDateException;
import com.progressoft.task.service.DealService;
import com.progressoft.task.util.Utility;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.stream.Stream;

@SpringBootTest
public class ProgressoftTaskApplicationTests {

	public static final String TEST_DIR = "src/test/testdir/";
	public static final String TEST_FILE = TEST_DIR+"test_file.csv";

	public static final String VALID_DATE = "12/12/2020 10:00:04";
	public static final String INVALID_DATE = "12/2020 10:00:04";

	@Test
	public void contextLoads() {
	}

	@Test
	public void testSuccessfulDateFormatUtil(){
		Date validDate = Utility.formatDate(VALID_DATE);
		Assertions.assertNotNull(validDate);
	}

	@Test(expected = InvalidDateException.class)
	public void testInvalidDateFormatUtil(){
		Utility.formatDate(INVALID_DATE);
	}

}
