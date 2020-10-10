package com.technicalassignment.urlshortener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


/*@SpringBootApplication
public class UrlShortenerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UrlShortenerApplication.class, args);
    }
}*/

@SpringBootApplication
@RestController
@RibbonClient(
        name = "ping-a-server",
        configuration = RibbonConfiguration.class)
public class ServerLocationApp {

    @LoadBalanced
    @Bean
    RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/server-location")
    public String serverLocation() {
        return this.restTemplate.getForObject(
                "http://ping-server/locaus", String.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ServerLocationApp.class, args);
    }
}