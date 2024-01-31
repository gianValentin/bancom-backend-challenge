package com.app.core.entity.dto.user;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetUserDto{
		Long id;		
		String name;	
		String cellphone;
		String lastname;	
		@JsonFormat(pattern = "dd-MM-yyyy")
		Date createAt;
		@JsonFormat(pattern = "dd-MM-yyyy")
		Date lastModified;
}
