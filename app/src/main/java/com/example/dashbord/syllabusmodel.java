package com.example.dashbord;

public class syllabusmodel {
    String Name,SIG,id;

    public syllabusmodel(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    syllabusmodel()
    {

    }

    public syllabusmodel(String name, String SIG) {
        Name = name;
        this.SIG = SIG;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSIG() {
        return SIG;
    }

    public void setSIG(String SIG) {
        this.SIG = SIG;
    }
}
