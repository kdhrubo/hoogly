package com.effectiv.crm.business;


import org.springframework.stereotype.Component;

import com.effectiv.crm.domain.Case;
import com.effectiv.crm.repository.CaseRepository;


@Component
public class CaseBusinessDelegate extends AbstractBaseBusinessDelegate<Case, String>{
	
	
	public CaseBusinessDelegate(CaseRepository repository) {
		super(repository);
	}

	
	
}