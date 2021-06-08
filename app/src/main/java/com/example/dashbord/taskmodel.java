package com.example.dashbord;

public class taskmodel {
     String Name,id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    taskmodel(){

     }

    public taskmodel(String name) {
        Name = name;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
