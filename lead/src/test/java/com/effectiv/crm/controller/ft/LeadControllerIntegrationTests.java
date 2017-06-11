package com.effectiv.crm.controller.ft;


import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.effectiv.crm.domain.Lead;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;

@ActiveProfiles("IT")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Transactional
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
	DbUnitTestExecutionListener.class })
@Slf4j
@DatabaseSetup("classpath:/com/effectiv/crm/repository/ut/lead.xml")
public class LeadControllerIntegrationTests {
	@Autowired
	private TestRestTemplate restTemplate;

	private final String BASE_URL = "/leads";
	

	@Test
	public void create() {
		
		Lead lead = new Lead();
		lead.setFirstName("Jos");
		lead.setLastName("Butler");
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
		
		
		assertThat(responseEntity.getStatusCode().value()).isEqualTo(HttpStatus.CREATED.value());
		assertThat(createdLead.getId()).isNotNull();
		assertThat(createdLead.getFirstName()).isEqualTo("Jos");
		
	}

	@Test
	public void findOne() {
		ResponseEntity<Lead> responseEntity = restTemplate.getForEntity(BASE_URL + "/1", Lead.class);
		log.info("retrieved responseEntity- {}", responseEntity);
		log.info("retrieved getBody- {} ", responseEntity.getBody());
		Lead retrievedLead = responseEntity.getBody();
		log.info("retrieved lead - {}", retrievedLead);
		assertThat(responseEntity.getStatusCode().value()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(retrievedLead.getFirstName()).isEqualTo("Virat");
		assertThat(retrievedLead.getId()).isNotNull();
	}

	//@Test
	public void update() {
		// fail("Not implemented");
	//	when(leadRepository.findOne(any(String.class))).thenReturn(lead);
	}

	@Test
	public void delete() {
		ResponseEntity<Lead> responseEntity = restTemplate.getForEntity(BASE_URL + "/1", Lead.class);
		Lead deleteLead = responseEntity.getBody();
		log.info("deleted lead - {}", deleteLead);
		log.info("deleted lead - {}", responseEntity.getStatusCode());
		deleteLead.setDeleted(true);
		log.info("deleted lead - {}", responseEntity.getStatusCode());
		assertThat(deleteLead.isDeleted()).isEqualTo(true);
	}

	//@Test
	public void deleteAll() {
		// fail("Not implemented");
	}

	@Test
	public void purge() {
		ResponseEntity<Lead> responseEntity = restTemplate.getForEntity(BASE_URL + "/1", Lead.class);
		Lead purgeLead=responseEntity.getBody();
		log.info("purge lead - {}", purgeLead);		
	}

	//@Test
	public void restore() {
		// fail("Not implemented");
	}

	//@Test
	public void findAll() {
		ResponseEntity<Lead> responseEntity=restTemplate.getForEntity(BASE_URL, Lead.class);
	//	Page<Lead> p=(Page)restTemplate.getForEntity(BASE_URL, Page.class);
		
	//	log.info("Page size :", p.getSize());
		Lead allLead = responseEntity.getBody();
		System.out.println("allLead :"+allLead);
		log.info("allLead :", allLead);
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
