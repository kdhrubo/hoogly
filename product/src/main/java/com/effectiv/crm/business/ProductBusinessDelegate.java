package com.effectiv.crm.business;


import org.springframework.stereotype.Component;

import com.effectiv.crm.domain.Product;
import com.effectiv.crm.repository.ProductRepository;


@Component
public class ProductBusinessDelegate extends AbstractBaseBusinessDelegate<Product, String>{
	
	
	public ProductBusinessDelegate(ProductRepository repository) {
		super(repository);
	}

	
	
}