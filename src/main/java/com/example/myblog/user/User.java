package com.example.myblog.user;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "user_tb")
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String email;
    private String nickname;
    private String password;
    @CreationTimestamp
    private Timestamp createdAt;

    @Builder
    public User(Integer id, String email,String nickname, String password,  Timestamp createdAt) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.createdAt = createdAt;
    }

    public void update(UserRequest.UpdateDTO updateDTO) {
        this.nickname = updateDTO.getNickname();
        this.password = updateDTO.getPassword();
    }
}
