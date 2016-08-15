package com.supermy;

import com.supermy.domain.Person;
import com.supermy.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by moyong on 16/8/8.
 */
//@Service
public class DbInitService {
    ///@Autowired
    private PersonRepository repository;
    //@PostConstruct
    public void init() {
        Person person1 = new Person( "Mert", "Caliskan");
        repository.save(person1);
        Person person2 = new Person("Steve", "Millidge");
        repository.save(person2);
        Person person3 = new Person( "Andrew", "Pielage");
        repository.save(person3);
    }
}