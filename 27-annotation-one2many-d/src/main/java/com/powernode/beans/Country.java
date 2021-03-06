package com.powernode.beans;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Country {

    @Id
    @GeneratedValue(generator = "xxx")
    @GenericGenerator(name = "xxx", strategy = "native")
    private Integer cid;
    private String cname;

    @OneToMany(targetEntity = Minister.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "countryId")
    private Set<Minister> ministers;//关联属性

    public Country() {
        ministers = new HashSet<Minister>();
    }

    public Country(String cname) {
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

    public Set<Minister> getMinisters() {
        return ministers;
    }

    public void setMinisters(Set<Minister> ministers) {
        this.ministers = ministers;
    }

    @Override
    public String toString() {
        return "Country [cid=" + cid + ", cname=" + cname + ", ministers=" + ministers + "]";
    }

}
