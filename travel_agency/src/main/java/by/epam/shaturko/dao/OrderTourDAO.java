package by.epam.shaturko.dao;

import java.sql.Connection;

import by.epam.shaturko.bean.tour.Tour;
import by.epam.shaturko.bean.user.User;
import by.epam.shaturko.dao.exception.DAOException;

public interface OrderTourDAO {
	
	void orderTour(User user, Tour tour, ThreadLocal<Connection> threadLocal) throws DAOException;
}
