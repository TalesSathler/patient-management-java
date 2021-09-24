package com.scp.patientmanagement.service;

import com.scp.patientmanagement.model.Person;
import com.scp.patientmanagement.repository.PersonRepository;

import javax.persistence.EntityManager;

public interface PersonServiceInterface {

    public PersonRepository getRepository();

    public void setEntityManager(EntityManager entityManager);

    public Person save(Person person);

    public Person edit(Person personEdit, Person person);

    public boolean remove(Person person);

}
