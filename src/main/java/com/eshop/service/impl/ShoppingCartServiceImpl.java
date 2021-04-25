package com.eshop.service.impl;

import com.eshop.model.Product;
import com.eshop.model.ShoppingCart;
import com.eshop.model.User;
import com.eshop.model.enumerations.ShoppingCartStatus;
import com.eshop.model.exceptions.ProductAlreadyInShoppingCartException;
import com.eshop.model.exceptions.ProductNotFoundException;
import com.eshop.model.exceptions.ShoppingCartNotFoundException;
import com.eshop.model.exceptions.UserNotFoundException;
import com.eshop.repository.impl.InMemoryProductRepository;
import com.eshop.repository.impl.InMemoryShoppingCartRepository;
import com.eshop.repository.impl.InMemoryUserRepository;
import com.eshop.repository.jpa.ProductRepository;
import com.eshop.repository.jpa.ShoppingCartRepository;
import com.eshop.repository.jpa.UserRepository;
import com.eshop.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }


    @Override
    public List<Product> listAllProductsInShoppingCart(Long id) {
        if (!this.shoppingCartRepository.findById(id).isPresent()) {
            throw new ShoppingCartNotFoundException(id);
        }
        return this.shoppingCartRepository.findById(id).get().getProducts();
    }

    @Override
    public ShoppingCart getActiveShoppingCart(String username) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        return this.shoppingCartRepository
                .findByUserAndStatus(user, ShoppingCartStatus.CREATED)
                .orElseGet(() -> {
                    ShoppingCart shoppingCart = new ShoppingCart(user);
                    return this.shoppingCartRepository.save(shoppingCart);
                });
    }

    @Override
    public ShoppingCart addProductToShoppingCart(String username, Long productId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(username);
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));

        if (shoppingCart.getProducts()
                .stream().filter(r -> r.getId().equals(productId))
                .collect(Collectors.toList()).size() > 0)
            throw new ProductAlreadyInShoppingCartException(productId, username);
        shoppingCart.getProducts().add(product);
        return this.shoppingCartRepository.save(shoppingCart);
    }
}
