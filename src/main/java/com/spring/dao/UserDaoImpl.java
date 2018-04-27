package com.spring.dao;

import org.springframework.stereotype.Service;

import com.spring.model.Login;
import com.spring.model.User;

@Service
public class UserDaoImpl implements UserDao {

	public void register(User user) {

	}

	public User validateUser(Login login) {
		return new User();
	}

}
