package com.example.sampleproject.model.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "artists")
public class ArtistEntity extends BaseEntity {

    @Column
    private String artist;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "artist", fetch = FetchType.EAGER)
    private List<AlbumEntity> albums;

    public List<AlbumEntity> getAlbums() {
        return albums;
    }

    public void setAlbums(List<AlbumEntity> albumEntity) {
        this.albums = albumEntity;
    }

    public String getArtist() {
        return artist;
    }

    public ArtistEntity setArtist(String name) {
        this.artist = name;
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
        return artist;
    }
}
