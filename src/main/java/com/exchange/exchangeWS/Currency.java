package com.exchange.exchangews;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Currency {

    private final String code;
    public Map<String, Double> rates = new HashMap<>();

    public Currency(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public Map<String, Double> getRates() throws IOException {
        String code = this.code;
        String url = Constants.CONVERTER_URL + code + "/";
        String response = getResponseString(url);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response);
        rates.put(Constants.BUY_MAP_KEY, Double.parseDouble(jsonNode.get(Constants.RATES_JSON_NODE).get(0).get(Constants.BID_JSON_NODE).asText()));
        rates.put(Constants.SELL_MAP_KEY, Double.parseDouble(jsonNode.get(Constants.RATES_JSON_NODE).get(0).get(Constants.ASK_JSON_NODE).asText()));
        return this.rates;
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

        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        inputStream));

        while ((currentLine = in.readLine()) != null)
            response.append(currentLine);

        in.close();

        return response.toString();
    }

}
