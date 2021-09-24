package com.scp.patientmanagement.service;

import com.scp.patientmanagement.model.Nurse;
import com.scp.patientmanagement.repository.NurseRepository;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.Objects;

@ComponentScan
@EnableTransactionManagement
@Service
public class NurseService implements NurseServiceInterface {

    private final NurseRepository nurseRepository;

    private EntityManager entityManager;


    public NurseService(EntityManager entityManager, NurseRepository nurseRepository) {
        this.entityManager = entityManager;
        this.nurseRepository = nurseRepository;
    }

    /**
     * @return NurseRepository
     */
    public NurseRepository getRepository() {
        return this.nurseRepository;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Create a nurse
     * @param Nurse nurse
     * @return Nurse
     * @throws RuntimeException
     */
    @Override
    public Nurse save (Nurse nurse) throws RuntimeException {
        try {
            this.entityManager.persist(nurse);
        } catch (Exception $ex) {
            throw new RuntimeException($ex.getMessage());
        }

        return nurse;
    }

    /**
     * Edit a nurse
     * @param Nurse nurseEdit
     * @param Nurse nurse
     * @return Nurse
     * @throws RuntimeException
     */
    @Override
    public Nurse edit(Nurse nurseEdit, Nurse nurse) throws RuntimeException {
        try {
            if (nurse.getNurseCOREN() != null && !Objects.equals(nurse.getNurseCOREN(), "")) {
                nurseEdit.setNurseCOREN(nurse.getNurseCOREN());
            }

            Date now = new Date();
            nurseEdit.setUpdatedAt(now);

            this.entityManager.merge(nurseEdit);
        } catch (Exception $ex) {
            throw new RuntimeException($ex.getMessage());
        }

        return nurseEdit;
    }

    /**
     * Remove a nurse
     * @param Nurse nurse
     * @return boolean
     * @throws RuntimeException
     */
    @Override
    public boolean remove(Nurse nurse) throws RuntimeException {
        try {
            this.entityManager.remove(this.entityManager.merge(nurse));
            this.entityManager.flush();
        } catch (Exception $ex) {
            throw new RuntimeException($ex.getMessage());
        }

        return true;
    }
}
