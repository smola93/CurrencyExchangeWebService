package com.example.exchangeWS;

import com.exchange.exchangews.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.math.RoundingMode;

@SpringBootTest(classes = CurrencyExchangeWebServiceApplication.class)
@AutoConfigureMockMvc
class CurrencyExchangeWebServiceApplicationTests {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void contextLoads() {
    }

    @Test
    void exchangeToPlnTest() throws Exception {
        //Given
        BigDecimal testedValue = BigDecimal.valueOf(100);
        Currency currency = new Currency(Constants.EUR_CODE);
        currency.getRates();
        BigDecimal actualRate = currency.rates.get(Constants.SELL_MAP_KEY);
        BigDecimal expectedValue = (testedValue.multiply(actualRate)).multiply(BigDecimal.valueOf(0.98));
        //When
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/foreign-to-pln/eur/" + testedValue))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andReturn();
        JsonResult jsonResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), JsonResult.class);
        //Then
        Assertions.assertEquals(expectedValue, jsonResult.getExchangeValue());
    }

    @Test
    void exchangePlnToForeignTest() throws Exception {
        //Given
        BigDecimal testedValue = BigDecimal.valueOf(100);
        Currency currency = new Currency(Constants.EUR_CODE);
        currency.getRates();
        BigDecimal actualRate = currency.rates.get(Constants.BUY_MAP_KEY);
        BigDecimal expectedValue = (testedValue.divide(actualRate, 2, RoundingMode.HALF_UP)).multiply(BigDecimal.valueOf(0.98));
        //When
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/pln-to-foreign/eur/" + testedValue))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andReturn();
        JsonResult jsonResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), JsonResult.class);
        //Then
        Assertions.assertEquals(expectedValue, jsonResult.getExchangeValue());
    }

    @Test
    void foreignCurrencyExchangeTest() throws Exception {
        //Given
        BigDecimal testedValue = BigDecimal.valueOf(200);
        Currency received = new Currency(Constants.EUR_CODE);
        Currency exchanged = new Currency(Constants.USD_CODE);
        received.getRates();
        BigDecimal receivedRate = received.rates.get(Constants.SELL_MAP_KEY);
        BigDecimal receivedToPln = (testedValue.multiply(receivedRate)).multiply(BigDecimal.valueOf(0.98));
        exchanged.getRates();
        BigDecimal exchangedRate = exchanged.rates.get(Constants.BUY_MAP_KEY);
        BigDecimal expectedValue = (receivedToPln.divide(exchangedRate, 2, RoundingMode.HALF_UP)).multiply(BigDecimal.valueOf(0.98));
        //When
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/foreign-to-foreign")
                .contentType(MediaType.APPLICATION_JSON)
                .content(setRequestBody(testedValue)))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andReturn();
        JsonResult jsonResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), JsonResult.class);
        //Then
        Assertions.assertEquals(expectedValue, jsonResult.getExchangeValue());
    }

    //Setting request body for foreignCurrencyExchangeTest()
    private String setRequestBody(BigDecimal value) throws Exception {
        JsonRequest request = new JsonRequest();
        request.setExchangeFrom(Constants.EUR_CODE);
        request.setExchangeTo(Constants.USD_CODE);
        request.setValue(value);
        return new ObjectMapper().writeValueAsString(request);
    }
}
