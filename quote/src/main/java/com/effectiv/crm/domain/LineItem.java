package com.effectiv.crm.domain;

import javax.persistence.Column;

public class LineItem {
	
	@Column(name="ID", nullable=true)
	private String id;
	
	@Column(name="PRODUCT_NAME", nullable=true)
	private String product_name;
	
	@Column(name="PART_NUMBER", nullable=true)
	private int part_number;
	
	@Column(name="NUMBER", nullable=true)
	private int number;
	
	@Column(name="QUANTITY", nullable=true)
	private int quantity;
	
	@Column(name="COST_PRICE", nullable=true)
	private double cost_price;
	
	@Column(name="LIST_PRICE", nullable=true)
	private double list_price;
	
	@Column(name="DISCOUNT", nullable=true)
	private double discount;

	@Column(name="DISCOUNT_AMOUNT", nullable=true)
	private double discount_amount;
	
	@Column(name="DISCOUNT_TYPE", nullable=true)
	private String discount_type;
	
	@Column(name="UNIT_PRICE", nullable=true)
	private double unit_price;
	
	@Column(name="TAX_AMOUNT", nullable=true)
	private double tax_amount;
	
	@Column(name="TOTAL_PRICE", nullable=true)
	private double total_price;
	
	@Column(name="TAX", nullable=true)
	private double tax;
	
	@Column(name="NOTE", nullable=true)
	private String note;
	
	@Column(name="DESCRIPTION", nullable=true)
	private String description;
}
