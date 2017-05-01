package com.effectiv.crm.domain;

import javax.persistence.*;


@Embeddable
public class Relation {
	@Column(name="ENTITY_NAME", nullable=true)
	private String entityName;
	
	@Column(name="ENTITY_ID", nullable=true)
	private String entityId;
	
}
