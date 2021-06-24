package com.exchange.exchangeWS;

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

    public final String code;
    public Map<String, Double> rates = new HashMap<>();

    public Currency(String code) {
        this.code = code;
    }

    public Map<String, Double> getRates() throws IOException {
        String code = this.code;
        String url = "http://api.nbp.pl/api/exchangerates/rates/c/" + code + "/";
        String response = getResponseString(url);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response);
        rates.put("buy", Double.parseDouble(jsonNode.get("rates").get(0).get("bid").asText()));
        rates.put("sell", Double.parseDouble(jsonNode.get("rates").get(0).get("ask").asText()));
        return this.rates;
    }

    String getResponseString(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        int responseCode = connection.getResponseCode();
        InputStream inputStream;
        if (200 <= responseCode && responseCode <= 299) {
            inputStream = connection.getInputStream();
        } else {
            inputStream = connection.getErrorStream();
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder response = new StringBuilder();
        String currentLine;
        while ((currentLine = in.readLine()) != null)
            response.append(currentLine);
        in.close();
        return response.toString();
    }
}
