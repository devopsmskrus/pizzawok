package com.gabiev.pizzawok.entities;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "dishes")
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "isRoll")
    private boolean isRoll;

    @Column(name = "isPizza")
    private boolean isPizza;

    @Column(name = "isWok")
    private boolean isWok;

    @Column(name = "price")
    private int price;

    @Column(name = "image")
    private String image;

}
