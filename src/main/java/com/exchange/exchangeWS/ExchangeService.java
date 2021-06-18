package com.exchange.exchangeWS;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ExchangeService implements Exchange {

    public double calculateCommission (double value){
        value = value - ((value * 2) / 100);
        return value;
    }

    public JsonResult exchangeToPln(Currency currency, double value) throws IOException {
        JsonResult json = new JsonResult();
        currency.getRates();
        double rate = currency.rates.get("sell");
        double result = value * rate;
        result = calculateCommission(result);
        json.setReceivedValue(value);
        json.setExchangeValue(result);
        json.setBoughtCurrency("pln");
        json.setSoldCurrency(currency.code);
        return json;
    }

    public JsonResult exchangePlnToForeign(Currency currency, double value) throws IOException {
        JsonResult json = new JsonResult();
        currency.getRates();
        double rate = currency.rates.get("buy");
        double result = value / rate;
        result = calculateCommission(result);
        json.setReceivedValue(value);
        json.setExchangeValue(result);
        json.setSoldCurrency("pln");
        json.setBoughtCurrency(currency.code);
        return json;
    }
    //REQUEST MAPPING BY BODY TO DO:
    public JsonResult foreignCurrencyExchange(Currency SoldCurrency, Currency BoughtCurrency, double value) throws IOException {
        JsonResult json = new JsonResult();
        SoldCurrency.getRates();
        double firstCurrencyRate = SoldCurrency.rates.get("sell");
        double firstCurrencyToPln = value * firstCurrencyRate;
        BoughtCurrency.getRates();
        double secondCurrencyRate = BoughtCurrency.rates.get("buy");
        double result = firstCurrencyToPln / secondCurrencyRate;
        result = calculateCommission(result);
        json.setReceivedValue(value);
        json.setExchangeValue(result);
        json.setSoldCurrency(SoldCurrency.code);
        json.setBoughtCurrency(BoughtCurrency.code);
        return json;
    }

}

