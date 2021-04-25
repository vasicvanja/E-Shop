package com.eshop.web.controller;

import com.eshop.model.Manufacturer;
import com.eshop.service.ManufacturerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/manufacturers")
public class ManufacturerController {

    private final ManufacturerService manufacturerService;

    public ManufacturerController(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @GetMapping
    public String getManufacturerPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        model.addAttribute("manufacturers", this.manufacturerService.findAll());
        model.addAttribute("bodyContent", "manufacturers");
        return "master-template";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteManufacturer(@PathVariable Long id) {
        this.manufacturerService.deleteById(id);
        return "redirect:/manufacturers";
    }

    @GetMapping("/edit-form/{id}")
    public String editManufacturerPage(@PathVariable Long id, Model model) {
        if (this.manufacturerService.findById(id).isPresent()) {
            Manufacturer manufacturer = this.manufacturerService.findById(id).get();
            model.addAttribute("manufacturer", manufacturer);
            model.addAttribute("bodyContent", "add-manufacturer");
            return "master-template";
        }
        return "redirect:/manufacturers?error=ManufacturerNotFoundException";
    }

    @GetMapping("/add-form")
    public String addManufacturerPage(Model model) {
        List<Manufacturer> manufacturers = this.manufacturerService.findAll();
        model.addAttribute("manufacturers", manufacturers);
        model.addAttribute("bodyContent", "add-manufacturer");
        return "master-template";
    }

    @PostMapping("/add")
    public String saveManufacturer(@RequestParam String name,
                                   @RequestParam String address) {
        this.manufacturerService.save(name, address);
        return "redirect:/manufacturers";
    }
}
