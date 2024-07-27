package com.example.sampleproject.service.impl;

import com.example.sampleproject.exception.AlbumNotFoundException;
import com.example.sampleproject.model.binding.AlbumAddBindingModel;
import com.example.sampleproject.model.binding.ArtistAddBindingModel;
import com.example.sampleproject.model.binding.UpdateAlbumBindingModel;
import com.example.sampleproject.model.entities.AlbumEntity;
import com.example.sampleproject.model.entities.ArtistEntity;
import com.example.sampleproject.model.serviceModels.AlbumServiceModel;
import com.example.sampleproject.repository.AlbumRepository;
import com.example.sampleproject.repository.ArtistRepository;
import com.example.sampleproject.service.AlbumService;
import com.example.sampleproject.service.ArtistService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlbumServiceImpl implements AlbumService {

    private final ModelMapper mapper;
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;
    private final ArtistService artistService;

    public AlbumServiceImpl(AlbumRepository albumRepository, ModelMapper mapper,
                            ArtistRepository artistRepository, @Lazy ArtistService artistService) {
        this.albumRepository = albumRepository;
        this.mapper = mapper;
        this.artistRepository = artistRepository;
        this.artistService = artistService;
    }


    @Override
    public List<AlbumServiceModel> findAllAlbums() {
        List<AlbumEntity> allAlbums = this.albumRepository.getAllAlbums();
        List<AlbumServiceModel> collect = allAlbums
                .stream()
                .map(a -> {
                    AlbumServiceModel map = this.mapper.map(a, AlbumServiceModel.class);
                    return map.setArtist(a.getArtist().getArtist());
                })
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    public List<AlbumServiceModel> findByArtist(String name) {
        List<AlbumEntity> albumEntities = this.albumRepository.findByArtist(name);
        return getAlbumServiceModels(albumEntities);


    }

    @Override
    public List<AlbumServiceModel> findByArtistId(Long id) {
        List<AlbumEntity> entities = this.albumRepository.findByArtistId(id);
        return getAlbumServiceModels(entities);
    }

    @Override
    public AlbumServiceModel finById(Long id) {
        AlbumServiceModel albumServiceModel = this.albumRepository.findById(id)
                .map(v -> {
                    AlbumServiceModel map = this.mapper.map(v, AlbumServiceModel.class);
                    map.setArtist(v.getArtist().getArtist());
                    return map;
                })
                .orElseThrow(() -> new AlbumNotFoundException("Album with id " + id + " was not found!"));
        return albumServiceModel;
    }

    @Override
    public AlbumServiceModel findByAlbum(String album) {
        AlbumEntity albumEntity = this.albumRepository
                .findByAlbumName(album)
                .orElseThrow(() -> new AlbumNotFoundException("Album with name " + album + " was not found!"));

        AlbumServiceModel serviceModel = this.mapper.map(albumEntity, AlbumServiceModel.class);
        serviceModel.setArtist(albumEntity.getArtist().getArtist());
        return serviceModel;
    }

    @Override
    public boolean existsByName(String name) {
        if (this.albumRepository.existsByAlbumName(name)) {
            return true;
        }
        return false;
    }

    @Override
    public AlbumEntity addAlbum(AlbumAddBindingModel albumAddBindingModel) {
        Optional<ArtistEntity> optionalArtist = artistRepository
                .findByArtist(albumAddBindingModel.getArtist());

        ArtistEntity artistEntity;
        if (optionalArtist.isEmpty()) {
            ArtistAddBindingModel artistAddBindingModel = new ArtistAddBindingModel();
            artistAddBindingModel.setArtist(albumAddBindingModel.getArtist());
            artistAddBindingModel.setAlbums(new ArrayList<>());

            artistEntity = artistService.addArtist(artistAddBindingModel);
        } else {
            artistEntity = optionalArtist.get();
        }
//        ArtistEntity artist = artistRepository
//                .findByArtist(addBindingModel.getArtist())
//                .orElseThrow(() -> new ArtistNotFoundException("Artist was not found! Please add an artist first!"));

        AlbumEntity albumEntity = mapper.map(albumAddBindingModel, AlbumEntity.class);
        albumEntity.setArtist(artistEntity);
        return albumRepository.save(albumEntity);
    }

    @Override
    public AlbumServiceModel updateAlbum(UpdateAlbumBindingModel bindingModel) {

        AlbumEntity albumEntity = albumRepository
                .findById(bindingModel.getId())
                .orElseThrow(() -> new AlbumNotFoundException("Album Entity with id " + bindingModel.getId() + " was not found!"));

        albumEntity
                .setDescription(bindingModel.getDescription());
        if (bindingModel.getAlbumName() != null) {
            albumEntity.setAlbumName(bindingModel.getAlbumName());
        }
        this.albumRepository.save(albumEntity);
        AlbumServiceModel albumServiceModel = mapper.map(albumEntity, AlbumServiceModel.class);
        albumServiceModel.setArtist(albumEntity.getArtist().getArtist());


        return albumServiceModel;
    }

    @Override
    public void deleteAlbum(Long id) {
        AlbumEntity albumEntity = albumRepository.findById(id)
                .orElseThrow(() -> new AlbumNotFoundException("Album Entity with id " + id + " was not found!"));
        albumRepository.deleteById(albumEntity.getId());
    }

    private List<AlbumServiceModel> getAlbumServiceModels(List<AlbumEntity> entities) {
        List<AlbumServiceModel> collect = entities
                .stream()
                .map(v -> {
                    AlbumServiceModel map = mapper.map(v, AlbumServiceModel.class);
                    map.setArtist(v.getArtist().getArtist());
                    return map;
                })
                .collect(Collectors.toList());
        return collect;
    }
}
