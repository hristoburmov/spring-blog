package eu.burmov.springblog.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import eu.burmov.springblog.entity.Category;
import eu.burmov.springblog.repository.CategoryRepository;
import eu.burmov.springblog.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	private CategoryRepository categoryRepository;
	
	// Constructors
	public CategoryServiceImpl(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
	
	@Override
	public List<Category> findAllByOrderByName() {
		return categoryRepository.findAllByOrderByName();
	}

	@Override
	public Page<Category> findAllByOrderByName(Pageable pageable) {
		return categoryRepository.findAllByOrderByName(pageable);
	}

	@Override
	public Page<Category> findAllByNameContainingOrderByName(String name, Pageable pageable) {
		return categoryRepository.findAllByNameContainingOrderByName(name, pageable);
	}

	@Override
	public Category save(Category category) {
		return category == null ? null : categoryRepository.save(category);
	}

	@Override
	public Category findById(Integer id) {
		if(id == null) return null;
		Optional<Category> category = categoryRepository.findById(id);
		return category.isPresent() ? category.get() : null;
	}

	@Override
	public boolean deleteById(Integer id) {
		if(id == null) return false;
		categoryRepository.deleteById(id);
		return categoryRepository.findById(id).isPresent() ? false : true;
	}

}
