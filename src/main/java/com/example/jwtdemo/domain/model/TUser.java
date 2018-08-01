package com.example.jwtdemo.domain.model;

import com.example.jwtdemo.domain.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author liuyiqian
 */
@Entity
@Table(name = "t_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TUser {

    @Id
    @GeneratedValue
    private Long userId;

    private String userName;

    private String password;

    private Role role;
}
