package com.SpringSecuritySignupFeature;

import com.SpringSecuritySignupFeature.Entity.User;
import com.SpringSecuritySignupFeature.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringSecuritySignupFeatureApplicationTests {

    @Autowired
	UserRepository userRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void createUser(){
		User user = new User();

		user.setId(2);
		user.setName("pankaj P Muthha");
		user.setEmail("pankaj@gmail.com");
		user.setUsername("pankaj@gmail.com");
		user.setPassword("pankaj");

		userRepository.save(user);
	}

}
