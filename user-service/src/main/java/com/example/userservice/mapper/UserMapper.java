package com.example.userservice.mapper;

import com.example.userservice.dto.CreateUserRequest;
import com.example.userservice.dto.CreateUserResponse;
import com.example.userservice.dto.UserDTO;
import com.example.userservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User userDTOToUser(UserDTO userDTO);
    UserDTO userToUserDTO(User user);
    UserDTO createUserReqToUserDTO(CreateUserRequest request);
    CreateUserResponse userDTOToCreateUserRes(UserDTO userDTO);


}
