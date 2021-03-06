package com.powernode.beans;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)//表示当前类为缓存对象
public class Country {

    @Id
    @GeneratedValue(generator = "xxx")
    @GenericGenerator(name = "xxx", strategy = "native")
    private Integer cid;
    private String cname;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "countryId")
    @Cache(usage = CacheConcurrencyStrategy.READ_ONLY)//表示当前集合为缓存对象
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

	/*@Override
	public String toString() {
		return "Country [cid=" + cid + ", cname=" + cname + ", ministers=" + ministers + "]";
	}*/

}
