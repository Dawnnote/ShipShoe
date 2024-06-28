package com.hanghae.shipshoe.domain.user.service.implement;

import com.hanghae.shipshoe.domain.user.common.CertificationNumber;
import com.hanghae.shipshoe.domain.user.dto.UserResponseValidationDto;
import com.hanghae.shipshoe.domain.user.dto.auth.*;
import com.hanghae.shipshoe.domain.user.entity.CertificationEntity;
import com.hanghae.shipshoe.domain.user.entity.User;
import com.hanghae.shipshoe.domain.user.jwt.provider.EmailProvider;
import com.hanghae.shipshoe.domain.user.jwt.provider.JwtProvider;
import com.hanghae.shipshoe.domain.user.repository.CertificationRepository;
import com.hanghae.shipshoe.domain.user.repository.UserRepository;
import com.hanghae.shipshoe.domain.user.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService {

    private final CertificationRepository certificationRepository;
    private final UserRepository userRepository;
    private final EmailProvider emailProvider;

    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Override
    public ResponseEntity<? super IdCheckResponseDto> idCheck(IdCheckRequestDto dto) {

        try {

            String email = dto.getEmail();
            boolean isExistId = userRepository.existsByEmail(email);
            if (isExistId) return IdCheckResponseDto.duplicatedId();

        } catch (Exception exception) {
            exception.getStackTrace();
            return UserResponseValidationDto.databaseError();
        }

        return IdCheckResponseDto.success();
    }

    @Override
    public ResponseEntity<? super EmailCertificationResponseDto> emailCertification(EmailCertificationRequestDto dto) {
        try {

            String email = dto.getEmail();

            boolean isExistId = userRepository.existsByEmail(email);
            if (isExistId) return EmailCertificationResponseDto.duplicateId();

            String certificationNumber = CertificationNumber.getCertificationNumber();

            boolean isSuccessed = emailProvider.sendCertificationMail(email, certificationNumber);
            if (!isSuccessed) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

            CertificationEntity certificationEntity = new CertificationEntity(email, certificationNumber);
            certificationRepository.save(certificationEntity);

        } catch (Exception exception) {
            exception.getStackTrace();
            return UserResponseValidationDto.databaseError();
        }

        return EmailCertificationResponseDto.success();
    }

    @Override
    public ResponseEntity<? super CheckCertificationResponseDto> checkCertification(CheckCertificationRequestDto dto) {
       try {

           String email = dto.getEmail();
           String certificationNumber = dto.getCertificationNumber();

           CertificationEntity certificationEntity = certificationRepository.findByEmail(email);
           if (certificationEntity == null) return CheckCertificationResponseDto.certificationFail();

           boolean isMatch = certificationEntity.getEmail().equals(email) && certificationEntity.getCertificationNumber().equals(certificationNumber);
           if (!isMatch) return CheckCertificationResponseDto.certificationFail();

       } catch (Exception exception) {
           exception.getStackTrace();
           return UserResponseValidationDto.databaseError();
       }

       return CheckCertificationResponseDto.success();
    }

    @Override
    public ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto) {
        try {

            String email = dto.getEmail();
            boolean isExistEmail = userRepository.existsByEmail(email);
            if (isExistEmail) return SignUpResponseDto.duplicatedId();

            String certificationNumber = dto.getCertificationNumber();
            CertificationEntity certificationEntity = certificationRepository.findByEmail(email);
            boolean isMatched = certificationEntity.getEmail().equals(email) &&
                                certificationEntity.getCertificationNumber().equals(certificationNumber);
            if (!isMatched) return SignUpResponseDto.certificationFail();

            String password = dto.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            dto.setPassword(encodedPassword);

            User user = new User(dto);
            userRepository.save(user);

            certificationRepository.deleteByEmail(email);

        } catch (Exception exception) {
            exception.getStackTrace();
            return UserResponseValidationDto.databaseError();
        }

        return SignUpResponseDto.success();
    }

    @Override
    public ResponseEntity<? super SignInResponseDto> logIn(@Valid SignInRequestDto dto) {
        String token = null;

        try {

            String email = dto.getEmail();
            User user = userRepository.findByEmail(email);
            if (user == null) return SignInResponseDto.signInFail();

            String password = dto.getPassword();
            String encodedPassword = user.getPassword();
            boolean isMatched = passwordEncoder.matches(password, encodedPassword);
            if (!isMatched) return SignInResponseDto.signInFail();

            token = jwtProvider.create(user.getId(), user.getRole().toString());

        } catch (Exception exception) {
            exception.getStackTrace();
            return UserResponseValidationDto.databaseError();
        }

        return SignInResponseDto.success(token);
    }
}
