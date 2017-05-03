package com.effectiv.crm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="T_PRODUCT_CATEGORY")
@Data
@EqualsAndHashCode(callSuper=true)
public class ProductCategory  extends BaseEntity{
	@Column(name="NAME", nullable=true)
	private String name;
	
	@Column(name="DESCRIPTION", nullable=true)
	private String description;
	
	@Column(name="ASSIGNED_USER_ID")
	private String assignedUserId;
	
	@Column(name="PARENT_INDICATOR")
	private boolean parentIndicator;
	
	@Column(name="PARENT_CATEGORY_ID")
	private String parentCategoryId;
	
	//array of sub categories
}
