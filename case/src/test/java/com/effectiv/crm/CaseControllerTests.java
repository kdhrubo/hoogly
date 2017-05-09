package com.effectiv.crm;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import com.effectiv.crm.business.CaseBusinessDelegate;
import com.effectiv.crm.controller.CaseController;
import com.effectiv.crm.domain.Case;
import com.effectiv.crm.web.SearchRequest;


@RunWith(SpringRunner.class)
@WebMvcTest(value = CaseController.class, secure = false)
@AutoConfigureRestDocs("build/generated-snippets")
public class CaseControllerTests extends AbstractControllerTests<Case,String>{
	
	@MockBean
	private CaseBusinessDelegate caseBusinessDelegate;
	private final String BASE_URL = "/cases";
	
	@Test
	public void testHeartBeat() throws Exception{
		mockMvc.perform(get(BASE_URL + "/hb"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		.andDo(print()).andDo(document("cheak-contact-heartbeat"));
	}

	@Test
	public void testCreate() throws Exception {
		Case casee = new Case();
		casee.setTitle("1234567890");
		casee.setStatusId("yes");
		casee.setDeleted(false);
		casee.setPrority("Unit - test - entity :: Contact");
		casee.setContactId("888899999");
		casee.setAssignedUser("effectvlead@effectiv.com");
		casee.setPrimaryEmail("effectvlead@effectiv.com");
		casee.setId("1");
		
		// mocking
			when(caseBusinessDelegate.save(any(Case.class))).thenReturn(casee);
		// testing and assert
			
			mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON_UTF8).content(json(casee)))
			.andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.id", is("1"))).andDo(print()).andDo(document("create-account"));
			
			// verify
			verify(caseBusinessDelegate, times(1)).save(casee);
			verifyNoMoreInteractions(caseBusinessDelegate);
	}
	
	@Test
	public void testFindAll() throws Exception {
		
	List<Case> caselists=new ArrayList<Case>();
	Case casee=new Case();
	int id=1;
	for(int i=0;i<10;i++)
	{
		casee.setId(Integer.toString(id));
		caselists.add(casee);
		id +=1;
		casee=new Case();
		
	}
	// mock
			when(caseBusinessDelegate.findAll(any(SearchRequest.class), any(Pageable.class)))
				.thenReturn(new PageImpl<>(caselists));
			mockMvc.perform(get(BASE_URL)
					.param("page", "1")
					.param("size", "10")
					.param("sort", "name")
					.param("sort", "name,asc")
					.param("sort", "name,asc")
					.param("search", "deleted:false,name:doe,age>25")
					.contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isFound())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.numberOfElements", is(10)))
			.andExpect(jsonPath("$.content[0].id", is("1")))
			.andExpect(jsonPath("$.content[1].id", is("2")))
			.andExpect(jsonPath("$.content[2].id", is("3")))
			.andExpect(jsonPath("$.content[9].id", is("10")))
			.andDo(print())
			.andDo(document("find-all-account"));
			
			// verify
			ArgumentCaptor<SearchRequest> searchRequest = ArgumentCaptor.forClass(SearchRequest.class);
			ArgumentCaptor<Pageable> page = ArgumentCaptor.forClass(Pageable.class);
			
			verify(caseBusinessDelegate, times(1)).findAll(searchRequest.capture(), page.capture());
			verifyNoMoreInteractions(caseBusinessDelegate);
			
			assertThat(searchRequest.getValue().getCriterias().get(0).getKey(), is("deleted"));
			assertThat(searchRequest.getValue().getCriterias().get(0).getOperation(), is(":"));
			assertThat(searchRequest.getValue().getCriterias().get(0).getValue().toString(), is("false"));
			
			assertThat(page.getValue().getPageNumber(), is(1));
			assertThat(page.getValue().getPageSize(), is(10));
			assertThat(page.getValue().getSort().getOrderFor("name").isAscending(), is(true));
			assertThat(page.getValue().getSort().getOrderFor("name").isAscending(), is(true));
	
	}
	
	@Test
	public void testFindOne() throws Exception {
		
		Case casee = new Case();
		casee.setId("1");
		casee.setPrimaryEmail("Rajiv");
		casee.setChannelId("1234567892");
		// mock
		when(caseBusinessDelegate.findOne(any(String.class)))
			.thenReturn(casee);
		
		mockMvc.perform(get(BASE_URL + "/{id}","1")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isFound())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(jsonPath("$.id", is("1")))
        .andExpect(jsonPath("$.primaryEmail", is("Rajiv")))
        .andExpect(jsonPath("$.channelId", is("1234567892")))
		.andDo(print())
		.andDo(document("find-one-contact"));
		
		// verify
		verify(caseBusinessDelegate, times(1)).findOne("1");
		verifyNoMoreInteractions(caseBusinessDelegate);
		
	}
	
	@Test
	public void testDelete() throws Exception {
		
		// mock
		doNothing().when(caseBusinessDelegate).delete(anyString());
		
		mockMvc.perform(delete(BASE_URL + "/{id}","1")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk())
		.andDo(print())
		.andDo(document("delete-one-contact"));
		
		// verify
		verify(caseBusinessDelegate, times(1)).delete("1");
		verifyNoMoreInteractions(caseBusinessDelegate);
		
	}
	
	@Test
	public void testPurge() throws Exception {
		
		// mock
		doNothing().when(caseBusinessDelegate).purge(anyString());
		
		mockMvc.perform(delete(BASE_URL + "/{id}","1")
				.param("purge", "true")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk())
		.andDo(print())
		.andDo(document("purge-case"));
		
		// verify
		verify(caseBusinessDelegate, times(1)).purge("1");
		verifyNoMoreInteractions(caseBusinessDelegate);
		
	}
	
	@Test
	public void testRestore() throws Exception {
		
		// mock
		doNothing().when(caseBusinessDelegate).restore(anyString());
		
		mockMvc.perform(put(BASE_URL + "/{id}/restore","1")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk())
		.andDo(print())
		.andDo(document("restore-case"));
		
		// verify
		verify(caseBusinessDelegate, times(1)).restore("1");
		verifyNoMoreInteractions(caseBusinessDelegate);
		
	}
	
}
