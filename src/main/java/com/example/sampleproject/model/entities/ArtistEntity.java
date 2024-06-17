package com.example.sampleproject.model.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "artists")
public class ArtistEntity extends BaseEntity {

    @Column
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL)
    private List<AlbumEntity> albums;

    public List<AlbumEntity> getAlbums() {
        return albums;
    }

    public void setAlbums(List<AlbumEntity> albumEntity) {
        this.albums = albumEntity;
    }

    public String getName() {
        return name;
    }

    public ArtistEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ArtistEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public String toString() {
        return name;
    }
}
