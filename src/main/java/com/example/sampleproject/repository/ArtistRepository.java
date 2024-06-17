package com.example.sampleproject.repository;

import com.example.sampleproject.model.entities.ArtistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<ArtistEntity, Long> {

//Encountered a duplicated sql alias [id] during auto-discovery of a native-sql query
    @Query(value = "SELECT a.id, a.description, a.name FROM sample_app.artists as a", nativeQuery = true)
    List<ArtistEntity> getAllArtists();

    Optional<ArtistEntity> findByName(String name);

    boolean existsByName(String name);
}
