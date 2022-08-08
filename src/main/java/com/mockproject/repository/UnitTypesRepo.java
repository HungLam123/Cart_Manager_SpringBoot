package com.mockproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mockproject.entity.UnitTypes;

@Repository
public interface UnitTypesRepo extends JpaRepository<UnitTypes, Long>{

}
