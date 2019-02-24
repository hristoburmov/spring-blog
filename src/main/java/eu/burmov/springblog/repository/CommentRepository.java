package eu.burmov.springblog.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import eu.burmov.springblog.entity.Comment;

public interface CommentRepository extends PagingAndSortingRepository<Comment, Integer> {
	public List<Comment> findAllByOrderByPost();
	public Page<Comment> findAllByOrderByPost(Pageable pageable);
	public Page<Comment> findAllByContentContainingOrderByPost(String content, Pageable pageable);
}
