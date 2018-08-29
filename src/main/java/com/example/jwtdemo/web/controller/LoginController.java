package com.example.jwtdemo.web.controller;

import com.example.jwtdemo.config.constant.MessageConstant;
import com.example.jwtdemo.domain.model.APIResult;
import com.example.jwtdemo.domain.model.TUser;
import com.example.jwtdemo.domain.model.enums.Role;
import com.example.jwtdemo.domain.repository.UserRepository;
import com.example.jwtdemo.web.common.error.exception.BusinessApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuyiqian
 */
@RestController
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public APIResult signUp(@RequestBody TUser tUser) {
        TUser tUser2 = userRepository.findByUsername(tUser.getUsername());
        if (tUser2 != null) {
            throw new BusinessApiException(MessageConstant.USER_EXIST);
        }
        tUser.setPassword(new BCryptPasswordEncoder().encode(tUser.getPassword()));
        tUser.setRole(Role.USER);
        userRepository.save(tUser);
        return APIResult.success();
    }
}
