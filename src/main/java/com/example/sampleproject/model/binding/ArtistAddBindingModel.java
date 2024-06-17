package com.example.sampleproject.model.binding;

import com.example.sampleproject.model.service.AlbumServiceModel;
import com.example.sampleproject.validator.ValidateAlbums;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class ArtistAddBindingModel {

    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @ValidateAlbums
    private List<AlbumServiceModel> albums;

    public String getName() {
        return name;
    }

    public ArtistAddBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ArtistAddBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<AlbumServiceModel> getAlbums() {
        return albums;
    }

    public ArtistAddBindingModel setAlbums(List<AlbumServiceModel> albums) {
        this.albums = albums;
        return this;
    }
}
