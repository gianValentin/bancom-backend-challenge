package com.app.core.controller;

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

import com.app.core.entity.dto.post.RequestPostDTO;
import com.app.core.entity.dto.post.ResponsePostDTO;
import com.app.core.entity.model.PostModel;
import com.app.core.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/${post-controller.path}")
@Tag(name = "Post", description = "Post controller")
public class PostController {

    private final PostService postService;
    private final ModelMapper modelMapper;

    @Operation(description = "Get pageable endpoint for Post", summary = "This is a summary for Post get pageable endpoint")
    @GetMapping
    public ResponseEntity<Page<ResponsePostDTO>> getPageable( @ParameterObject  Pageable pageable) {
	Page<PostModel> PostPage = postService.finadAll(pageable);
	Page<ResponsePostDTO> PostPageDto = PostPage.map(Post -> modelMapper.map(Post, ResponsePostDTO.class));
	return ResponseEntity.status(HttpStatus.OK).body(PostPageDto);
    }

    @Operation(description = "Save  endpoint for post", summary = "This is a summary for post save endpoint")
    @PostMapping
    public ResponseEntity<ResponsePostDTO> savepost(@Valid @RequestBody RequestPostDTO dto) {
	PostModel post = modelMapper.map(dto, PostModel.class);
	PostModel postDb = postService.save(post);
	ResponsePostDTO getpostDto = modelMapper.map(postDb, ResponsePostDTO.class);
	return ResponseEntity.status(HttpStatus.CREATED).body(getpostDto);
    }

    @Operation(description = "Update  endpoint for post", summary = "This is a summary for post update endpoint")
    @PutMapping(value = "/{id}")
    public ResponseEntity<ResponsePostDTO> updatepost(@Valid @RequestBody RequestPostDTO dto,
	    @PathVariable(name = "id") Long id) {
	PostModel post = modelMapper.map(dto, PostModel.class);
	PostModel postDb = postService.update(post, id);
	ResponsePostDTO getpostDto = modelMapper.map(postDb, ResponsePostDTO.class);
	return ResponseEntity.status(HttpStatus.OK).body(getpostDto);
    }

    @Operation(description = "Delete  endpoint for post", summary = "This is a summary for post delete endpoint")
    @DeleteMapping(value = "{id}")
    public ResponseEntity<ResponsePostDTO> deleteUser(@PathVariable(name = "id") Long id) {
	postService.deleteById(id);
	return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
