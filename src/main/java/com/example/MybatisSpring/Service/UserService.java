package com.example.MybatisSpring.Service;


import com.example.MybatisSpring.Domain.User;
import com.example.MybatisSpring.Repository.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Spring이 이 클래스를 서비스 빈으로 관리하도록 합니다.
public class UserService {

    private final UserMapper userMapper; // UserMapper 의존성 주입

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    // 모든 회원 조회
    public List<User> getAllUsers() {
        return userMapper.findAllUsers();
    }

    // 특정 회원 조회
    public User getUserById(long userid) {
        return userMapper.findUser(userid);
    }

    // 회원 가입
    public User insertUser(User user) {
        // 비즈니스 로직 추가 가능: 예) 이메일 중복 확인, 데이터 유효성 검사
        userMapper.insertUser(user);
        return user; // 삽입 후 userid가 채워진 User 객체 반환
    }

    // 회원 정보 수정
    public boolean updateUser(User user) {
        // userid가 유효한지 확인하는 로직 추가 가능
        int updatedRows = userMapper.updateUser(user);
        return updatedRows > 0; // 업데이트 성공 여부 반환
    }

    // 회원 탈퇴 (삭제)
    public void deleteUser(long userid) {
        // 삭제 권한 확인 등 비즈니스 로직 추가 가능
        userMapper.deleteUser(userid);
    }
}
