package com.app.core.entity.dto.post;

import java.util.Date;
import com.app.core.entity.dto.user.GetUserDto;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponsePostDTO {
    private Long id;
    private String text;
    private GetUserDto user;
    @JsonFormat(pattern = "dd-MM-yyyy")
    Date createAt;
    @JsonFormat(pattern = "dd-MM-yyyy")
    Date lastModified;
}
