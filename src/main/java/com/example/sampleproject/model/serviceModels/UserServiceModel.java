package com.example.sampleproject.model.serviceModels;

import java.util.List;

public class UserServiceModel {

    private String name;
    private String password;
    private List<AlbumServiceModel> albumServiceModels;

    public String getName() {
        return name;
    }

    public UserServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserServiceModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public List<AlbumServiceModel> getAlbumServiceModels() {
        return albumServiceModels;
    }

    public UserServiceModel setAlbumServiceModels(List<AlbumServiceModel> albumServiceModels) {
        this.albumServiceModels = albumServiceModels;
        return this;
    }
}
