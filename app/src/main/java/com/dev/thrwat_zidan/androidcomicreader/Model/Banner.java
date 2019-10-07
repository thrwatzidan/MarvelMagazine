package com.dev.thrwat_zidan.androidcomicreader.Model;

public class Banner {
    private String Link;
    private int ID;

    public Banner(String link, int ID) {
        Link = link;
        this.ID = ID;
    }

    public Banner() {
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
