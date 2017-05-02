
package com.effectiv.crm.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SearchRequestMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		SearchParams parameterAnnotation = parameter.getParameterAnnotation(SearchParams.class);
		boolean retVal = false;
		if (parameterAnnotation != null && SearchRequest.class.isAssignableFrom(parameter.getParameterType())) {
			retVal = true;
		}
		return retVal;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		
		log.info("== Resolving search request ===");
		
		HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
		return SearchRequest.getFromRequest(request);
	}
}