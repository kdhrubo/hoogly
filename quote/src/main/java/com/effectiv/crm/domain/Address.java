package com.effectiv.crm.domain;

import javax.persistence.*;


@Embeddable
public class Address {
	@Column(name="BILLING_STREET", nullable=true)
	private String billing_street;
	
	@Column(name="BILLING_CITY", nullable=true)
	private String billing_city;
	
	@Column(name="BILLING_STATE", nullable=true)
	private String billing_state;
	
	@Column(name="BILLING_POSTAL_CODE", nullable=true)
	private String billing_postalCode;
	
	@Column(name="BILLING_COUNTRY", nullable=true)
	private String billing_country;
	
	@Column(name="SHIPPING_STREET", nullable=true)
	private String shipping_street;
	
	
	@Column(name="SHIPPING_CITY", nullable=true)
	private String shipping_city;
	
	@Column(name="SHIPPING_STATE", nullable=true)
	private String shipping_state;
	
	@Column(name="SHIPPING_POSTAL_CODE", nullable=true)
	private String shipping_postalCode;
	
	@Column(name="SHIPPING_COUNTRY", nullable=true)
	private String shipping_country;
	
}
