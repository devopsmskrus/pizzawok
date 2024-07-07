package com.gorbachev.pizzawok.repositories;

import com.gorbachev.pizzawok.entities.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Integer> {
}
