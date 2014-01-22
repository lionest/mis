package com.htlab.mis.common;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 基础ENTITY类
 * 只实现equal()、hashCoce()、toString()方法
 * @author ZOUXIAOMING
 *
 */
public abstract class BaseObject implements Serializable{
	private static final long serialVersionUID = -1L;
	
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
