package com.example.myblog.user;

import com.example.myblog.core.errors.Exception400;
import com.example.myblog.core.errors.Exception404;
import com.example.myblog.core.errors.Exception500;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository ur;

    public UserResponse.SessionDTO login(UserRequest.LoginDTO loginDTO) {

        User userEntity = ur.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword())
                .orElseThrow(
                        () -> new Exception404("아이디 혹은 비밀번호를 잘못 입력했습니다.")
                );
        return new UserResponse.SessionDTO(userEntity);
    }

    @Transactional
    public UserResponse.JoinDTO join(UserRequest.JoinDTO joinDTO) {
        ur.findByUserEmail(joinDTO.getEmail()).ifPresent(user -> {
            throw new Exception400("이미 존재하는 이메일입니다");
        });

        User user = joinDTO.toEntity();
        User savedUserEntity = ur.save(user);
        return new UserResponse.JoinDTO(savedUserEntity);
    }

    public UserResponse.SessionDTO checkUser(Integer id) {

        User userEntity = ur.findById(id).orElseThrow(
                () -> new Exception404("사용자를 찾을 수 없습니다")
        );

        return new UserResponse.SessionDTO(userEntity);
    }

    @Transactional
    public UserResponse.SessionDTO update(Integer id,UserRequest.UpdateDTO updateDTO, HttpSession session) {
        User userEntity = ur.findById(id).orElseThrow(
                () -> new Exception404("사용자를 찾을 수 없습니다")
        );

        userEntity.update(updateDTO);

        UserResponse.SessionDTO sessionDTO = new UserResponse.SessionDTO(userEntity);
        session.setAttribute("sessionUser",sessionDTO);

        return sessionDTO;
    }

}
