package com.example.dashbord;

public class newmodel {

    String Name,isDone;

    newmodel()
    {

    }

    public newmodel(String name, String isDone) {
        Name = name;
        this.isDone = isDone;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getIsDone() {
        return isDone;
    }

    public void setIsDone(String isDone) {
        this.isDone = isDone;
    }
}
