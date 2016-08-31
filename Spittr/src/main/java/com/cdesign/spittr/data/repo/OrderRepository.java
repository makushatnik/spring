package com.cdesign.spittr.data.repo;

import com.cdesign.spittr.data.entity.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by Ageev Evgeny on 30.08.2016.
 */
public interface OrderRepository extends MongoRepository<Order, String> {

    List<Order> findByCustomer(String customer);

    List<Order> findByCustomerLike(String customer);

    List<Order> findByCustomerAndType(String customer, String type);

    List<Order> getByType(String type);

    @Query("{customer:'Chuck Wagon'}")
    List<Order> findChucksOrders();
}
