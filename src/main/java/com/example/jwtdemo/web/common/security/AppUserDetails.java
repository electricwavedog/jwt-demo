package com.example.jwtdemo.web.common.security;

import com.example.jwtdemo.domain.model.TUser;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

/**
 * @author liuyiqian
 */
public class AppUserDetails extends User {

    private TUser user;

    public AppUserDetails(TUser user) {
        super(user.getUsername(), user.getPassword(), AuthorityUtils.createAuthorityList("ROLE_" + user.getRole().name()));
        this.user = user;
    }

    public TUser getUser() {
        return user;
    }
}
