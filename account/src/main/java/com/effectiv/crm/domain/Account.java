package com.effectiv.crm.domain;
import javax.persistence.*;

@Entity
public class Account extends BaseEntity{
	@Column(name="NAME", nullable=true)
	private String name;
	
	@Column(name="WEBSITE", nullable=true)
	private String website;
	
	@Column(name="PHONE", nullable=true)
	private String phone;
	
	@Column(name="SECONDARY_PHONE", nullable=true)
	private String secondaryPhone;
	
	@Column(name="FAX", nullable=true)
	private String fax;

	@Column(name="ANNUAL_REVENUE", nullable=true)
	private double annualRevenue;
	
	@Column(name="NO_OF_EMPLOYEES", nullable=true)
	private int noOfEmployees;
	
	@Column(name="COMPANY", nullable=true)
	private String company;

	@Column(name="EMAIL", nullable=true)
	private String email;
	
	@Column(name="SECONDARY_EMAIL", nullable=true)
	private String secondaryEmail;

	@Column(name="MOBILE", nullable=true)
	private String mobile;
	
	@Column(name="DESIGNATION", nullable=true)
	private String designation;

	@Column(name="EMAIL_OPT_OUT", nullable=true)
	private boolean emailOptOut;

	@Column(name="ASSIGNED_USER_ID", nullable=true)
	private String assignedUser;

	@Column(name="ASSIGNED_TEAM_ID", nullable=true)
	private String assignedTeam;

	@Column(name="INDUSTRY_LOV_ID", nullable=true)
	private String industry;
	
	@Column(name="ACCOUNT_TYPE", nullable=true)
	private String type;

	@Column(name="RATING_ID", nullable=true)
	private String rating;

	//Address primaryAddress
	//Address secondaryAddress

	@Column(name="description", nullable=true)
	private String description;

}