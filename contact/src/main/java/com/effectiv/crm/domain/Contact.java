package com.effectiv.crm.domain;

import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="T_CONTACT")
@Data
@EqualsAndHashCode(callSuper=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Contact extends BaseEntity{
	
	@Column(name="SALUTATION_ID")
	private String salutationId;
	
	@Column(name="FIRST_NAME", nullable=true)
	private String firstName;
	
	@Column(name="LAST_NAME", nullable=true)
	private String lastName;
	
	@Column(name="EMAIL", nullable=true)
	private String email;
	
	@Column(name="PHONE", nullable=true)
	private String phone;
	
	@Column(name="MOBILE", nullable=true)
	private String mobile;
	
	@Column(name="HOME_PHONE", nullable=true)
	private String homePhone;
	
	@Column(name="DATE_OF_BIRTH", nullable=true)
	private Date dob;
	
	@Column(name="FAX", nullable=true)
	private String fax;
	
	@Column(name="ACCOUNT_ID", nullable=true)
	private String accountId;
	
	@Column(name="TITLE", nullable=true)
	private String title;
	
	@Column(name="DEPARTMENT", nullable=true)
	private String department;
	
	@Column(name="PARENT_CONTACT_ID", nullable=true)
	private String parentContactId;

	@Column(name="LEAD_SOURCE_ID")
	private String leadSourceId;
	
	@Column(name="ASSIGNED_USER_ID", nullable=true)
	private String assignedUserId;
	
	@Column(name="ASSIGNED_TEAM_ID", nullable=true)
	private String assignedTeamId;
	
	@Column(name="DO_NOT_CALL", nullable=true)
	private boolean doNotCall;
	
	@Column(name="EMAIL_OPT_OUT", nullable=true)
	private boolean emailOptOut;
	
	@Column(name="NOTIFY_OWNER", nullable=true)
	private boolean notifyOwner;
	
	@Column(name="CONTACT_TYPE_ID")
	private String contactTypeId;
	
	@Column(name="CONTACT_STATUS_ID")
	private String contactStatusId;
		
	@Column(name="TWITTER", nullable=true)
	private String twitter;
	
	@Column(name="FACEBOOK", nullable=true)
	String facebook;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="street", column= @Column(name="primary_street")),
		@AttributeOverride(name="zip", column= @Column(name="primary_zip")),
		@AttributeOverride(name="poBox", column= @Column(name="primary_po_box")),
		@AttributeOverride(name="city", column= @Column(name="primary_city")),
		@AttributeOverride(name="state", column= @Column(name="primary_state")),
		@AttributeOverride(name="country", column= @Column(name="primary_country"))
		
	})
	private Address primaryAddress;
	
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="street", column= @Column(name="secondary_street")),
		@AttributeOverride(name="zip", column= @Column(name="secondary_zip")),
		@AttributeOverride(name="poBox", column= @Column(name="secondary_po_box")),
		@AttributeOverride(name="city", column= @Column(name="secondary_city")),
		@AttributeOverride(name="state", column= @Column(name="secondary_state")),
		@AttributeOverride(name="country", column= @Column(name="secondary_country"))
	})
	private Address secondaryAddress;

	
	@Column(name="DESCRIPTION", nullable=true)
	private String description;
}
