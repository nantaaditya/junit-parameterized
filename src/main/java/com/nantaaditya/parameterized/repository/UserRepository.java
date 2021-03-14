package com.nantaaditya.parameterized.repository;

import com.nantaaditya.parameterized.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * created by pramuditya.anantanur
 **/
@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
