package com.scp.patientmanagement.service;

import com.scp.patientmanagement.model.User;
import com.scp.patientmanagement.repository.UserRepository;

import javax.persistence.EntityManager;

public interface UserServiceInterface {

    public UserRepository getRepository();

    public void setEntityManager(EntityManager entityManager);

    public User save(User user);

    public User edit(User userEdit, User user);

    public boolean remove(User user);

}
