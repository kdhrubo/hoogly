package com.effectiv.crm.domain;

import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="T_OPPORTUNITY")
@Data
@EqualsAndHashCode(callSuper=true)
public class Opportunity extends BaseEntity{

	
	@Column(name="NAME", nullable=true)
	private String name;
	
	@Column(name="AMOUNT", nullable=true)
	private double amount;
	
	@Column(name="CONTACT_ID", nullable=true)
	private String contactId;
	
	@Column(name="ACCOUNT_ID", nullable=true)
	private String accountId;
	
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
	@Column(name="EXPECTED_CLOSE",nullable=true)
	private Date expectedClose;
	
	@Column(name="SALES_STAGE_ID")
	private String salesStage;
	
	@Column(name="ASSIGNED_USER_ID")
	private String assignedUserId;
	

	@Column(name="LEAD_SOURCE_ID")
	private String leadSourceId;
	
	@Column(name="NEXT_STEP")
	private String nextStep;
	
	@Column(name="OPPORTUNITY_TYPE_ID")
	private String opportunityTypeId;
	
	@Column(name="CAMPAIGN_ID")
	private String campaignId;
	
	@Column(name="PROBABILITY", nullable=true)
	private int probablity;
	
	@Column(name="FORECAST_AMOUNT", nullable=true)
	private double forecastAmount;
	
	@Column(name="EMAIL", nullable=true)
	private String email;
	
	@Column(name="LOST_REASON_ID")
	private String lostReasonId;
	
	@Column(name="LEAD_ID", nullable=true)
	private String leadId;
	
	@Column(name="ASSIGNED_TEAM_ID", nullable=true)
	private String assignedTeamId;

	@Column(name="DESCRIPTION", nullable=true)
	private String description;	
	
}