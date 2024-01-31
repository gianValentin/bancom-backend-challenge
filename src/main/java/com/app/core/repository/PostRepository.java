package com.app.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.core.entity.model.PostModel;

public interface PostRepository extends JpaRepository<PostModel, Long>{

}
