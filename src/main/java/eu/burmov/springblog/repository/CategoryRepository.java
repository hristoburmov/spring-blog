package eu.burmov.springblog.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import eu.burmov.springblog.entity.Category;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {
	public List<Category> findAllByOrderByName();
	public Page<Category> findAllByOrderByName(Pageable pageable);
	public Page<Category> findAllByNameContainingOrderByName(String name, Pageable pageable);
}
