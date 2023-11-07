package com.example.foodsuggestion.Controller;

import com.example.foodsuggestion.Data.Entity.UserEntity;
import com.example.foodsuggestion.Data.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // 사용자 등록
    @PostMapping("/register")
    public UserEntity registerUser(@RequestBody UserEntity userEntity) {
        // 비밀번호를 해싱하지 않고 저장
        // 다른 사용자 정보 필드 설정
        UserEntity savedUser = userRepository.save(userEntity);
        return savedUser;
    }


    // 사용자 이름과 비밀번호를 사용한 로그인
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserEntity userEntity) {
        // 사용자 이름을 기반으로 사용자를 찾습니다.
        UserEntity existingUser = userRepository.findByName_user(userEntity.getName_user());

        if (existingUser != null && existingUser.getPasswd_user().equals(userEntity.getPasswd_user())) {
            // 사용자를 인증하고 성공 응답을 반환합니다.
            return new ResponseEntity<>("Login successful", HttpStatus.OK);
        } else {
            // 인증 실패 시 실패 응답을 반환합니다.
            return new ResponseEntity<>("Login failed", HttpStatus.UNAUTHORIZED);
        }
    }

}

