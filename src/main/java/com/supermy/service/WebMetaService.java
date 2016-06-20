/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.supermy.service;



import com.supermy.security.domain.User;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取每个实体的元数据
 */
// Spring Bean的标识.
@Component
// 类中所有public函数都纳入事务管理的标识.
@Transactional
public class WebMetaService {


	private SessionFactory sessionfactory;

	@Autowired
	public WebMetaService(EntityManagerFactory factory) {
		if(factory.unwrap(SessionFactory.class) == null){
			throw new NullPointerException("factory is not a hibernate factory");
		}
		this.sessionfactory = factory.unwrap(SessionFactory.class);
	}


	/**
	 *
	 *
	 * @param domainName
	 * @return
     */
	public Map getHibernateMetaJson(String domainName) {
		System.out.println("getHibernateMetaJson:"+domainName);

		ClassMetadata meta = sessionfactory.getClassMetadata(domainName);

		//Object[] propertyValues = catMeta.getPropertyValues(fritz);
		String[] propertyNames = meta.getPropertyNames();
		Type[] propertyTypes = meta.getPropertyTypes();

// get a Map of all properties which are not collections or associations
		Map namedValues = new HashMap();
		for ( int i=0; i<propertyNames.length; i++ ) {
			namedValues.put( propertyNames[i], propertyTypes[i].getName() );
//			if ( !propertyTypes[i].isEntityType() && !propertyTypes[i].isCollectionType() ) {
//				namedValues.put( propertyNames[i], propertyValues[i] );
//			}
		}
		//configuration.getClassMapping([entity class name]).getTable().getColumn([column number]).getComment()

		Map result = new HashMap();
		result.put("domainName",meta.getEntityName());
		result.put("entityName",meta.getMappedClass().getSimpleName());
		result.put("id",meta.getIdentifierPropertyName());
		result.put("idType",meta.getIdentifierType().getName());

		result.put("data",namedValues);

		System.out.println(result);

		return result;
	}
}
