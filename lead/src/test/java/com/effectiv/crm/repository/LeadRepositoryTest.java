package com.effectiv.crm.repository;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.effectiv.crm.domain.Lead;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class LeadRepositoryTest {
	
	@Autowired
	private LeadRepository repository;
	
	@Autowired
    private TestEntityManager entityManager;
	
	@Test
	public void findOne() {
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
		
		this.entityManager.persist(lead);
		Lead persistedLead = this.repository.findOne("sboot");
        assertEquals("1",persistedLead.getId());
        assertEquals("EFFECTIV",persistedLead.getCompany());
	}
	
	//@Test
	public void findOneDoesNotExist() {
		
	}
	
	//@Test
	public void findAll() {
		
	}
	
	//@Test
	public void findAllError() {
		
	}
	
	//@Test
	public void save() {
		
	}
	
	//@Test
	public void delete() {
		
	}
	
	@Test
	public void update() {
		
	}
	
	//@Test
	public void purge() {
		
	}
}
