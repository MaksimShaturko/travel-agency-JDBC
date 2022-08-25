package by.epam.shaturko.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.time.LocalDateTime;

import by.epam.shaturko.bean.tour.Tour;
import by.epam.shaturko.bean.user.User;
import by.epam.shaturko.dao.OrderTourDAO;
import by.epam.shaturko.dao.exception.DAOException;

public class OrderTourDAOImpl implements OrderTourDAO {
	private final static String CREATE_ORDER = "INSERT into travel_agency.tour_orders (user_id, tour_id, confirmation_date, order_status) VALUES(?,?,?,?)";
	private final static String CHANGE_TOUR_STATUS = "UPDATE travel_agency.tours SET status=? where tour_id=?";
	private final static String ACTIVE = "ACTIVE";
	private final static String ORDERED = "ordered";

	@Override
	public void orderTour(User user, Tour tour, ThreadLocal<Connection> threadLocal) throws DAOException {
		Connection connection = threadLocal.get();
		try (PreparedStatement ps = connection.prepareStatement(CREATE_ORDER)) {
			int i = 1;
			ps.setInt(i++, user.getId());
			ps.setInt(i++, tour.getId());
			ps.setObject(i++, LocalDateTime.now());
			ps.setString(i++, ACTIVE);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Error while creating an order");
		}
		try (PreparedStatement ps = connection.prepareStatement(CHANGE_TOUR_STATUS)) {
			ps.setString(1, ORDERED);
			ps.setInt(2, tour.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Error while changing a tour status");
		}
	}
}
