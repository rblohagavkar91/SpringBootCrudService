package com.rahul.springboot.rest.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.rahul.springboot.rest.model.UserInfo;

public interface UserDirectoryRepository extends CrudRepository<UserInfo,String> {
	Optional<UserInfo> findByuserId(String userId);
}
