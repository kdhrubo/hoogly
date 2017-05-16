package com.effectiv.crm.repository.ut;

import static org.assertj.core.api.Assertions.*;


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
        
        assertThat(persistedLead.getId()).isEqualTo("1");
        assertThat(persistedLead.getFirstName()).isEqualTo("Virat");
        
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
	
	@Test
	public void delete() {
		Lead retrievedLead = this.repository.findOne("1");
		
	}
	
	@Test
	public void update() {
		Lead retrievedLead = this.repository.findOne("1");
		
		retrievedLead.setFirstName("Viraat");
		retrievedLead.setLastName("Kohli");
		retrievedLead.setAnnualRevenue(6000);
		
		Lead persistedLead = this.repository.save(retrievedLead);
		
		assertThat(persistedLead.getId()).isEqualTo(retrievedLead.getId()); //update must not change id
		assertThat(persistedLead.getFirstName()).isEqualTo("Viraat"); 
		assertThat(persistedLead.getLastName()).isEqualTo("Kohli"); 
		assertThat(persistedLead.getAnnualRevenue()).isEqualTo(6000); 
	}
	
	//@Test
	public void purge() {
		
	}
}
