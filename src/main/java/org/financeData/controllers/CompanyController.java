package org.financeData.controllers;

import org.financeData.models.CashFlow;
import org.financeData.models.News;
import org.financeData.services.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private IStockService companyService;

    //localhost:8080/company_/stockNews?symbol=AAPL
    @GetMapping("/stockNews")
    public List<News> getStockNews(@RequestParam(required = true) String symbol) {
        return companyService.getStockNews(symbol);
    }

    //localhost:8080/company_/cashFlow?symbol=AAPL
    @GetMapping("/cashFlow")
    public List<CashFlow> getCompanyCashFlow(@RequestParam(required = true) String symbol) {
        return companyService.getCompanyCashFlow(symbol);
    }
}
