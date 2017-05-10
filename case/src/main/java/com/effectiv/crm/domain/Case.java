package com.effectiv.crm.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="T_CASE")
@Data
@EqualsAndHashCode(callSuper=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Case extends BaseEntity{


	@Column(name="SUMMARY", nullable=false)
	private String summary;

	@Column(name="TITLE", nullable=false)
	private String title;
	
	@Column(name="CASE_STATUS_ID")
	private String statusId;
	
	@Column(name="PRIORITY_ID")
	private String prority;
	
	@Column(name="CONTACT_ID")
	private String contactId;
	
	@Column(name="ACCOUNT_ID")
	private String accountId;
	
	@Column(name="PRODUCT_ID")
	private String productId;
	
	@Column(name="SLA_ID")
	private String sla;
	
	@Column(name="CHANNEL_ID")
	private String channelId;
	
	@Column(name="ASSIGNED_USER_ID", nullable=true)
	private String assignedUser;
	
	@Column(name="ASSIGNED_TEAM_ID", nullable=true)
	private String assignedTeam;
	
	//@OneToOne(optional=false,fetch = FetchType.EAGER)
	//Group assignedGroup
	
	@Column(name="RESOLUTION", nullable=true)
	private String resolution;
	
	@Column(name="PRIMARY_EMAIL", nullable=true)
	private String primaryEmail;
	
	@Column(name="CATEGORY_ID")
	private String categoryId;
	
	@Column(name="SUB_CATEGORY_ID")
	private String subCategoryId;
	
	@Column(name="RESOLUTION_TYPE_ID")
	private String  resolutionTypeId;
	
	@Column(name="DEFFERED_DATE")
	private Date defferedDate;
	
	@Column(name="CLOSED_DATE")
	private Date closedDate;
	
}
