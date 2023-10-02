package com.gabiev.pizzawok;

import com.gabiev.pizzawok.entities.Dish;
import com.gabiev.pizzawok.repositories.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class MainController {
    private DishRepository dishRepo;

    @Autowired
    public MainController(DishRepository dishRepo) {
        this.dishRepo = dishRepo;
    }

    @GetMapping("/pizza")
    public String getPizza(Model model) {
        model.addAttribute("dishes", dishRepo.getAllPizzas());
        return "menu";
    }

    @GetMapping("/wok")
    public String getWok(Model model) {
        model.addAttribute("dishes", dishRepo.getAllWoks());
        return "menu";
    }

    @GetMapping("/roll")
    public String getRoll(Model model) {
        model.addAttribute("dishes", dishRepo.getAllRolls());
        return "menu";
    }
}
