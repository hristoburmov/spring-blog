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

import eu.burmov.springblog.entity.Comment;
import eu.burmov.springblog.service.CommentService;
import eu.burmov.springblog.service.PostService;
import eu.burmov.springblog.util.SpringBlogUtil;

@Controller
@RequestMapping(value = "/admin/comments")
public class CommentController {
	
	private CommentService commentService;
	private PostService postService;
	
	// Constructors
	public CommentController(CommentService commentService, PostService postService) {
		this.commentService = commentService;
		this.postService = postService;
	}
	
	// GET - List
	@RequestMapping(method = RequestMethod.GET)
	public String listComments(Model model, @RequestParam Optional<String> name, @PageableDefault(size = SpringBlogUtil.ITEMS_PER_ADMIN_PAGE) Pageable pageable) {
		Page<Comment> page = (name.isPresent()) ? commentService.findAllByContentContainingOrderByPost(name.get(), pageable) : commentService.findAllByOrderByPost(pageable);
		model.addAttribute("page", page);
		return "admin/comment/list";
	}
	
	// GET - Add
	@RequestMapping(method = RequestMethod.GET, value = "/add")
	public String addComment(Model model) {
		model.addAttribute("comment", new Comment());
		model.addAttribute("posts", postService.findAllByOrderByTitle());
		return "admin/comment/form";
	}
	
	// GET - Edit
	@RequestMapping(method = RequestMethod.GET, value = "/edit/{id}")
	public String editComment(Model model, @PathVariable("id") String idString) {
		Integer id = SpringBlogUtil.parseId(idString);
		Comment comment = commentService.findById(id);
		
		model.addAttribute("comment", (comment == null) ? new Comment() : comment);
		model.addAttribute("posts", postService.findAllByOrderByTitle());
		return "admin/comment/form";
	}
	
	// POST - Save
	@RequestMapping(method = RequestMethod.POST, value = "/save")
	public String saveComment(@Valid @ModelAttribute Comment comment, BindingResult result, Model model, RedirectAttributes attributes) {
		if(!result.hasErrors()) {
			if(commentService.save(comment) != null) {
				attributes.addFlashAttribute("success", "Comment has been SAVED");
			}
		} else {
			model.addAttribute("posts", postService.findAllByOrderByTitle());
			return "admin/comment/form";
		}
		return "redirect:/admin/comments";
	}
	
	// POST - Delete
	@RequestMapping(method = RequestMethod.DELETE, value = "/delete/{id}")
	public String deleteComment(@PathVariable Integer id, RedirectAttributes attributes) {
		if(commentService.deleteById(id)) {
			attributes.addFlashAttribute("success", "Comment has been DELETED");
		}
		return "redirect:/admin/comments";
	}

}
