package eu.burmov.springblog.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import eu.burmov.springblog.entity.Post;

public interface PostRepository extends PagingAndSortingRepository<Post, Integer> {
	public Page<Post> findAllByOrderByIdDesc(Pageable pageable);
	public List<Post> findAllByOrderByTitle();
	public Page<Post> findAllByOrderByTitle(Pageable pageable);
	public Page<Post> findAllByTitleContainingOrderByTitle(String title, Pageable pageable);
}
