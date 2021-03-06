package com.powernode.beans;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

//新闻栏目实体
@Entity
public class NewsLabel {

    @Id
    @GeneratedValue(generator = "xxx")
    @GenericGenerator(name = "xxx", strategy = "native")
    private Integer id;
    private String name;
    private String content;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pid")
    private NewsLabel parentNewsLabel;//父栏目

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "pid")
    private Set<NewsLabel> childNewsLabels;//子栏目

    public NewsLabel() {
        childNewsLabels = new HashSet<NewsLabel>();
    }

    public NewsLabel(String name, String content) {
        this();
        this.name = name;
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public NewsLabel getParentNewsLabel() {
        return parentNewsLabel;
    }

    public void setParentNewsLabel(NewsLabel parentNewsLabel) {
        this.parentNewsLabel = parentNewsLabel;
    }

    public Set<NewsLabel> getChildNewsLabels() {
        return childNewsLabels;
    }

    public void setChildNewsLabels(Set<NewsLabel> childNewsLabels) {
        this.childNewsLabels = childNewsLabels;
    }

    @Override
    public String toString() {
        return "NewsLabel [id=" + id + ", name=" + name + ", content=" + content + ", childNewsLabels="
                + childNewsLabels + "]";
    }

}
