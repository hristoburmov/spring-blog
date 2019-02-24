package eu.burmov.springblog.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import eu.burmov.springblog.entity.Comment;

public interface CommentService {
	public List<Comment> findAllByOrderByPost();
	public Page<Comment> findAllByOrderByPost(Pageable pageable);
	public Page<Comment> findAllByContentContainingOrderByPost(String content, Pageable pageable);
	public Comment save(Comment comment);
	public Comment findById(Integer id);
	public boolean deleteById(Integer id);
}
