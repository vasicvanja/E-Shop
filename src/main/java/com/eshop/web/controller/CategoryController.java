package com.eshop.web.controller;

import com.eshop.model.Category;
import com.eshop.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String getCategoryPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        model.addAttribute("categories", this.categoryService.listCategories());
        model.addAttribute("bodyContent", "categories");
        return "master-template";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        this.categoryService.deleteById(id);
        return "redirect:/categories";
    }

    @GetMapping("/edit-form/{id}")
    public String editCategoryPage(@PathVariable Long id, Model model) {
        if (this.categoryService.findById(id).isPresent()) {
            Category category = this.categoryService.findById(id).get();
            model.addAttribute("category", category);
            model.addAttribute("bodyContent", "add-category");
            return "master-template";

        }
        return "redirect:/categories?error=CategoryNotFoundException";
    }

    @GetMapping("/add-form")
    public String addCategoryPage(Model model) {
        List<Category> categories = this.categoryService.listCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("bodyContent", "add-category");
        return "master-template";
    }

    @PostMapping("/add")
    public String saveCategory(@RequestParam String name,
                               @RequestParam String description) {

        this.categoryService.create(name, description);
        return "redirect:/categories";
    }
}
