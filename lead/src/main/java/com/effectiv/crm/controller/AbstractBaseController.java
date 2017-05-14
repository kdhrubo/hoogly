package com.effectiv.crm.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.effectiv.crm.business.AbstractBaseBusinessDelegate;
import com.effectiv.crm.domain.BaseEntity;
import com.effectiv.crm.web.SearchParams;
import com.effectiv.crm.web.SearchRequest;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

import javax.validation.Valid;

@Slf4j
public abstract class AbstractBaseController<T extends BaseEntity, Id extends Serializable> {

	@Getter
	protected AbstractBaseBusinessDelegate<T, Id> service;

	public AbstractBaseController(AbstractBaseBusinessDelegate<T, Id> service) {
		this.service = service;
	}
	
	@GetMapping("/hb")
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public boolean heartBeat() {
		return true;
	}
	
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	@ResponseBody
	public T create(@RequestBody @Valid T t) {
		log.info("Entity submitform - {}", t);
		return service.save(t);
	}
	
	
	@GetMapping()
	@ResponseStatus(value = HttpStatus.FOUND)
	@ResponseBody
	public Page<T> findAll(@SearchParams SearchRequest searchRequest, Pageable pageable) {

		log.info("pageable.pageNumber -> {}" , pageable.getPageNumber());
		log.info("pageable.pageSize -> {}" , pageable.getPageSize());
		log.info("pageable.sort -> {}" , pageable.getSort());
		
		return service.findAll(searchRequest, pageable);
	}

	@GetMapping("/{id}")
	@ResponseStatus(value = HttpStatus.FOUND)
	@ResponseBody
	public T findOne(@PathVariable("id") Id id) {
		log.info("### retrieving lead with id - {}",id);
		T t = service.findOne(id);
		log.info("### retrieved lead - {}",t);
		return t;
	}
	
	
	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public void delete(@PathVariable("id") Id id, @RequestParam(value="purge", defaultValue="false") boolean purge) {
		if(purge){
			service.purge(id);
		}
		else{
			service.delete(id);
		}
	}

	@PutMapping("/{id}/restore")
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public void restore(@PathVariable("id") Id id) {
		log.info("==== restore entity called ====");
		service.restore(id);
	}

}