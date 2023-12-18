package main.java.service_layer.service;

import main.java.data_access_layer.dto.CategoryDTO;
import main.java.data_access_layer.entity.Category;
import main.java.data_access_layer.repository.CategoryRepository;
import main.java.service_layer.mapper.CategoryMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryService implements IService<CategoryDTO, Long> {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService() {
        this.categoryMapper = new CategoryMapper();
        this.categoryRepository = new CategoryRepository();
    }


    @Override
    public List<CategoryDTO> findAll() {
        List<Category> all = categoryRepository.findAll();
        List<CategoryDTO> dtos = new ArrayList<>();
        for (Category c : all) {
            dtos.add(categoryMapper.map(c));
        }
        return dtos;
    }

    @Override
    public CategoryDTO findOneById(Long id) {
        Optional<Category> category = categoryRepository.findOneById(id);
        return category.map(categoryMapper::map).orElse(null);
    }

    @Override
    public CategoryDTO save(CategoryDTO entity) {
        Category saved = categoryRepository.save(categoryMapper.remap(entity, null, null));
        return categoryMapper.map(saved);
    }

    @Override
    public CategoryDTO update(CategoryDTO entity) {
        Optional<Category> category = categoryRepository.findOneById(entity.getId());
        if (category.isPresent()) {
            Category updated = categoryRepository.update(
                    categoryMapper.remap(entity, category.get().getCreatedAt(), null));
            return categoryMapper.map(updated);
        }
        return null;
    }

    @Override
    public Boolean removeById(Long id) {
        return categoryRepository.removeById(id);
    }
}
