package com.powernode.beans;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class Minister {

    @Id
    @GeneratedValue(generator = "xxx")
    @GenericGenerator(name = "xxx", strategy = "native")
    private Integer mid;
    private String mname;

    @ManyToOne(targetEntity = Country.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "countryId")
    private Country country;//关联属性

    public Minister() {
        super();
    }

    public Minister(String mname) {
        super();
        this.mname = mname;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    //双向关联在定义toString()时，要求只有一方可以输出对方；若双方均可输出对象，则会形成递归调用，会出错。
    @Override
    public String toString() {
        return "Minister [mid=" + mid + ", mname=" + mname + "]";
    }

}
