package com.effectiv.crm.controller.ft;

import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;


import com.effectiv.crm.domain.Lead;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
//@Transactional
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
	DbUnitTestExecutionListener.class })
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Slf4j
@DatabaseSetup("classpath:/com/effectiv/crm/repository/ut/lead.xml")
public class LeadControllerIntegrationTests {
	@Autowired
	private TestRestTemplate restTemplate;

	private final String BASE_URL = "/leads";

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
		Lead retrievedLead = responseEntity.getBody();
		log.info("retrieved lead - {}", retrievedLead);
		assertThat(HttpStatus.FOUND, equalTo( responseEntity.getStatusCode()));
		assertThat("Virat", equalTo(retrievedLead.getFirstName()));
		assertThat(retrievedLead.getId(), is(notNullValue()));
	}

	//@Test
	public void update() {
		// fail("Not implemented");
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

	//@Test
	public void findAll() {
		// fail("Not implemented");
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
