package com.zensar.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

// @Autowired
// PasswordEncoder passwordEncoder;

// @Autowired
// UserDetailsServiceImpl userDetailsService;

// @Override
// protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
// auth.userDetailsService(userDetailsService);
//
// }

 @Bean
 public PasswordEncoder getPasswordEncoder() {
 return new BCryptPasswordEncoder();
 }

 @Bean
 public AuthenticationManager getAuthenticationManager() throws Exception {
 return super.authenticationManager();
 }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("/user").hasAnyRole("USER", "ADMIN").antMatchers("/admin")
				.hasRole("ADMIN").antMatchers("/olx/advertise").permitAll().and().formLogin();

	}
}