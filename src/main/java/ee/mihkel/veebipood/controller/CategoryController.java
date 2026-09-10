package ee.mihkel.veebipood.controller;

import ee.mihkel.veebipood.entity.Category;
import ee.mihkel.veebipood.entity.Product;
import ee.mihkel.veebipood.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200", "https://veebipood-09-2026-frontend.onrender.com"})
public class CategoryController {

    private final CategoryRepository categoryRepository;

    @GetMapping("categories")
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @PostMapping("categories")
    public List<Category> saveCategory(@RequestBody Category category) {
        if (category.getId() != null) {
            throw new RuntimeException("Cannot add category with id"); // katkesta kood ja viska välja viga.
        }
        categoryRepository.save(category);
        return categoryRepository.findAll();
    }

    @DeleteMapping("categories/{id}")
    public List<Category> deleteCategory(@PathVariable Long id) {
        categoryRepository.deleteById(id);
        return categoryRepository.findAll();
    }
}
