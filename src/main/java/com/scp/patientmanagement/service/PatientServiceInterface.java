package com.scp.patientmanagement.service;

import com.scp.patientmanagement.model.Patient;
import com.scp.patientmanagement.repository.PatientRepository;

import javax.persistence.EntityManager;

public interface PatientServiceInterface {

    public PatientRepository getRepository();

    public void setEntityManager(EntityManager entityManager);

    public Patient save(Patient patient);

    public Patient edit(Patient patientEdit, Patient patient);

    public boolean remove(Patient patient);

}
