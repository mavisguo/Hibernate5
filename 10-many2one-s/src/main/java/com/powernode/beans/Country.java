package com.powernode.beans;

public class Country {

    private Integer cid;
    private String cname;

    public Country() {
        super();
    }

    public Country(String cname) {
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
        return "Country [cid=" + cid + ", cname=" + cname + "]";
    }

}
