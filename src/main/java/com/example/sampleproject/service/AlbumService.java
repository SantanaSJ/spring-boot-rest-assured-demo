package com.example.sampleproject.service;

import com.example.sampleproject.model.binding.AlbumAddBindingModel;
import com.example.sampleproject.model.binding.UpdateAlbumBindingModel;
import com.example.sampleproject.model.entities.AlbumEntity;
import com.example.sampleproject.model.service.AlbumServiceModel;

import java.util.List;

public interface AlbumService {
    List<AlbumServiceModel> findAllAlbums();

    List<AlbumServiceModel> findByArtist(String name);

    List<AlbumServiceModel> findByArtistId(Long id);

    AlbumServiceModel finById(Long id);

    AlbumServiceModel findByAlbum(String name);

    boolean existsByName(String name);

    AlbumEntity addAlbum(AlbumAddBindingModel addBindingModel);

    AlbumServiceModel updateAlbum(UpdateAlbumBindingModel bindingModel);

    void deleteAlbum(Long id);

}

