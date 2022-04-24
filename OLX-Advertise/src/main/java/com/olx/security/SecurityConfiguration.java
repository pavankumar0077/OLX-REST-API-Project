package com.olx.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//@Autowired
//PasswordEncoder passwordEncoder;

	@Autowired
	UserDetailsService userDetailsService;

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {// used for authentication
		/*
		 * auth.inMemoryAuthentication().withUser("pavan").password(this.
		 * passwordEncoder.encode("pavan007")).roles("USER") .and()
		 * .withUser("pavan").password(this.passwordEncoder.encode("pavan007")).
		 * roles("ADMIN");
		 */

		auth.userDetailsService(userDetailsService);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {// used for Authorization
		http.csrf().disable().authorizeRequests().antMatchers("/user").hasAnyRole("USER", "ADMIN").antMatchers("/admin")
				.hasRole("ADMIN").antMatchers("/olx-login/user/authenticate").permitAll().and().formLogin();
	}

	@Bean
	public AuthenticationManager getAuthenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return super.userDetailsService();
	}
}