package com.thoughtworks.springbootemployee.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "company")
public class Company {

    @Id
    private Integer id;

    @Column(name = "COMPANYNAME")
    private String companyName;

    @Column(name = "EMPLOYEENUMBER")
    private int employeeNumber;

    @OneToMany(cascade = {CascadeType.ALL},mappedBy = "companyId")
    private List<Employee> employees;

}
