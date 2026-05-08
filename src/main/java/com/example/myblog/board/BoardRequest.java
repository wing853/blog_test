package com.example.myblog.board;

import com.example.myblog.user.User;
import lombok.Builder;
import lombok.Data;

public class BoardRequest {

    @Data
    @Builder
    public static class SaveDTO {
        private String title;
        private String content;

        public Board toEntity(User user) {
            return Board.builder()
                    .title(title)
                    .content(content)
                    .user(user)
                    .build();
        }

        public void validate() {
            if(title == null || title.trim().isEmpty()) {
                throw new IllegalArgumentException("제목은 필수입니다");
            }
            if(content == null || content.length() < 3) {
                throw new IllegalArgumentException("내용은 3글자 이상 작성해야 합니다.");
            }
        }
    }

    @Data
    public static class UpdateDTO {
        private String title;
        private String content;

        // 게시글 수정시 유효성 검사 편의 메서드
        public void validate() {
            if(title == null || title.trim().isEmpty()) {
                throw new IllegalArgumentException("제목은 필수입니다");
            }
            if(content== null || content.length() < 3) {
                throw new IllegalArgumentException("내용은 3글자 이상 작성해야 합니다.");
            }
        }
    }
}
