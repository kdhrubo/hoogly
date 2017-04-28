package com.effectiv.crm.controller;

import com.effectiv.crm.business.LeadBusinessDelegate;
import com.effectiv.crm.domain.Lead;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/leads")
public class LeadController extends AbstractBaseController<Lead, String>{
	
	public LeadController(LeadBusinessDelegate service) {
		super(service);
	}
	
}