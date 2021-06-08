package com.example.dashbord;

import android.util.Log;

import java.util.ArrayList;
import java.util.Map;

public class studentmodel {
   String Choice0, Choice1, Choice2,Name,id,isDone,newid;

    public studentmodel(String newid) {
        this.newid = newid;
    }

    public String getNewid() {
        return newid;
    }

    public void setNewid(String newid) {
        this.newid = newid;
    }

    public String getIsDone() {
        return isDone;
    }

    public void setIsDone(String isDone) {
        this.isDone = isDone;
    }

    public studentmodel(String choice0, String choice1, String choice2, String name, String id, String isDone, ArrayList<Map<String, String>> tasks) {
        Choice0 = choice0;
        Choice1 = choice1;
        Choice2 = choice2;
        Name = name;
        this.id = id;
        this.isDone = isDone;
        Tasks = tasks;
    }

    ArrayList<Map<String,String>> Tasks;

    public ArrayList<Map<String, String>> getTasks() {
        return Tasks;
    }

    public void setTasks(ArrayList<Map<String, String>> tasks) {
        Tasks = tasks;
    }

    public studentmodel() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChoice0() {
        return Choice0;
    }

    public void setChoice0(String choice0) {
        Choice0 = choice0;
    }

    public String getChoice1() {
        return Choice1;
    }

    public void setChoice1(String choice1) {
        Choice1 = choice1;
    }

    public String getChoice2() {
        return Choice2;
    }

    public void setChoice2(String choice2) {
        Choice2 = choice2;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

}
