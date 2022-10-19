package com.hard.qroyal.infrastructure.services.queries;

import com.hard.qroyal.domain.entities.Catalog;
import com.hard.qroyal.infrastructure.repositories.CatalogRepository;
import com.hard.qroyal.infrastructure.services.BaseQuery;
import com.hard.qroyal.infrastructure.services.commands.CatalogService;
import org.springframework.stereotype.Service;

@Service
public class CatalogQuery extends BaseQuery<Catalog, CatalogRepository> implements CatalogService {

}
