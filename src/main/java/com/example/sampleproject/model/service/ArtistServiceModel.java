package com.example.sampleproject.model.service;

import jdk.jfr.Name;

import java.util.List;

public class ArtistServiceModel {

    private String artist;
    private String description;

    private List<AlbumServiceModel> albums;

    public String getArtist() {
        return artist;
    }

    public ArtistServiceModel setArtist(String artist) {
        this.artist = artist;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ArtistServiceModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<AlbumServiceModel> getAlbums() {
        return albums;
    }

    public void setAlbums(List<AlbumServiceModel> albums) {
        this.albums = albums;
    }
}
