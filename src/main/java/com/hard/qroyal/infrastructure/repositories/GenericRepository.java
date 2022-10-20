package com.hard.qroyal.infrastructure.repositories;

import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericRepository<T> extends ReadOnlyRepository<T>, PersistRepository<T> {

}
