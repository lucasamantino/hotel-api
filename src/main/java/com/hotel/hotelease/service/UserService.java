package com.hotel.hotelease.service;

import com.hotel.hotelease.dto.UserRequest;
import com.hotel.hotelease.entity.Role;
import com.hotel.hotelease.entity.User;
import com.hotel.hotelease.repository.RoleRepository;
import com.hotel.hotelease.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public void updateUser(UserRequest userRequest) {
        String email = userRequest.email();
        String[] request = userRequest.roles();
        Set<Role> roles = new HashSet<>();
        for (String newRole : request) {
            Role role = roleRepository.findByName(newRole);
            roles.add(role);
        }
        if (userRepository.existsByEmail(email)) {
            User user = userRepository.findByEmail(email);
            user.setRoles(roles);
            userRepository.save(user);
        }
    }
}
