package com.app.core.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.core.entity.dto.user.GetAllUserDto;
import com.app.core.entity.dto.user.GetUserDto;
import com.app.core.entity.dto.user.PostUserDto;
import com.app.core.entity.dto.user.PutUserDto;
import com.app.core.entity.model.UserModel;
import com.app.core.exception.CJNotFoundException;
import com.app.core.service.UserService;
import com.app.core.utils.CustomCodeException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/${user-controller.path}")
@Tag(name = "User", description = "User controller")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Operation(description = "Get pageable endpoint for user", summary = "This is a summary for user get pageable endpoint")
    @GetMapping
    public ResponseEntity<Page<GetUserDto>> getPageable(@ParameterObject Pageable pageable) {
	Page<UserModel> userPage = userService.finadAll(pageable);
	Page<GetUserDto> userPageDto = userPage.map(user -> modelMapper.map(user, GetUserDto.class));
	return ResponseEntity.status(HttpStatus.OK).body(userPageDto);
    }

    @Operation(description = "Get by name endpoint for user", summary = "This is a summary for user get by name endpoint")
    @GetMapping("/{name}/name")
    public ResponseEntity<GetUserDto> getUserByName(@PathVariable String name) {
	UserModel userDb = userService.getByName(name);
	if (ObjectUtils.isEmpty(userDb))
	    throw new CJNotFoundException(CustomCodeException.CODE_400, "user not found with name " + name);
	GetUserDto getUserDto = modelMapper.map(userDb, GetUserDto.class);
	return ResponseEntity.status(HttpStatus.OK).body(getUserDto);
    }

    @Operation(description = "Save  endpoint for user", summary = "This is a summary for user save endpoint")
    @PostMapping
    public ResponseEntity<GetUserDto> saveUser(@Valid @RequestBody PostUserDto dto) {
	UserModel user = modelMapper.map(dto, UserModel.class);
	UserModel userDb = userService.save(user);
	GetUserDto getUserDto = modelMapper.map(userDb, GetUserDto.class);
	return ResponseEntity.status(HttpStatus.CREATED).body(getUserDto);
    }

    @Operation(description = "Update  endpoint for user", summary = "This is a summary for user update endpoint")
    @PutMapping(value = "/{id}")
    public ResponseEntity<GetUserDto> updateUser(@Valid @RequestBody PutUserDto dto,
	    @PathVariable(name = "id") Long id) {
	UserModel user = modelMapper.map(dto, UserModel.class);
	UserModel userDb = userService.update(user, id);
	GetUserDto getUserDto = modelMapper.map(userDb, GetUserDto.class);
	return ResponseEntity.status(HttpStatus.OK).body(getUserDto);
    }

    @Operation(description = "Delete  endpoint for user", summary = "This is a summary for user delete endpoint")
    @DeleteMapping(value = "{id}")
    public ResponseEntity<GetUserDto> deleteUser(@PathVariable(name = "id") Long id) {
	userService.deleteById(id);
	return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
