package com.citi.services;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public class CitiService {

    public Map<String, List<String>> encontrarHeaders(HttpHeaders httpHeaders) {
        return httpHeaders.keySet().stream().collect(Collectors.toMap(Function.identity(),header-> httpHeaders.getValuesAsList(header)));
    }
    public Map<String, List<String>> encontrarHeaders(HttpHeaders httpHeaders, List<String> headerNames) {
        return httpHeaders.keySet().stream().filter(header->headerNames.contains(header))
        .collect(Collectors.toMap(Function.identity(),header-> httpHeaders.getValuesAsList(header)));        
    }
    
}
