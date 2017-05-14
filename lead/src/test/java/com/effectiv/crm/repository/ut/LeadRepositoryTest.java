package com.effectiv.crm.repository.ut;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.effectiv.crm.domain.Lead;
import com.effectiv.crm.repository.LeadRepository;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

/**
 * Lead Repository Unit test
 * This test cannot be run with @DataJpaTest due to issues
 * with Spring Data JPA custom repository method
 * @author Dhrubo
 *
 */

@SpringBootTest(webEnvironment=WebEnvironment.NONE)
@Transactional
@RunWith(SpringRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class })
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
//@WithMockUser(username = "test@t.com", roles = { "ADMIN", "USER" })
@DatabaseSetup("lead.xml")
//TODO WithSecurityContextTestExecutionListener.class to be added in future to include security in this test
public class LeadRepositoryTest {
	
	@Autowired
	private LeadRepository repository;
	
	@Test
	public void findOne() {
		
		Lead persistedLead = this.repository.findOne("1");
        assertEquals("1",persistedLead.getId());
        assertEquals("Virat",persistedLead.getFirstName());
	}
	
	//@Test
	public void findOneDoesNotExist() {
		//TODO - Need to provide implementation
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
