package eu.burmov.springblog.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import eu.burmov.springblog.entity.Post;

public interface PostService {
	public Page<Post> findAllByOrderByIdDesc(Pageable pageable);
	public List<Post> findAllByOrderByTitle();
	public Page<Post> findAllByOrderByTitle(Pageable pageable);
	public Page<Post> findAllByTitleContainingOrderByTitle(String title, Pageable pageable);
	public Post save(Post post);
	public Post findById(Integer id);
	public boolean deleteById(Integer id);
}
