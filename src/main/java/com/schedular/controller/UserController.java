package com.schedular.controller;

import com.schedular.entity.UserRegistration;
import com.schedular.payload.JWTResponse;
import com.schedular.payload.LoginDto;
import com.schedular.payload.UserDto;
import com.schedular.service.UserRegistrationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin("http://localhost:4200")
public class UserController {

    private UserRegistrationService userRegistrationService;

    public UserController(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public UserRegistration createUser(@Valid @RequestBody UserDto userDto, BindingResult result) {
        if(result.hasErrors()){
            throw new IllegalArgumentException(result.getAllErrors().get(0).getDefaultMessage());
        }
        return userRegistrationService.saveRegistration(userDto);
    }

    // http://localhost:8080/api/v1/users/login
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDto loginDto) throws Exception {
        String jwtToken = userRegistrationService.verifyLogin(loginDto);

//        JWTResponse jwtResponse=new JWTResponse();
//        jwtResponse.setToken(jwtToken);
        return new ResponseEntity<>(new JWTResponse(jwtToken), HttpStatus.OK);
    }
}
