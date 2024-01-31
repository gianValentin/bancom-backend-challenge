package com.app.core.entity.dto.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdatePostDTO {
    @NotBlank(message = "{post.text.notBlank}")
    @Size(min = 2, max = 32, message = "{post.text.size}")
    private String text;
}
