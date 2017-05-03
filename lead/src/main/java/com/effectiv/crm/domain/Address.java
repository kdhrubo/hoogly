package com.effectiv.crm.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Embeddable
public class Address {
	@Column(name="STREET", nullable=true)
	private String street;
	
	@Column(name="ZIP", nullable=true)
	private String zip;
	
	@Column(name="PO_BOX", nullable=true)
	private String poBox;
	
	@Column(name="CITY", nullable=true)
	private String city;
	
	@Column(name="STATE", nullable=true)
	private String state;
	
	@Column(name="COUNTRY", nullable=true)
	private String country;
}
