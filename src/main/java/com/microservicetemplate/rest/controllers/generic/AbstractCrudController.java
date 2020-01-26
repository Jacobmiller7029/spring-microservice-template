package com.microservicetemplate.rest.controllers.generic;

import com.microservicetemplate.services.interfaces.CrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin
public class AbstractCrudController<T, U> {

    private final CrudService crudService;
    private final String entityName;

    private final HttpHeaders headers = new HttpHeaders();

    public AbstractCrudController(String entityName, CrudService crudService) {
        this.entityName = entityName;
        this.crudService = crudService;

        //TODO: Remove before push to prod
        headers.add("Access-Control-Allow-Origin", "localhost:8090, localhost:3000");
    }

    @ResponseBody
    @GetMapping
    public ResponseEntity searchForLanes(U queryParams) {
        log.info("GET ALL: {} with params: {}", entityName, queryParams.toString());
        return new ResponseEntity<List<T>>(crudService.getListOfEntities(queryParams), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/{id}")
    public ResponseEntity getSingleEntityById(@PathVariable String id) {
        log.info("GET: {} with Id: {}", entityName, id);
        T entity = (T) crudService.getEntityById(Long.valueOf(id));
        log.info("{} being returned: {}", entityName, entity);
        return new ResponseEntity<T>(entity, HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping
    public ResponseEntity saveNewEntity(@RequestBody List<T> entityToBeSaved) {
        log.info("POST: {} received: {}", entityName, entityToBeSaved);
        T savedEntity = (T) crudService.saveEntities(entityToBeSaved);
        log.info("{} being returned: {}", entityName, savedEntity);
        return new ResponseEntity<T>(savedEntity, HttpStatus.CREATED);
    }

    @ResponseBody
    @PutMapping("/{id}")
    public ResponseEntity updateEntity(@RequestBody T entityToBeUpdated) {
        log.info("PUT: {} received: {}", entityName, entityToBeUpdated);
        T savedEntity = (T) crudService.updateEntity(entityToBeUpdated);
        log.info("{} being returned: {}", entityName, savedEntity);
        return new ResponseEntity<T>(savedEntity, HttpStatus.OK);
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public ResponseEntity deleteEntityById(@PathVariable String id) {
        log.info("DELETE: {} with ID: {}", entityName, id);
        crudService.deleteEntityById(Long.valueOf(id));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
