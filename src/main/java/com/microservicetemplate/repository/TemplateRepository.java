package com.microservicetemplate.repository;

import com.microservicetemplate.domain.TemplateDomain;
import org.springframework.data.repository.CrudRepository;

public interface TemplateRepository extends CrudRepository<TemplateDomain, Long> {



}
