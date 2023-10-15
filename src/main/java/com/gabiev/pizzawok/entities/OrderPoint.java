package com.gabiev.pizzawok.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "order_points")
public class OrderPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "dish_id")
    private Dish dish;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "price")
    private int price;

    public OrderPoint() {

    }

    public OrderPoint(Dish dish, Order order) {
        this.dish = dish;
        this.order = order;
        this.price = dish.getPrice();
    }

    @Override
    public String toString() {
        return "OrderPoint{" +
                "id=" + id +
                ", dish=" + dish.getId() +
                ", order=" + order.getId() +
                ", price=" + price +
                '}';
    }
}
