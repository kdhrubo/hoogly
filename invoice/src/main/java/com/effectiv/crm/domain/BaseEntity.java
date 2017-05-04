package com.effectiv.crm.domain;

import java.util.*;
import javax.persistence.*;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

import org.springframework.data.annotation.*;
import org.springframework.data.annotation.Version;

import com.fasterxml.jackson.annotation.*;

import lombok.Data;

@Data
@MappedSuperclass
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseEntity {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name = "ID", length = 40)
	private String id;

	
	@Column(name = "DELETED")
	private boolean deleted;

	@Version
	@Column(name = "VERSION")
	private int version;

	@ElementCollection
	private Map<String, String> extensions = new HashMap<>();

	@CreatedDate
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@CreatedBy
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@LastModifiedDate
	@Column(name = "LAST_MODIFIED_DATE")
	private Date lastModifiedDate;
	
	@LastModifiedBy
	@Column(name = "LAST_MODIFIED_BY")
	private String lastModifiedBy;
  
	
	
	@JsonAnySetter
	public void anySet(String name, String value) { 
		extensions.put(name, value); 
	}
	
	@JsonAnyGetter
	public String anyGet(String name) { 
		return extensions.get(name); 
	}
}
