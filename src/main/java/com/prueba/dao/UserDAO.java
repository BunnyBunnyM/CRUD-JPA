package com.prueba.dao;

import com.prueba.util.Conexion;
import com.prueba.util.GenericDAO;
import com.prueba.entities.User;

public class UserDAO extends Conexion<User> implements GenericDAO<User> {
	
	public UserDAO(){
		super(User.class);
	}

}
