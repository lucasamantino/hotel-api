package com.hotel.hotelease.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "tb_roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @ManyToMany(mappedBy = "roles")
    List<User> users;

    @Setter
    @Getter
    private String name;

    public enum Values {
        ADMIN(3),
        BASIC(1),
        RECEPTIONIST(2);

        private int roleId;

        Values (int roleId) {
            this.roleId = roleId;
        }
    }

}
