package com.gabiev.pizzawok.controllers;

import com.gabiev.pizzawok.entities.Address;
import com.gabiev.pizzawok.entities.Dish;
import com.gabiev.pizzawok.entities.Order;
import com.gabiev.pizzawok.entities.OrderPoint;
import com.gabiev.pizzawok.repositories.DishRepository;
import com.gabiev.pizzawok.repositories.OrderRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@SessionAttributes(names = "order")
@RequestMapping("/")
public class OrderController {

    @Autowired
    private DishRepository dishRepo;

    @Autowired
    private OrderRepository orderRepo;

    @ModelAttribute
    private Order order() {
        return new Order();
    }

    @RequestMapping("/menu")
    public String getMenu(Model model, @RequestParam String menuSection) {

        List<Dish> dishes = new ArrayList<>();
        if (menuSection.equals("pizza")) {
            dishes = dishRepo.getAllPizzas();
        } else if (menuSection.equals("wok")) {
            dishes = dishRepo.getAllWoks();
        } else if (menuSection.equals("roll")) {
            dishes = dishRepo.getAllRolls();
        }
        model.addAttribute("dishes", dishes);

        Order order = (Order) model.getAttribute("order");
        model.addAttribute("dishCountInOrderById", getDishCountInOrderById(order));

        model.addAttribute("menuSection", menuSection);

        return "menu";
    }

    /**
    * key=dish_id, value=count_in_order
    */
    private Map<Integer, Integer> getDishCountInOrderById(Order order) {
        Map<Integer, Integer> map = new HashMap<>();
        if (order.getOrderPointList() != null) {

            map = order.getOrderPointList().stream()
                    .collect(Collectors.toMap(k -> k.getDish().getId(), v -> 1, Integer::sum));
        }
        return map;
    }

    @RequestMapping("/menu/add/{id}")
    public String addDishFromMenu(
            Model model
            , @PathVariable int id
            , @RequestParam String menuSection
            , RedirectAttributes ra) {

        Order order = (Order) model.getAttribute("order");
        order.addOrderPoint(dishRepo.findById(id).get());
        ra.addAttribute("menuSection", menuSection);
        return "redirect:/menu";
    }

    @RequestMapping("/menu/remove/{id}")
    public String delDishFromMenu(
            Model model
            , @PathVariable int id
            , @RequestParam String menuSection
            //, HttpServletRequest request
            , RedirectAttributes ra) {

        Order order = (Order) model.getAttribute("order");
        order.removeOrderPoint(id);
        ra.addAttribute("menuSection", menuSection);
        return "redirect:/menu";
    }

    @RequestMapping("/cart")
    public String getCart(/*Model model*/) {
        //Order order = (Order) model.getAttribute("order");
        //model.addAttribute("dishCountInOrderById", getDishCountInOrderById(order));
        return "cart";
    }

    /*@RequestMapping("/cart/add/{id}")
    public String addDishFromCart(@ModelAttribute("order") Order order, @PathVariable int id) {
        order.addOrderPoint(dishRepo.findById(id).get());
        return "redirect:/cart";
    }*/

    @RequestMapping("/cart/remove/{id}")
    public String removeDishFromCart(Model model, @PathVariable int id) {
        Order order = (Order) model.getAttribute("order");
        order.removeOrderPoint(id);
        return "redirect:/cart";
    }
    @RequestMapping("/cart/remove")
    public String removeAllDishFromCart(Model model) {
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

        order.setAddress(new Address());
        return "delivery";
    }

    @PostMapping("/cart/delivery")
    public String setOrder(@Valid @ModelAttribute Order order, BindingResult bindingResultOrder
            //, @Valid @ModelAttribute Address address, BindingResult bindingResultAddress
            , SessionStatus status) {
        if (order.getOrderPointList().isEmpty()) {
            return "cart";
        }

        if (bindingResultOrder.hasErrors() /*|| bindingResultAddress.hasErrors()*/) {
            return "delivery";
        }

        String phone = order.getCustomerPhone().replace(" ", "").replace("+", "");
        order.setCustomerPhone(phone);
        order.setAddedDatetime(LocalDateTime.now());
        order.setStatus(Order.Status.ADD);
        orderRepo.save(order);
        status.setComplete();
        return "redirect:/order_completed";
    }

    @RequestMapping("/order_completed")
    public String getOrderCompletedForm() {
        return "orderCompleted";
    }
}
