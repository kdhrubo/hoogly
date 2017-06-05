package com.effectiv.crm.controller.ft;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.effectiv.crm.business.LeadBusinessDelegate;
import com.effectiv.crm.controller.LeadController;
import com.effectiv.crm.domain.Lead;
import com.effectiv.crm.repository.LeadRepository;
import com.effectiv.crm.web.SearchRequest;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Transactional
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
	DbUnitTestExecutionListener.class })
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Slf4j
@DatabaseSetup("classpath:/com/effectiv/crm/repository/ut/lead.xml")
public class LeadControllerIntegrationTests {
	@Autowired
	private TestRestTemplate restTemplate;

	private final String BASE_URL = "/leads";
	
	@MockBean
	private LeadBusinessDelegate leadBusinessDelegate;
	
	@Autowired
	private LeadRepository leadRepository;
	private LeadController leadController;
	
	@Before
	public void setUp() {
		leadController =Mockito.mock(LeadController.class);
		leadRepository = Mockito.mock(LeadRepository.class);
		leadBusinessDelegate = new LeadBusinessDelegate(leadRepository);
	}

	@Test
	public void create() {
		
		Lead lead = new Lead();
		lead.setFirstName("Virat");
		lead.setLastName("Kohli");
		lead.setAddress(null);
		lead.setAnnualRevenue(50000.00D);
		lead.setCompany("EFFECTIV");
		lead.setDeleted(false);
		lead.setDescription("Unit - test - entity :: Lead");
		lead.setDesignation("Project Manager");
		lead.setEmail("effectvlead@effectiv.com");
		lead.setEmailOptOut(false);

		ResponseEntity<Lead> responseEntity = restTemplate.postForEntity(BASE_URL, lead, Lead.class);
		Lead createdLead = responseEntity.getBody();

		assertThat(HttpStatus.CREATED, equalTo( responseEntity.getStatusCode()));
		assertThat("Virat", equalTo(createdLead.getFirstName()));
		assertThat(createdLead.getId(), is(notNullValue()));

	}

	@Test
	
	public void findOne() {
		ResponseEntity<Lead> responseEntity = restTemplate.getForEntity(BASE_URL + "/1", Lead.class);
		log.info("retrieved responseEntity- {}", responseEntity);
		log.info("retrieved getBody- {} ", responseEntity.getBody());
		Lead retrievedLead = responseEntity.getBody();
		log.info("retrieved lead - {}", retrievedLead);
		assertThat(HttpStatus.FOUND, equalTo( responseEntity.getStatusCode()));
		assertThat("Virat", equalTo(retrievedLead.getFirstName()));
		assertThat(retrievedLead.getId(), is(notNullValue()));
	}

	//@Test
	public void update() {
		// fail("Not implemented");
	//	when(leadRepository.findOne(any(String.class))).thenReturn(lead);
	}

	//@Test
	public void delete() {
		// fail("Not implemented");
	}

	//@Test
	public void deleteAll() {
		// fail("Not implemented");
	}

	//@Test
	public void purge() {
		// fail("Not implemented");
	}

	//@Test
	public void restore() {
		// fail("Not implemented");
	}

	@Test
	public void findAll() {
		List<Lead> leadLists=new ArrayList<Lead>();
		Lead lead=new Lead();
		int id=1;
		for(int i=0;i<10;i++)
		{
			lead.setId(Integer.toString(id));
			leadLists.add(lead);
			id +=1;
			lead=new Lead();
			
		}
		when(leadBusinessDelegate.findAll(any(SearchRequest.class), any(Pageable.class))).thenReturn(new PageImpl<>(leadLists));
		SearchRequest sr = new SearchRequest();
		PageRequest pr = new PageRequest(1, 5);
		
		Page<Lead> rLeadPage = leadBusinessDelegate.findAll(sr, pr);
		assertThat(rLeadPage.getTotalElements()).isEqualTo(10L);
		assertThat(rLeadPage.getTotalPages()).isEqualTo(1);
		
		List<Lead> rLeads = rLeadPage.getContent();
		int counter = 1;
		for (Lead ld : rLeads) {
			assertThat(ld.getId()).isEqualTo(counter + "");
			counter++;
		}
		
		verify(leadRepository, times(1)).findAll(sr,pr);
		verifyNoMoreInteractions(leadRepository);
	}

	//@Test
	public void copy() {
		// fail("Not implemented");
	}

	//@Test
	public void findAllDeleted() {
		// fail("Not implemented");
	}

	//@Test
	public void importAll() {
		// fail("Not implemented");
	}

	//@Test
	public void exportAll() {
		// fail("Not implemented");
	}

	//@Test
	public void convert() {
		// fail("Not implemented");
	}

}
