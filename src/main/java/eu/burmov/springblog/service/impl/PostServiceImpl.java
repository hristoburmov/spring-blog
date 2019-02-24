package eu.burmov.springblog.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import eu.burmov.springblog.entity.Post;
import eu.burmov.springblog.repository.PostRepository;
import eu.burmov.springblog.service.PostService;

@Service
public class PostServiceImpl implements PostService {
	
	private PostRepository postRepository;

	// Constructors
	public PostServiceImpl(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	@Override
	public Page<Post> findAllByOrderByIdDesc(Pageable pageable) {
		return postRepository.findAllByOrderByIdDesc(pageable);
	}
	
	@Override
	public List<Post> findAllByOrderByTitle() {
		return postRepository.findAllByOrderByTitle();
	}

	@Override
	public Page<Post> findAllByOrderByTitle(Pageable pageable) {
		return postRepository.findAllByOrderByTitle(pageable);
	}

	@Override
	public Page<Post> findAllByTitleContainingOrderByTitle(String title, Pageable pageable) {
		return postRepository.findAllByTitleContainingOrderByTitle(title, pageable);
	}

	@Override
	public Post save(Post post) {
		return post == null ? null : postRepository.save(post);
	}

	@Override
	public Post findById(Integer id) {
		if(id == null) return null;
		Optional<Post> post = postRepository.findById(id);
		return post.isPresent() ? post.get() : null;
	}

	@Override
	public boolean deleteById(Integer id) {
		if(id == null) return false;
		postRepository.deleteById(id);
		return postRepository.findById(id).isPresent() ? false : true;
	}

}
