package com.mockproject.service.impl;

import java.util.List;
import java.util.Optional;

import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mockproject.Security.EncoderConfig;
import com.mockproject.entity.Roles;
import com.mockproject.entity.Users;
import com.mockproject.repository.UsersRepo;
import com.mockproject.service.RolesService;
import com.mockproject.service.UsersService;
import com.mockproject.util.UserNotFoundException;

import net.bytebuddy.utility.RandomString;

@Service
public class UsersServiceImpl implements UsersService {

	@Autowired
	private EncoderConfig encoderConfig;

	@Autowired
	private UsersRepo repo;

	@Autowired
	private RolesService roleService;

	@Autowired
	private JavaMailSender mailSender;

	@Override
	public Users doLogin(String username, String password) {
		Users user = repo.findByUsernameAndIsDeletedAndEnabled(username, Boolean.FALSE, Boolean.TRUE);

		if (user != null) {
			boolean checkPassword = encoderConfig.passwordEncoder().matches(password, user.getHashPassword());
			return checkPassword ? user : null;
		}
		return null;
	}

	@Transactional
	@Override
	public Users save(Users user) throws Exception {
		// TODO Auto-generated method stub
		if (checkIfUserExit(user.getUsername())) {
			throw new Exception("Users already exits for this username");
		} else {
			Roles role = roleService.findByDescription("user");
			user.setRoles(role);
			user.setIsDeleted(Boolean.FALSE);
			String hashPassWord = encoderConfig.passwordEncoder().encode(user.getHashPassword());
			user.setHashPassword(hashPassWord);
			return repo.saveAndFlush(user);
		}
	}

	private boolean checkIfUserExit(String username) {
		// TODO Auto-generated method stub
		return repo.findByUsername(username) != null ? true : false;
	}
	
	@Override
	public Users findByEmail(String email) {
		// TODO Auto-generated method stub
		return repo.findByEmail(email);
	}

	@Override
	public List<Users> findAll() {
		return repo.findByIsDeleted(Boolean.FALSE);
	}

	@Transactional(rollbackOn = { Exception.class, Throwable.class })
	@Override
	public void deleteLogical(String username) {
		repo.deleteLogical(username);
	}

	@Override
	public Users findByUsernameIsdeletedEnabled(String username) {
		// TODO Auto-generated method stub
		return repo.findByUsernameAndIsDeletedAndEnabled(username, Boolean.FALSE, Boolean.TRUE);
	}

	@Transactional(rollbackOn = { Exception.class, Throwable.class })
	@Override
	public void update(Users user) {
		// neu user k emty thi di kt buoc 2 va nguoc lai
		if (ObjectUtils.isNotEmpty(user) && StringUtils.isEmpty(user.getHashPassword())) {
			repo.updateNonPass(user.getEmail(), user.getFullname(), user.getUsername());
		} else {
			String hashPassword = encoderConfig.passwordEncoder().encode(user.getHashPassword());
			user.setHashPassword(hashPassword);
			repo.update(user.getEmail(), hashPassword, user.getFullname(), user.getUsername());
		}
	}

	@Override
	@Transactional
	public void updateResetPassword(String token, String email) throws UserNotFoundException {
		Users user = repo.findByEmail(email);
		if (user != null) {
			user.setResetPassword(token);
			repo.save(user);
		} else {
			throw new UserNotFoundException("Could not find any customer with the email " + email);
		}
	}

	@Override
	public Users getByResetPasswordToken(String token) {
		// TODO Auto-generated method stub
		return repo.findByResetPassword(token);
	}

	public void updatePassword(Users user, String newPassword) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(newPassword);

		user.setHashPassword(encodedPassword);
		user.setResetPassword(null);
		repo.save(user);
	}

	@Override
	public void changePassword(Users user, String newPassword) {
		String encondedPassword = encoderConfig.passwordEncoder().encode(newPassword);
		user.setHashPassword(encondedPassword);
		repo.save(user);
	}

	@Override
	@Transactional(rollbackOn = { Exception.class, Throwable.class })
	public Users register(Users user) throws Exception {
		Roles role = roleService.findByDescription("user");
		user.setRoles(role);
		user.setEnabled(Boolean.FALSE);
		user.setIsDeleted(Boolean.FALSE);
		String hashPassword = encoderConfig.passwordEncoder().encode(user.getHashPassword());
		user.setHashPassword(hashPassword);

		String randomCode = RandomString.make(64);
		user.setVerificationCode(randomCode);

		return repo.save(user);
	}

	@Override
	public void sendVerifycationEmail(Users user, String siteURL) throws Exception {
		String subject = "Please verify your Registration";
		String senderName = "Shop Team";
		String mailContent = "<p>Dear :" + user.getFullname() + ",</p>";
		mailContent += "<p>Plase click the link below verify to your registration.</p>";

		String verifiURL = siteURL + "/verify?code=" + user.getVerificationCode();
		mailContent += "<h1> <a href=\"" + verifiURL + "\">VERIFY</a></h1>";

		mailContent += "<p>Thank You <br> The Shop Team</p>";

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom("lamnhps16063@fpt.edu.vn", senderName);
		helper.setTo(user.getEmail());
		helper.setSubject(subject);
		helper.setText(mailContent, true);

		mailSender.send(message);
	}

	@Override
	@Transactional(rollbackOn = { Exception.class, Throwable.class })
	public Boolean verify(String verifyCationCode) {
		Users user = repo.findByVerificationCode(verifyCationCode);
		if (user == null || user.getEnabled()) {
			return false;
		} else {
			user.setVerificationCode(null);
			user.setEnabled(Boolean.TRUE);
			repo.save(user);
			return true;
		}
	}

	@Override
	public Users findByUsername(String username) {
		return repo.findByUsername(username);
	}

	@Transactional(rollbackOn = { Exception.class, Throwable.class })
	@Override
	public void updateUserDetails(Users user) {
		repo.updateUserDetails(user.getFullname(), user.getUsername());
	}

	@Override
	public Users findByIdUser(Long id) {
		Optional<Users> optional = repo.findById(id);
		return optional.isPresent() ? optional.get() : null;
	}

	@Override
	public Users updateUserAfterOauthLoginSuccess(String emailUser) {
		Users userResponse = repo.findByEmail(emailUser);
		return userResponse;
	}

	@Transactional(rollbackOn = { Exception.class, Throwable.class })
	@Override
	public Users proccessOauthPostLogin(String username, String fullname, String password, String email,
			String clientName) {
		Users user = new Users();
		user.setUsername(username);
		user.setFullname(fullname);
		
		String hashPassword = encoderConfig.passwordEncoder().encode(password);
		user.setHashPassword(hashPassword);
		
		user.setEmail(email);
		
		Roles role = roleService.findByDescription("user");
		user.setRoles(role);
		
		user.setEnabled(Boolean.TRUE);
		user.setIsDeleted(Boolean.FALSE);
		user.setAuthProvider(clientName); 
		
		return repo.saveAndFlush(user);
	}
}
