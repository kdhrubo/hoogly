package com.effectiv.crm.business;


import org.springframework.stereotype.Component;

import com.effectiv.crm.domain.Account;
import com.effectiv.crm.repository.AccountRepository;


@Component
public class AccountBusinessDelegate extends AbstractBaseBusinessDelegate<Account, String>{
	
	
	public AccountBusinessDelegate(AccountRepository repository) {
		super(repository);
	}

	
	
}