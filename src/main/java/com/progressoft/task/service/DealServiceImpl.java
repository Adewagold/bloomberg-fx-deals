package com.progressoft.task.service;

import com.progressoft.task.exception.InvalidDateException;
import com.progressoft.task.exception.MissingFieldsException;
import com.progressoft.task.model.Deal;
import com.progressoft.task.repository.DealRepository;
import com.progressoft.task.util.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DealServiceImpl implements DealService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DealService.class);
    private final DealRepository dealRepository;

    @Autowired
    public DealServiceImpl(DealRepository dealRepository) {
        this.dealRepository = dealRepository;
    }

    @Override
    public void persistToDB(String dealRows){
        String[] deals = dealRows.split(",");
        Deal deal = new Deal();
        deal.setId(Long.valueOf(deals[0].trim()));
        deal.setFromCurrencyIsoCode(deals[1].trim());
        deal.setToCurrencyIsoCode(deals[2].trim());
        deal.setTimestamp(Utility.formatDate(deals[3].trim()));
        deal.setAmount(new BigDecimal(deals[4].trim()));
        dealRepository.save(deal);
    }

}
