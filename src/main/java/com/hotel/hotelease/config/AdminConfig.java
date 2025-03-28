package com.hotel.hotelease.config;

import com.hotel.hotelease.entity.Role;
import com.hotel.hotelease.entity.User;
import com.hotel.hotelease.repository.RoleRepository;
import com.hotel.hotelease.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.Set;

@Configuration
public class AdminConfig implements CommandLineRunner {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public AdminConfig(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private void initRoles () {
        if (roleRepository.findByName("ADMIN") == null) {
            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            roleRepository.save(adminRole);
        }

        if (roleRepository.findByName("BASIC") == null) {
            Role basicRole = new Role();
            basicRole.setName("BASIC");
            roleRepository.save(basicRole);
        }

        if (roleRepository.findByName("RECEPTIONIST") == null) {
            Role receptionistRole = new Role();
            receptionistRole.setName("RECEPTIONIST");
            roleRepository.save(receptionistRole);
        }
    }

    @Override
    public void run(String... args) throws Exception {
        initRoles();
        Role roleADM = roleRepository.findByName("ADMIN");
        Optional<User> userADM = userRepository.findByName("admin");

        if (userADM.isEmpty()) {
            User user = new User();
            user.setName("admin");
            user.setPassword(passwordEncoder.encode("123"));
            user.setRoles(Set.of(roleADM));
            user.setEmail("ADM@root.com");
            userRepository.save(user);
        }
    }
}