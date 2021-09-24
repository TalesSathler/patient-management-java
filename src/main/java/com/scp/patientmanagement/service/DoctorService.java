package com.scp.patientmanagement.service;

import com.scp.patientmanagement.model.Doctor;
import com.scp.patientmanagement.repository.DoctorRepository;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.Objects;

@ComponentScan
@EnableTransactionManagement
@Service
public class DoctorService implements DoctorServiceInterface {

    private final DoctorRepository doctorRepository;

    private EntityManager entityManager;


    public DoctorService(EntityManager entityManager, DoctorRepository doctorRepository) {
        this.entityManager = entityManager;
        this.doctorRepository = doctorRepository;
    }

    /**
     * @return DoctorRepository
     */
    public DoctorRepository getRepository() {
        return this.doctorRepository;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Create a doctor
     * @param Doctor doctor
     * @return Doctor
     * @throws RuntimeException
     */
    @Override
    public Doctor save (Doctor doctor) throws RuntimeException {
        try {
            this.entityManager.persist(doctor);
        } catch (Exception $ex) {
            throw new RuntimeException($ex.getMessage());
        }

        return doctor;
    }

    /**
     * Edit a doctor
     * @param Doctor doctorEdit
     * @param Doctor doctor
     * @return Doctor
     * @throws RuntimeException
     */
    @Override
    public Doctor edit(Doctor doctorEdit, Doctor doctor) throws RuntimeException {
        try {
            if (doctor.getDoctorCRM() != null && !Objects.equals(doctor.getDoctorCRM(), "")) {
                doctorEdit.setDoctorCRM(doctor.getDoctorCRM());
            }

            Date now = new Date();
            doctorEdit.setUpdatedAt(now);

            this.entityManager.merge(doctorEdit);
        } catch (Exception $ex) {
            throw new RuntimeException($ex.getMessage());
        }

        return doctorEdit;
    }

    /**
     * Remove a doctor
     * @param Doctor doctor
     * @return boolean
     * @throws RuntimeException
     */
    @Override
    public boolean remove(Doctor doctor) throws RuntimeException {
        try {
            this.entityManager.remove(this.entityManager.merge(doctor));
            this.entityManager.flush();
        } catch (Exception $ex) {
            throw new RuntimeException($ex.getMessage());
        }

        return true;
    }
}
