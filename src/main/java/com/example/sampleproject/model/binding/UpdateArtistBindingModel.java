package com.example.sampleproject.model.binding;

import com.example.sampleproject.model.serviceModels.AlbumServiceModel;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class UpdateArtistBindingModel {


    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private List<AlbumServiceModel> albums;

    public String getName() {
        return name;
    }

    public UpdateArtistBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public UpdateArtistBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<AlbumServiceModel> getAlbums() {
        return albums;
    }

    public UpdateArtistBindingModel setAlbums(List<AlbumServiceModel> albums) {
        this.albums = albums;
        return this;
    }

    public Long getId() {
        return id;
    }

    public UpdateArtistBindingModel setId(Long id) {
        this.id = id;
        return this;
    }
}
