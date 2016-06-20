package com.supermy.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "t_parent")
public class Parent extends BaseObj{

//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "children")
//	private Child children;

	private String name;
	private String code;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
