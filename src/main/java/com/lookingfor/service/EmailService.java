package com.lookingfor.service;

import com.lookingfor.dto.CodeDTO;
import com.lookingfor.dto.EmailDTO;
import com.lookingfor.entity.EmailVerificationEntity;
import com.lookingfor.repository.EmailVerificationRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.MailException;
import java.util.Optional;
import java.util.Random;

@Service
public class EmailService {

    @Autowired
    private EmailVerificationRepository emailVerificationRepository;

    @Autowired
    private JavaMailSender mailSender;

    public boolean sendVerificationCode(EmailDTO emailDto) {
        String code = generateVerificationCode();
        EmailVerificationEntity emailVerification = new EmailVerificationEntity(emailDto.getEmail(), code, false);
        
        // 저장
        emailVerificationRepository.save(emailVerification);

        // 이메일 전송
        try {
            sendEmail(emailDto.getEmail(), code);
            System.out.println(code);
            return true;
        } catch (MailException | MessagingException e) {
        	e.printStackTrace();
            return false;
        }
    }

    private void sendEmail(String to, String code) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo(to);
        helper.setSubject("Email Verification Code");
        helper.setText("Your verification code is: " + code);
        mailSender.send(message);
    }

    private String generateVerificationCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }

    public boolean verifyCode( CodeDTO codeDto) {
        Optional<EmailVerificationEntity> verification = emailVerificationRepository.findByEmailAndVerificationCode(codeDto.getEmail(), codeDto.getCode());
        if (verification.isPresent()) {
            EmailVerificationEntity emailVerification = verification.get();
            if (emailVerification.getVerificationCode().equals(codeDto.getCode())) {
                emailVerification.setVerified(true);
                emailVerificationRepository.save(emailVerification);
                return true;
            }	
        }
        return false;
    }
}
