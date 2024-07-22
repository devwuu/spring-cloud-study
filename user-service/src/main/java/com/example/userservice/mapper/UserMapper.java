package com.example.userservice.mapper;

import com.example.userservice.dto.CreateUserRequest;
import com.example.userservice.dto.UserResponse;
import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User userDtoToUser(UserDto userDto);

    UserDto userToUserDto(User user);
    List<UserDto> userToUserDto(List<User> user);

    UserDto createUserReqToUserDto(CreateUserRequest request);

    UserResponse userDtoToCreateUserRes(UserDto userDto);
    List<UserResponse> userDtoToCreateUserRes(List<UserDto> userDt);


}
