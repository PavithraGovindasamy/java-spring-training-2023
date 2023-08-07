package com.sirius.springenablement.demo.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private  int id;

    @Column(name = "title")
    private String title;

    // mapping course to student
    @ManyToMany(fetch=FetchType.LAZY
            ,cascade = {CascadeType.DETACH,CascadeType.MERGE,
            CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable( name = "course_student",
            joinColumns=@JoinColumn(name = "course_id"), // this course id will reference to id in course
            inverseJoinColumns = @JoinColumn(name = "student_id") //this student id will reference to id in student
    )
    private List<Student> student;

    public List<Student> getStudent() {
        return student;
    }

    public void setStudent(List<Student> student) {
        this.student = student;
    }
    // student
    public  void addStudent(Student students){
        if(student==null){
            student=new ArrayList<>();
        }
        student.add(students);
    }

    public Course() {

    }

    public Course(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +


                '}';
    }
}

