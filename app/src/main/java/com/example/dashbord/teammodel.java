package com.example.dashbord;

public class teammodel {

    String F_Email,F_Name,F_Post,F_Image,key;

    public teammodel(String f_Image) {
        F_Image = f_Image;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getF_Image() {
        return F_Image;
    }

    public void setF_Image(String f_Image) {
        F_Image = f_Image;
    }

    public teammodel(String f_Email, String f_Name, String f_Post) {
        F_Email = f_Email;
        F_Name = f_Name;
        F_Post = f_Post;
    }
    public teammodel()
    {

    }

    public String getF_Email() {
        return F_Email;
    }

    public void setF_Email(String f_Email) {
        F_Email = f_Email;
    }

    public String getF_Name() {
        return F_Name;
    }

    public void setF_Name(String f_Name) {
        F_Name = f_Name;
    }

    public String getF_Post() {
        return F_Post;
    }

    public void setF_Post(String f_Post) {
        F_Post = f_Post;
    }
}
