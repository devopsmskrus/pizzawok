package com.gabiev.pizzawok.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "customer_phone")
    private String customerPhone;

    @Column(name = "customer_name")
    private String customerName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(name = "comment")
    private String comment;

    @Column(name = "added_datetime")
    private LocalDateTime addedDatetime;

    @Column(name = "expiration_datetime")
    private LocalDateTime expirationDatetime;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderPoint> orderPointList;

    public interface Status {
        String COMPLETED = "COMPLETED";
    }

    public Order() {

    }

    public Order(String customerPhone, String customerName, Address address, String comment, LocalDateTime addedDatetime, LocalDateTime expirationDatetime, String status) {
        this.customerPhone = customerPhone;
        this.customerName = customerName;
        this.address = address;
        this.comment = comment;
        this.addedDatetime = addedDatetime;
        this.expirationDatetime = expirationDatetime;
        this.status = status;
    }

    public void addOrderPoint(Dish dish) {
        if (orderPointList == null) {
            orderPointList = new ArrayList<OrderPoint>();
        }
        OrderPoint orderPoint = new OrderPoint(dish, this);
        orderPointList.add(orderPoint);
    }

}
