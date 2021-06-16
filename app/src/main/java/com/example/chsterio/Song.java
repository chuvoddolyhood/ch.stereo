package com.example.chsterio;

public class Song {
    private String Title;
    private int File;
    private int Picture;

    public Song(String title, int file, int picture) {
        Title = title;
        File = file;
        Picture = picture;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getFile() {
        return File;
    }

    public void setFile(int file) {
        File = file;
    }

    public int getPicture() {
        return Picture;
    }

    public void setPicture(int picture) {
        Picture = picture;
    }
}
