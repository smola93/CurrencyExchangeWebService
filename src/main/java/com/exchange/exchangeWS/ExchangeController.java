package com.exchange.exchangeWS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class ExchangeController {

    @Autowired
    private ExchangeService exchangeService;

    @RequestMapping(value = "/to_pln/{currency}/{value}", method = RequestMethod.GET)

    public JsonResult toPLN(@PathVariable("currency") String currency, @PathVariable("value") double value) throws IOException {
        Currency cur = new Currency(currency);
        return exchangeService.exchangeToPln(cur, value);
    }

    @RequestMapping(value = "/pln_to_foreign/{currency}/{value}", method = RequestMethod.GET)

    public JsonResult toForeign(@PathVariable("currency") String currency, @PathVariable("value") double value) throws IOException {
        Currency cur = new Currency(currency);
        return exchangeService.exchangePlnToForeign(cur, value);
    }
}