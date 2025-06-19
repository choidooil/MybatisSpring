package com.example.MybatisSpring.Repository;

import com.example.MybatisSpring.Domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> findAllUsers();
    User findUser(long id);
    void insertUser(User user);
    int updateUser(User user);
    void deleteUser(long id);
}
