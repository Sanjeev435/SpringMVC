package com.spring.dao;

import com.spring.model.Login;
import com.spring.model.User;

public interface UserDao {

	void register(User user);

	User validateUser(Login login);

}
