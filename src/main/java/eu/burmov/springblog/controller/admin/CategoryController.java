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

import eu.burmov.springblog.entity.Category;
import eu.burmov.springblog.service.CategoryService;
import eu.burmov.springblog.service.PostService;
import eu.burmov.springblog.util.SpringBlogUtil;

@Controller
@RequestMapping(value = "/admin/categories")
public class CategoryController {
	
	private CategoryService categoryService;
	private PostService postService;
	
	// Constructors
	public CategoryController(CategoryService categoryService, PostService postService) {
		this.categoryService = categoryService;
		this.postService = postService;
	}
	
	// GET - List
	@RequestMapping(method = RequestMethod.GET)
	public String listCategories(Model model, @RequestParam Optional<String> name, @PageableDefault(size = SpringBlogUtil.ITEMS_PER_ADMIN_PAGE) Pageable pageable) {
		Page<Category> page = (name.isPresent()) ? categoryService.findAllByNameContainingOrderByName(name.get(), pageable) : categoryService.findAllByOrderByName(pageable);
		model.addAttribute("page", page);
		return "admin/category/list";
	}
	
	// GET - Add
	@RequestMapping(method = RequestMethod.GET, value = "/add")
	public String addCategory(Model model) {
		model.addAttribute("category", new Category());
		model.addAttribute("posts", postService.findAllByOrderByTitle());
		return "admin/category/form";
	}
	
	// GET - Edit
	@RequestMapping(method = RequestMethod.GET, value = "/edit/{id}")
	public String editCategory(Model model, @PathVariable("id") String idString) {
		Integer id = SpringBlogUtil.parseId(idString);
		Category category = categoryService.findById(id);
		
		model.addAttribute("category", (category == null) ? new Category() : category);
		model.addAttribute("posts", postService.findAllByOrderByTitle());
		return "admin/category/form";
	}
	
	// POST - Save
	@RequestMapping(method = RequestMethod.POST, value = "/save")
	public String saveCategory(@Valid @ModelAttribute Category category, BindingResult result, Model model, RedirectAttributes attributes) {
		if(!result.hasErrors()) {
			if(categoryService.save(category) != null) {
				attributes.addFlashAttribute("success", "Category has been SAVED");
			}
		} else {
			return "admin/category/form";
		}
		return "redirect:/admin/categories";
	}
	
	// POST - Delete
	@RequestMapping(method = RequestMethod.DELETE, value = "/delete/{id}")
	public String deletePost(@PathVariable Integer id, RedirectAttributes attributes) {
		if(categoryService.deleteById(id)) {
			attributes.addFlashAttribute("success", "Category has been DELETED");
		}
		return "redirect:/admin/categories";
	}

}
