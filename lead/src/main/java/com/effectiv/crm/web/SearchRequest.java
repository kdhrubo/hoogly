package com.effectiv.crm.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.h2.util.StringUtils;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j

public class SearchRequest implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Getter
	private List<SearchCriteria> criterias;
	
	public SearchRequest() {
		List<SearchCriteria> params = new ArrayList<SearchCriteria>();
		params.add(new SearchCriteria("deleted",":","false"));
		this.criterias = params;
	}
	
	public SearchRequest(List<SearchCriteria> criterias) {
		super();
		this.criterias = criterias;
	}

	
	
	/**
	 * Specify search criteria like this
	 * http://api.hoogly.io/leads/search?sc=lastName:doe,age>25
	 * @param request
	 * @return
	 */
	
	public static SearchRequest getFromRequest(HttpServletRequest request) {
		log.info("------- creating search request from servlet request ----");
		
		String search = request.getParameter("sc");
		
		boolean deletedParamNotAvailable = true;
		
		List<SearchCriteria> params = new ArrayList<SearchCriteria>();
        if (search != null) {
        	Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
            	SearchCriteria criteria = new SearchCriteria(matcher.group(1), 
                        matcher.group(2), matcher.group(3));
            	
            	if(StringUtils.equals("deleted", criteria.getKey())) {
            		deletedParamNotAvailable = false;
            	}
                params.add(criteria);
                
               
            }
        }
        
        if(deletedParamNotAvailable) {
        	params.add(new SearchCriteria("deleted",":","false"));
        }
        
        log.info("params  ----> {}", params );		
		return new SearchRequest(params);
	}
}
