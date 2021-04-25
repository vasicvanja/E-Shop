package com.eshop.repository.impl;

import com.eshop.bootstrap.DataHolder;
import com.eshop.model.ShoppingCart;
import com.eshop.model.enumerations.ShoppingCartStatus;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryShoppingCartRepository {

    public List<ShoppingCart> findAll() {
        return DataHolder.shoppingCarts;
    }

    public Optional<ShoppingCart> findById(Long id) {
        return DataHolder.shoppingCarts.stream().filter(r -> r.getId().equals(id)).findFirst();
    }

    public Optional<ShoppingCart> findByUsernameAndStatus(String username, ShoppingCartStatus status) {
        return DataHolder.shoppingCarts.stream().filter(r -> r.getUser().getUsername().equals(username) && r.getStatus().equals(status)).findFirst();
    }

    public ShoppingCart save(ShoppingCart shoppingCart) {
        DataHolder.shoppingCarts.removeIf(r -> r.getUser().getUsername().equals(shoppingCart.getUser().getUsername()));
        DataHolder.shoppingCarts.add(shoppingCart);
        return shoppingCart;
    }
}
