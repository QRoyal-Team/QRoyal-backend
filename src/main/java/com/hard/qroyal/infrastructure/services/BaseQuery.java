package com.hard.qroyal.infrastructure.services;

import com.hard.qroyal.domain.BaseEntity;
import com.hard.qroyal.infrastructure.GenericRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Slf4j
public abstract class BaseQuery<E extends BaseEntity, R extends GenericRepository<E>> {

	@Autowired
	protected R repository;

	public E findById(Long id) {
		log.info("Find item by id: {}", id);
		return repository.findById(id).orElse(null);
	}

	public List<E> findAll() {
		log.info("Find all items");
		return repository.findAll();
	}

	public E save(E entity) {
		return repository.save(entity);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}
}
