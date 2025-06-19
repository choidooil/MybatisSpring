package com.example.MybatisSpring.Domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter //lombok 으로 getter, setter 자동설정
@AllArgsConstructor // 모든 필드를 인자로 받는 생성자 자동 생성
@NoArgsConstructor  // 기본생성자도 따로 만들어줘야함.
public class User {
    private long userid;
    private String username;
    private String useraddress;

    @Override
    /* toString의 목적
      1. 디버깅 및 로깅:
      2. 가독성 향상 - 객체에 대한 정보를 사람이 읽기 쉬운 형태로 제공.
         예를들어, User 객체의 id, name, email 필드 값을 포함하는 문자열을 반환함으로써 객체의 내용을 명확하게 파악할 수 있습니다.
      3. 데이터 표현 - 다른시스템에 문자열 형태로 전달시, Json으로 하면 유용하고 쉽게 전달가능*/
    public String toString() {
        return "User{" + "id=" + userid + ", name='" + username + '\'' + ", address='" + useraddress + '\'' + '}';
    }

}