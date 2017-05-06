package com.effectiv.crm.controller;

import com.effectiv.crm.business.AccountBusinessDelegate;
import com.effectiv.crm.domain.Account;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/accounts")
public class AccountController extends AbstractBaseController<Account, String>{
	
	public AccountController(AccountBusinessDelegate service) {
		super(service);
	}
	
}