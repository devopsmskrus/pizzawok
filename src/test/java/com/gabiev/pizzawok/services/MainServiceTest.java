package com.gabiev.pizzawok.services;

import com.gabiev.pizzawok.entities.Dish;
import com.gabiev.pizzawok.entities.Order;
import com.gabiev.pizzawok.repositories.DishRepository;
import com.gabiev.pizzawok.repositories.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

@SpringBootTest
public class MainServiceTest {

    @Autowired
    private MainService mainService;

    @MockBean
    private DishRepository dishRepo;

    @MockBean
    private OrderRepository orderRepo;

    @Test
    public void getDishes4MenuSection() {

        for (Dish.Type type : Dish.Type.values()) {
            List<Dish> dishes = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                Dish dish = new Dish();
                dish.setType(type);
                dish.setName(type.name() + "_" + i);
                dishes.add(dish);
            }

            if (type == Dish.Type.PIZZA)
                Mockito.doReturn(dishes).when(dishRepo).getAllPizzas();
            if (type == Dish.Type.WOK)
                Mockito.doReturn(dishes).when(dishRepo).getAllWoks();
            if (type == Dish.Type.ROLL)
                Mockito.doReturn(dishes).when(dishRepo).getAllRolls();

            List<Dish> result = mainService.getDishes4MenuSection(type.name().toLowerCase());
            Assertions.assertEquals(dishes, result);
        }

        List<Dish> result = mainService.getDishes4MenuSection("bad string");
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void getDishCountInOrderById() {
        Order order = new Order();
        Dish dish1 = new Dish();
        dish1.setId(1);
        Dish dish2 = new Dish();
        dish2.setId(2);

        order.addOrderPoint(dish1);
        order.addOrderPoint(dish1);
        order.addOrderPoint(dish1);

        order.addOrderPoint(dish2);
        order.addOrderPoint(dish2);

        Map<Integer, Integer> result = mainService.getDishCountInOrderById(order);
        Assertions.assertEquals(3, result.get(dish1.getId()));
        Assertions.assertEquals(2, result.get(dish2.getId()));
        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void getDish() {
        Dish dish = new Dish();
        dish.setId(1);
        Mockito.doReturn(Optional.of(dish)).when(dishRepo).findById(1);
        Dish result = mainService.getDish(1);
        Assertions.assertEquals(dish, result);
    }

    @Test
    public void saveOrder() {
        Order order = new Order();
        order.setCustomerPhone("+7 999 999 99 99");
        mainService.saveOrder(order);
        Assertions.assertEquals("79999999999", order.getCustomerPhone());
        Assertions.assertNotNull(order.getAddedDatetime());
        Assertions.assertEquals(Order.Status.ADD, order.getStatus());
        Mockito.verify(orderRepo, Mockito.only()).save(Mockito.any(Order.class));
    }
}
