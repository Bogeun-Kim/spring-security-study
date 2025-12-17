package com.example.springsecurity.service;

import com.example.springsecurity.config.CustomUserDetails;
import com.example.springsecurity.entity.UserEntity;
import com.example.springsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userData = userRepository.findByUsername(username);

        // null이 아닌 경우 CustomUserDetiails 라는 클래스에 userData 객체를 넣어서 반환해준다.
        if (userData != null) {

            return new CustomUserDetails(userData);
        }

        // null인 경우 저장된 ID가 없기 때문에 null을 반환한다. (임시)
        // Spring security 기본 계약상 DB에 찾는 유저가 없을 땐 예외를 던지는 것이 좋다.
        return null;
    }
}
