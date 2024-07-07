package com.gorbachev.pizzawok.services;

import com.gorbachev.pizzawok.entities.Dish;
import com.gorbachev.pizzawok.entities.Order;
import com.gorbachev.pizzawok.repositories.DishRepository;
import com.gorbachev.pizzawok.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MainService {
    @Autowired
    private DishRepository dishRepo;

    @Autowired
    private OrderRepository orderRepo;

    public List<Dish> getDishes4MenuSection(String menuSection) {
        List<Dish> dishes = new ArrayList<>();
        if (menuSection.equals("pizza")) {
            dishes = dishRepo.getAllPizzas();
        } else if (menuSection.equals("wok")) {
            dishes = dishRepo.getAllWoks();
        } else if (menuSection.equals("roll")) {
            dishes = dishRepo.getAllRolls();
        }
        return dishes;
    }

    /**
     * key=dish_id, value=count_in_order
     */
    public Map<Integer, Integer> getDishCountInOrderById(Order order) {
        Map<Integer, Integer> map = new HashMap<>();
        if (order.getOrderPointList() != null) {

            map = order.getOrderPointList().stream()
                    .collect(Collectors.toMap(k -> k.getDish().getId(), v -> 1, Integer::sum));
        }
        return map;
    }

    public Dish getDish(int id) {
        return dishRepo.findById(id).get();
    }

    public void saveOrder(Order order) {
        String phone = order.getCustomerPhone().replace(" ", "").replace("+", "");
        order.setCustomerPhone(phone);
        order.setAddedDatetime(LocalDateTime.now());
        order.setStatus(Order.Status.ADD);
        orderRepo.save(order);
    }
}
