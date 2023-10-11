package com.gabiev.pizzawok.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "address")
    private String address;

    @Column(name = "front_door")
    private int frontDoor;

    @Column(name = "floor")
    private int floor;

    @Column(name = "apartment")
    private int apartment;

    public Address() {

    }

    public Address(String address, int frontDoor, int floor, int apartment) {
        this.address = address;
        this.frontDoor = frontDoor;
        this.floor = floor;
        this.apartment = apartment;
    }
}
