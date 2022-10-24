package com.hard.qroyal.integration;

import com.hard.qroyal.domain.BaseMapper;
import com.hard.qroyal.infrastructure.services.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseController<S extends BaseService, M extends BaseMapper> {

	@Autowired
	protected S service;

	@Autowired
	protected M mapper;
}
