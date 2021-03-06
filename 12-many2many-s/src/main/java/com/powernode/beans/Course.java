package com.powernode.beans;

public class Course {

    private Integer cid;
    private String cname;

    public Course() {
        super();
    }

    public Course(String cname) {
        super();
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

    @Override
    public String toString() {
        return "Course [cid=" + cid + ", cname=" + cname + "]";
    }

}
