package com.example.sampleproject.service.impl;

import com.example.sampleproject.exception.ArtistNotFoundException;
import com.example.sampleproject.model.binding.ArtistAddBindingModel;
import com.example.sampleproject.model.binding.UpdateArtistBindingModel;
import com.example.sampleproject.model.entities.AlbumEntity;
import com.example.sampleproject.model.entities.ArtistEntity;
import com.example.sampleproject.model.service.AlbumServiceModel;
import com.example.sampleproject.model.service.ArtistServiceModel;
import com.example.sampleproject.repository.AlbumRepository;
import com.example.sampleproject.repository.ArtistRepository;
import com.example.sampleproject.service.AlbumService;
import com.example.sampleproject.service.ArtistService;
import com.github.javafaker.Faker;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtistServiceImpl implements ArtistService {
    private final ModelMapper mapper;
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;
    private final AlbumService albumService;
    private final Faker faker;

    public ArtistServiceImpl(ModelMapper mapper, ArtistRepository artistRepository,
                             AlbumRepository albumRepository, AlbumService albumService,
                             Faker faker) {
        this.mapper = mapper;
        this.artistRepository = artistRepository;
        this.albumRepository = albumRepository;
        this.albumService = albumService;
        this.faker = faker;
    }

    @Override
    public List<ArtistServiceModel> findAllArtists() {
        List<ArtistEntity> allArtists = this.artistRepository.getAllArtists();
        List<ArtistServiceModel> collect = allArtists
                .stream()
                .map(a -> {
                    ArtistServiceModel map = this.mapper.map(a, ArtistServiceModel.class);
                    List<AlbumServiceModel> albumServiceModels = a.getAlbums().stream()
                            .map(albumEntity -> this.mapper.map(albumEntity, AlbumServiceModel.class))
                            .toList();
                    map.setAlbums(albumServiceModels);
                    return map;
                })
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    public ArtistServiceModel findById(Long id) {
        ArtistServiceModel artistServiceModel = this.artistRepository
                .findById(id)
                .map(a -> {
                    ArtistServiceModel serviceModel = this.mapper.map(a, ArtistServiceModel.class);
                    List<AlbumServiceModel> serviceModels = this.albumService.findByArtistId(id);
                    serviceModel.setAlbums(serviceModels);
                    return serviceModel;
                })
                .orElseThrow(() -> new ArtistNotFoundException("Artist Entity with id " + id + " was not found!"));
        return artistServiceModel;
    }

    @Override
    public ArtistServiceModel findByName(String name) {
        ArtistServiceModel artistServiceModel = this.artistRepository
                .findByName(name)
                .map(a -> {
                    ArtistServiceModel serviceModel = this.mapper.map(a, ArtistServiceModel.class);
                    List<AlbumServiceModel> serviceModels = this.albumService.findByArtist(a.getName());
                    serviceModel.setAlbums(serviceModels);
                    return serviceModel;
                })
                .orElseThrow(() -> new ArtistNotFoundException("Artist Entity with name " + name + " was not found!"));
        return artistServiceModel;
    }

    @Override
    public boolean existsByName(String name) {
        return this.artistRepository.existsByName(name);
    }

    @Override
    public ArtistEntity addArtist(ArtistAddBindingModel addBindingModel) {
        List<AlbumServiceModel> modelAlbums = addBindingModel.getAlbums();

        ArtistEntity entity = this.mapper.map(addBindingModel, ArtistEntity.class);
        List<AlbumEntity> albumEntities = modelAlbums
                .stream()
                .map(a -> {
                    AlbumEntity map = this.mapper.map(a, AlbumEntity.class);
                    map.setArtist(entity);
                    return map;
                })
                .collect(Collectors.toList());
        entity.setAlbums(albumEntities);
        ArtistEntity saved = this.artistRepository.save(entity);
//        this.albumService.save

        return saved;
    }

    @Override
    public ArtistEntity updateArtist(UpdateArtistBindingModel updateModel) {

        ArtistEntity artist = this.artistRepository
                .findById(updateModel.getId())
                .orElseThrow(() -> new ArtistNotFoundException("Artist Entity with id " + updateModel.getId() + " was not found!"));

        artist
                .setName(updateModel.getName())
                .setDescription(updateModel.getDescription());

        if (updateModel.getAlbums() != null) {
            artist.setAlbums(updateModel.getAlbums()
                    .stream()
                    .map(a -> this.mapper.map(a, AlbumEntity.class))
                    .collect(Collectors.toList()));
        }
        return this.artistRepository.save(artist);
    }

    @Override
    public void deleteArtist(Long id) {
        ArtistEntity artist = this.artistRepository.findById(id)
                .orElseThrow(() -> new ArtistNotFoundException("Artist Entity with id " + id + " was not found!"));
        this.artistRepository.deleteById(artist.getId());
    }

    @Override
    public void loadArtists() {

        ArtistEntity artistEntity;
        if (artistRepository.count() == 0) {
            for (int i = 0; i < 5; i++) {
                artistEntity = new ArtistEntity();
                artistEntity.setName(faker.name().fullName());
                artistEntity.setDescription(faker.lorem().sentence());
                artistRepository.save(artistEntity);

                List<AlbumEntity> albums = new ArrayList<>();
                    for (int j = 0; j < 5; j++) {
                        AlbumEntity albumEntity = new AlbumEntity();
                        albumEntity.setAlbumName(faker.book().title());
                        albumEntity.setDescription(faker.lorem().sentence());
                        albumEntity.setArtist(artistEntity);
                        albums.add(albumEntity);
                        albumRepository.save(albumEntity);
                    }
                    artistEntity.setAlbums(albums);
                    artistRepository.save(artistEntity);
            }
        }
    }
}
