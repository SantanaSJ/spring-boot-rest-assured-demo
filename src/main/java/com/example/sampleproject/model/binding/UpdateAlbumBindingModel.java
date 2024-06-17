package com.example.sampleproject.model.binding;


import jakarta.validation.constraints.NotBlank;

public class UpdateAlbumBindingModel {

    private Long id;
    @NotBlank
    private String albumName;
    @NotBlank
    private String description;



    public Long getId() {
        return id;
    }

    public UpdateAlbumBindingModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getAlbumName() {
        return albumName;
    }

    public UpdateAlbumBindingModel setAlbumName(String albumName) {
        this.albumName = albumName;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public UpdateAlbumBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }
}
