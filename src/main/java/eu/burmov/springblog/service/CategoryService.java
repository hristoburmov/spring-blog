package eu.burmov.springblog.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import eu.burmov.springblog.entity.Category;

public interface CategoryService {
	public List<Category> findAllByOrderByName();
	public Page<Category> findAllByOrderByName(Pageable pageable);
	public Page<Category> findAllByNameContainingOrderByName(String name, Pageable pageable);
	public Category save(Category category);
	public Category findById(Integer id);
	public boolean deleteById(Integer id);
}
