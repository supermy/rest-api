package com.supermy.domain;

import org.hibernate.annotations.*;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_person")
//@org.hibernate.annotations.Table(appliesTo = "PERSON",comment = "个人信息")
public class Person extends BaseObj{//extends ResourceSupport {

	//@Column(columnDefinition = "VARCHAR(20) comment '姓'")
	private String firstName;
	//@Column(columnDefinition = "VARCHAR(20) comment '名'")
	private String lastName;

	public Person( String first, String last) {

		super();
		firstName = first;
		lastName = last;

	}

	public Person() {
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
