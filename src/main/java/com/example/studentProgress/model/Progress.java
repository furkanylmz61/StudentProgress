package com.example.studentProgress.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "progress")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "score")
    private Double score; // öğrencinin notu belirleniyor
    @Column(name = "status")
    private Status status; //ENUM class ile status durumu belirleniyor



    /*
    her öğrenci birden çok sınav kaydına sahip olabilir
    manyToOne (Progress -> Student) ayarladım
     */
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    /*
    * Bir sınava dair birden fazla Progress kaydı olabilir (farklı öğrenciler için).
     */

    @ManyToOne
    @JoinColumn(name = "exam_id")
    private Exam exam;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }
}

