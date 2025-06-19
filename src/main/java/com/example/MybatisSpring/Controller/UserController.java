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
    public ResponseEntity<User> insertUser(@RequestBody User user) {
        userService.insertUser(user);
        // 생성된 리소스의 URI를 Location 헤더에 포함하여 201 Created 반환
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    // 4. 회원 정보 수정 (PUT/PATCH: /api/users/{userid})
    // PUT: 리소스 전체 교체 (클라이언트가 모든 필드를 보내야 함)
    // PATCH: 리소스 부분 업데이트 (클라이언트가 변경할 필드만 보내도 됨)
    // 여기서는 PUT을 사용하며, User 객체에 있는 모든 필드를 업데이트합니다.
    @PutMapping("/{userid}")
    public ResponseEntity<User> updateUser(@PathVariable("userid") long userid, @RequestBody User user) {
        User existingUser = userService.getUserById(userid);
        if (existingUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }

        // PathVariable의 userid를 사용하도록 설정 (혹시라도 RequestBody의 userid와 다를 경우)
        user.setUserid(userid);

        boolean updated = userService.updateUser(user);
        if (updated) {
            // 업데이트된 유저 정보를 다시 조회하여 반환
            return new ResponseEntity<>(userService.getUserById(userid), HttpStatus.OK); // 200 OK
        } else {
            // 업데이트는 가능했지만 DB에서 변경된 행이 없는 경우 (데이터가 동일한 경우)
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED); // 304 Not Modified
        }
    }

    // 5. 회원 탈퇴 (DELETE: /api/users/{userid})
    @DeleteMapping("/{userid}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userid") long userid) {
        /*boolean deleted = userService.deleteUser(userid);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content (성공적으로 삭제되었지만 반환할 내용 없음)
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found (삭제할 회원이 없음)
        }*/
        userService.deleteUser(userid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content (성공적으로 삭제되었지만 반환할 내용 없음)

    }
}