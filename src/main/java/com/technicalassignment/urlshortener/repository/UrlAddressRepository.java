package com.technicalassignment.urlshortener.repository;

import com.technicalassignment.urlshortener.model.UrlAddress;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlAddressRepository extends CrudRepository<UrlAddress, String> {

}
