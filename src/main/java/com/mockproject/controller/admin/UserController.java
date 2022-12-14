package com.mockproject.controller.admin;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mockproject.entity.Users;
import com.mockproject.service.UsersService;

@Controller(value = "UserControllerOfAdmin")
@RequestMapping("/admin/user")
public class UserController {

	@Autowired
	private UsersService userService;

	@GetMapping("")
	public String doGetUser(Model model) {
		List<Users> users = userService.findAll();
		model.addAttribute("users", users);
		// tạo user Null Cho admin
		model.addAttribute("userRequest", new Users());
		return "admin/user";
	}

	@GetMapping("/delete")
	public String doGetDelete(@RequestParam("username") String username, RedirectAttributes redirectAttributes) {
		try {
			userService.deleteLogical(username);
			redirectAttributes.addFlashAttribute("succeedMessage", "User " + username + " was deleted");
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("errorMessage", "Cannot delete user" + username);
		}
		return "redirect:/admin/user";
	}

	@GetMapping("/edit")
	public String doGetEditUser(@RequestParam("username") String username, Model model) {
		Users userRequest = userService.findByUsername(username);
		model.addAttribute("userRequest", userRequest);
		return "admin/user::#form";
	}

	@PostMapping("/create")
	public String doPostCreate(@ModelAttribute("userRequest") Users userRequest, RedirectAttributes redirectAttribute, 
			@RequestParam("imageFile")MultipartFile multipartFile) {
		try {
			if(!multipartFile.isEmpty()) {
				String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				userRequest.setImgUrl(fileName);
				userRequest.setEnabled(Boolean.TRUE);
				Users insertUsers = userService.save(userRequest);
				String uploadDir = "\\Fpoly\\Springboot\\Java5\\Cart_Manager-main\\src\\main\\resources\\static\\admin\\img\\User_Image\\" + insertUsers.getId();
				Path uploadPath = Paths.get(uploadDir);
				
				if(!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}
				InputStream inputStream = multipartFile.getInputStream();
				Path filePath = uploadPath.resolve(fileName);
				Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
			}else {
				userRequest.setEnabled(Boolean.TRUE);
				userService.save(userRequest);
			}
			redirectAttribute.addFlashAttribute("succeedMessage",
					"User " + userRequest.getUsername() + " was created successfully");
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttribute.addFlashAttribute("errorMessage", "Cannot create user : " + userRequest.getUsername());
		}
		return "redirect:/admin/user";
	}

	@PostMapping("/edit")
	public String doPostUpdate(@Valid @ModelAttribute("userRequest") Users userRequest, BindingResult bindingResult,
			RedirectAttributes redirectAttribute) {
		if (bindingResult.hasErrors()) {
			bindingResult.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
			redirectAttribute.addFlashAttribute("errorMessage", "User is not valid");
		} else {
			try {
				userService.update(userRequest);
				redirectAttribute.addFlashAttribute("succeedMessage",
						"User " + userRequest.getUsername() + " has been edited successfully");
			} catch (Exception e) {
				e.printStackTrace();
				redirectAttribute.addFlashAttribute("errorMessage",
						"Cannot update user : " + userRequest.getUsername());
			}
		}
		return "redirect:/admin/user";
	}
}
