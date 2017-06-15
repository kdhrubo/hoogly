package com.effectiv.crm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.effectiv.crm.web.SearchCriteria;
import com.effectiv.crm.web.SearchRequest;

import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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

	
	
	@Override
	public Page<T> findAll(SearchRequest searchRequest, Pageable pageable) {
		
		
		log.info("Entity name : {}", entityInformation.getEntityName());
		
		
		List<T> content =  search(searchRequest,pageable);
				
		SimplePage<T> page = new SimplePage<T>();
		page.setContent(content);
		
		return page;
	}
	
	protected List<T> search(SearchRequest searchRequest, Pageable pageable) {
		
		Class<T> clazz = entityInformation.getJavaType();
		
		log.info("Entity clazz : {}", clazz);
		
		final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		
        final CriteriaQuery<T> query = builder.createQuery(clazz);
        final Root<T> r = query.from(clazz);

        Predicate predicate = builder.conjunction();

        for (final SearchCriteria param : searchRequest.getCriterias()) {
        	
        	
            if (param.getOperation().equalsIgnoreCase(">")) {
                predicate = builder.and(predicate, builder.greaterThanOrEqualTo(r.get(param.getKey()), param.getValue().toString()));
            } else if (param.getOperation().equalsIgnoreCase("<")) {
                predicate = builder.and(predicate, builder.lessThanOrEqualTo(r.get(param.getKey()), param.getValue().toString()));
            } else if (param.getOperation().equalsIgnoreCase(":")) {
            	
            log.info("### type --- {}", r.get(param.getKey()).getJavaType());
            	
                if (r.get(param.getKey()).getJavaType() == String.class) {
                    predicate = builder.and(predicate, builder.like(r.get(param.getKey()), "%" + param.getValue() + "%"));
                } 
                else if (r.get(param.getKey()).getJavaType() == boolean.class) {
                	log.info("Handling boolean parameter - {}", new Boolean((String)param.getValue()));
                	
                	predicate = builder.and(predicate, builder.equal(r.get(param.getKey()), new Boolean((String)param.getValue())) );
                }
                
                else {
                	
                    predicate = builder.and(predicate, builder.equal(r.get(param.getKey()), param.getValue()));
                }
            }
        }
        query.where(predicate);

        final List<T> result = entityManager.createQuery(query).getResultList();
        return result;
	}

	
}
