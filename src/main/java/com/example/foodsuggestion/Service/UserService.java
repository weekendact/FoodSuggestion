package com.example.foodsuggestion.Service;

import com.example.foodsuggestion.Data.Entity.UserEntity;
import com.example.foodsuggestion.Data.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public boolean deleteUser(Long userId) {
        // 사용자 식별자로 사용자를 찾습니다.
        UserEntity user = userRepository.findById(userId).orElse(null);

        // 사용자가 존재하면 삭제
        if (user != null) {
            userRepository.delete(user);
            return true; // 삭제 성공
        } else {
            return false; // 사용자가 존재하지 않아 삭제 실패
        }
    }
}


