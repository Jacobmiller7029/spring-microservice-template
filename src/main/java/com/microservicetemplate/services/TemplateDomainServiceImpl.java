package com.microservicetemplate.services;

import com.microservicetemplate.domain.TemplateDomain;
import com.microservicetemplate.rest.controllers.query_params.TemplateDomainQueryParams;
import com.microservicetemplate.services.interfaces.TemplateDomainService;

import java.util.List;

public class TemplateDomainServiceImpl implements TemplateDomainService {
    @Override
    public List<TemplateDomain> getListOfEntities(TemplateDomainQueryParams queryParams) {
        return null;
    }

    @Override
    public List<TemplateDomain> saveEntities(List<TemplateDomain> newEntity) {
        return null;
    }

    @Override
    public TemplateDomain getEntityById(Long id) {
        return null;
    }

    @Override
    public TemplateDomain updateEntity(TemplateDomain entityToUpdate) {
        return null;
    }

    @Override
    public void deleteEntityById(Long id) {

    }
}
