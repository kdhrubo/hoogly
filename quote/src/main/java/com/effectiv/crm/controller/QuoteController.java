package com.effectiv.crm.controller;

import com.effectiv.crm.business.QuoteBusinessDelegate;
import com.effectiv.crm.domain.Quote;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/quotes")
public class QuoteController extends AbstractBaseController<Quote, String>{
	
	public QuoteController(QuoteBusinessDelegate service) {
		super(service);
	}
	
}