package com.mockproject.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mockproject.Constant.SessionConstant;
import com.mockproject.entity.OrderDetails;
import com.mockproject.entity.Orders;
import com.mockproject.entity.Users;
import com.mockproject.service.OrderDetailsService;
import com.mockproject.service.OrdersService;
import com.mockproject.service.UsersService;

@Controller
@RequestMapping("/user/user_details")
public class UserController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private OrdersService ordersService;
	
	@Autowired
	private OrderDetailsService orderDetailService;

	@GetMapping("")
	public String doGetUser_details(Model model, RedirectAttributes ra, HttpSession session) {
		Users user = (Users) session.getAttribute(SessionConstant.CURRENT_USER);

		if (user == null) {
			ra.addFlashAttribute("message", "Please Login!");
			return "redirect:/login";
		}
		model.addAttribute("usersRequest", new Users());
		return "user/user_details";
	}

	@GetMapping("/user_purchase")
	public String doGetPurchase(HttpSession session, Model model, RedirectAttributes ra) {
		Users currentUser =  (Users) session.getAttribute(SessionConstant.CURRENT_USER);
		
		if(currentUser != null) {
			List<Orders> orders = ordersService.findByUserId(currentUser.getId());
			model.addAttribute("orders", orders);
			return "user/user_purchase";
		}else {
			ra.addFlashAttribute("message", "please login again!");
			return "redirect:/login";
		}
	}
	
	@GetMapping("/user_purchase/user_purchase_details")
	public String doGetPurchaseDetails(@RequestParam("id")Long id, RedirectAttributes ra, Model model) {
		List<OrderDetails> orders = orderDetailService.findByOrderId(id);
		model.addAttribute("orders", orders);
		return "user/user_purchase_detail";
	}

	@GetMapping("/edit")
	public String doGetEditUser_Details(@RequestParam("username") String username, Model model) {
		Users userRequest = usersService.findByUsernameIsdeletedEnabled(username);
		model.addAttribute("usersRequest", userRequest);
		return "user/user_details::#form";
	}

	@PostMapping("/edit")
	public String doPostUser(@Valid @ModelAttribute("userRequest") Users userRequest, BindingResult bindingResult,
			RedirectAttributes redirectAttribute, HttpSession session,
			Model model/* , @RequestParam("image")MultipartFile multipartFile */) {
//		if(!multipartFile.isEmpty()) {
//			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
//			userRequest.setImgUrl(fileName);
//			Users savedUser = usersService.updateUserDetails(userRequest);
//			
//			String updloadDir = "user-photo/" + savedUser.getId();
//			
//			FileUploadUtil.cleanDir(uploadDir);
//			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
//		}else {
//			if(userRequest.getImgUrl().isEmpty()) userRequest.setImgUrl(null);
//			usersService.updateUserDetails(userRequest);
//		}
		if (bindingResult.hasErrors()) {
			bindingResult.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
			redirectAttribute.addFlashAttribute("errorMessage", "User is not valid");
		} else {
			try {
				usersService.updateUserDetails(userRequest);
				session.removeAttribute(SessionConstant.CURRENT_USER);
				redirectAttribute.addFlashAttribute("message", "Changed profile successfully!, please login agian!");
//				model.addAttribute("message", "Changed profile successfully!, please login agian!");
				return "redirect:/login";
			} catch (Exception e) {
				e.printStackTrace();
				redirectAttribute.addFlashAttribute("errorMessage",
						"Cannot update user : " + userRequest.getUsername());
			}
		}
		return "redirect:/user/user_details";
	}
}
