package com.app.core.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.core.security.entity.SecurityUser;

public interface SecurityUserRepository  extends JpaRepository<SecurityUser, Long>{	
	Optional<SecurityUser> findByName(final String name);
	boolean existsSecurityUserByName(final String username);
}
