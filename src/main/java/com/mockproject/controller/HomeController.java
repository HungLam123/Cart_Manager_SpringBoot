package com.mockproject.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mockproject.Constant.SessionConstant;
import com.mockproject.entity.Products;
import com.mockproject.entity.Users;
import com.mockproject.service.AuthenticationService;
import com.mockproject.service.ProductsService;
import com.mockproject.service.UsersService;
import com.mockproject.util.SessionUtil;

@Controller
public class HomeController {

	@Autowired
	private ProductsService productservice;

	@Autowired
	private UsersService userService;

	@Autowired
	private AuthenticationService authenticationService;

	private static final int MAX_SIZE = 4;

	// localhost:8080/index || localhost:8080 || localhost:8080/

	@GetMapping(value = { "/index", "", "/" })
	public String doGetIndex(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			Model model) {

		List<Products> products = new ArrayList<>();
		try {
			Page<Products> pageProducts = productservice.findAll(MAX_SIZE, page);
			products = pageProducts.getContent();// trả ra list
			model.addAttribute("totalPages", pageProducts.getTotalPages());
			model.addAttribute("currentPage", page);
		} catch (Exception e) {
			products = productservice.findAll();
		}
		model.addAttribute("products", products);

		return "user/index";
	}

	@GetMapping("/dienthoai")
	public String doGetDienThoai(Model model) {
		List<Products> products = productservice.findDienThoai();
		model.addAttribute("products", products);
		return "user/dienthoai";
	}

	@GetMapping("/laptop")
	public String doGetLapTop(Model model) {
		List<Products> products = productservice.findLapTop();
		model.addAttribute("products", products);
		return "user/laptop";
	}

	@GetMapping("/login")
	public String doGetLogin(Model model) {
		model.addAttribute("userRequest", new Users());
		return "user/login";
	}

	@GetMapping("/logout")
	public String doGetLogout(HttpSession session) {
		session.removeAttribute(SessionConstant.CURRENT_USER);
		return "redirect:/index";
	}

	@GetMapping("/register")
	public String doGetRegister(Model model) {
		model.addAttribute("userRequest", new Users());
		return "user/register";
	}

	@GetMapping("/index/{slug}")
	public String doGetSlug(@PathVariable("slug") String slug, Model model) {
		Products product = productservice.findBySlug(slug);
		if (product == null) {
			return "redirect:/index";
		} else {
			model.addAttribute("product", product);
		}
		return "user/detail_product";
	}

	@GetMapping("/verify")
	public String verifyAccount(@Param("code") String code, RedirectAttributes ra) {
		if (userService.verify(code)) {
			ra.addFlashAttribute("message", "Your accout is activated, please login!");
			return "redirect:/login";
		} else {
			ra.addFlashAttribute("message", "The code has expired, try again!");
			return "redirect:/register";
		}
	}

	@PostMapping("/login")
	public String doPostLogin(@ModelAttribute("userRequest") Users userRequest, HttpSession session,
			RedirectAttributes ra) {
		try {
			Users userResponse = authenticationService.doLogin(userRequest.getUsername(), userRequest.getHashPassword(), session);
			session.setAttribute(SessionConstant.CURRENT_USER, userResponse);
			return "redirect:/index";
		} catch (Exception e) {
			ra.addFlashAttribute("message", "Your account is not activated ,please again!");
			return "redirect:/login";
		}
	}

	@PostMapping("/register")
	public String doPostRegister(@Valid Users user, BindingResult bindingResult, HttpServletRequest request,
			RedirectAttributes ra, HttpSession session, Model model) throws Exception {
		if (bindingResult.hasErrors()) {
			model.addAttribute("userRequest", user);
			return "redirect:/register";
		}
		try {
			Users users = userService.register(user);
			String siteURL = SessionUtil.getSiteURL(request);
			userService.sendVerifycationEmail(users, siteURL);
			return "user/register_success";
		} catch (Exception e) {
			bindingResult.rejectValue("email", "user.email", "an account already exists for this username.");
			model.addAttribute("userRequest", user);
			ra.addFlashAttribute("message", "an account already exists for this username.");
			return "redirect:/register";
		}
	}
}