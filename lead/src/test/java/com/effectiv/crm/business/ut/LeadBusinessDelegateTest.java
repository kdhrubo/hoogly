package com.effectiv.crm.business.ut;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import com.effectiv.crm.business.LeadBusinessDelegate;
import com.effectiv.crm.domain.Lead;
import com.effectiv.crm.repository.LeadRepository;
import com.effectiv.crm.web.SearchRequest;

import static org.assertj.core.api.Assertions.*;

public class LeadBusinessDelegateTest {
	private LeadBusinessDelegate leadBusinessDelegate;

	@Autowired
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
		when(leadRepository.findOne(any(String.class))).thenReturn(l);

		Lead rl = leadBusinessDelegate.findOne("1");

		assertEquals("1", rl.getId());
		assertEquals("Virat", rl.getFirstName());
		assertEquals("Kohli", rl.getLastName());
	}

	@Test
	public void findOneDoesNotExist() {
		// TODO
		when(leadRepository.findOne(any(String.class))).thenReturn(null);
		Lead rl = leadBusinessDelegate.findOne("1");

		assertThat(rl).isNull();

		verify(leadRepository, times(1)).findOne("1");
		verifyNoMoreInteractions(leadRepository);
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

		when(leadRepository.findAll(any(SearchRequest.class), any(Pageable.class))).thenReturn(new PageImpl<>(leads));

		Page<Lead> rLeadPage = leadBusinessDelegate.findAll(new SearchRequest(), new PageRequest(1, 5));
		assertEquals(5L, rLeadPage.getTotalElements());
		assertEquals(1, rLeadPage.getTotalPages());

		List<Lead> rLeads = rLeadPage.getContent();
		int counter = 1;
		for (Lead ld : rLeads) {
			assertEquals(counter + "", ld.getId());
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
		// mock
		Lead lead = new Lead();
		lead.setAnnualRevenue(50000.00D);
		lead.setCompany("EFFECTIV");
		lead.setDeleted(false);
		lead.setDescription("Unit - test - entity :: Lead");
		lead.setDesignation("Project Manager");
		lead.setEmail("effectvlead@effectiv.com");
		lead.setEmailOptOut(false);
		lead.setId("1");
		when(leadRepository.findOne(any(String.class))).thenReturn(lead);
		leadBusinessDelegate.delete("1");
		ArgumentCaptor<Lead> leadCaptor = ArgumentCaptor.forClass(Lead.class);

		verify(leadRepository, times(1)).findOne("1");
		verify(leadRepository, times(1)).save(leadCaptor.capture());

		verifyNoMoreInteractions(leadRepository);

		Lead deletedLead = leadCaptor.getValue();
		// check same lead

		assertThat(deletedLead.getId()).isEqualTo("1");

		// check if deleted flag is true
		assertThat(deletedLead.isDeleted()).isEqualTo(true);
	}

	@Test
	public void testPurge() {
		Lead lead = new Lead();
		lead.setId("18");
		when(leadRepository.findOne(any(String.class))).thenReturn(lead);
		leadBusinessDelegate.delete("18");
		verify(leadRepository, times(1)).findOne("18");

		ArgumentCaptor<Lead> leadCaptor = ArgumentCaptor.forClass(Lead.class);

		verify(leadRepository, times(1)).findOne("18");
		verify(leadRepository, times(1)).save(leadCaptor.capture());
		Lead deletedLead = leadCaptor.getValue();
		assertThat(deletedLead.getId()).isEqualTo("18");
		assertThat(deletedLead.isDeleted()).isEqualTo(true);
		assertNotNull(deletedLead);
	}

	@Test
	public void testRestore() {
		// fail("Not yet implemented");
	}

}
