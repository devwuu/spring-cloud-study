package com.example.userservice.service;

import com.example.userservice.dto.UserDTO;
import com.example.userservice.entity.User;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserDTO create(UserDTO userDTO){
        userDTO.setUserId(UUID.randomUUID().toString());
        User user = UserMapper.INSTANCE.userDTOToUser(userDTO);
        String encoded = passwordEncoder.encode(userDTO.getPwd());
        user.setEncryptedPwd(encoded);
        User saved = repository.saveAndFlush(user);
        return UserMapper.INSTANCE.userToUserDTO(saved);
    }

}
