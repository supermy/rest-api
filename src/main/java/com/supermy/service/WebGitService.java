/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.supermy.service;



import com.supermy.security.UserRepository;
import com.supermy.security.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.supermy.security.domain.User;
import com.supermy.security.domain.UserRole;

import java.util.List;

/**
 * 复杂逻辑处理
 */
// Spring Bean的标识.
@Component
// 类中所有public函数都纳入事务管理的标识.
@Transactional
public class WebGitService {

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private UserRepository userRepository;

	/**
	 * 左右多选
	 * 传入用户的id
	 * 获取所有角色的数据;
	 * 获取用户具备的角色;
	 * @param uId
	 * @return
     */
	public String getUserDeptCollectionResource(Long uId) {

		StringBuffer sb=new StringBuffer();
		//左侧列表
		sb.append("<div class=\"col-sm-3\">");
		sb.append("<select id=\"users\" name=\"users\" size=\"12\" class=\"form-control m-b\" multiple>");
		sb.append("");

		//获取所有的角色数据
		List<UserRole> roleList = userRoleRepository.findAll();
		for (UserRole d :roleList
			 ) {
			sb.append("<option value=\""+d.getUserRoleId()+"\">"+d.getRole()+"</option>");
		}
		sb.append("</select>");
		sb.append("</div>");

		//中间按钮
		sb.append("<div class=\"col-sm-1\">");
		sb.append("<div class=\"btn-group\" style=\"margin-top: 50px;\">");
		sb.append("<button type=\"button\" class=\"btn btn-white\" onclick=\"move('users', 'users_selected')\"><i class=\"fa fa-chevron-right\"></i></button>");
		sb.append("<button type=\"button\" class=\"btn btn-white\" onclick=\"move('users_selected', 'users')\"><i class=\"fa fa-chevron-left\"></i> </button>");
		sb.append("</div>");
		sb.append("</div>");

		//获取用户所属的角色
		//右侧列表
		sb.append("<div class=\"col-sm-3\">");
		sb.append("<select id=\"users_selected\" name=\"users_selected\" class=\"form-control m-b\" size=\"12\"  multiple>");

		User u = userRepository.getOne(uId);
		for (UserRole g :u.getUserRole()) {
			sb.append("<option value=\""+g.getUserRoleId()+"\">"+g.getRole()+"</option>");
		}
		sb.append("</select>");
		sb.append("</div>");

		return sb.toString();

	}

	/**
	 * 列表多选
	 *
	 * @param uId
	 * @return
     */
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String getUserDeptSelected(Long uId) {

		StringBuffer sb=new StringBuffer();
		//左侧列表
		sb.append("<select id=\"users\" name=\"users\" size=\"12\" class=\"form-control m-b\" multiple>");
		sb.append("");

		User u = userRepository.getOne(uId);

		//获取所有的部门数据
		List<UserRole> roleList = userRoleRepository.findAll();
		for (UserRole d :roleList
				) {

			sb.append("<option value=\""+d.getUserRoleId()+"\"");

			for (UserRole ur :u.getUserRole()){
				if (d.getUserRoleId() == ur.getUserRoleId())
					sb.append(" selected ");
			}

			sb.append(">"+d.getRole()+"</option>");

		}
		sb.append("</select>");


		return sb.toString();

	}
}
