package com.schedular.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class CountryController {

    @PostMapping
    public String allCountries() {
        return "post countries";
    }
    @GetMapping
    public String getCountryDetailes() {
        return "get countries";
    }
}
