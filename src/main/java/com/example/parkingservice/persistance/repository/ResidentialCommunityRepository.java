package com.example.parkingservice.persistance.repository;

import com.example.parkingservice.persistance.entity.ResidentialCommunity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResidentialCommunityRepository extends JpaRepository<ResidentialCommunity, Long> {

    ResidentialCommunity findByName(String name);

}
