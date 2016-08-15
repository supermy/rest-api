package com.superman.domain;

import com.supermy.domain.BaseObj;
import com.supermy.domain.Parent;

import javax.persistence.*;

@Entity
@Table(name = "t_superman")
public class SuperMan extends BaseObj{//extends ResourceSupport {

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
