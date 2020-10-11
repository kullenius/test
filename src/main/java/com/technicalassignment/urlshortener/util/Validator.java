package com.technicalassignment.urlshortener.util;

import com.technicalassignment.urlshortener.exception.InternalServerException;
import com.technicalassignment.urlshortener.repository.UrlAddressRepository;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Validator {
    private static final String URL_REGEX = "[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
    private UrlValidator urlValidator = new UrlValidator(new String[]{"http", "https"});

    private static boolean isMatch(String url, String regEx) {
        try {
            Pattern p = Pattern.compile(regEx);
            Matcher matcher = p.matcher(url);
            return matcher.matches();
        } catch (RuntimeException e) {
            throw new InternalServerException(Message.INTERNAL_ERROR_MATCHER);
        }
    }

    public boolean isValid(String url) {
        return urlValidator.isValid(url) && isMatch(url, URL_REGEX);
    }

    public boolean idAlreadyExist(String id, UrlAddressRepository urlAddressRepository) {
        if (id != null && urlAddressRepository != null) {
            return urlAddressRepository.findById(id).isPresent();
        } else {
            return false;
        }
    }
}
