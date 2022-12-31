package com.driver.ui.controller;

import java.util.ArrayList;
import java.util.List;

import com.driver.io.converter.UserConverter;
import com.driver.model.request.UserDetailsRequestModel;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.UserResponse;
import com.driver.service.impl.UserServiceImpl;
import com.driver.shared.dto.UserDto;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserServiceImpl userService;

	@GetMapping(path = "/{id}")
	public UserResponse getUser(@PathVariable String id) throws Exception{
		UserDto userDto = userService.getUserByUserId(id);
		UserResponse userResponse = UserConverter.convertDtoToResponse(userDto);
		return userResponse;
	}

	@PostMapping()
	public UserResponse createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception{
		UserDto userDto = UserConverter.convertRequestToDto(userDetails);
		UserDto userDto1 = userService.createUser(userDto);

		UserResponse userResponse = UserConverter.convertDtoToResponse(userDto1);
		return userResponse;
	}

	@PutMapping(path = "/{id}")
	public UserResponse updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) throws Exception{
		UserDto userDto = UserConverter.convertRequestToDto(userDetails);
		UserDto updatedUserDto = userService.updateUser(id,userDto);
		UserResponse userResponse = UserConverter.convertDtoToResponse(updatedUserDto);
		return userResponse;
	}

	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteUser(@PathVariable String id) throws Exception{
		OperationStatusModel operationStatusModel = userService.deleteUser(id);

		return operationStatusModel;
	}
	
	@GetMapping()
	public List<UserResponse> getUsers(){
		List<UserResponse> allUserResponses = new ArrayList<>();

		List<UserDto> allUsersDto = userService.getUsers();
		for(UserDto userDto : allUsersDto){
			UserResponse userResponse = UserConverter.convertDtoToResponse(userDto);
			allUserResponses.add(userResponse);
		}
		return allUserResponses;
	}
	
}
