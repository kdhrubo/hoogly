package com.effectiv.crm.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import com.effectiv.crm.business.LeadBusinessDelegate;

import com.effectiv.crm.controller.LeadController;
import com.effectiv.crm.domain.Lead;


@RunWith(SpringRunner.class)
@WebMvcTest(value = LeadController.class, secure = false)
public class LeadControllerTests extends AbstractControllerTests{

	@MockBean
	private LeadBusinessDelegate leadBusinessDelegate;

	private Lead create() {
		Lead lead = new Lead();
		lead.setAddress(null);
		lead.setAnnualRevenue(50000.00D);
		lead.setCompany("EFFECTIV");
		lead.setDeleted(false);
		lead.setDescription("Unit - test - entity :: Lead");
		lead.setDesignation("Project Manager");
		lead.setEmail("effectvlead@effectiv.com");
		lead.setEmailOptOut(false);

		return lead;
	}

	@Test
	public void save() throws Exception {
		Lead lead = create();
		lead.setId("1");
		// mock
		when(leadBusinessDelegate.save(any(Lead.class))).thenReturn(lead);
		// test and assert
		Lead subMittedLead = create();

		mockMvc.perform(post("/lead").contentType(MediaType.APPLICATION_JSON).content(json(subMittedLead)))
				.andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.id", is("1"))).andDo(print());
		// verify
		verify(leadBusinessDelegate, times(1)).save(subMittedLead);
		verifyNoMoreInteractions(leadBusinessDelegate);
	}

}
