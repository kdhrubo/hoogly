package com.effectiv.crm.controller.ut;


import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertThat;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import com.effectiv.crm.business.LeadBusinessDelegate;

import com.effectiv.crm.controller.LeadController;
import com.effectiv.crm.domain.Lead;
import com.effectiv.crm.web.SearchRequest;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

@RunWith(SpringRunner.class)
@WebMvcTest(value = LeadController.class, secure = false)
@AutoConfigureRestDocs("build/generated-snippets")

public class LeadControllerTests extends AbstractControllerTests<Lead,String>{

	@MockBean
	private LeadBusinessDelegate leadBusinessDelegate;
	
	private final String BASE_URL = "/leads";
	
	@Test
	public void testHeartBeat() throws Exception{
		mockMvc.perform(get(BASE_URL + "/hb"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		.andDo(print()).andDo(document("cheak-lead-heartbeat"));
	}

	@Test
	public void testCreate() throws Exception {
		Lead lead = new Lead();
		lead.setAddress(null);
		lead.setAnnualRevenue(50000.00D);
		lead.setCompany("EFFECTIV");
		lead.setDeleted(false);
		lead.setDescription("Unit - test - entity :: Lead");
		lead.setDesignation("Project Manager");
		lead.setEmail("effectvlead@effectiv.com");
		lead.setEmailOptOut(false);
		lead.setId("1");
		// mock
		when(leadBusinessDelegate.save(any(Lead.class))).thenReturn(lead);
		// test and assert
		
		mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON_UTF8).content(json(lead)))
				.andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.id", is("1"))).andDo(print()).andDo(document("create-lead"));
		// verify
		verify(leadBusinessDelegate, times(1)).save(lead);
		verifyNoMoreInteractions(leadBusinessDelegate);
	}
	
	@Test
	public void testFindAll() throws Exception {
		List<Lead> leads = new ArrayList<Lead>();
		Lead l = new Lead();
		l.setId("1");
		leads.add(l);
		
		l = new Lead();
		l.setId("2");
		leads.add(l);
		
		l = new Lead();
		l.setId("3");
		leads.add(l);
		
		l = new Lead();
		l.setId("4");
		leads.add(l);
		
		l = new Lead();
		l.setId("5");
		leads.add(l);
		
		l = new Lead();
		l.setId("6");
		leads.add(l);
		
		l = new Lead();
		l.setId("7");
		leads.add(l);
		
		l = new Lead();
		l.setId("8");
		leads.add(l);
		
		l = new Lead();
		l.setId("9");
		leads.add(l);
		
		l = new Lead();
		l.setId("10");
		leads.add(l);
		
		
		// mock
		when(leadBusinessDelegate.findAll(any(SearchRequest.class), any(Pageable.class)))
			.thenReturn(new PageImpl<>(leads));
		
		mockMvc.perform(get(BASE_URL)
				.param("page", "1")
				.param("size", "10")
				.param("sort", "firstName")
				.param("sort", "lastNname,asc")
				.param("sort", "lastNname,asc")
				.param("search", "deleted:false,lastName:doe,age>25")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isFound())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(jsonPath("$.numberOfElements", is(10)))
		.andExpect(jsonPath("$.content[0].id", is("1")))
		.andExpect(jsonPath("$.content[1].id", is("2")))
		.andExpect(jsonPath("$.content[2].id", is("3")))
		.andDo(print())
		.andDo(document("find-all-lead"));
		
		// verify
		ArgumentCaptor<SearchRequest> searchRequest = ArgumentCaptor.forClass(SearchRequest.class);
		ArgumentCaptor<Pageable> page = ArgumentCaptor.forClass(Pageable.class);

		verify(leadBusinessDelegate, times(1)).findAll(searchRequest.capture(), page.capture());
		verifyNoMoreInteractions(leadBusinessDelegate);
		
		
		assertThat(searchRequest.getValue().getCriterias().get(0).getKey(), is("deleted"));
		assertThat(searchRequest.getValue().getCriterias().get(0).getOperation(), is(":"));
		assertThat(searchRequest.getValue().getCriterias().get(0).getValue().toString(), is("false"));
		
		assertThat(page.getValue().getPageNumber(), is(1));
		assertThat(page.getValue().getPageSize(), is(10));
		assertThat(page.getValue().getSort().getOrderFor("firstName").isAscending(), is(true));
		assertThat(page.getValue().getSort().getOrderFor("lastNname").isAscending(), is(true));
	}
	
	@Test
	public void testFindOne() throws Exception {
		
		Lead l = new Lead();
		l.setId("1");
		l.setFirstName("Virat");
		l.setLastName("Kohli");
		// mock
		when(leadBusinessDelegate.findOne(any(String.class)))
			.thenReturn(l);
		
		mockMvc.perform(get(BASE_URL + "/{id}","1")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isFound())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(jsonPath("$.id", is("1")))
        .andExpect(jsonPath("$.firstName", is("Virat")))
        .andExpect(jsonPath("$.lastName", is("Kohli")))
		.andDo(print())
		.andDo(document("find-one-lead"));
		
		// verify
		verify(leadBusinessDelegate, times(1)).findOne("1");
		verifyNoMoreInteractions(leadBusinessDelegate);
		
	}
	
	@Test
	public void testDelete() throws Exception {
		
		// mock
		doNothing().when(leadBusinessDelegate).delete(anyString());
		
		mockMvc.perform(delete(BASE_URL + "/{id}","1")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk())
		.andDo(print())
		.andDo(document("delete-one-lead"));
		
		// verify
		verify(leadBusinessDelegate, times(1)).delete("1");
		verifyNoMoreInteractions(leadBusinessDelegate);
		
	}
	
	@Test
	public void testPurge() throws Exception {
		
		// mock
		doNothing().when(leadBusinessDelegate).purge(anyString());
		
		mockMvc.perform(delete(BASE_URL + "/{id}","1")
				.param("purge", "true")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk())
		.andDo(print())
		.andDo(document("purge-lead"));
		
		// verify
		verify(leadBusinessDelegate, times(1)).purge("1");
		verifyNoMoreInteractions(leadBusinessDelegate);
		
	}
	
	@Test
	public void testRestore() throws Exception {
		
		// mock
		doNothing().when(leadBusinessDelegate).restore(anyString());
		
		mockMvc.perform(put(BASE_URL + "/{id}/restore","1")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk())
		.andDo(print())
		.andDo(document("restore-lead"));
		
		// verify
		verify(leadBusinessDelegate, times(1)).restore("1");
		verifyNoMoreInteractions(leadBusinessDelegate);
		
	}

}
