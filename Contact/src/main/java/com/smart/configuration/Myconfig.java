package com.smart.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.ldap.EmbeddedLdapServerContextSourceFactoryBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class Myconfig  {
	
	
	@Bean
	public UserDetailsService getUserDetailsService()
	{
		return new UserDetailServiceImpl();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
    
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider()
	{
		DaoAuthenticationProvider daoA=new DaoAuthenticationProvider();
		daoA.setUserDetailsService(this.getUserDetailsService());
		daoA.setPasswordEncoder(passwordEncoder());
		return daoA;
	}
	
	//configure method
	
	    @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	        http
	            .csrf(AbstractHttpConfigurer::disable)
	            .authorizeHttpRequests((authz) -> {
	            	authz.requestMatchers("/admin/**").hasRole("ADMIN");
	            	authz.requestMatchers("/user/**").hasRole("USER");
	            	authz.requestMatchers("/**").permitAll();
	                authz.anyRequest().authenticated();
	                
	            })
	            .formLogin(httpSecurityFormLoginConfigurer->{
	            	httpSecurityFormLoginConfigurer.loginPage("/logi").permitAll();
	            	httpSecurityFormLoginConfigurer.loginProcessingUrl("/dologin");
	            	httpSecurityFormLoginConfigurer.defaultSuccessUrl("/user/index");
	            });
	            
	        return http.build();
	    }

	
    
		/*
		 * @Bean public AuthenticationManager
		 * authenticationManagerBean(AuthenticationConfiguration coufiguration)throws
		 * Exception { return coufiguration.getAuthenticationManager(); }
		 */
}
