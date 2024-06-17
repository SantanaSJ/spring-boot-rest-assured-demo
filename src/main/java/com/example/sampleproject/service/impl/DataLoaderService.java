package com.example.sampleproject.service.impl;

import com.example.sampleproject.service.ArtistService;
import com.example.sampleproject.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class DataLoaderService {

    private final UserService userService;
    private final ArtistService artistService;


    public DataLoaderService(UserService userService, ArtistService artistService) {
        this.userService = userService;
        this.artistService = artistService;
    }

    public void loadData() {
        artistService.loadArtists();
        userService.initializeRoles();
        userService.loadUsers();
    }
}
