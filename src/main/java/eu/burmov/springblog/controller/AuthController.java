package eu.burmov.springblog.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import eu.burmov.springblog.entity.Role;
import eu.burmov.springblog.entity.User;

@Controller
@RequestMapping(value = "/auth")
public class AuthController {
	
	private UserDetailsManager userDetailsManager;
	
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	// Constructors
	public AuthController(UserDetailsManager userDetailsManager) {
		this.userDetailsManager = userDetailsManager;
	}
	
	// GET - Login
	@RequestMapping(method = RequestMethod.GET, value = "/login")
	public String loginForm(Model model) {
		model.addAttribute("user", new User());
		return "auth/login";
	}
	
	// GET - Register
	@RequestMapping(method = RequestMethod.GET, value = "/register")
	public String registerForm(Model model) {
		model.addAttribute("user", new User());
		return "auth/register";
	}
	
	// POST - Register
	@RequestMapping(method = RequestMethod.POST, value = "/register")
	public String registerProcess(@Valid @ModelAttribute("user") User user, BindingResult result, Model model, RedirectAttributes attributes) {
		
		// Validation errors
		if(result.hasErrors()) {
			return "auth/register";
		}
		
		// Username exists
		if(userDetailsManager.userExists(user.getUsername())) {
			result.addError(new FieldError("user", "username", "Username '" + user.getUsername() + "' already exists"));
			return "auth/register";
		}
		
		// Create and store user
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_" + Role.USER.getRole());
		userDetailsManager.createUser(new org.springframework.security.core.userdetails.User(user.getUsername(), passwordEncoder.encode(user.getPassword()), authorities));
		attributes.addFlashAttribute("success", "Registration succcessful, you may now login");
		return "redirect:/auth/login";
		                      																																												
	}

}
