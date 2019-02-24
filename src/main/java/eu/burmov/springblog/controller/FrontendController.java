package eu.burmov.springblog.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import eu.burmov.springblog.entity.Category;
import eu.burmov.springblog.entity.Comment;
import eu.burmov.springblog.entity.Post;
import eu.burmov.springblog.service.CategoryService;
import eu.burmov.springblog.service.CommentService;
import eu.burmov.springblog.service.PostService;
import eu.burmov.springblog.util.SpringBlogUtil;

@Controller
public class FrontendController {
	
	private PostService postService;
	private CategoryService categoryService;
	private CommentService commentService;
	
	// Constructors
	public FrontendController(PostService postService, CategoryService categoryService, CommentService commentService) {
		this.postService = postService;
		this.categoryService = categoryService;
		this.commentService = commentService;
	}
	
	// GET - Index
	@RequestMapping(method = RequestMethod.GET, value = {"", "/"})
	public String listPosts(Model model, @RequestParam Optional<String> name, @PageableDefault(size = SpringBlogUtil.ITEMS_PER_PAGE) Pageable pageable) {
		Page<Post> page = (name.isPresent() && !name.get().isEmpty()) ? postService.findAllByTitleContainingOrderByTitle(name.get(), pageable) : postService.findAllByOrderByIdDesc(pageable);
		model.addAttribute("page", page);
		model.addAttribute("categories", categoryService.findAllByOrderByName());
		return "frontend/posts";
	}
	
	// GET - Post
	@RequestMapping(method = RequestMethod.GET, value = "/posts/{id}")
	public String showPost(Model model, @PathVariable("id") String idString) {
		Integer id = SpringBlogUtil.parseId(idString);
		Post post = postService.findById(id);
		
		if(post == null) {
			return "redirect:/";
		}

		Comment comment = new Comment();
		comment.setAuthor(SecurityContextHolder.getContext().getAuthentication().getName());
		comment.setPost(post);
		
		model.addAttribute("post", post);
		model.addAttribute("categories", categoryService.findAllByOrderByName());
		model.addAttribute("title", (post != null) ? post.getTitle() : "Post not found");
		model.addAttribute("comment", comment);
		return "frontend/post";
	}
	
	// POST - Post comment on post
	@RequestMapping(method = RequestMethod.POST, value = "/posts/{id}/comment")
	public String saveComment(@Valid @ModelAttribute Comment comment, BindingResult result, Model model, RedirectAttributes attributes, HttpServletRequest request, @PathVariable("id") String idString) {
		if(!result.hasErrors()) {
			if(commentService.save(comment) != null) {
				attributes.addFlashAttribute("success", "Comment has been SAVED");
			}
			System.out.println("Comment should be saved");
		} else {
			Integer id = SpringBlogUtil.parseId(idString);
			Post post = postService.findById(id);
			
			model.addAttribute("post", post);
			model.addAttribute("categories", categoryService.findAllByOrderByName());
			model.addAttribute("title", (post != null) ? post.getTitle() : "Post not found");
			return "frontend/post";
		}
		return "redirect:" + request.getHeader("Referer");
	}
	
	// GET - Category Posts
	@RequestMapping(method = RequestMethod.GET, value = "/categories/{id}")
	public String listCategoryPosts(Model model, @PathVariable("id") String idString, @RequestParam Optional<String> name, @PageableDefault(size = SpringBlogUtil.ITEMS_PER_PAGE) Pageable pageable) {
		Integer id = SpringBlogUtil.parseId(idString);
		Category category = categoryService.findById(id);
		
		if(category == null) {
			return "redirect:/";
		}
		
		List<Post> posts = category.getPosts();
		
		// Filter
		if(name.isPresent() && !name.get().isEmpty()) {
			String nameLowerCase = name.get().toLowerCase();
			for(Iterator<Post> iterator = posts.iterator(); iterator.hasNext();) {
				if(!iterator.next().getTitle().toLowerCase().contains(nameLowerCase)) {
					iterator.remove();
				}
			}
		}
		
		Page<Post> page = new PageImpl<Post>(posts, pageable, posts.size());
		model.addAttribute("page", page);
		model.addAttribute("category", category);
		model.addAttribute("categories", categoryService.findAllByOrderByName());
		return "frontend/category";
	}

}
