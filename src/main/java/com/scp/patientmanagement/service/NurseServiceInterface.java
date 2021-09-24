package com.scp.patientmanagement.service;

import com.scp.patientmanagement.model.Nurse;
import com.scp.patientmanagement.repository.NurseRepository;

import javax.persistence.EntityManager;

public interface NurseServiceInterface {

    public NurseRepository getRepository();

    public void setEntityManager(EntityManager entityManager);

    public Nurse save(Nurse nurse);

    public Nurse edit(Nurse nurseEdit, Nurse nurse);

    public boolean remove(Nurse nurse);

}
