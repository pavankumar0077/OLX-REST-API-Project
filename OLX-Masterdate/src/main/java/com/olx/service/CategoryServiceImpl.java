package com.olx.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olx.dto.Category;
import com.olx.dto.Status;
import com.olx.entity.CategoryEntity;
import com.olx.entity.StatusEntity;
import com.olx.repository.CategoryRepo;
import com.olx.repository.StatusRepo;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	CategoryRepo categoryRepo;

	@Autowired
	StatusRepo statusRepo;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<Category> getAllCategory() {
		List<CategoryEntity> categoryEntityList = categoryRepo.findAll();
		List<Category> categoryDtoList = new ArrayList<Category>();
		for (CategoryEntity categoryEntity : categoryEntityList) {
			Category category = convertEntityIntoDTO(categoryEntity);
			categoryDtoList.add(category);
		}
		return categoryDtoList;
	}

	@Override
	public List<Status> getAllStatus() {
		List<StatusEntity> statusEntityList = statusRepo.findAll();
		List<Status> statusDtoList = new ArrayList<Status>();
		for (StatusEntity statusEntity : statusEntityList) {
			Status status = convertEntityIntoDTO2(statusEntity);
			statusDtoList.add(status);
		}
		return statusDtoList;
	}

	private CategoryEntity convertDTOIntoEntity(Category category) {
		CategoryEntity categoryEntity = modelMapper.map(category, CategoryEntity.class);
		return categoryEntity;
	}

	private Category convertEntityIntoDTO(CategoryEntity categoryEntity) {
		Category category = modelMapper.map(categoryEntity, Category.class);
		return category;
	}

	private StatusEntity convertDTOIntoEntity2(Status status) {
		StatusEntity statusEntity = modelMapper.map(status, StatusEntity.class);
		return statusEntity;
	}

	private Status convertEntityIntoDTO2(StatusEntity statusEntity) {
		Status status = modelMapper.map(statusEntity, Status.class);
		return status;
	}

	@Override
	public String getCategoryDescription(int CateId) {
		Optional<CategoryEntity> categoryEntity = categoryRepo.findById(CateId);
		String CategoryName = null;
		if(categoryEntity.isPresent())
		{
			Category category = convertEntityIntoDTO(categoryEntity.get());
			CategoryName = category.getCategoryName();
		}
		return CategoryName;
	}

	@Override
	public String getStatusName(int StatusId) {
		Optional<StatusEntity> stateEntity = statusRepo.findById(StatusId);
		String StatusName = null;
		if(stateEntity.isPresent())
		{
			Status status = convertEntityIntoDTO2(stateEntity.get());
			StatusName = status.getStatusName();
		}
		return StatusName;
	}

}
