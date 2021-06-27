package com.exchange.exchangews.util;

public final class Constants {
    //currencies
    public static final String PLN_CODE = "pln";
    public static final String GBP_CODE = "gbp";
    public static final String USD_CODE = "usd";
    public static final String EUR_CODE = "eur";

    //rates map
    public static final String BUY_MAP_KEY = "buy";
    public static final String SELL_MAP_KEY = "sell";

    //NBP service
    public static final String CONVERTER_URL = "http://api.nbp.pl/api/exchangerates/rates/c/";

    //NBP response nodes
    public static final String RATES_JSON_NODE = "rates";
    public static final String BID_JSON_NODE = "bid";
    public static final String ASK_JSON_NODE = "ask";

    //Messages
    public static final String VALIDATION_INFO = "Allows exchange foreign currency to Polish Zloty";
    public static final String VALIDATION_CODES = "Remember we support only EUR, GBP, USD and PLN";
    public static final String PARAM_CURRENCY_INFO = "code of currency, for example \\\"usd\\\"";
    public static final String PARAM_VALUE_INFO = "value to exchange";
}
