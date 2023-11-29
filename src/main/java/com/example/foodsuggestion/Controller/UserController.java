package com.example.foodsuggestion.Controller;

import com.example.foodsuggestion.Data.Entity.AllergyInfoEntity;
import com.example.foodsuggestion.Data.Repository.AllergyInfoRepository;
import com.example.foodsuggestion.Data.Entity.UserEntity;
import com.example.foodsuggestion.Service.UserService;
import com.example.foodsuggestion.Data.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AllergyInfoRepository allergyInfoRepository;

    // 회원가입
    // http://localhost:8080/register POST 방식으로
    // "name_user" : varchar20,
    // "passwd_user" : varchar20,
    // "allergy_user" : 0 or 1
    // "allergies" : ["메밀", "밀"]
    // "allergy_user 가 1 이면 무조건 알러지 값 입력
    // "allergies" 값을 제대로 전달하는 프론트 만들기 * 값 제대로 전달 안하면 allergyinfo 에 값 전달 실패 allergy_user 가 1이라도 회원가입이 됨
    // "allergies" 값은 메밀, 밀, 대두, 땅콩, 호두, 잣, 아황산류, 복숭아, 토마토, 난류, 우유, 새우, 고등어, 오징어, 게, 조개류, 돼지고기, 쇠고기, 닭고기
    // 코드 내에 성공, 실패 구문 있음
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserEntity userEntity) {
        // 아이디 중복 확인
        UserEntity existingUser = userRepository.findByName_user(userEntity.getName_user());
        if (existingUser != null) {
            // 중복된 아이디가 이미 존재하면 실패 응답을 반환
            return new ResponseEntity<>("회원가입 실패 : 중복된 아이디", HttpStatus.BAD_REQUEST);
        }

        // 사용자 정보 저장
        UserEntity savedUser = userRepository.save(userEntity);

        // 알러지 정보 추가
        if (savedUser.isAllergy_user()) {
            List<String> allergies = userEntity.getAllergies();

            if (allergies == null || allergies.isEmpty()) {
                // 알러지 정보가 비어 있는 경우 처리
                userRepository.delete(savedUser); // 사용자 삭제
                return new ResponseEntity<>("회원가입 실패 : 알러지 정보 누락", HttpStatus.BAD_REQUEST);
            }

            for (String allergy : allergies) {
                AllergyInfoEntity allergyInfo = new AllergyInfoEntity();
                allergyInfo.setUser(savedUser);
                allergyInfo.setAllergyName(allergy);
                allergyInfoRepository.save(allergyInfo);
            }
        }

        return new ResponseEntity<>("회원가입 성공", HttpStatus.OK);
    }


    // 사용자 이름과 비밀번호를 사용한 로그인
    // http://localhost:8080/login POST 방식
    // name_user : name_user,
    // passwd_user : passwd_user
    // 성공 시 로그인 성공 반환
    // 실패 시 로그인 실패 반환
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserEntity userEntity) {
        // user_name 으로 이름 찾기
        UserEntity existingUser = userRepository.findByName_user(userEntity.getName_user());

        if (existingUser != null && existingUser.getPasswd_user().equals(userEntity.getPasswd_user())) {
            // 사용자를 인증하고 성공 응답을 반환
            return new ResponseEntity<>("로그인 성공", HttpStatus.OK);
        } else {
            // 인증 실패 시 실패 응답을 반환
            return new ResponseEntity<>("로그인 실패", HttpStatus.UNAUTHORIZED);
        }
    }


    // http://localhost:8080/getUsername?userId=<유저 아이디>
    // GET 방식으로 받음
    // 성공 시 user_name 반환 = 아이디 반환
    // 실패 시 User not found 반환
    @GetMapping("/getUsername")
    public ResponseEntity<String> getUsername(@RequestParam(name = "userId") Long userId) {
        UserEntity user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            String username = user.getName_user();
            return new ResponseEntity<>(username, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("유저가 검색되지 않습니다.", HttpStatus.NOT_FOUND);
        }
    }


    // http://localhost:8080/resetPassword/<유저 아이디>?newPassword=<변경 할 비밀번호>
    // 성공 시 Password reset successful 반환
    // 실패 시 User not found 반환
    @PostMapping("/resetPassword/{userId}")
    public ResponseEntity<String> resetPassword(
            @PathVariable Long userId,
            @RequestParam String newPassword
    ) {
        Optional<UserEntity> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();

            // 비밀번호 해싱하는 로직 작성

            user.setPasswd_user(newPassword);
            userRepository.save(user);

            return ResponseEntity.ok("비밀번호가 변경되었습니다.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // 사용자의 알러지 정보를 반환하는 엔드포인트
    // http://localhost:8080/allergyInfo/<유저 아이디>
    // 리스트로 나오는듯?
    @GetMapping("/allergyInfo/{userId}")
    public ResponseEntity<List<String>> getUserAllergyInfo(@PathVariable Long userId) {
        UserEntity user = userRepository.findById(userId).orElse(null);
        if (user != null && user.isAllergy_user()) {
            List<String> allergies = user.getAllergies();
            return new ResponseEntity<>(allergies, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("deleteUser/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {

        boolean success = userService.deleteUser(userId);

        if (success) {
            return ResponseEntity.ok("회원탈퇴.");
        } else {
            return ResponseEntity.badRequest().body("회원탈퇴 실패");
        }
    }
}

