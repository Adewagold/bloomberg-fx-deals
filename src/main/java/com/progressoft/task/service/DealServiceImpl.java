package com.progressoft.task.service;

import com.progressoft.task.model.Deal;
import com.progressoft.task.repository.DealRepository;
import com.progressoft.task.util.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DealServiceImpl implements DealService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DealService.class);
    private final DealRepository dealRepository;

    @Autowired
    public DealServiceImpl(DealRepository dealRepository) {
        this.dealRepository = dealRepository;
    }

    @Override
    public void persistToDB(String dealRows){
       Deal deal = formatDeals(dealRows);
       dealRepository.save(deal);
    }

    private Deal formatDeals(String dealRows){
        String[] deals = dealRows.split(",");
        Deal deal = new Deal();
        String id = deals[0].trim();
        String fromCurrencyCode = deals[1].trim();
        String toCurrencyCode = deals[2].trim();
        String timeStamp = deals[3].trim();
        String amount = deals[4].trim();

        deal.setId(Long.valueOf(id));
        deal.setFromCurrencyIsoCode(Utility.validateCurrency(fromCurrencyCode));
        deal.setToCurrencyIsoCode(Utility.validateCurrency(toCurrencyCode));
        deal.setTimestamp(Utility.formatDate(timeStamp));
        deal.setAmount(new BigDecimal(amount));
        return deal;
    }

}
