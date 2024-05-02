package com.SpringSecuritySignupFeature.Controller;

import com.SpringSecuritySignupFeature.Entity.User;
import com.SpringSecuritySignupFeature.Payload.LoginDto;
import com.SpringSecuritySignupFeature.Payload.Signup;
import com.SpringSecuritySignupFeature.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager; // this will set the value whether pass or fail.
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;// It is an External library

    // So, when an external library is added,it will not have the details which object to create.
    // it will give me the error Consider defining a bean of type '' in your configuration.
    // Spring IOC is not able to create the object of external library.
    // in such cases, go to main class, and define there @Bean .

    // * sign-up feature
    @PostMapping("/sign-up")
    public ResponseEntity<String> createUser(@RequestBody Signup signup){
        if (userRepository.existsByEmail(signup.getEmail())){
            return new ResponseEntity<>("Email is already registered. Please try another one...", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (userRepository.existsByUsername(signup.getUsername())){
            return new ResponseEntity<>("Username is already in use. Please put another one...", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        User user = new User();

        user.setId(signup.getId());
        user.setName(signup.getName());
        user.setEmail(signup.getEmail());
        user.setUsername(signup.getUsername());
        //user.setPassword(signup.getPassword());
        // Here Encode the password and save it.
        user.setPassword(passwordEncoder.encode(signup.getPassword()));
        userRepository.save(user);
        return new ResponseEntity<>("User created Successfully...!!!", HttpStatus.CREATED);
    }

    // IQ->1. How do you save the password in database in encrypting it?
    // using BCryptPasswordEncoder() class.
    // IQ->2. What is @Bean? (because he knows passwordEncoder can not be used without @ Bean)
    //
    // Where did you do @Bean in your project?
    // in SecurityConfig file or in @SpringBootApplication main class.

    // * sign-in feature
    @PostMapping("/sign-in")
    public ResponseEntity<String> signIn(@RequestBody LoginDto loginDto) {
        // UsernamePasswordAuthenticationToken() - This is a built-in class in springSecurity
        // constructor of this class have 2 main work here.
        // 1- Supply loginDto.getUsername()- username to loadByUser method in CustomUserDetails class.
        // 2- It will compare expected credentials - loginDto.getUsername(), loginDto.getPassword() with Actual credentials given by loadByUser method.
        // once it is done , further you have take Authentication reference.
        // because Authentication reference here acts like similar to session variable.
        // so, if it is valid, then set the variable in session variable, If not valid, set invalid in the session variable.
        
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        return new ResponseEntity<>("Sign-in done successfully !!!", HttpStatus.OK);

        // here we do 2 things. 1st- Give the username to loadByUsername().
        // then loadByUsername will load the data from userRepository, keep that in springBoot.
        // then UserPasswordAuthentication() constructor will compare these value with that.
        // after comparison whatever result it is referencing , that result you need you set into authenticationManager object.
        // whether pass or fail it will set that details .
        // Once the details been set now,we will have to create a session variable.
        // remember whenever a login is successful, we create a session variable. login unsuccessful, this will throw an exception & stop.

    }

}
