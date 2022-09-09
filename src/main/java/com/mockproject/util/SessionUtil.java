package com.mockproject.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.util.ObjectUtils;

import com.mockproject.Constant.SessionConstant;
import com.mockproject.Dto.CartDto;

public class SessionUtil {

	private SessionUtil() {

	}

	public static void validateCart(HttpSession session) {
		//trong session chưa có giỏ hàng thì new 1 cái giỏ hàng cho ngt
		if (ObjectUtils.isEmpty(session.getAttribute(SessionConstant.CURRENT_CART))) {
			session.setAttribute(SessionConstant.CURRENT_CART, new CartDto());
		}
	}

	public static CartDto getCurrentCart(HttpSession session) {
		return (CartDto) session.getAttribute(SessionConstant.CURRENT_CART);
	}

	public static String getSiteURL(HttpServletRequest request) {
		String siteURL = request.getRequestURL().toString();
		return siteURL.replace(request.getServletPath(), "");
	}
}
