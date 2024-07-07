package com.gorbachev.pizzawok.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_phone")
    private Long idPhone;

    @Column(name = "name")
    private String name;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "mail")
    private String mail;

    public User() {

    }

    public User(String name, Date birthday, String mail) {
        this.name = name;
        this.birthday = birthday;
        this.mail = mail;
    }

}
