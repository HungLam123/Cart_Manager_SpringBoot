package com.mockproject.service;

import java.util.List;

import com.mockproject.entity.UnitTypes;

public interface UnitTypesService {
	UnitTypes findById(Long id);
	List<UnitTypes> findAll();
}
