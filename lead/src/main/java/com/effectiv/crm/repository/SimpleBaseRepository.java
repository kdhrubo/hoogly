package com.effectiv.crm.repository;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

@Slf4j
public class SimpleBaseRepository<T, ID extends Serializable> extends SimpleJpaRepository<T, ID>
		implements BaseRepository<T, ID> {

	private JpaEntityInformation<T, ID> entityInformation;
	private EntityManager entityManager;

	public SimpleBaseRepository(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.entityInformation = entityInformation;
		this.entityManager = entityManager;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAllByDeleted(boolean deleted) {
		
		log.info("Deleted : {}", deleted);
		log.info("Entity name : {}", entityInformation.getEntityName());
		
		return entityManager.createQuery("SELECT t from " + entityInformation.getEntityName() + " t where t.deleted =:deleted")
				.setParameter("deleted", deleted)
				.getResultList();

	}
	


}
