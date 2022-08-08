package com.mockproject.controller.admin;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mockproject.Constant.SessionConstant;
import com.mockproject.entity.Users;
import com.mockproject.service.StatsService;

@Controller(value = "homeControllerOfAdmin")
@RequestMapping("/admin")
public class HomeController {

	@Autowired
	private StatsService statsService;

	@GetMapping("")
	public String dogetIndex(Model model, RedirectAttributes ra, HttpSession session) {
		Users user = (Users) session.getAttribute(SessionConstant.CURRENT_USER);

		if (user != null) {
			String[][] chartData = statsService.getTotalPriceLast6Month();
			model.addAttribute("chartData", chartData);
			return "admin/index";
		}else {
			ra.addFlashAttribute("message", "Please Login!");
			return "redirect:/login";
		}
	}
}
