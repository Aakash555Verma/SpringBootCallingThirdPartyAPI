package org.financeData.controllers;

import org.financeData.models.CashFlow;
import org.financeData.models.News;
import org.financeData.services.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {

	@Autowired
	private IStockService companyService;

	// localhost:8080/company_/stockNews?symbol=AAPL
	@GetMapping("/stockNews")
	public ResponseEntity<List<News>> getStockNews(
	        @RequestParam @NotBlank(message = "Symbol is required") String symbol) {
		return new ResponseEntity<>(companyService.getStockNews(symbol), HttpStatus.OK);
	}

	// localhost:8080/company_/cashFlow?symbol=AAPL
	@GetMapping("/cashFlow")
	public ResponseEntity<List<CashFlow>> getCompanyCashFlow(@RequestParam(required = true) String symbol) {
		return new ResponseEntity<>(companyService.getCompanyCashFlow(symbol), HttpStatus.OK);
	}
}
