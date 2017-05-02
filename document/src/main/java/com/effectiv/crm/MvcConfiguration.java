package com.effectiv.crm;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.effectiv.crm.web.SearchRequestMethodArgumentResolver;

@Configuration
public class MvcConfiguration extends WebMvcConfigurerAdapter{
	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new SearchRequestMethodArgumentResolver());
	}
}
