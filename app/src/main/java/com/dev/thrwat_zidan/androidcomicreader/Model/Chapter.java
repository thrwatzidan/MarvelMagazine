package com.dev.thrwat_zidan.androidcomicreader.Model;

public class Chapter {
    private String Name;
    private int ID;
    private int MangaID;

    public Chapter() {
    }

    public Chapter(String name, int ID, int mangaID) {
        Name = name;
        this.ID = ID;
        MangaID = mangaID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getMangaID() {
        return MangaID;
    }

    public void setMangaID(int mangaID) {
        MangaID = mangaID;
    }
}
