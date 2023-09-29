package com.nighthawk.spring_portfolio.mvc.test;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import groovy.transform.Generated;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Student {
    // Primary column and Key value
    @Id
    // @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    // Non-null, String plaintext password in backend
    @Column(columnDefinition = "String")
    @NotNull
    private String studentPassword;

    // Unique, String, Email formatted student Email
    @Column(unique=true, columnDefinition = "String")
    @NotNull
    @Email
    private String studentEmail;

    // 
    @Column(columnDefinition = "String")
    @NotNull
    private String firstName;

    @Column(columnDefinition = "String")
    @NotNull
    private String lastName;

    // The init method allows to initialize some objects within our table
    public Student[] init() {
        Student stu1 = new Student();
        stu1.setId((long) 1944773);
        stu1.setStudentPassword("1944773");
        stu1.setStudentEmail("jishnus2944773@stu.powayusd.com");
        stu1.setFirstName("Jishnu");
        stu1.setLastName("Singiresu");

        Student stu2 = new Student();
        stu2.setId((long) 1860793);
        stu2.setStudentPassword("1860793");
        stu2.setStudentEmail("drewr860793@stu.powayusd.com");
        stu2.setFirstName("Drew");
        stu2.setLastName("Reed");

        Student stu3 = new Student();
        stu3.setId((long) 1904002);
        stu3.setStudentPassword("1904002");
        stu3.setStudentEmail("derrickh04002@stu.powayusd.com");
        stu3.setFirstName("Derrick");
        stu3.setLastName("Huang");

        Student stu4 = new Student();
        stu4.setId((long) 1942185);
        stu4.setStudentPassword("1942185");
        stu4.setStudentEmail("davidv942185@stu.powayusd.com");
        stu4.setFirstName("David");
        stu4.setLastName("Vasilev");

        Student stu5 = new Student();
        stu5.setId((long) 1920450);
        stu5.setStudentPassword("1920450");
        stu5.setStudentEmail("alexanderl920450@stu.powayusd.com");
        stu5.setFirstName("Alexander");
        stu5.setLastName("Lu");

        Student[] studentsArray = {stu1, stu2, stu3, stu4, stu5};
        return studentsArray;
    }

}
