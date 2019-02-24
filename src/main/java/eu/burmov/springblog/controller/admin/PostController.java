package eu.burmov.springblog.controller.admin;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import eu.burmov.springblog.entity.Post;
import eu.burmov.springblog.service.CategoryService;
import eu.burmov.springblog.service.PostService;
import eu.burmov.springblog.util.SpringBlogUtil;

@Controller
@RequestMapping(value = "/admin/posts")
public class PostController {
	
	private PostService postService;
	private CategoryService categoryService;
	
	// Constructors
	public PostController(PostService postService, CategoryService categoryService) {
		this.postService = postService;
		this.categoryService = categoryService;
	}
	
	// GET - List
	@RequestMapping(method = RequestMethod.GET)
	public String listPosts(Model model, @RequestParam Optional<String> name, @PageableDefault(size = SpringBlogUtil.ITEMS_PER_ADMIN_PAGE) Pageable pageable) {
		Page<Post> page = (name.isPresent()) ? postService.findAllByTitleContainingOrderByTitle(name.get(), pageable) : postService.findAllByOrderByTitle(pageable);
		model.addAttribute("page", page);
		return "admin/post/list";
	}
	
	// GET - Add
	@RequestMapping(method = RequestMethod.GET, value = "/add")
	public String addPost(Model model) {
		model.addAttribute("post", new Post());
		model.addAttribute("categories", categoryService.findAllByOrderByName());
		return "admin/post/form";
	}
	
	// GET - Edit
	@RequestMapping(method = RequestMethod.GET, value = "/edit/{id}")
	public String editPost(Model model, @PathVariable("id") String idString) {
		Integer id = SpringBlogUtil.parseId(idString);
		Post post = postService.findById(id);
		
		model.addAttribute("post", (post == null) ? new Post() : post);
		model.addAttribute("categories", categoryService.findAllByOrderByName());
		return "admin/post/form";
	}
	
	// POST - Save
	@RequestMapping(method = RequestMethod.POST, value = "/save")
	public String savePost(@Valid @ModelAttribute Post post, BindingResult result, Model model, RedirectAttributes attributes) {
		if(!result.hasErrors()) {
			if(postService.save(post) != null) {
				attributes.addFlashAttribute("success", "Post has been SAVED");
			}
		} else {
			model.addAttribute("categories", categoryService.findAllByOrderByName());
			return "admin/post/form";
		}
		return "redirect:/admin/posts";
	}
	
	// POST - Delete
	@RequestMapping(method = RequestMethod.DELETE, value = "/delete/{id}")
	public String deletePost(@PathVariable Integer id, RedirectAttributes attributes) {
		if(postService.deleteById(id)) {
			attributes.addFlashAttribute("success", "Post has been DELETED");
		}
		return "redirect:/admin/posts";
	}

}
