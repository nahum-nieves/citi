package com.citi.services;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;

@Service
@Log
public class CitiService {

    public Map<String, List<String>> encontrarHeaders(HttpHeaders httpHeaders) {
        log.info("Collecting headers");
        return httpHeaders.keySet().stream().collect(Collectors.toMap(Function.identity(),header-> httpHeaders.getValuesAsList(header)));
    }
    public Map<String, List<String>> encontrarHeaders(HttpHeaders httpHeaders, List<String> headerNames) {
        log.info("Filtering and collecting headers");
        return httpHeaders.keySet().stream().filter(header->headerNames.contains(header))
        .collect(Collectors.toMap(Function.identity(),header-> httpHeaders.getValuesAsList(header)));        
    }
    
}
