package com.example.sampleproject.model.binding;

import jakarta.validation.constraints.NotBlank;
public class AlbumAddBindingModel {

    @NotBlank
    private String artist;
    @NotBlank
    private String albumName;
    @NotBlank
    private String description;

    public String getArtist() {
        return artist;
    }

    public AlbumAddBindingModel setArtist(String artist) {
        this.artist = artist;
        return this;
    }

    public String getAlbumName() {
        return albumName;
    }

    public AlbumAddBindingModel setAlbumName(String albumName) {
        this.albumName = albumName;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public AlbumAddBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }
}
