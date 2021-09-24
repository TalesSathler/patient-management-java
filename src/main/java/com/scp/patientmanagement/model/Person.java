package com.scp.patientmanagement.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.print.Doc;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "person_id", nullable = false)
    private Long personId;

    @NotBlank(message = "Name cannot be empty!")
    @Column(name = "person_name", nullable = false)
    protected String personName;

    @Size(max = 14, message = "CPF cannot be most than 14 characters!")
    @NotBlank(message = "CPF cannot be empty!")
    @Column(name = "person_cpf", nullable = false, unique = true)
    protected String personCPF;

    @Enumerated(EnumType.STRING)
    @Column(name = "person_type", nullable = false, columnDefinition = "varchar(10) default 'patient'")
    private PersonType personType;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "timestamp default current_timestamp")
    @CreatedDate
    protected Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    @LastModifiedDate
    protected Date updatedAt;

    @NotNull(message = "User of person cannot be empty!")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH, mappedBy = "person")
    private User user;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH, mappedBy = "person")
    private Doctor doctor;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH, mappedBy = "person")
    private Patient patient;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH, mappedBy = "person")
    private Nurse nurse;
}
