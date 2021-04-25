package com.eshop.repository.impl;

import com.eshop.bootstrap.DataHolder;
import com.eshop.model.Category;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class InMemoryCategoryRepository {

    public List<Category> findAll() {
        return DataHolder.categories;
    }

    public Category save(Category category) {
        if (category == null || category.getName().isEmpty()) {
            return null;
        }
        DataHolder.categories.removeIf(r -> r.getName().equals(category.getName()));
        DataHolder.categories.add(category);
        return category;
    }

    public Optional<Category> findById(Long id) {
        return DataHolder.categories.stream().filter(r -> r.getId().equals(id)).findFirst();
    }

    public List<Category> searchCategories(String searchText) {
        return DataHolder.categories.stream().filter(r -> r.getName().contains(searchText) || r.getDescription().equals(searchText)).collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        DataHolder.categories.removeIf(r -> r.getId().equals(id));
    }

    public void deleteByName(String name) {
        DataHolder.categories.removeIf(r -> r.getName().equals(name));
    }
}
