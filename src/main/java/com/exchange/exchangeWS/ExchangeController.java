package com.exchange.exchangews;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;

@RestController
public class ExchangeController {

    @Autowired
    private ExchangeService exchangeService;

    @Autowired
    private DataValidatorService dataValidator;

    @RequestMapping(value = "/foreign-to-pln/{currency}/{value}", method = RequestMethod.GET)
    public JsonResult convertForeignToPln(@PathVariable("currency") String currency, @PathVariable("value") BigDecimal value) throws IOException {
        dataValidator.valueValidation(value);
        if (dataValidator.currencyValidation(currency)) {
            Currency currencyInternal = new Currency(currency);
            return exchangeService.exchangeForeignToPln(currencyInternal, value);
        } else
            throw new IOException("Currency " + currency + " is not supported by our service. " +
                    "We support only British Pounds (gbp), United States Dollars (usd), Polish Zloty (pln) and Euro (eur).");
    }

    @RequestMapping(value = "/pln-to-foreign/{currency}/{value}", method = RequestMethod.GET)
    public JsonResult convertPlnToForeign(@PathVariable("currency") String currency, @PathVariable("value") BigDecimal value) throws IOException {
        dataValidator.valueValidation(value);
        if (dataValidator.currencyValidation(currency)) {
            Currency currencyInternal = new Currency(currency);
            return exchangeService.exchangePlnToForeign(currencyInternal, value);
        } else
            throw new IOException("Currency " + currency + " is not supported by our service. " +
                    "We support only British Pounds (gbp), United States Dollars (usd), Polish Zloty (pln) and Euro (eur).");
    }

    @RequestMapping(value = "/foreign-to-foreign", method = RequestMethod.POST)
    public JsonResult convertForeignToForeign(@RequestBody JsonRequest request) throws IOException {
        dataValidator.valueValidation(request.getValue());
        request.jsonRequestValidation();

        if (Constants.PLN_CODE.equals(request.getExchangeFrom()) || Constants.PLN_CODE.equals(request.getExchangeTo())) {
            throw new IOException("For Polish Zloty exchange please use another endpoints. See documentation for more details.");
        }
        if (dataValidator.currencyValidation(request.getExchangeFrom()) && dataValidator.currencyValidation(request.getExchangeFrom())) {
            Currency inputCurrency = new Currency(request.getExchangeFrom());
            Currency outputCurrency = new Currency(request.getExchangeTo());
            return exchangeService.exchangeForeignToForeign(inputCurrency, outputCurrency, request.getValue());
        } else
            throw new IOException("One of your currencies is not supported by our service. Are you sure you provided correct ones? " +
                    "We support only British Pounds (gbp), United States Dollars (usd), Polish Zloty (pln) and Euro (eur).");
    }
}