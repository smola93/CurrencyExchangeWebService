package com.example.exchangeWS;

import com.exchange.exchangeWS.CurrencyExchangeWebServiceApplication;
import com.exchange.exchangeWS.model.Currency;
import com.exchange.exchangeWS.model.ExchangeRequestDto;
import com.exchange.exchangeWS.model.ExchangeResultDto;
import com.exchange.exchangeWS.util.Constants;
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
    void exchangeForeignToPlnTest() throws Exception {
        //Given
        BigDecimal testedValue = BigDecimal.valueOf(100);
        Currency currency = new Currency(Constants.EUR_CODE);
        currency.getExchangeRates();
        BigDecimal actualRate = currency.getRates().get(Constants.SELL_MAP_KEY);
        BigDecimal expectedValue = (testedValue.multiply(actualRate))
                .multiply(BigDecimal.valueOf(0.98)).setScale(2, RoundingMode.CEILING);

        //When
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/foreign-to-pln/eur/" + testedValue))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andReturn();
        ExchangeResultDto result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ExchangeResultDto.class);

        //Then
        Assertions.assertEquals(expectedValue, result.getExchangeValue());
    }

    @Test
    void exchangePlnToForeignTest() throws Exception {
        //Given
        BigDecimal testedValue = BigDecimal.valueOf(100);
        Currency currency = new Currency(Constants.EUR_CODE);
        currency.getExchangeRates();
        BigDecimal actualRate = currency.getRates().get(Constants.BUY_MAP_KEY);
        BigDecimal expectedValue = (testedValue.divide(actualRate, 2, RoundingMode.HALF_UP))
                .multiply(BigDecimal.valueOf(0.98)).setScale(2, RoundingMode.CEILING);

        //When
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/pln-to-foreign/eur/" + testedValue))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andReturn();
        ExchangeResultDto result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ExchangeResultDto.class);

        //Then
        Assertions.assertEquals(expectedValue, result.getExchangeValue());
    }

    @Test
    void exchangeForeignToForeignTest() throws Exception {
        //Given
        BigDecimal testedValue = BigDecimal.valueOf(200);
        Currency received = new Currency(Constants.EUR_CODE);
        Currency exchanged = new Currency(Constants.USD_CODE);
        received.getExchangeRates();
        BigDecimal receivedRate = received.getRates().get(Constants.SELL_MAP_KEY);
        BigDecimal receivedToPln = (testedValue.multiply(receivedRate)).multiply(BigDecimal.valueOf(0.98));
        exchanged.getExchangeRates();
        BigDecimal exchangedRate = exchanged.getRates().get(Constants.BUY_MAP_KEY);
        BigDecimal expectedValue = (receivedToPln.divide(exchangedRate, 2, RoundingMode.HALF_UP))
                .multiply(BigDecimal.valueOf(0.98)).setScale(2, RoundingMode.CEILING);

        //When
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/foreign-to-foreign")
                .contentType(MediaType.APPLICATION_JSON)
                .content(setRequestBody(testedValue)))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andReturn();
        ExchangeResultDto result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ExchangeResultDto.class);

        //Then
        Assertions.assertEquals(expectedValue, result.getExchangeValue());
    }

    //Setting request body for exchangeForeignToForeignTest()
    private String setRequestBody(BigDecimal value) throws Exception {
        ExchangeRequestDto request = new ExchangeRequestDto();
        request.setExchangeFrom(Constants.EUR_CODE);
        request.setExchangeTo(Constants.USD_CODE);
        request.setValue(value);
        return new ObjectMapper().writeValueAsString(request);
    }
}
