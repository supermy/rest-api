package com.supermy.domain;

import javax.persistence.*;

@Entity
@Table(name = "t_child")
public class Child extends BaseObj{//extends ResourceSupport {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id", nullable = false)
	public Parent parent;

	private String name;
	private String code;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
