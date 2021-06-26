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
        //Arrange
        double testedValue = 100;
        Currency currency = new Currency(Constants.EUR_CODE);
        currency.getRates();
        double actualRate = currency.rates.get(Constants.SELL_MAP_KEY);
        double expectedValue = (testedValue * actualRate) * 0.98;
        //Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/foreign-to-pln/eur/" + testedValue))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andReturn();
        JsonResult jsonResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), JsonResult.class);
        //Assert
        Assertions.assertEquals(expectedValue, jsonResult.getExchangeValue(), 0.001);
    }

    @Test
    void exchangePlnToForeignTest() throws Exception {
        //Arrange
        double testedValue = 100;
        Currency currency = new Currency(Constants.EUR_CODE);
        currency.getRates();
        double actualRate = currency.rates.get(Constants.BUY_MAP_KEY);
        double expectedValue = (testedValue / actualRate) * 0.98;
        //Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/pln-to-foreign/eur/" + testedValue))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andReturn();
        JsonResult jsonResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), JsonResult.class);
        //Assert
        Assertions.assertEquals(expectedValue, jsonResult.getExchangeValue(), 0.001);
    }

    @Test
    void foreignCurrencyExchangeTest() throws Exception {
        //Arrange
        double testedValue = 200;
        Currency received = new Currency(Constants.EUR_CODE);
        Currency exchanged = new Currency(Constants.USD_CODE);
        received.getRates();
        double receivedRate = received.rates.get(Constants.SELL_MAP_KEY);
        double receivedToPln = (testedValue * receivedRate) * 0.98;
        exchanged.getRates();
        double exchangedRate = exchanged.rates.get(Constants.BUY_MAP_KEY);
        double expectedValue = (receivedToPln / exchangedRate) * 0.98;
        //Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/foreign-to-foreign")
                .contentType(MediaType.APPLICATION_JSON)
                .content(setRequestBody(testedValue)))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andReturn();
        JsonResult jsonResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), JsonResult.class);
        //Assert
        Assertions.assertEquals(expectedValue, jsonResult.getExchangeValue(), 0.001);
    }

    //Setting request body for foreignCurrencyExchangeTest()
    private String setRequestBody(double value) throws Exception {
        JsonRequest request = new JsonRequest();
        request.setExchangeFrom(Constants.EUR_CODE);
        request.setExchangeTo(Constants.USD_CODE);
        request.setValue(value);
        return new ObjectMapper().writeValueAsString(request);
    }
}
