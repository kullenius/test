package com.technicalassignment.urlshortener.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.technicalassignment.urlshortener.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;


@RequestMapping("/")
@RestController
public class Controller {

    @Autowired
    UrlShortenerService urlService = new UrlShortenerService();


    @GetMapping("{id}")
    public RedirectView redirectShortUrl(@PathVariable(value = "id") String id) {
        return urlService.redirectToOrgUrl(id);
    }

    @PostMapping("add")
    public String create(@RequestBody String request) throws JsonProcessingException {
        return urlService.createShortUrl(request);
    }
}