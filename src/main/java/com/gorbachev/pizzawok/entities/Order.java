package com.gorbachev.pizzawok.entities;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "customer_phone")
    @NotBlank(message = "Введите номер телефона")
    private String customerPhone;

    @Column(name = "customer_name")
    @NotBlank(message = "Введите имя")
    private String customerName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    @Valid
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
        String ADD = "ADD";
        String COMPLETED = "COMPLETED";
    }

    public Order() {
        this.orderPointList = new ArrayList<>();
    }

    public Order(String customerPhone, String customerName, Address address, String comment, LocalDateTime addedDatetime, LocalDateTime expirationDatetime, String status) {
        this.customerPhone = customerPhone;
        this.customerName = customerName;
        this.address = address;
        this.comment = comment;
        this.addedDatetime = addedDatetime;
        this.expirationDatetime = expirationDatetime;
        this.status = status;
        this.orderPointList = new ArrayList<>();
    }

    public void addOrderPoint(Dish dish) {
        if (orderPointList == null) {
            orderPointList = new ArrayList<OrderPoint>();
        }
        OrderPoint orderPoint = new OrderPoint(dish, this);
        orderPointList.add(orderPoint);
    }

    public void deleteOrderPoint(int dishId) {
        if (orderPointList == null)
            return;

        Iterator<OrderPoint> iterator = orderPointList.iterator();
        while(iterator.hasNext()) {
            OrderPoint op = iterator.next();
            if (op.getDish().getId() == dishId) {
                orderPointList.remove(op);
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customerPhone='" + customerPhone + '\'' +
                ", customerName='" + customerName + '\'' +
                ", address=" + address +
                ", comment='" + comment + '\'' +
                ", addedDatetime=" + addedDatetime +
                ", expirationDatetime=" + expirationDatetime +
                ", status='" + status + '\'' +
                '}';
    }
}
