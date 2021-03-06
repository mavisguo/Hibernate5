package com.powernode.beans;

import java.util.HashSet;
import java.util.Set;

//新闻栏目实体
public class NewsLabel {

    private Integer id;
    private String name;
    private String content;
    private NewsLabel parentNewsLabel;//父栏目
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
