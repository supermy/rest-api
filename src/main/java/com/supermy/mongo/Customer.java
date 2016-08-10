package com.supermy.mongo;

import org.springframework.data.annotation.Id;



public class Customer  {

//    @Id
//    private String id;
//
//    private String firstName;
//    private String lastName;
//
    public Customer() {}
//
//    public Customer(String firstName, String lastName) {
//        this.firstName = firstName;
//        this.lastName = lastName;
//    }
@Id private String id;

    private String name;
    private String sex;



    @Override
    public String toString() {
        return String.format(
                "Customer[id=%s, name='%s', sex='%s']",
                id, name, sex);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}

