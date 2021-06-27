package com.exchange.exchangews.controller;

import com.exchange.exchangews.util.Constants;
import com.exchange.exchangews.service.DataValidatorService;
import com.exchange.exchangews.service.ExchangeService;
import com.exchange.exchangews.model.Currency;
import com.exchange.exchangews.model.ExchangeRequestDto;
import com.exchange.exchangews.model.ExchangeResultDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;

@RestController
public class ExchangeController {

    @Autowired
    private ExchangeService exchangeService;

    @Autowired
    private DataValidatorService dataValidator;

    @ApiOperation(value = Constants.VALIDATION_INFO, notes = Constants.VALIDATION_CODES)
    @GetMapping(value = "/foreign-to-pln/{currency}/{value}")
    public ExchangeResultDto convertForeignToPln(@ApiParam(value = Constants.PARAM_CURRENCY_INFO)
                                                 @PathVariable("currency") String currency,
                                                 @ApiParam(value = Constants.PARAM_VALUE_INFO)
                                                 @PathVariable("value") BigDecimal value) throws IOException {
        dataValidator.validateValue(value);
        dataValidator.validateCurrency(currency);
        dataValidator.validatePln(currency);
        Currency currencyInternal = new Currency(currency);
        return exchangeService.exchangeForeignToPln(currencyInternal, value);
    }

    @ApiOperation(value = Constants.VALIDATION_INFO, notes = Constants.VALIDATION_CODES)
    @GetMapping(value = "/pln-to-foreign/{currency}/{value}")
    public ExchangeResultDto convertPlnToForeign(@ApiParam(value = Constants.PARAM_CURRENCY_INFO)
                                                 @PathVariable("currency") String currency,
                                                 @ApiParam(value = Constants.PARAM_VALUE_INFO)
                                                 @PathVariable("value") BigDecimal value) throws IOException {
        dataValidator.validateValue(value);
        dataValidator.validateCurrency(currency);
        dataValidator.validatePln(currency);
        Currency currencyInternal = new Currency(currency);
        return exchangeService.exchangePlnToForeign(currencyInternal, value);
    }

    @ApiOperation(value = "Exchanging foreign currency to another foreign currency", notes = "Remember we support only EUR, GBP, USD and PLN")
    @PostMapping(value = "/foreign-to-foreign")
    public ExchangeResultDto convertForeignToForeign(@RequestBody ExchangeRequestDto request) throws IOException {
        dataValidator.validateValue(request.getValue());
        request.jsonRequestValidation();

        if (Constants.PLN_CODE.equals(request.getExchangeFrom()) || Constants.PLN_CODE.equals(request.getExchangeTo())) {
            throw new IOException("For Polish Zloty exchange please use another endpoints. See documentation for more details.");
        }

        dataValidator.validateCurrency(request.getExchangeTo());
        dataValidator.validateCurrency(request.getExchangeFrom());
        Currency inputCurrency = new Currency(request.getExchangeFrom());
        Currency outputCurrency = new Currency(request.getExchangeTo());
        return exchangeService.exchangeForeignToForeign(inputCurrency, outputCurrency, request.getValue());
    }
}