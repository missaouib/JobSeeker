package com.Backend.service;

import com.Backend.dto.CategoryDto;
import org.springframework.ui.ModelMap;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> scrapCategoryStatistics(String city);
    List<CategoryDto> getCategoryStatistics(String city);
}
