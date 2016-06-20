package com.supermy.utils;

/**
 * Created by jamesmo on 2014/6/12.
 *
 * 保存extjs-gridfilter 过滤的元数据；
 *
 */
public class FilterMeta
{

    public enum Operator {
        eq,  gt, lt, like, gte, lte
    }

    private String type;
    private String value;
    private Operator comparison;
    private String field;
    private String property;

    public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }


    public Operator getComparison() {
        return comparison;
    }

    public void setComparison(Operator comparison) {
        this.comparison = comparison;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }




}