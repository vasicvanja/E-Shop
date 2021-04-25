package com.eshop.service.impl;

import com.eshop.model.Category;
import com.eshop.repository.jpa.CategoryRepository;
import com.eshop.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> listCategories() {
        return this.categoryRepository.findAll();
    }

    @Override
    public Category create(String name, String description) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
        Category category = new Category(name, description);
        this.categoryRepository.save(category);
        return category;
    }

    @Override
    public Category update(String name, String description) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
        Category category = new Category(name, description);
        this.categoryRepository.save(category);
        return category;
    }

    @Override
    public List<Category> search(String text) {
        return this.categoryRepository.findAllByNameLike(text);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return this.categoryRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        this.categoryRepository.deleteById(id);
    }

    @Override
    public void deleteByName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.categoryRepository.deleteByName(name);
    }
}
