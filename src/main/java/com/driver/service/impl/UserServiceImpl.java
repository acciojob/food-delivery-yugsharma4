package com.driver.service.impl;

import com.driver.io.converter.UserConverter;
import com.driver.io.entity.UserEntity;
import com.driver.io.repository.UserRepository;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.RequestOperationName;
import com.driver.model.response.RequestOperationStatus;
import com.driver.service.UserService;
import com.driver.shared.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto user) throws Exception {
        UserEntity userExist = userRepository.findByUserId(user.getUserId());
        if(userExist != null){
            throw new Exception("User already exist!!!");
        }

        //convert UserDto to UserEntity
        UserEntity newUserEntity = UserConverter.convertDtoToEntity(user);
        newUserEntity = userRepository.save(newUserEntity);

        //convert new UserEntity into new User Dto
        UserDto newUserDto = UserConverter.convertEntityToDto(newUserEntity);

        return newUserDto;
    }

    @Override
    public UserDto getUser(String email) throws Exception {
        return null;
    }

    @Override
    public UserDto getUserByUserId(String userId) throws Exception {
        UserEntity userEntity = userRepository.findByUserId(userId);

        //User doesn't exist
        if(userEntity == null)
            throw new Exception("User doesn't exist!!!");

        UserDto userDto = UserConverter.convertEntityToDto(userEntity);
        return userDto;
    }

    @Override
    public UserDto updateUser(String userId, UserDto user) throws Exception {
        UserEntity oldUser = userRepository.findByUserId(userId);
        long oldUserId = oldUser.getId();
        if(oldUser == null) throw new Exception("User doesn't exist with userId: " + userId);

        UserEntity updatedUserEntity = UserEntity.builder()
                .id(oldUserId)
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .userId(user.getUserId())
                .build();
        userRepository.save(updatedUserEntity);

        UserDto updatedUserDto = UserConverter.convertEntityToDto(updatedUserEntity);
        return updatedUserDto;
    }

    @Override
    public OperationStatusModel deleteUser(String userId) throws Exception {
        UserEntity isUserExist = userRepository.findByUserId(userId);
        OperationStatusModel operationStatusModel;

        //User doesn't exist
        if(isUserExist == null) {
            operationStatusModel = OperationStatusModel.builder()
                    .operationName(RequestOperationName.DELETE.toString())
                    .operationResult(RequestOperationStatus.ERROR.toString())
                    .build();

            throw new Exception("User doesn't exist with userId: " + userId);
        } //User exist
        else{
            operationStatusModel = OperationStatusModel.builder()
                    .operationResult(RequestOperationStatus.SUCCESS.toString())
                    .operationName(RequestOperationName.DELETE.toString())
                    .build();

            userRepository.delete(isUserExist);
        }

        return operationStatusModel;

    }

    @Override
    public List<UserDto> getUsers() {
        List<UserDto> allUsers = new ArrayList<>();
        List<UserEntity> allUsersEntity = userRepository.findAllById();

        for(UserEntity userEntity : allUsersEntity){
            UserDto userDto = UserConverter.convertEntityToDto(userEntity);
            allUsers.add(userDto);
        }
        return allUsers;
    }
}