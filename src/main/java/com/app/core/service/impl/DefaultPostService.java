package com.app.core.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.app.core.entity.model.PostModel;
import com.app.core.entity.model.UserModel;
import com.app.core.exception.CJNotFoundException;
import com.app.core.repository.PostRepository;
import com.app.core.service.PostService;
import com.app.core.service.UserService;
import com.app.core.utils.CustomCodeException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DefaultPostService implements PostService {
    
    private final PostRepository postRepository;
    private final UserService userService;
    
    @Override
    @Transactional(readOnly = true)
    public PostModel getById(Long id) {
	Assert.notNull(id, "id cannot be null");
	return postRepository.findById(id).orElseThrow(
			() -> new CJNotFoundException(CustomCodeException.CODE_400, "post not found with id "+id));
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<PostModel> getAll() {
	return postRepository.findAll();
    }

    @Override
    @Transactional
    public PostModel save(PostModel post) {
	Assert.notNull(post, "post cannot be null");
	
	UserModel userSession = userService.getSessionUser();
	post.setUser(userSession);
	
	return postRepository.save(post);
    }

    @Override
    @Transactional
    public PostModel update(PostModel post, Long id) {
	Assert.notNull(id, "id cannot be null");
	Assert.notNull(post, "post cannot be null");

	PostModel postDb = postRepository.findById(id)
			.orElseThrow(() -> new CJNotFoundException(CustomCodeException.CODE_400, "post not found"));	
	
	validateUserPost(postDb);
	
	postDb.setText(post.getText());
	postDb.setLastModified(new Date());

	return postRepository.save(postDb);
    }
    
    private void validateUserPost(PostModel post) {
	Assert.notNull(post, "post cannot be null");
	
	UserModel userSession = userService.getSessionUser();
	UserModel userPost = Optional.ofNullable(post.getUser()).orElseThrow(() -> new CJNotFoundException(CustomCodeException.CODE_500, "user not found in post"));
	
	if(!userPost.getName().equals(userSession.getName())) {
	    throw  new CJNotFoundException(CustomCodeException.CODE_500, "You cannot modify this post");
	}
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
	Assert.notNull(id, "id cannot be null");
	PostModel postDb = postRepository.findById(id)
			.orElseThrow(() -> new CJNotFoundException(CustomCodeException.CODE_400, "post not found"));
	postRepository.delete(postDb);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostModel> finadAll(Pageable pageable) {
	return postRepository.findAll(pageable);
    }


}
