package com.effectiv.crm.controller.ft;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.effectiv.crm.domain.Lead;
import com.effectiv.crm.repository.SimplePage;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import lombok.extern.slf4j.Slf4j;

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

		Lead retrievedLead = responseEntity.getBody();

		assertThat(responseEntity.getStatusCode().value()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(retrievedLead.getFirstName()).isEqualTo("Virat");
		assertThat(retrievedLead.getId()).isNotNull();
		assertThat(retrievedLead.getId()).isEqualTo("1");
	}

	@Test
	public void update() {
		//use create or save as update, ensure that entity has ID
	}

	@Test
	public void delete() {
		ResponseEntity<Void> responseEntity = restTemplate.exchange(BASE_URL + "/1", HttpMethod.DELETE, null,
				Void.class);
		assertThat(responseEntity.getStatusCode().value()).isEqualTo(HttpStatus.GONE.value());
	}

	@Test
	public void deleteAll() {
		List<String> ids = Arrays.asList(new String[] { "1", "2", "3", "4" });

		for (String id : ids) {
			ResponseEntity<Void> responseEntity = restTemplate.exchange(BASE_URL + "/" + id, HttpMethod.DELETE, null,
					Void.class);
			assertThat(responseEntity.getStatusCode().value()).isEqualTo(HttpStatus.GONE.value());
		}
	}

	@Test
	public void purge() {
		ResponseEntity<Void> responseEntity = restTemplate.exchange(BASE_URL + "/1?purge=true", HttpMethod.DELETE, null,
				Void.class);
		assertThat(responseEntity.getStatusCode().value()).isEqualTo(HttpStatus.GONE.value());
	}

	@Test
	public void restore() {
		ResponseEntity<Lead> responseEntity = restTemplate.exchange(BASE_URL + "/13/restore", HttpMethod.PUT, null,Lead.class);
		Lead restoredLead = responseEntity.getBody();
		assertThat(responseEntity.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
		assertThat(restoredLead.isDeleted()).isEqualTo(false);
	}

	@Test
	public void findAll() {
		ResponseEntity<SimplePage> responseEntity = restTemplate.getForEntity(BASE_URL + "/search", SimplePage.class);
		SimplePage allLeads = responseEntity.getBody();
		assertThat(responseEntity.getStatusCode().value()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(allLeads.getContent().size()).isEqualTo(12);
	}

	// @Test
	public void copy() {
		// fail("Not implemented");
	}

	@Test
	public void findAllDeleted() {
		ResponseEntity<SimplePage> responseEntity = restTemplate.getForEntity(BASE_URL + "/search?sc=deleted:true"  , SimplePage.class);
		SimplePage allLeads = responseEntity.getBody();
		assertThat(responseEntity.getStatusCode().value()).isEqualTo(HttpStatus.FOUND.value());
		assertThat(allLeads.getContent().size()).isEqualTo(13);
	}

	// @Test
	public void importAll() {
		// fail("Not implemented");
	}

	// @Test
	public void exportAll() {
		// fail("Not implemented");
	}

	// @Test
	public void convert() {
		// fail("Not implemented");
	}

}
