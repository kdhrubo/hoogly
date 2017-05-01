package com.effectiv.crm.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Entity
@Data
@EqualsAndHashCode(callSuper=true)
class AccountEvent extends BaseEntity {
	@Column(name="SUBJECT", nullable=true)
	private String subject;
	
	@Column(name="ASSIGNED_USER_ID", nullable=true)
	private String assignedUser;
	
	private Date startDateTime;
	
	private Date dueDateTime;
	
	private Relation relation;
	
	private String location;
	
	private ActivityType activityType;
	
	private String statusId;
	
	private String priorityId;
	
	private boolean sendNotification;
		
	private String description;
	
	//TODO - list of reminders for this task to be added in future
	//List<Reminder> reminders;
}
