package com.example.foodsuggestion.Service;

import com.example.foodsuggestion.Data.Entity.UserEntity;
import com.example.foodsuggestion.Data.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 여기에 사용자 관련 로직 추가
}

