package com.example.sampleproject.service.impl;

import com.example.sampleproject.model.entities.AlbumEntity;
import com.example.sampleproject.model.entities.UserEntity;
import com.example.sampleproject.model.entities.UserRoleEntity;
import com.example.sampleproject.model.entities.UserRoleEnum;
import com.example.sampleproject.model.serviceModels.UserServiceModel;
import com.example.sampleproject.repository.AlbumRepository;
import com.example.sampleproject.repository.UserRepository;
import com.example.sampleproject.repository.UserRoleRepository;
import com.example.sampleproject.service.UserService;
import com.github.javafaker.Faker;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
//@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final ModelMapper modelMapper;
    private final Faker faker;
    private final AlbumRepository albumRepository;

    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository,
                           ModelMapper modelMapper, Faker faker, AlbumRepository albumRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.modelMapper = modelMapper;
        this.faker = faker;
        this.albumRepository = albumRepository;
    }


    @Override
    public UserServiceModel findByName(String name) {
        UserEntity byName = userRepository.findByName(name);
        return modelMapper.map(byName, UserServiceModel.class);
    }

    @Override
    public void initializeRoles() {
        if (userRoleRepository.count() == 0) {
            UserRoleEntity adminRole = new UserRoleEntity();
            adminRole.setRole(UserRoleEnum.ADMIN);

            UserRoleEntity userRole = new UserRoleEntity();
            userRole.setRole(UserRoleEnum.USER);

            userRoleRepository.saveAll(List.of(adminRole, userRole));
        }
    }

    @Override
    public void loadUsers() {

        if (userRepository.count() == 0) {
            UserRoleEntity adminRole = userRoleRepository.findByRole(UserRoleEnum.ADMIN);
            UserRoleEntity userRole = userRoleRepository.findByRole(UserRoleEnum.USER);

            List<AlbumEntity> albums = new ArrayList<>(albumRepository.findAll());

            Random random = new Random();

            for (int i = 0; i < 5; i++) {
                UserEntity user = new UserEntity();
                user.setName(faker.name().firstName() + " " + faker.name().lastName());
                user.setPassword(faker.internet().password());
                int randomNumberOfAlbums = 1 + random.nextInt(5);

                List<AlbumEntity> userAlbums = assignRandomNumberOfAlbums(randomNumberOfAlbums, albums);
                user.setAlbums(userAlbums);
                user.setRoles(Set.of(adminRole, userRole));
                userRepository.save(user);
            }
        }
    }

    private List<AlbumEntity> assignRandomNumberOfAlbums(int randomNumberOfAlbums, List<AlbumEntity> albums) {
        List<AlbumEntity> shuffled = new ArrayList<>(albums);
        Collections.shuffle(albums);
        List<AlbumEntity> albumEntities = shuffled.subList(0, Math.min(randomNumberOfAlbums, albums.size()));
        return albumEntities;
    }
}
