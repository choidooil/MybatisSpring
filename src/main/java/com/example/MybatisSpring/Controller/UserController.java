package com.example.MybatisSpring.Controller;

import com.example.MybatisSpring.Domain.User;
import com.example.MybatisSpring.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // RESTful 웹 서비스임을 나타냅니다.
@RequestMapping("/api/user") // 이 컨트롤러의 모든 API는 /api/user 경로 아래에 있습니다.
public class UserController {

    private final UserService userService; // UserService 의존성 주입

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 1. 회원 조회 (GET: /api/users/{userid})
    @GetMapping("/{userid}")
    public ResponseEntity<User> getUserById(@PathVariable("userid") long userid) {
        User user = userService.getUserById(userid);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK); // 200 OK
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }
    }

    // 2. 모든 회원 조회 (GET: /api/users) - 추가 기능
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK); // 200 OK
    }

    // 3. 회원 가입 (POST: /api/users)
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        userService.createUser(user);
        // 생성된 리소스의 URI를 Location 헤더에 포함하여 201 Created 반환
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    // 4. 회원 정보 수정 (PUT/PATCH: /api/users/{userid})
    @PutMapping("/{userid}")
    public ResponseEntity<User> patchUser(@PathVariable("userid") long userid, @RequestBody User user) {
        User existingUser = userService.getUserById(userid);
        if (existingUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }

        // PathVariable의 userid를 사용하도록 설정 (혹시라도 RequestBody의 userid와 다를 경우)
        user.setUserid(userid);

        boolean updated = userService.patchUser(user);
        if (updated) {
            // 업데이트 성공시 업데이트된 유저 정보를 다시 조회하여 반환
            return new ResponseEntity<>(userService.getUserById(userid), HttpStatus.OK); // 200 OK
        }
        else{ //유저는 있지만 업데이트 실패. 잘못된 요청으로 판단.
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // 5. 회원 탈퇴 (DELETE: /api/users/{userid})
    @DeleteMapping("/{userid}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userid") long userid) {
        userService.deleteUser(userid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 성공적으로 삭제, 리턴값 없음)

    }
}