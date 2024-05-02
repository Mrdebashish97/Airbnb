package com.SpringSecuritySignupFeature;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SpringSecuritySignupFeatureApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecuritySignupFeatureApplication.class, args);
	}

	// 1. What is @Bean ?
	// sir, when I was encoding the password,the passwordEncoder bean is not working.
	// so, I went to the config file,I created one method with @Bean ,
	// so that spring IOC would know, which object to create for this particular reference variable.
	// so @Bean is helps us to configure in IOC which bean to create for a particular reference variable, when IOC doesn't have that information.
	// 2nd approach- Instead of defining in main class, go to your config file,
	// & there also if write @Bean and define it, still it will work.

//	@Bean
//	public PasswordEncoder getPasswordEncoder() {
//		return new BCryptPasswordEncoder();
//	}

	// 1. Which is the starting point the project execution begins with?
	// @SpringBootApplication.
	// 2. What are the other things we can do in that?
	// we can define some configurations like @Bean, & that's where Boot Strapping project begins.
	// BootStrapping means Starting points.
}




