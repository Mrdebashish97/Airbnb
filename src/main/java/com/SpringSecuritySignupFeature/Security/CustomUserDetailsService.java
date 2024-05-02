package com.SpringSecuritySignupFeature.Security;

import com.SpringSecuritySignupFeature.Entity.User;
import com.SpringSecuritySignupFeature.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

// What @Service or @Component does? - It helps us to perform dependency injection on those classes. Because Spring IOC will get the information about this class only when you hand over the class to springBoot.
// How do you hand over your java class to springBoot? - @Service or @Component you have to use this , which is called stereotype.
// What are stereotype annotations in SpringBoot?- @Service, @Controller, @Component etc.
@Component  // any ordinary class if you want this class object to be created, mark with @Service or @Component
public class CustomUserDetailsService implements UserDetailsService {

    // here we have to implement user details.
    // this UserDetails class has a method called as loadUserByUsername().
    @Autowired
    private UserRepository userRepository;


    // this method is responsible to get the user details based on the username.
    // Go to UserRepository and create one method - findByUsername(String username)
    // loadUserByUsername(String username) - this method verify the database whether user exists or not.
    // If user exists, username & password will give to SpringSecurity User object.
    // now this User object how will you give it to spring memory?- You will go to the config file, will do dependency injection (private CustomUserDetailsService customUserDetailsService;)
    // then will use protected void configure(AuthenticationManagerBuilder auth) // Object details can be got through this reference.

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        // here we put conditions
        if (user==null){  // when there is no record found in the database, then simply throw an exception.
            throw new UsernameNotFoundException("User not Found");
        }
        // when user is present in the database, then take that username & password and store that into a built-in Object called as User(org.springframework.security.core.userDetails) .
        // here we have to give fully qualified package name because there is a conflict, that there are 2 Users one is from entity & another is from spring security.
        // so, whatever UserDetails you are found, the rule here is, You need to put those UserDetails into the UserDetails of springSecurity.
        // After you put the details into the User object, you will give this object to springSecurity, telling that the username & password that come from database is in the User object. I will give this User object to spring IOC.
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), null);
    }
}
