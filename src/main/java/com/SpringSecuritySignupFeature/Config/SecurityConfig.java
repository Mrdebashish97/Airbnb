package com.SpringSecuritySignupFeature.Config;

import com.SpringSecuritySignupFeature.Security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// This is the class where we will do configuration, that which URL who can access.
// 1st we need to extend WebSecurityConfigurerAdapter.
// @Configuration- with this annotation is when i start spring boot project, spring boot
// project can read this file & will understand the configuration.

    // Two important concept of SpringSecurity-> Authentication & Authorization.
    //Authentication is whether username and password is correct or not, we will check that.
    // Authorization is which user can access which feature of your application.
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // so inorder to configure, 1st we have to implement one method, called as configure(http:)
    // Right click->Generate->Override methods->configure(http:HttpSecurity):void .

    // Here we configure which url who can access or url does not require any authentication.

        @Autowired
        private CustomUserDetailsService customUserDetailsService;

        @Bean
        public PasswordEncoder getPasswordEncoder(){
            return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationManager getAuthenticationManager() throws Exception {
           return super.authenticationManagerBean();
        }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Here when I start the project, one HttpSecurity object is created & address will be injected into http reference variable.
    // csrf - (Cross-Site Request Forgery) it is one of the hacking technique.
    // antMatcher()- this is the place where you give the url- which url who can access.
        // configure(HttpSecurity http) - HttpSecurity help me to configure the url who can access which url & it helps me to configure that.
        //Q- How do I compare expected username & password with actual username & password present in the database?
        //Q- How do I design the roles? this is not a complete code.

        http.csrf().disable().authorizeRequests().antMatchers(HttpMethod.POST,"/api/auth/**").permitAll().anyRequest().authenticated().and().httpBasic();

    }

    // here we override another method - configure(auth:AuthenticationManagerBuilder):void
        // here we configure few details about the username & password that has comes from database.

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
             auth.userDetailsService(customUserDetailsService).passwordEncoder(getPasswordEncoder());
        }

// Q- HOW EXPECTED VALUE CAN BE COMPARE WITH ACTUAL VALUE?
        // A- By using UsernameAuthenticationToken class object constructor.
        // That constructor, when you give username & password, taking that we create authentication reference & set that in security context with the result true or false.
        // if true, it will continue user sign-in. If false, this will not work.

    }
