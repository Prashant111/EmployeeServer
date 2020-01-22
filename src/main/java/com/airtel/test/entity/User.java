package com.airtel.test.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "user_name")
    private String userName;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "encrypted_password", nullable = false)
    private String encryptedPassword;
}
