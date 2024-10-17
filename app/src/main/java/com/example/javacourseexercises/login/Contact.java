package com.example.javacourseexercises.login;

import java.util.ArrayList;
import java.util.List;

public class Contact {
    Integer id;
    String name;
//    String phone;
    List<String> phones = new ArrayList<>();

    public Contact(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }
}
