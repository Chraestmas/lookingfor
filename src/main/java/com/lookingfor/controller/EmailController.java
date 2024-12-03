package com.lookingfor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.lookingfor.dto.CodeDTO;
import com.lookingfor.dto.EmailDTO;
import com.lookingfor.service.EmailService;

@Controller
@CrossOrigin(origins = "http://localhost:8089")
public class EmailController {

    @Autowired
    private EmailService emailService;

    // 이메일로 인증 코드 보내기
    @PostMapping("/api/email/sendCode")
    public ResponseEntity<String> sendVerificationCode(@RequestBody EmailDTO emailDto) {
        boolean success = emailService.sendVerificationCode(emailDto);
        if (success) {
            return ResponseEntity.ok("Code sent successfully");
        } else {
            return ResponseEntity.status(500).body("Failed to send code");
        }
    }

    // 인증 코드 확인
    @PostMapping("/api/email/verifyCode")
    public ResponseEntity<String> verifyCode( @RequestBody CodeDTO codeDto) {
        boolean success = emailService.verifyCode(codeDto);
        if (success) {
            return ResponseEntity.ok("Code verified successfully");
        } else {
            return ResponseEntity.status(400).body("Invalid code");
        }
    }
}
