package com.effectiv.crm.controller;

import com.effectiv.crm.business.ProductBusinessDelegate;
import com.effectiv.crm.domain.Product;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/products")
public class ProductController extends AbstractBaseController<Product, String>{
	
	public ProductController(ProductBusinessDelegate service) {
		super(service);
	}
	
}