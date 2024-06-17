package com.example.sampleproject.service;

import com.example.sampleproject.model.binding.ArtistAddBindingModel;
import com.example.sampleproject.model.binding.UpdateArtistBindingModel;
import com.example.sampleproject.model.entities.ArtistEntity;
import com.example.sampleproject.model.service.ArtistServiceModel;

import java.util.List;

public interface ArtistService {
    List<ArtistServiceModel> findAllArtists();

    ArtistServiceModel findById(Long id);

    ArtistServiceModel findByName(String name);

    boolean existsByName(String name);

    ArtistEntity addArtist(ArtistAddBindingModel addBindingModel);

    ArtistEntity updateArtist(UpdateArtistBindingModel updateArtistBindingModel);

    void deleteArtist(Long id);

    void loadArtists();

}
