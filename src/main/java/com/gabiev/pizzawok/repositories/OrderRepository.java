package com.gabiev.pizzawok.repositories;

import com.gabiev.pizzawok.entities.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Integer> {
}
