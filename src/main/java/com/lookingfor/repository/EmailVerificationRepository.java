package com.lookingfor.repository;

import org.springframework.stereotype.Repository;

import com.lookingfor.entity.EmailVerificationEntity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface EmailVerificationRepository extends JpaRepository<EmailVerificationEntity, Long> {
	 Optional<EmailVerificationEntity> findByEmailAndVerificationCode(String email, String verificationCode);
}
