package peaksoft.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.CategoryRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.categoryResponse.CategoryResponse;
import peaksoft.dto.response.categoryResponse.CategoryResponseById;
import peaksoft.entity.Category;
import peaksoft.exception.AlreadyExistsException;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.CategoryRepository;
import peaksoft.service.CategoryService;

import java.util.List;

/**
 * Shabdanov Ilim
 **/
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public SimpleResponse saveCategory(CategoryRequest categoryRequest) {
        if (categoryRepository.existsByName(categoryRequest.name())) {
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message(String.format("Category with name : %s already exists",
                            categoryRequest.name()))
                    .build();
        }
        Category category = Category.builder()
                .name(categoryRequest.name())
                .build();

        categoryRepository.save(category);
       return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Category with name : %s successfully saved ...!",
                        categoryRequest.name()))
                .build();
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.getAllCategories();
    }

    @Override
    public CategoryResponseById getCategoriesById(Long id) {
        return categoryRepository.getCategoriesById(id).orElseThrow(()->
                new NotFoundException("Restaurant with id : " + id + " doesn't exist"));
    }

    @Override
    public SimpleResponse deleteCategoriesBiId(Long id) {

        if (!categoryRepository.existsById(id)) {
            throw new AlreadyExistsException();
        }
        categoryRepository.deleteById(id);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Category with id : "+id+" successfully deleted ...!"))
                .build();    }

    @Override
    public SimpleResponse updateCategory(Long id, Category category) {
        Category oldCategory = categoryRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Category with id : " + id + " doesn't exist"));

        oldCategory.setName(category.getName());
        categoryRepository.save(oldCategory);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Category successfully updated ...!")
                .build();
    }


}
