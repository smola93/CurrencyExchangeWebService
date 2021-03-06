package com.exchange.exchangeWS.model;

import com.exchange.exchangeWS.util.Constants;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Currency {

    private final String code;
    private final Map<String, BigDecimal> rates = new HashMap<>();

    public Currency(String code) {
        this.code = code;
    }

    public Map<String, BigDecimal> getRates() {
        return rates;
    }

    public String getCode() {
        return code;
    }

    public void getExchangeRates() throws IOException {
        String currencyCode = this.code;
        String url = Constants.CONVERTER_URL + currencyCode + "/";
        String response = getResponseString(url);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response);
        rates.put(Constants.BUY_MAP_KEY, new BigDecimal(jsonNode.get(Constants.RATES_JSON_NODE).get(0).get(Constants.BID_JSON_NODE).asText()));
        rates.put(Constants.SELL_MAP_KEY, new BigDecimal(jsonNode.get(Constants.RATES_JSON_NODE).get(0).get(Constants.ASK_JSON_NODE).asText()));
    }

    String getResponseString(String url) throws IOException {
        String currentLine;
        InputStream inputStream;
        StringBuilder response = new StringBuilder();
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        int responseCode = connection.getResponseCode();

        if (HttpURLConnection.HTTP_OK == responseCode) {
            inputStream = connection.getInputStream();
        } else {
            inputStream = connection.getErrorStream();
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));

        while ((currentLine = in.readLine()) != null)
            response.append(currentLine);

        in.close();

        return response.toString();
    }
}
