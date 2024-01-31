package com.app.core.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.core.entity.model.PostModel;

public interface PostService {	
	List<PostModel> getAll();
	PostModel getById(Long id);
	PostModel save(final PostModel post);
	PostModel update(final PostModel post, final Long id);
	void deleteById(final Long id);
	Page<PostModel> finadAll(Pageable pageable);
}
