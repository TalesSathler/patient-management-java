package com.scp.patientmanagement.controller;

import com.scp.patientmanagement.model.Person;
import com.scp.patientmanagement.response.ResponseHandler;
import com.scp.patientmanagement.service.PersonServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RequestMapping("/api/person")
@RestController
public class PersonController extends BaseAbstractController {

    private final EntityManager entityManager;

    private final PersonServiceInterface personService;

    public PersonController(EntityManagerFactory entityManagerFactory, PersonServiceInterface personService) {
        this.entityManager = entityManagerFactory.createEntityManager();

        personService.setEntityManager(this.entityManager);
        this.personService = personService;
    }

    @GetMapping("")
    public ResponseEntity<Object> getPerson() {
        return ResponseHandler.generateResponse("Successfully retrieved person!", HttpStatus.OK, personService.getRepository().findAll());
    }

    @GetMapping("/{personId}")
    public ResponseEntity<Object> getPerson(@PathVariable Long personId) {
        Person person = personService.getRepository().findById(personId).orElse(null);
        if (person == null) {
            return ResponseHandler.generateResponse("Person not found!", HttpStatus.NOT_FOUND, personId);
        }

        return ResponseHandler.generateResponse("Successfully retrieved person!", HttpStatus.OK, person);
    }

    @Transactional
    @PostMapping("")
    public ResponseEntity<Object> store(@Valid @RequestBody Person person) {
        try {
            this.entityManager.getTransaction().begin();
            personService.save(person);
            this.entityManager.flush();
        } catch (RuntimeException $ex) {
            this.entityManager.getTransaction().rollback();
            return ResponseHandler.generateResponse("Error try storing person, please try again!", HttpStatus.OK, $ex.getMessage());
        }

        this.entityManager.getTransaction().commit();
        return ResponseHandler.generateResponse("Person stored with success!", HttpStatus.OK, person);
    }

    @Transactional
    @PutMapping("/{personId}")
    public ResponseEntity<Object> update(@Valid @RequestBody Person person, @PathVariable Long personId) {
        try {
            this.entityManager.getTransaction().begin();

            Person personEdit = personService.getRepository().findById(personId).orElse(null);
            if (personEdit == null) {
                return ResponseHandler.generateResponse("Person not found!", HttpStatus.NOT_FOUND, person);
            }

            personService.edit(personEdit, person);
            this.entityManager.flush();
        } catch (Exception $ex) {
            this.entityManager.getTransaction().rollback();
            return ResponseHandler.generateResponse("Error try updating person, please try again!", HttpStatus.OK, $ex.getMessage());
        }

        this.entityManager.getTransaction().commit();
        return ResponseHandler.generateResponse("Person updated with success!", HttpStatus.OK, person);
    }

    @Transactional
    @DeleteMapping("/{personId}")
    public ResponseEntity<Object> delete(@PathVariable Long personId) throws Exception {
        try {
            this.entityManager.getTransaction().begin();

            Person person = personService.getRepository().findById(personId).orElse(null);
            if (person == null) {
                return ResponseHandler.generateResponse("Person not found!", HttpStatus.NOT_FOUND, personId);
            }

            personService.remove(person);
            this.entityManager.flush();
        } catch (Exception $ex) {
            this.entityManager.getTransaction().rollback();
            return ResponseHandler.generateResponse("Error try deleting person, please try again!", HttpStatus.OK, $ex.getMessage());
        }

        this.entityManager.getTransaction().commit();
        return ResponseHandler.generateResponse("Person deleted with success!", HttpStatus.OK, null);
    }

}
