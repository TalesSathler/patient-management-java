package com.scp.patientmanagement.service;

import com.scp.patientmanagement.model.*;
import com.scp.patientmanagement.repository.PersonRepository;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.Objects;

@ComponentScan
@EnableTransactionManagement
@Service
public class PersonService implements PersonServiceInterface {

    private final PersonRepository personRepository;

    public EntityManager entityManager;

    private final UserServiceInterface userService;
    private final DoctorServiceInterface doctorService;
    private final NurseServiceInterface nurseService;
    private final PatientServiceInterface patientService;



    public PersonService(EntityManager entityManager, PersonRepository personRepository, UserServiceInterface userService,
                         DoctorServiceInterface doctorService, NurseServiceInterface nurseService, PatientServiceInterface patientService) {
        this.entityManager = entityManager;
        this.personRepository = personRepository;
        this.doctorService = doctorService;
        this.nurseService = nurseService;
        this.patientService = patientService;

        userService.setEntityManager(entityManager);
        this.userService = userService;
    }

    public PersonRepository getRepository() {
        return this.personRepository;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Person save(Person person) throws RuntimeException {
        try {
            person.setPersonCPF(person.getPersonCPF().replaceAll("[^0-9]", ""));
            this.entityManager.persist(person);

            //Create user
            User user = person.getUser();
            user.setPerson(person);
            this.userService.save(user);

            //Save another tables related with person
            switch (person.getPersonType().toString()) {
                case "doctor":
                    person.setPersonType(PersonType.doctor);

                    Doctor doctor = person.getDoctor();
                    doctor.setPerson(person);

                    this.doctorService.save(doctor);
                    break;

                case "nurse":
                    person.setPersonType(PersonType.nurse);

                    Nurse nurse = person.getNurse();
                    nurse.setPerson(person);

                    this.nurseService.save(nurse);
                    break;

                default:
                    //patient
                    person.setPersonType(PersonType.patient);

                    Patient patient = person.getPatient();
                    patient.setPerson(person);

                    this.patientService.save(patient);
                    break;
            }

        } catch (Exception $ex) {
            throw new RuntimeException($ex.getMessage());
        }

        return person;
    }

    @Override
    public Person edit(Person personEdit, Person person) throws RuntimeException {
        try {
            if (person.getPersonCPF() != null && !Objects.equals(person.getPersonCPF(), "")) {
                personEdit.setPersonCPF(person.getPersonCPF().replaceAll("[^0-9]", ""));
            }

            if (person.getPersonName() != null && !Objects.equals(person.getPersonName(), "")) {
                personEdit.setPersonName(person.getPersonName());
            }

            if (person.getPersonType() != null && !Objects.equals(person.getPersonType(), "")) {
                //Save another tables related with person
                switch (person.getPersonType().toString()) {
                    case "doctor":
                        person.setPersonType(PersonType.doctor);

                        //Edit doctor
                        Doctor doctor = personEdit.getDoctor();
                        if (doctor != null) {
                            Doctor doctorEdit = this.doctorService.getRepository().findById(doctor.getDoctorId()).orElse(null);
                            this.doctorService.edit(doctorEdit, person.getDoctor());
                        }
                        break;

                    case "nurse":
                        person.setPersonType(PersonType.nurse);

                        //Edit nurse
                        Nurse nurse = personEdit.getNurse();
                        if (nurse != null) {
                            Nurse nurseEdit = this.nurseService.getRepository().findById(nurse.getNurseId()).orElse(null);
                            this.nurseService.edit(nurseEdit, person.getNurse());
                        }
                        break;

                    default:
                        //patient
                        person.setPersonType(PersonType.patient);

                        //Edit patient
                        Patient patient = personEdit.getPatient();
                        if (patient != null) {
                            Patient patientEdit = this.patientService.getRepository().findById(patient.getPatientId()).orElse(null);
                            this.patientService.edit(patientEdit, person.getPatient());
                        }
                        break;
                }
            }

            Date now = new Date();
            personEdit.setUpdatedAt(now);

            this.entityManager.merge(personEdit);


            //Edit user
            User user = personEdit.getUser();
            if (user != null) {
                User userEdit = this.userService.getRepository().findById(user.getUserId()).orElse(null);
                this.userService.edit(userEdit, person.getUser());
            }
        } catch (Exception $ex) {
            throw new RuntimeException($ex.getMessage());
        }

        return personEdit;
    }

    @Override
    public boolean remove(Person person) throws RuntimeException {
        try {
            this.userService.remove(person.getUser());

            if (person.getDoctor() != null) {
                this.doctorService.remove(person.getDoctor());
            }

            if (person.getNurse() != null) {
                this.nurseService.remove(person.getNurse());
            }

            if (person.getPatient() != null) {
                this.patientService.remove(person.getPatient());
            }

//            person.setUser(null);
//            person.setDoctor(null);
//            person.setNurse(null);
//            person.setPatient(null);
            this.entityManager.remove(this.entityManager.merge(person));
        } catch (Exception $ex) {
            throw new RuntimeException($ex.getMessage());
        }

        return true;
    }

}
