package com.example.springsecurity.service;

import com.example.springsecurity.dto.JoinDTO;
import com.example.springsecurity.entity.UserEntity;
import com.example.springsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    // 필드 주입 방식 (추후에 생성자 주입 방식으로 개선 필요)
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void joinProcess(JoinDTO joinDTO) {

        // db에 이미 동일한 username을 가진 회원이 존재하는지?
        boolean isUser = userRepository.existsByUsername(joinDTO.getUsername());
        if (isUser) {
            return;
        }

        // 앞단에서 받은 joinDTO를 엔티티로 변환
        UserEntity data = new UserEntity();

        data.setUsername(joinDTO.getUsername());
        data.setPassword(bCryptPasswordEncoder.encode(joinDTO.getPassword())); // 비밀번호 데이터 암호화
        data.setRole("ROLE_USER");

        // joinDTO로 받은 값을 data 객체에 넣어주고 ORM 쿼리러 DB에 저장.
        userRepository.save(data);
    }
}
