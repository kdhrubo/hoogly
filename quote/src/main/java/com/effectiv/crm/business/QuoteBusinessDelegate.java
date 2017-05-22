package com.effectiv.crm.business;


import org.springframework.stereotype.Component;

import com.effectiv.crm.domain.Quote;
import com.effectiv.crm.repository.QuoteRepository;


@Component
public class QuoteBusinessDelegate extends AbstractBaseBusinessDelegate<Quote, String>{
	
	
	public QuoteBusinessDelegate(QuoteRepository repository) {
		super(repository);
	}

	
	
}