package com.example.studentProgress.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "teachers")
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firs_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Email(message = "please provide a valid email address")
    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    @Size(min = 10,max = 10, message = "please enter 10 character")
    private String phoneNumber;

    @Column(name = "branch", nullable = false)
    @NotBlank(message = "please provide a valid branch")
    private String branch;

    /*
    1 teacher 1 user ilişkisi

     */
    @OneToOne
    @JoinColumn(name= "user_id", referencedColumnName = "id")
    private User user;


    /*
     1 Teacher - N Exam ilişkisi (OneToMany)
     mappedBy, Exam sınıfındaki teacher alanını referans eder
     Bu tarafta @JoinColumn kullanmaya gerek yoktur

     neden cascade kullandık?

     cascade type olarak all seçildi
     eğer bir öğretmen kaydedildiğinde onunla ilişkili olan sınavlarada
     * persist
     * merge
     * remove
     * refresh
     * detach
     işlemleri yapılabilir.

     hepsi özel bir cascade tipidir
    */
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<Exam> exams;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Exam> getExams() {
        return exams;
    }

    public void setExams(List<Exam> exams) {
        this.exams = exams;
    }
}
