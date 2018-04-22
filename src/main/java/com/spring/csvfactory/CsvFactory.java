package com.spring.csvfactory;

import java.io.IOException;

import com.spring.model.Login;
import com.spring.model.User;

public interface CsvFactory {
	
	User validateUser(Login login) throws IOException;

	User read(User user) throws IOException;

	String write(User user) throws IOException;
}
