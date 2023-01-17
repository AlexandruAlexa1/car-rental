package com.aa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aa.domain.Location;

public interface LocationRepository extends JpaRepository<Location, Integer> {

}
