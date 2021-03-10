package com.citi.api;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.citi.services.CitiService;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CitiController {

    private final CitiService citiService;

    @GetMapping(value = "/headers")
    public Map<String,List<String>> encontrarHeaders(@RequestHeader HttpHeaders httpHeaders){
        return citiService.encontrarHeaders(httpHeaders);
    }

    @GetMapping(value = "/headers-filter")
    public Map<String,List<String>> filtrarHeaders(@RequestHeader HttpHeaders httpHeaders,@RequestParam(value = "headerNames",required = false) List<String> headerNames){
        Objects.requireNonNull(headerNames, "You have to send headerNames param");
        return citiService.encontrarHeaders(httpHeaders, headerNames);
    }
}
