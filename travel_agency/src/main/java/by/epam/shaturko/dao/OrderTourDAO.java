package by.epam.shaturko.dao;

import java.sql.Connection;

import by.epam.shaturko.dao.exception.DAOException;
import by.epam.shaturko.entity.tour.Tour;
import by.epam.shaturko.entity.user.User;

public interface OrderTourDAO {
	
	void orderTour(User user, Tour tour, ThreadLocal<Connection> threadLocal) throws DAOException;
}
