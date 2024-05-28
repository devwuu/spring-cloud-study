package com.example.userservice.service;

import com.example.userservice.dto.UserDTO;
import com.example.userservice.entity.User;
import com.example.userservice.exception.ApiException;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserDTO create(UserDTO userDTO){
        userDTO.setUserId(UUID.randomUUID().toString());
        String encoded = passwordEncoder.encode(userDTO.getPwd());
        User user = UserMapper.INSTANCE.userDTOToUser(userDTO);
        user.setEncryptedPwd(encoded);
        User saved = repository.saveAndFlush(user);
        return UserMapper.INSTANCE.userToUserDTO(saved);
    }

    public UserDTO findByUserId(String userId){
        Optional<User> optional = repository.findByUserId(userId);
        User user = optional.orElseThrow(() -> new ApiException("Not Exist User"));
        return UserMapper.INSTANCE.userToUserDTO(user);
    }

    public List<UserDTO> findAll(){
        List<User> users = repository.findAll();
        return UserMapper.INSTANCE.userToUserDTO(users);
    }

}
