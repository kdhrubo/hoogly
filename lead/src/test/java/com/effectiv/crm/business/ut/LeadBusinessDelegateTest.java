package com.effectiv.crm.business.ut;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
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
		
		assertThat(rl.getId()).isEqualTo("1");
		assertThat(rl.getFirstName()).isEqualTo("Virat");
		assertThat(rl.getLastName()).isEqualTo("Kohli");
		
		verify(leadRepository, times(1)).findOne("1");
		verifyNoMoreInteractions(leadRepository);
	}

	@Test
	public void findOneDoesNotExist() {
		when(leadRepository.findOne(any(String.class))).thenReturn(null);
		Lead rl = leadBusinessDelegate.findOne("1");

		assertThat(rl).isNull();

		verify(leadRepository, times(1)).findOne("1");
		verifyNoMoreInteractions(leadRepository);
	}

	@Test
	public void findAll() {
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
		
		SearchRequest sr = new SearchRequest();
		PageRequest pr = new PageRequest(1, 5);
		
		Page<Lead> rLeadPage = leadBusinessDelegate.findAll(sr, pr);
		assertThat(rLeadPage.getTotalElements()).isEqualTo(5L);
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

	@Test
	public void save() {
		Lead lead = new Lead();
		lead.setFirstName("Viraat");
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
		Lead sLead = new Lead();
		Lead rLead = leadBusinessDelegate.save(new Lead());
		
		//check id 
		assertThat(rLead.getId()).isEqualTo("1");
		assertThat(rLead.getFirstName()).isEqualTo("Viraat");
		
		verify(leadRepository, times(1)).save(sLead);
		verifyNoMoreInteractions(leadRepository);
		
	}

	@Test
	public void delete() {
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
	public void purge() {
		
		doNothing().when(leadRepository).delete(any(String.class));
		leadBusinessDelegate.purge("18");
		ArgumentCaptor<String> leadIdCaptor = ArgumentCaptor.forClass(String.class);
		
		verify(leadRepository, times(1)).delete(leadIdCaptor.capture());
		verifyNoMoreInteractions(leadRepository);
		assertThat(leadIdCaptor.getValue()).isEqualTo("18");
	}

	@Test
	public void restore() {
		Lead lead = new Lead();
		lead.setId("20");
		lead.setDeleted(true);
		
		when(leadRepository.findOne(any(String.class))).thenReturn(lead);
		leadBusinessDelegate.restore("20");
		
		verify(leadRepository, times(1)).findOne("20");
		ArgumentCaptor<Lead> leadCaptor = ArgumentCaptor.forClass(Lead.class);
		
		verify(leadRepository, times(1)).findOne("20");
		verify(leadRepository, times(1)).save(leadCaptor.capture());
		
		Lead deletedLead = leadCaptor.getValue();
		// check same lead

		assertThat(deletedLead.getId()).isEqualTo("20");
		//restore means deleted = false, this the main test to check if the 
		//flag was flipped before call to repository save
		assertThat(deletedLead.isDeleted()).isEqualTo(false); 
		
		
	}

}
