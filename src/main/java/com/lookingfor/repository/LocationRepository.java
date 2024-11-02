package com.lookingfor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lookingfor.entity.LocationEntity;

@Repository
public interface LocationRepository extends JpaRepository<LocationEntity, Integer>{

}
