package com.aa.repository;

import org.springframework.data.repository.CrudRepository;

import com.aa.domain.Location;

public interface LocationRepository extends CrudRepository<Location, Integer> {

}
