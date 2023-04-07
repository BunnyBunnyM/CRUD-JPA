package com.prueba.test;

import com.prueba.dao.UserDAO;
import com.prueba.entities.User;

public class Test {
	public static void main(String[] args) {
		
		UserDAO uDao = new UserDAO();
		
		uDao.delete(new User(1));
		
		

		
	}

}
