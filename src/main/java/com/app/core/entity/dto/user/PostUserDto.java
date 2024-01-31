package com.app.core.entity.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostUserDto{
	@NotBlank(message = "{name.notblank}")
	@Size(min = 2, max=32, message="{name.size}")
	String name;
	@NotBlank(message = "{lastname.notblank}") 
	@Size(min = 9, max=9, message="{lastname.size")
	String cellphone;
	@NotBlank(message = "{password.notblank}") 
	@Size(min = 4, max=20, message="{password.size}")
	String password;
	@NotBlank(message = "{lastname.notblank}") 
	@Size(min = 2, max=32, message="{lastname.size")
	String lastname;		
}
