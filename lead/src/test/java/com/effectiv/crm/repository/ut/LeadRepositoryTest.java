package com.effectiv.crm.repository.ut;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	@Test
	public void findOneDoesNotExist() {
		//TODO - Need to provide implementation
		Lead noLeadEntry = this.repository.findOne("26");
		assertNull(noLeadEntry);
	}
	
	@Test
	public void findAll() {
		List<Lead> leadList = repository.findAll();
		assertEquals(25, leadList.size());
		assertEquals("Sachin", leadList.get(13).getFirstName());
	}
	
	//@Test
	public void findAllNotfound() {
	}
	
	@Test
	public void save() {
		Lead lead = new Lead();
		lead.setAddress(null);
		lead.setAnnualRevenue(50000.00D);
		// TODO - set null now, to be changed after security audit
		// implementation
		// lead.setAssignedUser(arg0);
		lead.setCompany("EFFECTIV");
		lead.setDeleted(false);
		lead.setDescription("Unit - test - entity :: Lead");
		lead.setDesignation("Project Manager");
		lead.setEmail("effectvlead@effectiv.com");
		lead.setEmailOptOut(false);

		Map<String, String> extension = new HashMap<String, String>();
		extension.put("flex_field_string", "some value");
		extension.put("flex_field_date", "02/20/2017");
		extension.put("flex_field_number", "77");

		lead.setExtensions(extension);

		lead.setFacebook("effectiv");
		lead.setFax("22445566");
		lead.setFirstName("Effectiv");
		lead.setLastName("Systems");
		lead.setIndustry(null);
		lead.setLeadSource(null);
		lead.setLeadStatus(null);
		lead.setMobile("1234567890");
		lead.setNoOfEmployees(22);
		lead.setPhone("9999988888");
		lead.setRating(null);
		lead.setSalutation(null);
		lead.setTwitter("@effectiv");
		lead.setWebsite("http://www.effectivcrm.com");

		Lead savedLead = repository.save(lead);

		assertNotNull(savedLead.getId());

	}
	
	@Test
	public void delete() {
		Lead retrievedLead = this.repository.findOne("11");
		retrievedLead.setDeleted(true);
		repository.save(retrievedLead);
		retrievedLead=repository.findOne("11");
	    assertEquals(true, retrievedLead.isDeleted());
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
	
	@Test
	public void purge() {
		Lead lead = repository.findOne("16");
		assertNotNull(lead);
		assertEquals(lead.getId(), "16");

		repository.delete("16");

		lead = repository.findOne("16");
		assertNull(lead);
	}
}
