package by.epam.shaturko.dao;

import java.sql.Connection;
import java.util.Optional;

import by.epam.shaturko.bean.user.User;
import by.epam.shaturko.dao.exception.DAOException;

public interface AuthorizationDAO {	
	Optional<User> logIn(String login, String password, ThreadLocal<Connection> threadLocal) throws DAOException;
}
