package com.shop.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.shop.service.CustomUserDetailService;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	CustomUserDetailService customUserDetailService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
			.authorizeRequests()
			.antMatchers("/","/shop/**", "/forgotpassword", "/register", "/").permitAll()
			.antMatchers("admin/**").hasRole("ADMIN")
			.anyRequest()
			.authenticated()
			.and()
			.formLogin()
			.loginPage("/login")
			.permitAll()
			.failureUrl("/login?error= true")
			.defaultSuccessUrl("/")
			.usernameParameter("email")
			.passwordParameter("password")
			.and()
			.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/login")
			.invalidateHttpSession(true)
			.deleteCookies("JSESSIONID")
			.and()
			.exceptionHandling()
			.and()
			.csrf()
			.disable();
	}

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
		
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(customUserDetailService);
	}
		
	@Override
	public void configure(WebSecurity web) throws Exception{
		web.ignoring().antMatchers("/resources/**", "/static/**", "/images/**","/productimages/**","/css/**", "/js/**");
	}
	
}
