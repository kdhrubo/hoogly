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

import com.effectiv.crm.business.ContactBusinessDelegate;
import com.effectiv.crm.controller.ContactController;
import com.effectiv.crm.domain.Contact;
import com.effectiv.crm.web.SearchRequest;


@RunWith(SpringRunner.class)
@WebMvcTest(value = ContactController.class, secure = false)
@AutoConfigureRestDocs("build/generated-snippets")
public class ContactControllerTests extends AbstractControllerTests<Contact,String>{
	
	@MockBean
	private ContactBusinessDelegate contactBusinessDelegate;
	private final String BASE_URL = "/contacts";
	
	@Test
	public void testHeartBeat() throws Exception{
		mockMvc.perform(get(BASE_URL + "/hb"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		.andDo(print()).andDo(document("cheak-contact-heartbeat"));
	}

	@Test
	public void testCreate() throws Exception {
		Contact contact = new Contact();
		contact.setPhone("1234567890");
		contact.setFirstName("Raji");
		contact.setDeleted(false);
		contact.setDescription("Unit - test - entity :: Contact");
		contact.setMobile("888899999");
		contact.setEmail("effectvlead@effectiv.com");
		contact.setEmailOptOut(false);
		contact.setId("1");
		
		// mocking
			when(contactBusinessDelegate.save(any(Contact.class))).thenReturn(contact);
		// testing and assert
			
			mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON_UTF8).content(json(contact)))
			.andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.id", is("1"))).andDo(print()).andDo(document("create-account"));
			
			// verify
			verify(contactBusinessDelegate, times(1)).save(contact);
			verifyNoMoreInteractions(contactBusinessDelegate);
	}
	
	@Test
	public void testFindAll() throws Exception {
		
	List<Contact> accountlists=new ArrayList<Contact>();
	Contact con=new Contact();
	int id=1;
	for(int i=0;i<10;i++)
	{
		con.setId(Integer.toString(id));
		accountlists.add(con);
		id +=1;
		con=new Contact();
		
	}
	// mock
			when(contactBusinessDelegate.findAll(any(SearchRequest.class), any(Pageable.class)))
				.thenReturn(new PageImpl<>(accountlists));
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
			
			verify(contactBusinessDelegate, times(1)).findAll(searchRequest.capture(), page.capture());
			verifyNoMoreInteractions(contactBusinessDelegate);
			
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
		
		Contact contact = new Contact();
		contact.setId("1");
		contact.setFirstName("Rajiv");
		contact.setPhone("1234567892");
		// mock
		when(contactBusinessDelegate.findOne(any(String.class)))
			.thenReturn(contact);
		
		mockMvc.perform(get(BASE_URL + "/{id}","1")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isFound())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(jsonPath("$.id", is("1")))
        .andExpect(jsonPath("$.firstName", is("Rajiv")))
        .andExpect(jsonPath("$.phone", is("1234567892")))
		.andDo(print())
		.andDo(document("find-one-contact"));
		
		// verify
		verify(contactBusinessDelegate, times(1)).findOne("1");
		verifyNoMoreInteractions(contactBusinessDelegate);
		
	}
	
	@Test
	public void testDelete() throws Exception {
		
		// mock
		doNothing().when(contactBusinessDelegate).delete(anyString());
		
		mockMvc.perform(delete(BASE_URL + "/{id}","1")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk())
		.andDo(print())
		.andDo(document("delete-one-contact"));
		
		// verify
		verify(contactBusinessDelegate, times(1)).delete("1");
		verifyNoMoreInteractions(contactBusinessDelegate);
		
	}
	
	@Test
	public void testPurge() throws Exception {
		
		// mock
		doNothing().when(contactBusinessDelegate).purge(anyString());
		
		mockMvc.perform(delete(BASE_URL + "/{id}","1")
				.param("purge", "true")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk())
		.andDo(print())
		.andDo(document("purge-account"));
		
		// verify
		verify(contactBusinessDelegate, times(1)).purge("1");
		verifyNoMoreInteractions(contactBusinessDelegate);
		
	}
	
	@Test
	public void testRestore() throws Exception {
		
		// mock
		doNothing().when(contactBusinessDelegate).restore(anyString());
		
		mockMvc.perform(put(BASE_URL + "/{id}/restore","1")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk())
		.andDo(print())
		.andDo(document("restore-account"));
		
		// verify
		verify(contactBusinessDelegate, times(1)).restore("1");
		verifyNoMoreInteractions(contactBusinessDelegate);
		
	}
	
}
