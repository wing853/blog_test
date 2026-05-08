package com.example.myblog.board;

import com.example.myblog.user.User;
import com.example.myblog.util.MyDateUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data
@Entity
@Builder
@Table(name = "board_tb")
@NoArgsConstructor
@AllArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @CreationTimestamp
    private Timestamp createdAt;

    public String getTime() {
        return MyDateUtil.timestampFormat(createdAt);
    }

    public void update(BoardRequest.UpdateDTO updateDTO) {
        this.title = updateDTO.getTitle();
        this.content = updateDTO.getContent();
    }
}
