package com.motomarket.repository.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "users")
public class User {
//avatar	Blob
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "user_name")
    private String userName;

    private String email;

    private Role role;

    @Column(name = "user_status")
    private StatusUser userStatus;

    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;



// One to many - Many to one
<<<<<<< HEAD
=======

>>>>>>> hoang-dev

    @OneToMany(mappedBy = "user")
    private List<Post> postList;


}

