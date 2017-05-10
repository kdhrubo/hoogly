package com.effectiv.crm.business;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.effectiv.crm.domain.Lead;
import com.effectiv.crm.repository.LeadRepository;
import com.effectiv.crm.web.SearchRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LeadBusinessDelegateTest {
	private LeadBusinessDelegate leadBusinessDelegate;
	private LeadRepository leadRepository;

	@Before
	public void setUp() {
		leadRepository = Mockito.mock(LeadRepository.class);
		leadBusinessDelegate = new LeadBusinessDelegate(leadRepository);
	}

	@Test
	public void findOne() {
		Lead l = new Lead();
		l.setId("1");
		l.setFirstName("Virat");
		l.setLastName("Kohli");
		// mock
		when(leadRepository.findOne(any(String.class)))
			.thenReturn(l);
		
		Lead rl = leadBusinessDelegate.findOne("1");
		
		assertEquals("1", rl.getId());
		assertEquals("Virat", rl.getFirstName());
		assertEquals("Kohli", rl.getLastName());
	}
	
	@Test
	public void findOneDoesNotExist() {
		//TODO
	}

	@Test
	public void testFindAll() {
		List<Lead> leads = new ArrayList<Lead>();
		Lead l = new Lead();
		l.setId("1");
		leads.add(l);
		
		l = new Lead();
		l.setId("2");
		leads.add(l);
		
		l = new Lead();
		l.setId("3");
		leads.add(l);
		
		l = new Lead();
		l.setId("4");
		leads.add(l);
		
		l = new Lead();
		l.setId("5");
		leads.add(l);
		
		when(leadRepository.findAll(any(SearchRequest.class), any(Pageable.class)))
		.thenReturn(new PageImpl<>(leads));
		
		Page<Lead> rLeadPage = leadBusinessDelegate.findAll(new SearchRequest(), new PageRequest(1, 5));
		assertEquals(5L, rLeadPage.getTotalElements());
		assertEquals(1, rLeadPage.getTotalPages());
		
		List<Lead> rLeads = rLeadPage.getContent();
		int counter = 1;
		for(Lead ld : rLeads) {
			assertEquals(counter + "",ld.getId());
			counter++;
		}
	}

	@Test
	public void testSave() {
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
		// mock
		when(leadRepository.save(any(Lead.class))).thenReturn(lead);
	}

	@Test
	public void testDelete() {
		//fail("Not yet implemented");
	}

	@Test
	public void testPurge() {
		//fail("Not yet implemented");
	}

	@Test
	public void testRestore() {
		//fail("Not yet implemented");
	}

}
