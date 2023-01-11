package com.aa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aa.domain.Rent;

public interface RentRepository extends JpaRepository<Rent, Integer> {

}
