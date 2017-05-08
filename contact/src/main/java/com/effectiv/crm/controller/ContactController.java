package com.effectiv.crm.controller;

import com.effectiv.crm.business.ContactBusinessDelegate;
import com.effectiv.crm.domain.Contact;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/contacts")
public class ContactController extends AbstractBaseController<Contact, String>{
	
	public ContactController(ContactBusinessDelegate service) {
		super(service);
	}
	
}