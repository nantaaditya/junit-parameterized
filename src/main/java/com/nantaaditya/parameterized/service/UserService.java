package com.nantaaditya.parameterized.service;

import com.nantaaditya.parameterized.entity.User;

import java.util.List;

/**
 * created by pramuditya.anantanur
 **/
public interface UserService {
  User save(User user);
  
  List<User> findAll();
  
  User get(String id);
  
  boolean delete(String id);
}
