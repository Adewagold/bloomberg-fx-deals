package com.progressoft.task.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
@Entity
public class Deal {
    @Id
    @Column(name = "Id")
    private Long id;
    private String fromCurrencyIsoCode;
    private String toCurrencyIsoCode;
    private Date timestamp;
    private BigDecimal amount;

    public Deal() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFromCurrencyIsoCode(String fromCurrencyIsoCode) {
        this.fromCurrencyIsoCode = fromCurrencyIsoCode;
    }

    public void setToCurrencyIsoCode(String toCurrencyIsoCode) {
        this.toCurrencyIsoCode = toCurrencyIsoCode;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public String getFromCurrencyIsoCode() {
        return fromCurrencyIsoCode;
    }

    public String getToCurrencyIsoCode() {
        return toCurrencyIsoCode;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
