package com.example.lectures.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;


@Entity
@Table(name = "lectures")
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecturer_id", nullable = false)
    @JsonBackReference
    private Lecturer lecturer;
    @Column(name = "code", nullable = false)
    private String code;


    public Lecture() {
    }

    public Lecture(Long id, String title, Lecturer lecturer, String code) {
        this.id = id;
        this.title = title;
        this.lecturer = lecturer;
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) { this.code = code;
    }


}