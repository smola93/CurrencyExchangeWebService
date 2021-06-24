package com.example.exchangeWS;

import com.exchange.exchangeWS.Currency;
import com.exchange.exchangeWS.CurrencyExchangeWebServiceApplication;
import com.exchange.exchangeWS.JsonRequest;
import com.exchange.exchangeWS.JsonResult;
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
        Currency currency = new Currency("eur");
        currency.getRates();
        double actualRate = currency.rates.get("sell");
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
        Currency currency = new Currency("eur");
        currency.getRates();
        double actualRate = currency.rates.get("buy");
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
        Currency received = new Currency("eur");
        Currency exchanged = new Currency("usd");
        received.getRates();
        double receivedRate = received.rates.get("sell");
        double receivedToPln = (testedValue * receivedRate) * 0.98;
        exchanged.getRates();
        double exchangedRate = exchanged.rates.get("buy");
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
        request.setExchangeFrom("eur");
        request.setExchangeTo("usd");
        request.setValue(value);
        return new ObjectMapper().writeValueAsString(request);
    }
}
