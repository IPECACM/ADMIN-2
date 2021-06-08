package com.example.dashbord;

public class noticemodel {

    String pdfTitle, pdfUrl,key;

    public noticemodel(String pdfTitle, String pdfUrl,String key) {
        this.pdfTitle = pdfTitle;
        this.pdfUrl = pdfUrl;
        this.key=key;
    }
    public  noticemodel()
    {

    }

    public String getPdfTitle() {
        return pdfTitle;
    }

    public void setPdfTitle(String pdfTitle) {
        this.pdfTitle = pdfTitle;
    }
    public  String getKey(){
        return  key;
    }
    public void setKey(String key) {
        this.key = key;
    }


    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }
}

