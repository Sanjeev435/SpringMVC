package com.spring.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.spring.csvfactory.CsvFactory;
import com.spring.model.Login;
import com.spring.model.User;

public class UserServiceImpl implements UserService {

	@Autowired
	private CsvFactory csvFactory;

	public void register(User user) {
		try {
			csvFactory.write(user);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public User validateUser(Login login) {
		try {
			return csvFactory.validateUser(login);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

}
