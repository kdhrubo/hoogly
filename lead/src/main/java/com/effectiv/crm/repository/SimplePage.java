package com.effectiv.crm.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;

import lombok.Data;

@Data
public class SimplePage<T> extends PageImpl<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int number;
	private int size;
	private int totalPages;
	private int numberOfElements;
	private long totalElements;
	private boolean previousPage;
	private boolean firstPage;
	private boolean nextPage;
	private boolean lastPage;
	private List<T> content;
	private Sort sort;

	public SimplePage() {
		super(new ArrayList<T>());
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getSize() {
		return size;
	}

}
