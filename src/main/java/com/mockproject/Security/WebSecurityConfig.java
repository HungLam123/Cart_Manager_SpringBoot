package com.mockproject.Security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.mockproject.Oauth2.CustomerOauth2UserService;
import com.mockproject.Oauth2.Oauth2LoginSuccessHandler;
import com.mockproject.jwt.JwtAuthenticationFilter;
import com.mockproject.service.impl.CustomerUserServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private EncoderConfig encoderConfig;

	@Autowired
	private CustomerUserServiceImpl customerUserServiceImpl;

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private CustomerOauth2UserService ouath2UserService;
	
	@Autowired
	private Oauth2LoginSuccessHandler oauth2LoginSuccessHandler;

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		// provider userservice for spring sercurity
		// provider password encoder
		auth.userDetailsService(customerUserServiceImpl).passwordEncoder(encoderConfig.passwordEncoder());
	}

	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
		db.setDataSource(dataSource);
		return db;
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}

	@Bean
	public AuthenticationManager customAuthenticationManager() throws Exception {
		return authenticationManager();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		
		http.authorizeRequests()
				.antMatchers("/admin/**")
				.hasAuthority("admin")
				.and()
				.oauth2Login()
					.loginPage("/login")
					.userInfoEndpoint()
						.userService(ouath2UserService)
					.and()
					.successHandler(oauth2LoginSuccessHandler)
					.and();
	
		http.logout(logout -> logout                                                
				.logoutUrl("/logout")                                            
				.logoutSuccessUrl("/index")
				.deleteCookies("JSESSIONID")
				.invalidateHttpSession(true));
	
		http.cors()
				.and().authorizeRequests()
				.antMatchers("/api/login", "/", "/index", "/login", "/logout", "/index/**", "/dienthoai", "/laptop", "/cart/**", "/register"
								, "/forgot_password", "/reset_password", "/user/**", "/verify/**", "/oauth/**").permitAll() // Cho phep tat ca truy cap link nay
				.anyRequest().authenticated(); // Cac link con lai thi phai xac thuc
		
		http.oauth2Login()
				.loginPage("/login")
				.userInfoEndpoint()
				.userService(ouath2UserService)
				.and();
			
		// logout
		http.logout(logout -> logout
				.logoutUrl("/logout")
				.logoutSuccessUrl("/index")
				.deleteCookies("JSESSIONID")
				.invalidateHttpSession(true));

		// filter check jwt
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/user/*", "/user/css/*", "/user/fonts/*", "/user/img/*", "/user/js/*", "/admin/*",
				"/admin/dist/*", "/admin/dist/css/*", "/admin/dist/css/alt/*", "/admin/dist/img/*",
				"/admin/dist/img/credit/*", "/admin/dist/js/*", "/admin/dist/js/pages/*", "/admin/img/*",
				"/admin/plugins/**");
	}
}
