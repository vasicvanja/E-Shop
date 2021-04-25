package com.eshop.bootstrap;

import com.eshop.model.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {

    public static List<Category> categories = new ArrayList<>();
    public static List<User> users = new ArrayList<>();
    public static List<Manufacturer> manufacturers = new ArrayList<>();
    public static List<Product> products = new ArrayList<>();
    public static List<ShoppingCart> shoppingCarts = new ArrayList<>();

//    @PostConstruct
//    public void init() {
//
//        categories.add(new Category("Movies", "Movies category"));
//        categories.add(new Category("Books", "Books category"));
//        categories.add(new Category("Software", "Software category"));
//
//        users.add(new User("vasicvanja","vv","Vanja","Vasic"));
//        users.add(new User("vasictoni","vt","Toni","Vasic"));
//
//        Category category = new Category("Sport", "Sports category");
//        categories.add(category);
//
//        Manufacturer manufacturer = new Manufacturer("Nike", "NY NY");
//        manufacturers.add(manufacturer);
//        manufacturers.add(new Manufacturer("Apple", "LA LA"));
//
//        products.add(new Product("Ball", 350.0, 3, category, manufacturer));
//        products.add(new Product("Harry Potter", 500.0, 3, category, manufacturer));
//
//    }
}
