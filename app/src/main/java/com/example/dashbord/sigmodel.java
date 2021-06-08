package com.example.dashbord;

public class sigmodel {

    String Name,id,logo;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    sigmodel()
    {

    }

    public String getName() {
        return Name;
    }

    public sigmodel(String name) {
        Name = name;
    }

    public void setName(String name) {
        Name = name;
    }
}
