package com.powernode.beans;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Student {

    @Id
    @GeneratedValue(generator = "xxx")
    @GenericGenerator(name = "xxx", strategy = "native")
    private Integer sid;
    private String sname;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Course> courses;

    public Student() {
        courses = new HashSet<Course>();
    }

    public Student(String sname) {
        this();
        this.sname = sname;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "Student [sid=" + sid + ", sname=" + sname + ", courses=" + courses + "]";
    }

}
