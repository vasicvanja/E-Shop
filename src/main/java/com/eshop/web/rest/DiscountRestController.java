package com.eshop.web.rest;

import com.eshop.model.Discount;
import com.eshop.model.dto.DiscountDto;
import com.eshop.repository.jpa.UserRepository;
import com.eshop.service.DiscountService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discounts")
public class DiscountRestController {

    private final DiscountService discountService;
    private final UserRepository userRepository;

    public DiscountRestController(DiscountService discountService, UserRepository userRepository) {
        this.discountService = discountService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<Discount> findAll() {
        return this.discountService.findAll();
    }

    @GetMapping("/pagination")
    public List<Discount> findAllWithPagination(Pageable pageable) {
        return this.discountService.findAllWithPagination((java.awt.print.Pageable) pageable).getContent();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Discount> findById(@PathVariable Long id) {
        return this.discountService.findById(id)
                .map(discount -> ResponseEntity.ok().body(discount))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Discount> save(@RequestBody DiscountDto discountDto) {
        return this.discountService.save(discountDto)
                .map(discount -> ResponseEntity.ok().body(discount))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Discount> edit(@PathVariable Long id, @RequestBody DiscountDto discountDto) {
        return this.discountService.edit(id, discountDto)
                .map(discount -> ResponseEntity.ok().body(discount))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        this.discountService.deleteById(id);
        if (this.discountService.findById(id).isEmpty()) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/assign/{id}")
    public ResponseEntity<Discount> assignDiscount(@PathVariable Long id, @RequestParam String username) {
        return this.discountService.assignDiscount(username, id)
                .map(discount -> ResponseEntity.ok().body(discount))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

}
