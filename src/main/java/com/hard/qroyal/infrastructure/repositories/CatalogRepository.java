package com.hard.qroyal.infrastructure.repositories;

import com.hard.qroyal.domain.entities.Catalog;
import com.hard.qroyal.infrastructure.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogRepository extends GenericRepository<Catalog> {

}
