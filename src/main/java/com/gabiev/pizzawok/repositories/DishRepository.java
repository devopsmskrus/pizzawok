package com.gabiev.pizzawok.repositories;

import com.gabiev.pizzawok.entities.Dish;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DishRepository extends CrudRepository<Dish, Integer> {
    @Query(value = "SELECT * FROM dishes WHERE is_pizza = true", nativeQuery = true)
    List<Dish> getAllPizzas();

    @Query(value = "SELECT * FROM dishes WHERE is_wok = true", nativeQuery = true)
    List<Dish> getAllWoks();

    @Query(value = "SELECT * FROM dishes WHERE is_roll = true", nativeQuery = true)
    List<Dish> getAllRolls();
}
