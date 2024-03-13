package com.smart_home.Authentication.Controller;

import com.smart_home.Authentication.DTO.AuthenticationTokenDTO;
import com.smart_home.Authentication.Request.UserRegistrationRequest;
import com.smart_home.Authentication.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/account/")
@CrossOrigin
@SuppressWarnings("unused")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("registration/")
    public ResponseEntity<Void> registrationUser (@Valid @RequestBody UserRegistrationRequest form){
        service.userRegistration(form);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }
//    @PostMapping("login/password/")
//    public ResponseEntity<AuthenticationTokenDTO> loginUserViaPassword(@RequestBody UserLoginRequest form){
//        return ResponseEntity.status(HttpStatus.OK).body(service.userLoginViaPassword(form));
//    }
//    @PostMapping("login/pin/")
//    public ResponseEntity<AuthenticationTokenDTO> loginUserViaPin(@RequestBody UserLoginRequest form){
//        return ResponseEntity.status(HttpStatus.OK).body(service.userLoginViaPin(form));
//    }
//
//    @DeleteMapping("logout/")
//    public ResponseEntity<Void> logoutUser (HttpServletRequest request) {
//        service.userLogout(request);
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
//    }
}
