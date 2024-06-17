package com.example.sampleproject.service;

import com.example.sampleproject.model.service.UserServiceModel;

public interface UserService {

    UserServiceModel findByName(String name);

    void initializeRoles();

    void loadUsers();
}
