package com.eshop.repository.impl;

import com.eshop.bootstrap.DataHolder;
import com.eshop.model.Manufacturer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryManufacturerRepository {

    public List<Manufacturer> findAll() {
        return DataHolder.manufacturers;
    }

    public Optional<Manufacturer> findById(Long id) {
        return DataHolder.manufacturers.stream().filter(r -> r.getId().equals(id)).findFirst();
    }

    public Optional<Manufacturer> findByName(String name) {
        return DataHolder.manufacturers.stream().filter(r -> r.getName().equals(name)).findFirst();
    }

    public Optional<Manufacturer> save(String name, String address) {
        DataHolder.manufacturers.removeIf(r -> r.getName().equals(name));
        Manufacturer manufacturer = new Manufacturer(name, address);
        DataHolder.manufacturers.add(manufacturer);
        return Optional.of(manufacturer);
    }

    public boolean deleteById(Long id) {
        return DataHolder.manufacturers.removeIf(r -> r.getId().equals(id));
    }

    public void deleteByName(String name) {
        DataHolder.manufacturers.removeIf(r -> r.getName().equals(name));
    }
}
