package com.eshop.service.impl;

import com.eshop.model.Category;
import com.eshop.model.Manufacturer;
import com.eshop.model.Product;
import com.eshop.model.dto.ProductDto;
import com.eshop.model.events.ProductCreatedEvent;
import com.eshop.model.exceptions.CategoryNotFoundException;
import com.eshop.model.exceptions.ManufacturerNotFoundException;
import com.eshop.model.exceptions.ProductNotFoundException;
import com.eshop.repository.jpa.CategoryRepository;
import com.eshop.repository.jpa.ManufacturerRepository;
import com.eshop.repository.jpa.ProductRepository;
import com.eshop.repository.views.ProductsPerManufacturerViewRepository;
import com.eshop.service.ProductService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final ProductsPerManufacturerViewRepository productsPerManufacturerViewRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, ManufacturerRepository manufacturerRepository, ProductsPerManufacturerViewRepository productsPerManufacturerViewRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.productsPerManufacturerViewRepository = productsPerManufacturerViewRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }


    @Override
    public List<Product> findAll() {
        return this.productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return this.productRepository.findById(id);
    }

    @Override
    public Optional<Product> findByName(String name) {
        return this.productRepository.findByName(name);
    }

    @Override
    @Transactional
    public Optional<Product> save(String name, Double price, Integer quantity, Long categoryId, Long manufacturerId) {
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
        Manufacturer manufacturer = this.manufacturerRepository.findById(manufacturerId)
                .orElseThrow(() -> new ManufacturerNotFoundException(manufacturerId));

        this.productRepository.deleteByName(name);
        Product product = new Product(name, price, quantity, category, manufacturer);
        this.productRepository.save(product);
        //this.refreshMaterializedView();

        this.applicationEventPublisher.publishEvent(new ProductCreatedEvent(product));
        return Optional.of(product);
    }

    @Override
    public Optional<Product> save(ProductDto productDto) {
        Category category = this.categoryRepository.findById(productDto.getCategory())
                .orElseThrow(() -> new CategoryNotFoundException(productDto.getCategory()));
        Manufacturer manufacturer = this.manufacturerRepository.findById(productDto.getManufacturer())
                .orElseThrow(() -> new ManufacturerNotFoundException(productDto.getManufacturer()));

        this.productRepository.deleteByName(productDto.getName());
        Product product = new Product(productDto.getName(), productDto.getPrice(), productDto.getQuantity(), category, manufacturer);
        this.productRepository.save(product);
        //this.refreshMaterializedView();

        this.applicationEventPublisher.publishEvent(new ProductCreatedEvent(product));
        return Optional.of(product);
    }

    @Override
    @Transactional
    public Optional<Product> edit(Long id, String name, Double price, Integer quantity, Long categoryId, Long manufacturerId) {
        Product product = this.productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);

        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
        product.setCategory(category);

        Manufacturer manufacturer = this.manufacturerRepository.findById(manufacturerId)
                .orElseThrow(() -> new ManufacturerNotFoundException(manufacturerId));
        product.setManufacturer(manufacturer);

        this.productRepository.save(product);
        //this.refreshMaterializedView();

        return Optional.of(product);
    }

    @Override
    public Optional<Product> edit(Long id, ProductDto productDto) {
        Product product = this.productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());

        Category category = this.categoryRepository.findById(productDto.getCategory())
                .orElseThrow(() -> new CategoryNotFoundException(productDto.getCategory()));
        product.setCategory(category);

        Manufacturer manufacturer = this.manufacturerRepository.findById(productDto.getManufacturer())
                .orElseThrow(() -> new ManufacturerNotFoundException(productDto.getManufacturer()));
        product.setManufacturer(manufacturer);

        this.productRepository.save(product);
        //this.refreshMaterializedView();

        return Optional.of(product);
    }

    @Override
    public void deleteById(Long id) {
        this.productRepository.deleteById(id);
    }

    @Override
    public void refreshMaterializedView() {
        this.productsPerManufacturerViewRepository.refreshMaterializedView();
    }
}
