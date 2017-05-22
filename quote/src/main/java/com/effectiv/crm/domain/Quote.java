package com.effectiv.crm.domain;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="T_QUOTE")
@Data
@EqualsAndHashCode(callSuper=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Quote extends BaseEntity{

	@Column(name="ID", nullable=true)
	private String id;
	
	@Column(name="TITLE", nullable=true)
	private String title;
	
	@Column(name="NAME", nullable=true)
	private String name;
	
	@Column(name="DATE_CREATED", nullable=true)
	private Date date_created;
	
	@Column(name="DATE_MODIFIED", nullable=true)
	private Date date_modified;
	
	@Column(name="MODIFIED_BY_NAME", nullable=true)
	private String modified_by_name;
	
	@Column(name="CREATED_BY", nullable=true)
	private String created_by;
	
	@Column(name="DESCRIPTION", nullable=true)
	private String description;
	
	@Column(name="ASSIGNED_TO", nullable=true)
	private String assignedto;
	
	@Column(name="APPROVAL_ISSUE", nullable=true)
	private String approval_issue;
	
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="street", column= @Column(name="primary_street")),
		@AttributeOverride(name="zip", column= @Column(name="primary_zip")),
		@AttributeOverride(name="poBox", column= @Column(name="primary_po_box")),
		@AttributeOverride(name="city", column= @Column(name="primary_city")),
		@AttributeOverride(name="state", column= @Column(name="primary_state")),
		@AttributeOverride(name="country", column= @Column(name="primary_country"))
		
	})
	private Address address;
	
	@Column(name="VALID_UNTIL", nullable=true)
	private Date valid_until;
	
	@Column(name="QUOTE_NUMBER", nullable=true)
	private int quote_number;
	
	
	
	/*@Column(name="ACCOUNT", nullable=true)
	private Account account;*/
	
	/*@Column(name="CONTACT", nullable=true)
	private Contact account;*/
	
	/*@Column(name="OPPORTUNITY", nullable=true)
	private Opportunity opportunity;*/
	
	@Column(name="LINEITEMS", nullable=true)
	private LineItem lineItems;
	
	@Column(name="TOTAl", nullable=true)
	private double total;
	
	@Column(name="SUB_TOTAl", nullable=true)
	private double subTotal;
	
	@Column(name="DISCOUNT", nullable=true)
	private double discount;
	
	@Column(name="TAX", nullable=true)
	private boolean tax;
	
	@Column(name="SHIPPING", nullable=true)
	private String shipping;
	
	@Column(name="SHIPPING_TAX", nullable=true)
	private String shippingTax;
	
	@Column(name="Grand_Total", nullable=true)
	private String grandTotal;
	
	@Column(name="CURRENCY", nullable=true)
	private String currency;
	
	@Column(name="QUOTE_STAGE", nullable=true)
	private String quoteStage;
	
	@Column(name="PAYMENT_TERMS", nullable=true)
	private String paymentTerms;
	
	@Column(name="TERMS", nullable=true)
	private String Terms;
	
	@Column(name="APPROAVL_STATUS", nullable=true)
	private String approvalStatus;
	
	@Column(name="INVOICE_STATUS", nullable=true)
	private String invoiceStatus;
	
/*	@Column(name="ACCOUNT", nullable=true)
	private Account account;
	
	@Column(name="Contact", nullable=true)
	private Contact contact;*/
	
	
	
	@Column(name="NAME", nullable=true)
	private String name1;
}
