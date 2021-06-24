package com.exchange.exchangeWS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class ExchangeController {

    @Autowired
    private ExchangeService exchangeService;

    @RequestMapping(value = "/foreign-to-pln/{currency}/{value}", method = RequestMethod.GET)

    public JsonResult toPLN(@PathVariable("currency") String currency, @PathVariable("value") double value) throws IOException {
        exchangeService.valueValidation(value);
        if (exchangeService.currencyValidation(currency)) {
            Currency cur = new Currency(currency);
            return exchangeService.exchangeToPln(cur, value);
        } else
            throw new IOException("Currency " + currency + " is not supported by our service. " +
                    "We support only British Pounds (gbp), United States Dollars (usd), Polish Zloty (pln) and Euro (eur).");
    }

    @RequestMapping(value = "/pln-to-foreign/{currency}/{value}", method = RequestMethod.GET)

    public JsonResult toForeign(@PathVariable("currency") String currency, @PathVariable("value") double value) throws IOException {
        exchangeService.valueValidation(value);
        if (exchangeService.currencyValidation(currency)) {
            Currency cur = new Currency(currency);
            return exchangeService.exchangePlnToForeign(cur, value);
        } else
            throw new IOException("Currency " + currency + " is not supported by our service. " +
                    "We support only British Pounds (gbp), United States Dollars (usd), Polish Zloty (pln) and Euro (eur).");
    }

    @RequestMapping(value = "/foreign-to-foreign", method = RequestMethod.POST)

    public JsonResult foreignToForeign(@RequestBody JsonRequest request) throws IOException {
        exchangeService.valueValidation(request.value);
        request.jsonRequestValidation();
        if (request.exchangeFrom.equals("pln") || request.exchangeTo.equals("pln")) {
            throw new IOException("For Polish Zloty exchange please use another endpoints. See documentation for more details.");
        }
        if (exchangeService.currencyValidation(request.exchangeFrom) && exchangeService.currencyValidation(request.exchangeTo)) {
            Currency from = new Currency(request.exchangeFrom);
            Currency to = new Currency(request.exchangeTo);
            return exchangeService.foreignCurrencyExchange(from, to, request.value);
        } else
            throw new IOException("One of your currencies is not supported by our service. Are you sure you provided correct ones? " +
                    "We support only British Pounds (gbp), United States Dollars (usd), Polish Zloty (pln) and Euro (eur).");
    }
}