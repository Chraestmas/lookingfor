package com.lookingfor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lookingfor.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String> {

}
