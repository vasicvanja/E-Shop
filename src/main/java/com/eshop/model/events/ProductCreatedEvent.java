package com.eshop.model.events;

import com.eshop.model.Product;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
public class ProductCreatedEvent extends ApplicationEvent {

    private LocalDateTime created;

    public ProductCreatedEvent(Product source) {
        super(source);
        this.created = LocalDateTime.now();
    }

    public ProductCreatedEvent(Product source, LocalDateTime created) {
        super(source);
        this.created = created;
    }

}
