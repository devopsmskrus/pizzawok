package com.gabiev.pizzawok.controllers;

import com.gabiev.pizzawok.entities.Address;
import com.gabiev.pizzawok.entities.Dish;
import com.gabiev.pizzawok.entities.Order;
import com.gabiev.pizzawok.entities.OrderPoint;
import com.gabiev.pizzawok.repositories.DishRepository;
import com.gabiev.pizzawok.repositories.OrderRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
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
    public String getDeliveryForm() {
        return "delivery";
    }

    @PostMapping("/cart/delivery")
    public String setOrder(Model model, @RequestParam Map<String, String> params, SessionStatus status) {
        Order order = (Order) model.getAttribute("order");
        order.setCustomerPhone(params.get("customerPhone"));
        order.setCustomerName(params.get("customerName"));
        order.setComment(params.get("comment"));
        order.setAddedDatetime(LocalDateTime.now());
        order.setStatus(Order.Status.ADD);
        Address address = new Address();
        address.setAddress(params.get("address"));
        address.setFrontDoor(Integer.valueOf(params.get("frontDoor")));
        address.setFloor(Integer.valueOf(params.get("floor")));
        address.setApartment(Integer.valueOf(params.get("apartment")));
        order.setAddress(address);

        orderRepo.save(order);
        status.setComplete();

        return "order";
    }
}
