package com.hanghae.shipshoe.domain.user.service;

import com.hanghae.shipshoe.domain.Address;
import com.hanghae.shipshoe.domain.user.dto.UserRequestDto;
import com.hanghae.shipshoe.domain.user.dto.UserResponseDto;
import com.hanghae.shipshoe.domain.user.entity.User;
import com.hanghae.shipshoe.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    @Transactional
    public UserResponseDto save(UserRequestDto request) {
        Address address = new Address(request.getState(), request.getCity(),
                                        request.getZipcode(), request.getStreet());
        User user = new User(request, address);
        validateUser(user);

        userRepository.save(user);
        return new UserResponseDto(user);
    }

    public void validateUser(User user) {
        List<User> findUser = userRepository.findByEmail(user.getEmail());
        if (!findUser.isEmpty()) {
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }
    }

    public UserResponseDto findUser(Long id) {
        User findUser = userRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        return new UserResponseDto(findUser);
    }
}
