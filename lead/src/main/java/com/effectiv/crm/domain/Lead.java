package com.effectiv.crm.domain;

import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="T_LEAD")
@Data
@EqualsAndHashCode(callSuper=true)
public class Lead extends BaseEntity{
	@Column(name="FIRST_NAME", nullable=true)
	private String firstName;
	
	@Column(name="LAST_NAME", nullable=true)
	private String lastName;
	
	@Column(name="COMPANY_NAME", nullable=true)
	private String company;
	
	@Column(name="EMAIL", nullable=true)
	private String email;
	
	@Column(name="PHONE", nullable=true)
	private String phone;
	
	@Column(name="MOBILE", nullable=true)
	private String mobile;
	
	@Column(name="DESIGNATION", nullable=true)
	private String designation;
	
	@Column(name="WEBSITE", nullable=true)
	private String website;
	
	@Column(name="FAX", nullable=true)
	private String fax;
	
	@Column(name="ANNUAL_REVENUE", nullable=true)
	private double annualRevenue;
	
	@Column(name="NO_OF_EMPLOYEES", nullable=true)
	private int noOfEmployees;
	
	@Column(name="INDUSTRY_LOV_ID", nullable=true)
	private String industry;
	
	@Column(name="SALUTATION_LOV_ID", nullable=true)
	private String salutation;
	
	@Column(name="LEADSOURCE_LOV_ID", nullable=true)
	private String leadSource;
	
	@Column(name="LEADSTATUS_LOV_ID", nullable=true)
	private String leadStatus;
	
	@Column(name="RATING_ID", nullable=true)
	private String rating;

	@Column(name="ASSIGNED_USER_ID", nullable=true)
	private String assignedUser;

	@Column(name="ASSIGNED_TEAM_ID", nullable=true)
	private String assignedTeam;
	
	@Column(name="EMAIL_OPT_OUT", nullable=true)
	private boolean emailOptOut;
	
	@Column(name="TWITTER", nullable=true)
	private String twitter;
	
	@Column(name="FACEBOOK", nullable=true)
	private String facebook;
	
	@Embedded
	private Address address;
	
	@Column(name="DESCRIPTION", nullable=true)
	private String description;
}
