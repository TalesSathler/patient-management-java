package com.scp.patientmanagement.service;

import com.scp.patientmanagement.model.Doctor;
import com.scp.patientmanagement.repository.DoctorRepository;

import javax.persistence.EntityManager;

public interface DoctorServiceInterface {

    public DoctorRepository getRepository();

    public void setEntityManager(EntityManager entityManager);

    public Doctor save(Doctor doctor);

    public Doctor edit(Doctor doctorEdit, Doctor doctor);

    public boolean remove(Doctor doctor);

}
