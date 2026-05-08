package com.example.myblog.user;

import lombok.Data;

public class UserRequest {

    @Data
    public static class LoginDTO {
        private String email;
        private String password;

        public void validate() {
            if(email == null || email.trim().isEmpty()) {
                throw new IllegalArgumentException("이메일을 입력하세요");
            }

            if(password == null || password.trim().isEmpty()) {
                throw new IllegalArgumentException("비밀번호를 입력하세요");
            }
        }
    }

    // 회원가입 DTO
    @Data
    public static class JoinDTO {
        private String nickname;
        private String password;
        private String email;

        // 편의 기능 추가 - 내가 가지고 있는 멤버 변수에 값으로 User 엔티를 생성
        public User toEntity() {
            return User.builder()
                    .nickname(nickname)
                    .password(password)
                    .email(email)
                    .build();
        }

        // 유효성 검사 메서드 만들기
        public void validate() {
            if (nickname == null || nickname.trim().isEmpty()) {
                throw new IllegalArgumentException("사용자명은 필수 입니다");
            }

            if(password == null || password.trim().isEmpty()) {
                throw new IllegalArgumentException("비밀번호는 필수 입니다");
            }

            if(email == null || email.trim().isEmpty()) {
                throw new IllegalArgumentException("이메을은 필수 입니다");
            }
            // 입력값 : abc@naver.com --> contains() -->   true   --> ! --> false
            if(email.contains("@") == false) {
                throw new IllegalArgumentException("올바른 이메일 형식이 아닙니다");
            }

        }

    }

    @Data
    public static class UpdateDTO {

        private String nickname;
        private String password;

        public void validate() {

            if(nickname == null || nickname.isBlank()) {
                throw new IllegalArgumentException("사용자명은 필수 입니다");
            }

            if(password == null || password.isBlank()) {
                throw new IllegalArgumentException("비밀번호는 필수 입니다");
            }
            if (password.length() < 4) {
                throw new IllegalArgumentException("비밀번호는 4자 이상이어야 합니다");
            }
        }
    }
}
