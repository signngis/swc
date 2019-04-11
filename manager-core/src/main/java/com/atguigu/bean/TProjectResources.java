package com.atguigu.bean;

public class TProjectResources {
    private Integer id;

    private Integer pid;

    private String iconpath;

    private String infopics;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getIconpath() {
        return iconpath;
    }

    public void setIconpath(String iconpath) {
        this.iconpath = iconpath == null ? null : iconpath.trim();
    }

    public String getInfopics() {
        return infopics;
    }

    public void setInfopics(String infopics) {
        this.infopics = infopics == null ? null : infopics.trim();
    }
}