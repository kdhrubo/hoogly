package com.effectiv.crm.business;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.effectiv.crm.domain.BaseEntity;
import com.effectiv.crm.exception.BusinessException;
import com.effectiv.crm.repository.BaseRepository;

import jodd.bean.BeanUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractBaseBusinessDelegate<T extends BaseEntity, Id extends Serializable> {
	@Getter
	private BaseRepository<T, Id> repository;

	public AbstractBaseBusinessDelegate(BaseRepository<T, Id> repository) {
		this.repository = repository;
	}

	@Transactional(readOnly = true)
	public T findOne(Id id) {
		return repository.findOne(id);
	}

	@Transactional(readOnly = true)
	public List<T> findAll() {
		return repository.findAll();
	}

	@Transactional(readOnly = true)
	public List<T> findAllByDeleted(boolean deleted) {
		log.info("## repository - {}", repository.getClass().getSimpleName());

		return repository.findAllByDeleted(deleted);
	}

	@Transactional
	public T save(T t) {
		log.info("saving ---> " + t);
		return repository.save(t);
	}

	@Transactional
	public void delete(Id id) {

		T t = findOne(id);

		if (t == null) {
			throw new BusinessException(
					"Record with id = " + id + " not found. May be it is already deleted or purged. ");
		}

		try {

			BeanUtil.silent.setProperty(t, "deleted", true);
		} catch (Exception e) {
			log.error("Error while setting deleted property in restore - {}", e);
			throw new BusinessException("Entity cannot be deleted (soft delete) - " + e.getLocalizedMessage()
					+ ". Check if deleted field is present.");
		}
		repository.save(t);
	}

	@Transactional
	public void purge(Id id) {
		repository.delete(id);
	}

	

	@Transactional
	public void deleteAll(Id[] ids) {
		// Since we are getting unique id of the entity we do not require to use
		// the parent id here.
		for (Id id : ids) {
			delete(id);
		}

	}

	

	@Transactional
	public void restore(Id id) {

		T t = findOne(id);

		if (t == null) {
			throw new BusinessException(
					"Record with id = " + id + " not found. May be it is already deleted or purged. ");
		}

		try {

			BeanUtil.silent.setProperty(t, "deleted", false);
		} catch (Exception e) {
			log.error("Error while setting deleted property in restore - {}", e);
			throw new BusinessException("Entity cannot be deleted (soft delete) - " + e.getLocalizedMessage()
					+ ". Check if deleted field is present.");
		}
		repository.save(t);
	}

}