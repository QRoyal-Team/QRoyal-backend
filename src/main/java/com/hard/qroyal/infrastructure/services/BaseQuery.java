package com.hard.qroyal.infrastructure.services;

import com.hard.qroyal.domain.BaseEntity;
import com.hard.qroyal.infrastructure.repositories.GenericRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Transactional
public abstract class BaseQuery<E extends BaseEntity, R extends GenericRepository<E>> {

	@Autowired
	protected R repository;

	@Autowired
	protected EntityManager entityManager;

	public E findById(Long id) {
		log.info("Find item by id: {}", id);
		return repository.findById(id).orElse(null);
	}

	public List<E> findAll() {
		log.info("Find all items");
		return repository.findAll();
	}

	public E save(E entity) {
		repository.saveAndFlush(entity);
		entityManager.refresh(entity);
		return entity;
	}

	public void delete(E entity) {
		repository.delete(entity);
	}
}
