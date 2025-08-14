package com.movieflix.service;

import com.movieflix.controller.request.CategoryRequest;
import com.movieflix.entity.Category;
import com.movieflix.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    public final CategoryRepository repository;

    public List<Category> findAll(){
        return repository.findAll();
    }

    public Category saveCategory(Category category){
        return repository.save(category);
    }

    public Optional<Category> findByCategoryId(Long id){
        return repository.findById(id);
    }

    public Optional<Category> updateCategory(Long id, CategoryRequest request) {
        return repository.findById(id)
                .map(existingCategory -> {
                    existingCategory.setName(request.name());
                    return repository.save(existingCategory);
                });
    }

    public void deleteByCategoryId(Long id){
        repository.deleteById(id);
    }
}
