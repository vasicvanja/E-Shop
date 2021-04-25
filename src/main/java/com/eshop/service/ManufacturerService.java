package com.eshop.service;

import com.eshop.model.Manufacturer;

import java.util.List;
import java.util.Optional;

public interface ManufacturerService {

    List<Manufacturer> findAll();

    Optional<Manufacturer> findById(Long id);

    List<Manufacturer> findByName(String name);

    Optional<Manufacturer> save(String name, String address);

    void deleteById(Long id);
}
