package com.example.multitenancy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.multitenancy.model.City;

public interface CityRepository extends JpaRepository<City, Long> {

}
