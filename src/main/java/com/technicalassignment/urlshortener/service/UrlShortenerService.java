package com.technicalassignment.urlshortener.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.hash.Hashing;
import com.technicalassignment.urlshortener.exception.InternalServerException;
import com.technicalassignment.urlshortener.exception.NotFoundException;
import com.technicalassignment.urlshortener.model.UrlAddress;
import com.technicalassignment.urlshortener.repository.UrlAddressRepository;
import com.technicalassignment.urlshortener.util.Message;
import com.technicalassignment.urlshortener.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import java.nio.charset.StandardCharsets;

@Service
public class UrlShortenerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UrlShortenerService.class);
    private ObjectMapper objectMapper = new ObjectMapper();
    private Validator validator = new Validator();

    @Autowired
    private UrlAddressRepository urlAddressRepository;

    public String createShortUrl(String body) throws JsonProcessingException {
        UrlAddress urlAddress = objectMapper.readValue(body, UrlAddress.class);
        LOGGER.info("Received URL to be shortened: " + urlAddress.getUrl());
        try {
            validator.isValid(urlAddress.getUrl());
            String id = Hashing.murmur3_32().hashString(urlAddress.getUrl(), StandardCharsets.UTF_8).toString();

            if (validator.idAlreadyExist(id, urlAddressRepository)) {
                UrlAddress existingAddress = urlAddressRepository.findById(id).orElseThrow(() -> new InternalServerException(Message.INTERNAL_ERROR + id));
                LOGGER.info("Id already exist for URL. Returning existing id.");
                return objectMapper.writeValueAsString(existingAddress);
            }
            urlAddress.setId(id);
            urlAddressRepository.save(urlAddress);
            LOGGER.info("URL was successfully shortened to: " + id);
            return objectMapper.writeValueAsString(urlAddress);
        } catch (RuntimeException e) {
            throw new NotFoundException(Message.INVALID_ID_ERROR);
        }
    }

    public RedirectView redirectToOrgUrl(String id) {
        RedirectView redirectView = new RedirectView();
        LOGGER.info("Id received: " + id);
        UrlAddress urlAddress = urlAddressRepository.findById(id).orElseThrow(() -> new NotFoundException(Message.URL_NOT_FOUND_ERROR+ id));
        LOGGER.info("URL from Id was found. Original URL: " + urlAddress.getUrl());
        redirectView.setUrl(urlAddress.getUrl());
        return redirectView;
    }

}
