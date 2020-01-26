package com.microservicetemplate.rest.controllers;

import com.microservicetemplate.domain.TemplateDomain;
import com.microservicetemplate.rest.controllers.generic.AbstractCrudController;
import com.microservicetemplate.rest.controllers.query_params.TemplateDomainQueryParams;
import com.microservicetemplate.services.interfaces.TemplateDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@CrossOrigin
@RequestMapping("/template")
public class TemplateDomainController extends AbstractCrudController<TemplateDomain, TemplateDomainQueryParams> {

    public TemplateDomainController(TemplateDomainService service) {
        super(TemplateDomain.class.getSimpleName(), service);
    }

}
