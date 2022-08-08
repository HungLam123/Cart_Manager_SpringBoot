package com.mockproject.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockproject.entity.UnitTypes;
import com.mockproject.repository.UnitTypesRepo;
import com.mockproject.service.UnitTypesService;

@Service
public class UnitTypesServiceImpl implements UnitTypesService {

	@Autowired
	private UnitTypesRepo repo;

	@Override
	public UnitTypes findById(Long id) {
		Optional<UnitTypes> optional = repo.findById(id);
		return optional.isPresent() ? optional.get() : null;
	}

	@Override
	public List<UnitTypes> findAll() {
		return repo.findAll();
	}
}
