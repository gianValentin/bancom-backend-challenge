package com.app.core.security.dto;

import com.app.core.security.annotation.UsernameExistsConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {
    	@UsernameExistsConstraint(message = "El nombre de usuario  ya existe.")
	@NotBlank(message = "Nombre de usuario no puede estar vacio.")
	@Size(min = 2, max = 20, message = "El rango de caracteres para el nombre de usuario es minimo 8 y maximo 20. ")
	private String name;
	private String cellphone;
	@NotBlank(message = "Lastname no puede estar vacio.")
	@Size(min = 2, max = 32, message = "El rango de caracteres para lastname es minimo 8 y maximo 20. ")
	private String lastname;
	@NotBlank(message = "Contraseña no puede estar vacio.")
	@Size(min = 4, max = 20, message = "El rango de caracteres para contraseña es minimo 8 y maximo 20. ")
	private String password;
}
