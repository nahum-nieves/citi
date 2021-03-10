package com.citi.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.citi.services.CitiService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CitiControllerTests {
    
    @Autowired
	private CitiService citiService;
    @Autowired
	private CitiController citiController;

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
    

    @Test
    public void verifyServiceInjection(){
        assertThat(citiService).isNotNull();
    }
    @Test
    public void verifyControllerInjection(){
        assertThat(citiService).isNotNull();
    }

    @Test
	public void heaadersShouldReturnMap() throws Exception {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/headers");
        MultiValueMap<String, String> headersMap =  new LinkedMultiValueMap();
        List<String> headersList = new ArrayList();
        headersList.add("value1");
        headersList.add("value2");
        headersMap.addAll("one-header", headersList);
        List<String> headersList2 = new ArrayList<>();
        headersList2.add("value");
        headersMap.add("another-header", "value");
        HttpEntity<String> request = new HttpEntity<>(headersMap);
        ResponseEntity<Map<String, List<String>>> exchange = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, request, new ParameterizedTypeReference<Map<String, List<String>>>(){});
        assertAll(()->assertEquals(headersList, exchange.getBody().get("one-header")),
        ()-> assertEquals(headersList2, exchange.getBody().get("another-header")));
        
       
	}

    @Test
	public void heaadersShouldReturnFilterMap() throws Exception {
        //These are the header names will be in the response
        List<String> filterHeaders = new ArrayList<>();
        filterHeaders.add("filtered-header");
        filterHeaders.add("another-filtered-header");

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/headers-filter")
        .queryParam("headerNames", String.join(",", filterHeaders));
        //These are garbage to fullfill the headers map
        MultiValueMap<String, String> headersMap =  new LinkedMultiValueMap();
        List<String> headersList = new ArrayList();
        headersList.add("value1");
        headersList.add("value2");
        headersMap.addAll("one-header", headersList);
        List<String> headersList2 = new ArrayList<>();
        headersList2.add("value");
        headersMap.add("another-header", "value");
        headersMap.add("filter-header", "value");
        //This is a variable to add a value at the end of the header's value
        for(int i =0;i<filterHeaders.size();i++){
            headersMap.add(filterHeaders.get(i), "value" +i);
            headersMap.add(filterHeaders.get(i), i+"value");
        } 
        HttpEntity<String> request = new HttpEntity<>(headersMap);
        ResponseEntity<Map<String, List<String>>> exchange = 
        restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, request, new ParameterizedTypeReference<Map<String, List<String>>>(){});
        assertAll(()->assertEquals(exchange.getBody().size(), filterHeaders.size()),
        ()->assertEquals(headersMap.get("filtered-header"), exchange.getBody().get("filtered-header")));
        
        
	}

    @Test
    public void throwMessageExceptionWhenParamIsMissing(){
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/headers-filter");
        ResponseEntity<String> s= restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET , null, String.class);
        assertEquals(s.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
