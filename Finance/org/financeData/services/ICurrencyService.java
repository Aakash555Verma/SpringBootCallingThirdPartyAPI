package org.financeData.services;

import org.financeData.models.News;

import java.util.List;

public interface ICurrencyService {
    List<News> getCurrencyNews(String fromCurrency, String toCurrency);
}
