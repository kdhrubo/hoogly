package com.effectiv.crm.domain;

import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="T_DOCUMENT")
@Data
@EqualsAndHashCode(callSuper=true)
public class Document extends BaseEntity{
	@Column(name="FILE_NAME")
	private String name;
	
	@Column(name="ORIGINAL_FILE_NAME")
	private String fileName;
	
	@Column(name="MIME_TYPE")
	private String mime;
	
	@Column(name="FILE_SIZE")
	private Long size;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Lob 
	@Basic(fetch = FetchType.LAZY)
	@Column(name="FILE_CONTENT", length=100000)
	private byte[] content;
	
}