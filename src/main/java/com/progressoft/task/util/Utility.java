package com.progressoft.task.util;

import com.progressoft.task.exception.InvalidDateException;
import com.progressoft.task.exception.InvalidFieldException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {
    public static final Logger LOGGER = LoggerFactory.getLogger(Utility.class);

    public static Date formatDate(String dateString){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm/DD/yyyy hh:mm:ss");
        try {
            Date date = simpleDateFormat.parse(dateString);
            return date;
        } catch (ParseException e) {
            LOGGER.error("The file contains an invalid date {}", dateString);
            throw new InvalidDateException("Invalid date", e);
        }
    }

    public static String validateCurrency(String currencyCode){

        if(currencyCode.length()!=3){
            LOGGER.error("The current file contains an invalid currency code {}", currencyCode);
            throw new InvalidFieldException("Invalid Currency ISO code");
        }
        return currencyCode;
    }
}
