package com.example.springsecurity.controller;

import com.example.springsecurity.dto.JoinDTO;
import com.example.springsecurity.service.JoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class JoinController {

    @Autowired
    private JoinService joinService;

    @GetMapping("/join")
    public String joinPage() {

        return "join";
    }

    @PostMapping("/joinProc")
    public String joinProcess(JoinDTO joinDTO) {
        /*
        * 회원가입이 완료되면 로그인 페이지로 옮기고
        * 회원가입이 실패하면 다시 회원가입 페이지로 돌아가도록 설정해야하는데
        * 가장 기본적인 작업만 할 예정이기 때문에 바로 로그인 페이지로 리다이렉팅함.
        * */

        // form 데이터 뽑아내보기
//        System.out.println(joinDTO.getUsername());
//        System.out.println(joinDTO.getPassword());

        joinService.joinProcess(joinDTO);

        return "redirect:/login";
    }
}
