package com.example.dashbord;

public class taskdisplaymodel {
    String Name,id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    taskdisplaymodel()
    {

    }

    public taskdisplaymodel(String name) {
        Name = name;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
