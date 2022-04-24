package com.olx.service;

import java.util.List;
import com.olx.dto.Category;
import com.olx.dto.Status;


public interface CategoryService {
    public List<Category> getAllCategory();

    public List<Status> getAllStatus();
    
    public String getCategoryDescription(int CateId);
    
	public String getStatusName(int StatusId);
}
