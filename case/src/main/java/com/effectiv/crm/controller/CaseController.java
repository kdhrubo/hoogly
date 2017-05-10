package com.effectiv.crm.controller;

import com.effectiv.crm.business.CaseBusinessDelegate;
import com.effectiv.crm.domain.Case;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/cases")
public class CaseController extends AbstractBaseController<Case, String>{
	
	public CaseController(CaseBusinessDelegate service) {
		super(service);
	}
	
}