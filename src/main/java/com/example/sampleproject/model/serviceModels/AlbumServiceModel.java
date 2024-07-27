package com.example.sampleproject.model.serviceModels;


public class AlbumServiceModel {

    private String artist;
    private String album;
    private String description;


    public String getDescription() {
        return description;
    }

    public AlbumServiceModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getAlbum() {
        return album;
    }

    public AlbumServiceModel setAlbum(String album) {
        this.album = album;
        return this;
    }

    public String getArtist() {
        return artist;
    }

    public AlbumServiceModel setArtist(String artist) {
        this.artist = artist;
        return this;
    }
}
