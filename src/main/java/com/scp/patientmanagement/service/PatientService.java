package com.scp.patientmanagement.service;

import com.scp.patientmanagement.model.Patient;
import com.scp.patientmanagement.repository.PatientRepository;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@ComponentScan
@EnableTransactionManagement
@Service
public class PatientService implements PatientServiceInterface {

    private final PatientRepository patientRepository;

    private EntityManager entityManager;


    public PatientService(EntityManager entityManager, PatientRepository patientRepository) {
        this.entityManager = entityManager;
        this.patientRepository = patientRepository;
    }

    /**
     * @return PatientRepository
     */
    public PatientRepository getRepository() {
        return this.patientRepository;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Create a patient
     * @param Patient patient
     * @return Patient
     * @throws RuntimeException
     */
    @Override
    public Patient save (Patient patient) throws RuntimeException {
        try {
            this.entityManager.persist(patient);
        } catch (Exception $ex) {
            throw new RuntimeException($ex.getMessage());
        }

        return patient;
    }

    /**
     * Edit a patient
     * @param Patient patientEdit
     * @param Patient patient
     * @return Patient
     * @throws RuntimeException
     */
    @Override
    public Patient edit(Patient patientEdit, Patient patient) throws RuntimeException {
        try {
            if (patient.getPatientUF() != null && !Objects.equals(patient.getPatientUF(), "")) {
                patientEdit.setPatientUF(patient.getPatientUF());
            }

            if (patient.getPatientHeight() != null && !Objects.equals(patient.getPatientHeight(), "")) {
                patientEdit.setPatientHeight(patient.getPatientHeight());
            }

            if (patient.getPatientBirthday() != null && !Objects.equals(patient.getPatientBirthday(), "")) {
                patientEdit.setPatientBirthday(patient.getPatientBirthday());
            }

            if (patient.getPatientWeight() != null && !Objects.equals(patient.getPatientWeight(), "")) {
                patientEdit.setPatientWeight(patient.getPatientWeight());
            }

            Date now = new Date();
            patientEdit.setUpdatedAt(now);

            this.entityManager.merge(patientEdit);
        } catch (Exception $ex) {
            throw new RuntimeException($ex.getMessage());
        }

        return patientEdit;
    }

    /**
     * Remove a patient
     * @param Patient patient
     * @return boolean
     * @throws RuntimeException
     */
    @Override
    public boolean remove(Patient patient) throws RuntimeException {
        try {
            this.entityManager.remove(this.entityManager.merge(patient));
            this.entityManager.flush();
        } catch (Exception $ex) {
            throw new RuntimeException($ex.getMessage());
        }

        return true;
    }
}
