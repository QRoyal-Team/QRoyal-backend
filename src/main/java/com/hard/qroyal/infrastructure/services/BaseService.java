package com.hard.qroyal.infrastructure.services;

import com.hard.qroyal.domain.BaseEntity;
import java.util.List;

public interface BaseService<E extends BaseEntity> {

	public abstract E findById(Long id);

	public abstract List<E> findAll();

	public abstract E save(E entity);

	public abstract void delete(Long id);
}
