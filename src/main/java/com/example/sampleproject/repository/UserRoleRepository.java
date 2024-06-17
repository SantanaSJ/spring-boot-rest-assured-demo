package com.example.sampleproject.repository;

import com.example.sampleproject.model.entities.UserRoleEntity;
import com.example.sampleproject.model.entities.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository  extends JpaRepository<UserRoleEntity, Long> {

    UserRoleEntity findByRole (UserRoleEnum role);

}
