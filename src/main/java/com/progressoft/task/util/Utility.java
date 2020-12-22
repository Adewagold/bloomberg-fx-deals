package com.progressoft.task.util;

import com.progressoft.task.exception.InvalidDateException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {

    public static Date formatDate(String dateString){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm/DD/yyyy hh:mm:ss");
        try {
            Date date = simpleDateFormat.parse(dateString);
            return date;
        } catch (ParseException e) {
            throw new InvalidDateException("Invalid date", e);
        }
    }
}
