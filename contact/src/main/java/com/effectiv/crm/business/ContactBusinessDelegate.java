package com.effectiv.crm.business;


import org.springframework.stereotype.Component;

import com.effectiv.crm.domain.Contact;
import com.effectiv.crm.repository.ContactRepository;


@Component
public class ContactBusinessDelegate extends AbstractBaseBusinessDelegate<Contact, String>{
	
	
	public ContactBusinessDelegate(ContactRepository repository) {
		super(repository);
	}

	
	
}