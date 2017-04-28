package com.effectiv.crm.business;


import org.springframework.stereotype.Component;

import com.effectiv.crm.domain.Lead;
import com.effectiv.crm.repository.LeadRepository;


@Component
public class LeadBusinessDelegate extends AbstractBaseBusinessDelegate<Lead, String>{
	
	
	public LeadBusinessDelegate(LeadRepository repository) {
		super(repository);
	}

	
	
}