package com.cdesign.spittr.data.entity;


import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * Created by Ageev Evgeny on 30.08.2016.
 * MongoDB Entity
 */
@Document
public class Order {
    @Id
    private String id;
    @Field("client")
    private String customer;

    private String type;

    private Collection<OrderItem> items = new LinkedHashSet<OrderItem>();

    public String getId() {
        return id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Collection<OrderItem> getItems() {
        return items;
    }

    public void setItems(Collection<OrderItem> items) {
        this.items = items;
    }
}
