package com.eshop.service;

import com.eshop.model.Category;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> listCategories();

    Category create(String name, String description);

    Category update(String name, String description);

    List<Category> search(String text);

    Optional<Category> findById(Long id);

    void deleteById(Long id);

    void deleteByName(String name);
}
