package com.atguigu.bean;

public class TMemberProcinst {
    private Integer id;

    private Integer memberid;

    private String procinstid;

    private String proctype;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberid() {
        return memberid;
    }

    public void setMemberid(Integer memberid) {
        this.memberid = memberid;
    }

    public String getProcinstid() {
        return procinstid;
    }

    public void setProcinstid(String procinstid) {
        this.procinstid = procinstid == null ? null : procinstid.trim();
    }

    public String getProctype() {
        return proctype;
    }

    public void setProctype(String proctype) {
        this.proctype = proctype == null ? null : proctype.trim();
    }
}