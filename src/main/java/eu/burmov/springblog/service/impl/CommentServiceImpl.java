package eu.burmov.springblog.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import eu.burmov.springblog.entity.Comment;
import eu.burmov.springblog.repository.CommentRepository;
import eu.burmov.springblog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	private CommentRepository commentRepository;
	
	// Constructors
	public CommentServiceImpl(CommentRepository commentRepository) {
		this.commentRepository = commentRepository;
	}

	@Override
	public List<Comment> findAllByOrderByPost() {
		return commentRepository.findAllByOrderByPost();
	}

	@Override
	public Page<Comment> findAllByOrderByPost(Pageable pageable) {
		return commentRepository.findAll(pageable);
	}

	@Override
	public Page<Comment> findAllByContentContainingOrderByPost(String content, Pageable pageable) {
		return commentRepository.findAllByContentContainingOrderByPost(content, pageable);
	}

	@Override
	public Comment save(Comment comment) {
		return comment == null ? null : commentRepository.save(comment);
	}

	@Override
	public Comment findById(Integer id) {
		if(id == null) return null;
		Optional<Comment> comment = commentRepository.findById(id);
		return comment.isPresent() ? comment.get() : null;
	}

	@Override
	public boolean deleteById(Integer id) {
		if(id == null) return false;
		commentRepository.deleteById(id);
		return commentRepository.findById(id).isPresent() ? false : true;
	}

}
