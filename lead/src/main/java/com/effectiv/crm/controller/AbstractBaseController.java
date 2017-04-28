package com.effectiv.crm.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.effectiv.crm.business.AbstractBaseBusinessDelegate;
import com.effectiv.crm.domain.BaseEntity;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

import java.util.List;

import javax.validation.Valid;

@Slf4j
public abstract class AbstractBaseController<T extends BaseEntity, Id extends Serializable> {

	@Getter
	protected AbstractBaseBusinessDelegate<T, Id> service;

	public AbstractBaseController(AbstractBaseBusinessDelegate<T, Id> service) {
		this.service = service;
	}

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	@ResponseBody
	public T save(@RequestBody @Valid T t) {
		log.info("Entity submitform - {}", t);
		return service.save(t);
	}

	@GetMapping("/")
	@ResponseStatus(value = HttpStatus.FOUND)
	@ResponseBody
	public List<T> findAll(@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
			@RequestParam(value = "pageNo", defaultValue = "0") int pageNo, @RequestParam("deleted") boolean deleted) {

		// TODO - Use pageSize, pageNo
		return service.findAllByDeleted(deleted);
	}

	@GetMapping("/{id}")
	@ResponseStatus(value = HttpStatus.FOUND)
	@ResponseBody
	public T findOne(@PathVariable("id") Id id) {
		return service.findOne(id);
	}
	
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Id id) {
		service.delete(id);
	}

	@DeleteMapping("/deleteall")
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public void delete(@RequestParam(value = "id") Id[] id) {
		service.deleteAll(id);
	}

	@DeleteMapping("/{id}/purge")
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public void purge(@PathVariable("id") Id id) {
		service.purge(id);
	}

	@RequestMapping("/{id}/restore")
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public void restore(@PathVariable("id") Id id) {
		service.restore(id);
	}

}