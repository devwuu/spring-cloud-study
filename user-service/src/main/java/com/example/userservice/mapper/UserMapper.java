package com.example.userservice.mapper;

import com.example.userservice.dto.CreateUserRequest;
import com.example.userservice.dto.UserResponse;
import com.example.userservice.dto.UserDTO;
import com.example.userservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User userDTOToUser(UserDTO userDTO);

    UserDTO userToUserDTO(User user);
    List<UserDTO> userToUserDTO(List<User> user);

    UserDTO createUserReqToUserDTO(CreateUserRequest request);

    UserResponse userDTOToCreateUserRes(UserDTO userDTO);
    List<UserResponse> userDTOToCreateUserRes(List<UserDTO> userDTO);


}
