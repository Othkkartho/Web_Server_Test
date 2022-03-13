package com.myserver.servertest.Service.user;

import com.myserver.servertest.advice.exception.CEmailLoginFailedException;
import com.myserver.servertest.advice.exception.CEmailSignupFailedException;
import com.myserver.servertest.advice.exception.CUserNotFoundException;
import com.myserver.servertest.domain.user.User;
import com.myserver.servertest.domain.user.UserJpaRepo;
import com.myserver.servertest.dto.sign.UserLoginResponseDto;
import com.myserver.servertest.dto.sign.UserSignupRequestDto;
import com.myserver.servertest.dto.user.UserRequestDto;
import com.myserver.servertest.dto.user.UserResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private UserJpaRepo userJpaRepo;
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Long save(UserRequestDto userDto) {
        User saved = userJpaRepo.save(userDto.toEntity());
        return saved.getUserId();
    }

    @Transactional(readOnly = true)
    public UserResponseDto findById(Long id) {
        User user = userJpaRepo.findById(id)
                .orElseThrow(CUserNotFoundException::new);
        return new UserResponseDto(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDto findByEmail(String email) {
        User user = userJpaRepo.findByEmail(email).orElseThrow(CUserNotFoundException::new);
        return new UserResponseDto(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> findAllUser() {
        return userJpaRepo.findAll()
                .stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long update(Long id, UserRequestDto userRequestDto) {
        User modifiedUser = userJpaRepo.findById(id).orElseThrow(CUserNotFoundException::new);
        modifiedUser.updateNickName(userRequestDto.getNickname());
        return id;
    }

    @Transactional
    public void delete(Long id) {
        userJpaRepo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public UserLoginResponseDto login(String email, String password) {
        User user = userJpaRepo.findByEmail(email).orElseThrow(CEmailLoginFailedException::new);
        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new CEmailLoginFailedException();
        return new UserLoginResponseDto(user);
    }

    @Transactional
    public Long signup(UserSignupRequestDto userSignupDto) {
        if (userJpaRepo.findByEmail(userSignupDto.getEmail()).orElse(null) == null)
            return userJpaRepo.save(userSignupDto.toEntity()).getUserId();
        else throw new CEmailSignupFailedException();
    }
}

