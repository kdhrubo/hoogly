package com.effectiv.crm.domain;

import java.util.Date;

import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="T_PRODUCT")
@Data
@EqualsAndHashCode(callSuper=true)
public class Product extends BaseEntity{
	
	@Column(name="NAME", nullable=true)
	private String name;
	
	@Column(name="OWNER", nullable=true)
	private String owner;
	
	@Column(name="PRODUCT_CODE", nullable=true)
	private String productCode;
	
	@Column(name="ACTIVE", nullable=true)
	private boolean active;
	
	@Column(name="VENDOR_ID", nullable=true)
	private String vendorId;
	
	@Column(name="PRODUCT_CATEGORY_ID", nullable=true)
	private String productCategory;
	
	@Column(name="SALES_START_DATE", nullable=true)
	private Date salesStartDate;
	
	@Column(name="SALES_END_DATE", nullable=true)
	private Date salesEndDate;

	@Column(name="COMMISSION_RATE", nullable=true)
	private double commissonRate;
	
	@Column(name="MANUFACTURER", nullable=true)
	private String manufacturer;
	
	@Column(name="UNIT_PRICE", nullable=true)
	private double unitPrice;

	@Column(name="TAXABLE", nullable=true)
	private boolean taxable;
		
	@Column(name="SUPPORT_START_DATE", nullable=true)
	private Date supportStartDate;

	@Column(name="SUPPORT_END_DATE", nullable=true)
	private Date supportEndDate;
	
	@Column(name="USAGE_UNIT_ID", nullable=true)
	private String usageUnitId;

	@Column(name="QTY_ORDERED", nullable=true)
	private int qtyOrdered;

	@Column(name="QTY_IN_STOCK", nullable=true)
	private int qtyInStock;

	@Column(name="REORDER_LEVEL", nullable=true)
	private int reorderLevel;
	
	
	@Column(name="HANDLER", nullable=true)
	private String handler;
	
	@Column(name="QTY_IN_DEMAND", nullable=true)
	private int qtyInDemand;


	@Column(name="DESCRIPTION", nullable=true)
	private String description;


}