package com.powernode.beans;

import java.util.HashSet;
import java.util.Set;

public class Course {

    private Integer cid;
    private String cname;
    private Set<Student> students;

    public Course() {
        students = new HashSet<Student>();
    }

    public Course(String cname) {
        this();
        this.cname = cname;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Course [cid=" + cid + ", cname=" + cname + "]";
    }

}
