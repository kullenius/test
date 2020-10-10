package com.technicalassignment.urlshortener.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
public class UrlAddress {
    @Id
    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String url;

}
