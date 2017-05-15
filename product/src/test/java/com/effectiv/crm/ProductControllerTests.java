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

import com.effectiv.crm.business.ProductBusinessDelegate;
import com.effectiv.crm.controller.ProductController;
import com.effectiv.crm.domain.Product;
import com.effectiv.crm.web.SearchRequest;


@RunWith(SpringRunner.class)
@WebMvcTest(value = ProductController.class, secure = false)
@AutoConfigureRestDocs("build/generated-snippets")
public class ProductControllerTests extends AbstractControllerTests<Product,String>{
	
	@MockBean
	private ProductBusinessDelegate productBusinessDelegate;
	private final String BASE_URL = "/products";
	
	@Test
	public void testHeartBeat() throws Exception{
		mockMvc.perform(get(BASE_URL + "/hb"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		.andDo(print()).andDo(document("cheak-account-heartbeat"));
	}

	@Test
	public void testCreate() throws Exception {
		Product product = new Product();
		product.setCommissonRate(50000.00D);
		product.setName("Rajiv-Product");
		product.setDeleted(false);
		product.setDescription("Unit - test - entity :: Product");
		product.setOwner("Project Manager");
		product.setManufacturer("effectvlead@effectiv.com");
		product.setTaxable(false);
		product.setId("1");
		
		// mocking
			when(productBusinessDelegate.save(any(Product.class))).thenReturn(product);
		// testing and assert
			
			mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON_UTF8).content(json(product)))
			.andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.id", is("1"))).andDo(print()).andDo(document("create-product"));
			
			// verify
			verify(productBusinessDelegate, times(1)).save(product);
			verifyNoMoreInteractions(productBusinessDelegate);
	}
	
	@Test
	public void testFindAll() throws Exception {
		
	List<Product> accountlists=new ArrayList<Product>();
	Product product=new Product();
	int id=1;
	for(int i=0;i<10;i++)
	{
		product.setId(Integer.toString(id));
		accountlists.add(product);
		id +=1;
		product=new Product();
		
	}
	// mock
			when(productBusinessDelegate.findAll(any(SearchRequest.class), any(Pageable.class)))
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
			.andDo(document("find-all-product"));
			
			// verify
			ArgumentCaptor<SearchRequest> searchRequest = ArgumentCaptor.forClass(SearchRequest.class);
			ArgumentCaptor<Pageable> page = ArgumentCaptor.forClass(Pageable.class);
			
			verify(productBusinessDelegate, times(1)).findAll(searchRequest.capture(), page.capture());
			verifyNoMoreInteractions(productBusinessDelegate);
			
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
		
		Product product = new Product();
		product.setId("1");
		product.setName("Rajiv");
		product.setQtyOrdered(1234567892);
		// mock
		when(productBusinessDelegate.findOne(any(String.class)))
			.thenReturn(product);
		
		mockMvc.perform(get(BASE_URL + "/{id}","1")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isFound())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(jsonPath("$.id", is("1")))
        .andExpect(jsonPath("$.name", is("Rajiv")))
        .andExpect(jsonPath("$.qtyOrdered", is(1234567892)))
		.andDo(print())
		.andDo(document("find-one-product"));
		
		// verify
		verify(productBusinessDelegate, times(1)).findOne("1");
		verifyNoMoreInteractions(productBusinessDelegate);
		
	}
	
	@Test
	public void testDelete() throws Exception {
		
		// mock
		doNothing().when(productBusinessDelegate).delete(anyString());
		
		mockMvc.perform(delete(BASE_URL + "/{id}","1")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk())
		.andDo(print())
		.andDo(document("delete-one-product"));
		
		// verify
		verify(productBusinessDelegate, times(1)).delete("1");
		verifyNoMoreInteractions(productBusinessDelegate);
		
	}
	
	@Test
	public void testPurge() throws Exception {
		
		// mock
		doNothing().when(productBusinessDelegate).purge(anyString());
		
		mockMvc.perform(delete(BASE_URL + "/{id}","1")
				.param("purge", "true")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk())
		.andDo(print())
		.andDo(document("purge-product"));
		
		// verify
		verify(productBusinessDelegate, times(1)).purge("1");
		verifyNoMoreInteractions(productBusinessDelegate);
		
	}
	
	@Test
	public void testRestore() throws Exception {
		
		// mock
		doNothing().when(productBusinessDelegate).restore(anyString());
		
		mockMvc.perform(put(BASE_URL + "/{id}/restore","1")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk())
		.andDo(print())
		.andDo(document("restore-product"));
		
		// verify
		verify(productBusinessDelegate, times(1)).restore("1");
		verifyNoMoreInteractions(productBusinessDelegate);
		
	}
	
}
