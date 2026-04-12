package org.financeData.services;

import org.financeData.models.CashFlow;
import org.financeData.models.News;

import java.util.List;

public interface IStockService {
    List<News> getStockNews(String symbol);

    List<CashFlow> getCompanyCashFlow(String symbol);
}
