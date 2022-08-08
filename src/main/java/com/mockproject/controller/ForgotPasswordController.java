package com.mockproject.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mockproject.Constant.SessionConstant;
import com.mockproject.entity.Users;
import com.mockproject.service.UsersService;
import com.mockproject.util.SessionUtil;
import com.mockproject.util.UserNotFoundException;

import net.bytebuddy.utility.RandomString;

@Controller
public class ForgotPasswordController {

	private BCryptPasswordEncoder bcry = new BCryptPasswordEncoder();

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private UsersService usersService;

	@GetMapping("/forgot_password")
	public String showForgotPasswordForm(Model model) {
		model.addAttribute("pageTitle", "ForgotPassword");
		return "user/forgot_password";
	}

	@GetMapping("/reset_password")
	public String showResetPasswordForm(@Param("token") String token,RedirectAttributes ra, Model model) {
		Users user = usersService.getByResetPasswordToken(token);

		if (user == null) {
			ra.addFlashAttribute("message", "The token has expired, try again!");
			return "redirect:/forgot_password";
		} else if (token == null) {
			ra.addFlashAttribute("message", "The token has expired, try again!");
			return "redirect:/forgot_password";
		} else {
			model.addAttribute("token", token);
			model.addAttribute("pageTitle", "Reset Password");
			return "user/reset_password";
		}
	}

	@GetMapping("/change_password")
	public String processChangePassword(Model model) {
		model.addAttribute("title", "Change Your Password");
		return "user/change_password";
	}

	@PostMapping("/change_password")
	public String processChangePassword(HttpServletRequest request, @RequestParam("oldPassword") String oldPassword,
			@RequestParam("newPassword") String newPassword, 
			HttpSession session, Model model, RedirectAttributes ra) {

		Users currentUser = (Users) session.getAttribute(SessionConstant.CURRENT_USER);

		if (oldPassword.equals(newPassword)) {
			model.addAttribute("message", "Your new password must be different than the onl one, try again!");
			return "user/change_password";
		}
		if (!bcry.matches(oldPassword, currentUser.getHashPassword())) {
			model.addAttribute("message", "Your old password is incorrect!");
			return "user/change_password";
		} else {
			usersService.changePassword(currentUser, newPassword);
			session.removeAttribute(SessionConstant.CURRENT_USER);
//			model.addAttribute("message", "You have changed your password successfully." + "Please login again!");
			ra.addFlashAttribute("message", "You have changed your password successfully." + "Please login again!");
			return "redirect:/login";
		}
	}

	@PostMapping("/reset_password")
	public String processResetPassword(HttpServletRequest request, Model model, RedirectAttributes ra,
			HttpSession session) {
		String token = request.getParameter("token");
		String password = request.getParameter("password");

		Users user = usersService.getByResetPasswordToken(token);
		model.addAttribute("title", "Reset your Password");

		if (user == null) {
			model.addAttribute("message", "Invalid Token");
			return "redirect:/forgot_password";
		} else {
			usersService.updatePassword(user, password);
			ra.addFlashAttribute("message", "You have successfully changed your password.Please login again!");
			return "redirect:/login";
		}
	}

	@PostMapping("/forgot_password")
	public String processForgotPassword(HttpServletRequest request, Model model) throws Exception {
		String email = request.getParameter("email");
		String token = RandomString.make(30);

//		System.out.println("email :" +email);
//		System.out.println("token :" +token);
		try {
			usersService.updateResetPassword(token, email);

			// generate reset password link
			// send email
			String resetPasswordLink = SessionUtil.getSiteURL(request) + "/reset_password?token=" + token;
			sendEmail(email, resetPasswordLink);

			model.addAttribute("message", "We have sent a reset password link to your email. Please check.");
		} catch (UserNotFoundException e) {
			model.addAttribute("error", e.getMessage());
		} catch (UnsupportedEncodingException | MessagingException e) {
			model.addAttribute("error", "Error while sending email.");
		}
		model.addAttribute("pageTitle", "Forgot Password");
		return "user/forgot_password";
	}

	private void sendEmail(String email, String resetPasswordLink)
			throws UnsupportedEncodingException, MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom("contact@shop.com", "Shop Support");
		helper.setTo(email);

		String subject = "Here's the link to reset your password";

		String content = "<p>Hello,</p>" + "<p>You have requested to reset your password.</p>"
				+ "<p>Click the link below to change your password:</p>" + "<p><a href=\"" + resetPasswordLink
				+ "\">Change my Password</a></p>" + "<br>" + "<p>Ignore this email if you do remember your password,"
				+ "or you have not made the request.</p>";
		helper.setSubject(subject);
		helper.setText(content, true);
		mailSender.send(message);
	}
}
