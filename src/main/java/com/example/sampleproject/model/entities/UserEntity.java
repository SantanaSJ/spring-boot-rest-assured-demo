package com.example.sampleproject.model.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {

    @Column
    private String name;

    @Column
    private String password;

//    Hibernate uses lazy loading by default for collections to improve performance by loading them only when needed.
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_albums",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "albums_id"))
    private List<AlbumEntity> albums;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id"))
    private Set<UserRoleEntity> roles = new HashSet<>();

    public Set<UserRoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRoleEntity> roles) {
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public UserEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<AlbumEntity> getAlbums() {
        return albums;
    }

    public void setAlbums(List<AlbumEntity> albums) {
        this.albums = albums;
    }
}
