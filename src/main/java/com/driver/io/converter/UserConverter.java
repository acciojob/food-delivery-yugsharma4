package com.driver.io.converter;

import com.driver.io.entity.UserEntity;
import com.driver.model.request.UserDetailsRequestModel;
import com.driver.model.response.UserResponse;
import com.driver.shared.dto.UserDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserConverter {

    public static UserDto convertRequestToDto(UserDetailsRequestModel userDetails){
        if(userDetails == null) return null;
        return UserDto.builder()
                .email(userDetails.getEmail())
                .firstName(userDetails.getFirstName())
                .lastName(userDetails.getLastName())
                .userId(userDetails.getUserId())
                .build();
    }

    public static UserResponse convertDtoToResponse(UserDto userDto){
        if(userDto == null) return null;
        return  UserResponse.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .userId(userDto.getUserId())
                .build();
    }

    public static UserDto convertEntityToDto(UserEntity userEntity){
        if(userEntity == null) return null;
        return  UserDto.builder()
                .userId(userEntity.getUserId())
                .id(userEntity.getId())
                .lastName(userEntity.getLastName())
                .firstName(userEntity.getFirstName())
                .email(userEntity.getEmail())
                .build();
    }

    public static UserEntity convertDtoToEntity(UserDto userDto){
        if(userDto == null) return null;
        return  UserEntity.builder()
                .userId(userDto.getUserId())
                .lastName(userDto.getLastName())
                .firstName(userDto.getFirstName())
                .email(userDto.getEmail())
                .build();
    }

}
