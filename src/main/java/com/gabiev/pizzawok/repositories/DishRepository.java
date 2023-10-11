package com.gabiev.pizzawok.repositories;

import com.gabiev.pizzawok.entities.Dish;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DishRepository extends CrudRepository<Dish, Integer> {
    @Query(value = "FROM Dish WHERE isPizza = true")
    List<Dish> getAllPizzas();

    @Query(value = "FROM Dish WHERE isWok = true")
    List<Dish> getAllWoks();

    @Query(value = "FROM Dish WHERE isRoll = true")
    List<Dish> getAllRolls();
}
