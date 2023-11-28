package com.gabiev.pizzawok.controllers;

import com.gabiev.pizzawok.entities.Address;
import com.gabiev.pizzawok.entities.Dish;
import com.gabiev.pizzawok.entities.Order;
import com.gabiev.pizzawok.services.MainService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@SessionAttributes(names = "order")
@RequestMapping("/")
public class MainController {

    @Autowired
    private MainService mainService;

    @ModelAttribute
    private Order order() {
        return new Order();
    }

    @RequestMapping("/menu")
    public String getMenu(Model model, @RequestParam String menuSection) {

        List<Dish> dishes = mainService.getDishes4MenuSection(menuSection);
        model.addAttribute("dishes", dishes);
        Order order = (Order) model.getAttribute("order");
        model.addAttribute("dishCountInOrderById", mainService.getDishCountInOrderById(order));
        model.addAttribute("menuSection", menuSection);

        return "menu";
    }

    @RequestMapping("/menu/add/{id}")
    public String addDishFromMenu(
            Model model
            , @PathVariable int id
            , @RequestParam String menuSection
            , RedirectAttributes ra) {

        Order order = (Order) model.getAttribute("order");
        order.addOrderPoint(mainService.getDish(id));
        ra.addAttribute("menuSection", menuSection);
        return "redirect:/menu";
    }

    @RequestMapping("/menu/delete/{id}")
    public String delDishFromMenu(
            Model model
            , @PathVariable int id
            , @RequestParam String menuSection
            //, HttpServletRequest request
            , RedirectAttributes ra) {

        Order order = (Order) model.getAttribute("order");
        order.deleteOrderPoint(id);
        ra.addAttribute("menuSection", menuSection);
        return "redirect:/menu";
    }

    @RequestMapping("/cart")
    public String getCart() {
        return "cart";
    }

    @RequestMapping("/cart/delete/{id}")
    public String deleteDishFromCart(Model model, @PathVariable int id) {
        Order order = (Order) model.getAttribute("order");
        order.deleteOrderPoint(id);
        return "redirect:/cart";
    }
    @RequestMapping("/cart/delete")
    public String deleteAllDishFromCart(Model model) {
        Order order = (Order) model.getAttribute("order");
        order.getOrderPointList().clear();
        return "redirect:/cart";
    }

    @GetMapping("/cart/delivery")
    public String getDeliveryForm(Model model) {
        Order order = (Order) model.getAttribute("order");
        if (order.getOrderPointList().isEmpty()) {
            return "cart";
        }

        if (order.getAddress() == null)
            order.setAddress(new Address());

        return "delivery";
    }

    @PostMapping("/cart/delivery")
    public String saveOrder(@Valid @ModelAttribute Order order, BindingResult bindingResultOrder
            , SessionStatus status) {

        if (bindingResultOrder.hasErrors()) {
            return "delivery";
        }

        mainService.saveOrder(order);
        status.setComplete();
        return "redirect:/order_completed";
    }

    @RequestMapping("/order_completed")
    public String getOrderCompletedForm() {
        return "orderCompleted";
    }
}
