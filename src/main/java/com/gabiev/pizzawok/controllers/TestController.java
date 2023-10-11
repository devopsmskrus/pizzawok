package com.gabiev.pizzawok.controllers;

import com.gabiev.pizzawok.entities.Address;
import com.gabiev.pizzawok.entities.Dish;
import com.gabiev.pizzawok.entities.Order;
import com.gabiev.pizzawok.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private EntityManagerFactory emf;

    @GetMapping("/add")
    public String add() {
        System.out.println("gabi : /test/add");
        EntityManager em = emf.createEntityManager();

        try {
            User user1 = new User("Ivan Drago", new Date(new java.util.Date(1982, 6, 3).getTime()), "IvanDrago@mail.com");
            user1.setIdPhone("79995553322");
            Address address1 = new Address("ул. Пушкина, д. 1", 1, 1, 1);
            Order order1 = new Order(user1.getIdPhone(), user1.getName(), address1, "Быстрее!!! Я не ел три дня"
                    , LocalDateTime.now(), LocalDateTime.now().plusHours(1), Order.Status.COMPLETED);
            Dish dish1 = em.find(Dish.class, 9);
            Dish dish2 = em.find(Dish.class, 30);
            order1.addOrderPoint(dish1);
            order1.addOrderPoint(dish2);

            User user2 = new User("Appolo Creed", new Date(new java.util.Date(1979, 9, 21).getTime()), "AppoloCreed@mail.com");
            user2.setIdPhone("79994445566");
            Address address2 = new Address("ул. Колотушкина д. 2", 2, 2, 2);
            Order order2 = new Order(user2.getIdPhone(), user2.getName(), address2, "Побольше соуса."
                    , LocalDateTime.now().minusHours(1), LocalDateTime.now().plusHours(2), Order.Status.COMPLETED);
            Dish dish3 = em.find(Dish.class, 15);
            Dish dish4 = em.find(Dish.class, 60);
            order2.addOrderPoint(dish3);
            order2.addOrderPoint(dish4);

            em.getTransaction().begin();
            em.persist(order1);
            em.persist(order2);
            em.getTransaction().commit();

        } catch(Throwable e) {
            em.getTransaction().rollback();
            throw e;

        } finally {
            em.close();
        }

        return "ok";
    }

    @GetMapping("/del")
    public String delete() {
        System.out.println("gabi : /test/del");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            List<Order> orders = em.createQuery("FROM Order WHERE customerName in (\"Appolo Creed\", \"Ivan Drago\")").getResultList();
            //List<Order> orders = em.createQuery("FROM Order WHERE customerName=\"Appolo Creed\"").getResultList();
            for(Order o : orders) {
                em.remove(o);
            }
            em.getTransaction().commit();

        } catch(Throwable e) {
            em.getTransaction().rollback();
            throw e;

        } finally {
            em.close();
        }

        return "ok";
    }
}
