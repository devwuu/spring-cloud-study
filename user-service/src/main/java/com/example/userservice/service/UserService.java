package com.example.userservice.service;

import com.example.userservice.dto.UserDTO;
import com.example.userservice.entity.User;
import com.example.userservice.exception.ApiException;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
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

    public UserDTO findByEmail(String email){
        Optional<User> optional = repository.findByEmail(email);
        User user = optional.orElseThrow(() -> new ApiException("Not Exist User"));
        return UserMapper.INSTANCE.userToUserDTO(user);
    }

    public List<UserDTO> findAll(){
        List<User> users = repository.findAll();
        return UserMapper.INSTANCE.userToUserDTO(users);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // username == email
        Optional<User> optional = repository.findByEmail(username);
        User user = optional.orElseThrow(() -> new ApiException("Not Exist User"));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getEncryptedPwd(),
                true, true, true, true,
                new ArrayList<>()
        );
    }
}
