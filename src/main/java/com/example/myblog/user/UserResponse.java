package com.example.myblog.user;

import lombok.Data;

public class UserResponse {

    @Data
    public static class JoinDTO {
        private Integer id;
        private String nickname;
        private String email;

        public JoinDTO(User user) {
            this.id = user.getId();
            this.nickname = user.getNickname();
            this.email = user.getEmail();
        }
    }

    @Data
    public static class SessionDTO {
        private Integer id;
        private String nickname;
        private String email;

        // 생성자에서 부모가 아닌 '본인 필드'에 값을 넣어야 합니다.
        public SessionDTO(User user) {
            this.id = user.getId();
            this.nickname = user.getNickname();
            this.email = user.getEmail();
        }
    }
}
