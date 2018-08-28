package com.example.jwtdemo.web.common.security;

import com.example.jwtdemo.domain.model.TUser;
import com.example.jwtdemo.domain.model.enums.Role;
import com.example.jwtdemo.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author liuyiqian
 */
@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        TUser tUser = userRepository.findByUsername(userName);
        if(tUser == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new AppUserDetails(tUser);
    }
}
