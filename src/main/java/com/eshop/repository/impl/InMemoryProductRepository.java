package com.eshop.repository.impl;

import com.eshop.bootstrap.DataHolder;
import com.eshop.model.Category;
import com.eshop.model.Manufacturer;
import com.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryProductRepository {

    public List<Product> findAll() {
        return DataHolder.products;
    }

    public Optional<Product> findById(Long id) {
        return DataHolder.products.stream().filter(r -> r.getId().equals(id)).findFirst();
    }

    public Optional<Product> findByName(String name) {
        return DataHolder.products.stream().filter(r -> r.getName().equals(name)).findFirst();
    }

    public Optional<Product> save(String name, Double price, Integer quantity, Category category, Manufacturer manufacturer) {
        DataHolder.products.removeIf(r -> r.getName().equals(name));
        Product product = new Product(name, price, quantity, category, manufacturer);
        DataHolder.products.add(product);
        return Optional.of(product);
    }

    public void deleteById(Long id) {
        DataHolder.products.removeIf(r -> r.getId().equals(id));
    }

    public void deleteByName(String name) {
        DataHolder.products.removeIf(r -> r.getName().equals(name));
    }
}
