package com.example.dashbord;

public class magazinemodel {
    String MagazineTitle,id;

    magazinemodel()
    {

    }

    public magazinemodel(String magazineTitle, String id) {
        MagazineTitle = magazineTitle;
        this.id = id;
    }

    public String getMagazineTitle() {
        return MagazineTitle;
    }

    public void setMagazineTitle(String magazineTitle) {
        MagazineTitle = magazineTitle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}