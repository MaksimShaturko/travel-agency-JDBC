package by.epam.shaturko.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import by.epam.shaturko.dao.ChangingDataDAO;
import by.epam.shaturko.dao.exception.DAOException;
import by.epam.shaturko.entity.tour.Tour;
import by.epam.shaturko.entity.tour.TourOrder;
import by.epam.shaturko.entity.user.User;

public class ChangingDataDAOImpl implements ChangingDataDAO {
	private final static String CHANGE_AGENCY_DISCOUNT = "UPDATE travel_agency.user_details SET travel_agency.user_details.agency_additional_discount"
			+ "=? where travel_agency.user_details.details_id=?";
	private final static String SET_TOUR_STATUS = "UPDATE travel_agency.tours SET status=? where tour_id=?";
	private final static String SET_TOUR_ORDER_STATUS = "UPDATE travel_agency.tour_orders SET order_status=? where tour_id=?";
	private final static String CREATE_VISITED_TOUR = "INSERT into travel_agency.visited_tours (user_id, tour_id) values(?,?)";
	private final static String CHANGE_ORDER_STATUS = "UPDATE travel_agency.tour_orders SET order_status='CANCELED' where user_id=? AND tour_id=?";
	private final static String CHANGE_TOUR_STATUS = "UPDATE travel_agency.tours SET status='free' where tour_id=?";
	private final static String GET_DETAILS_ID_BY_USER_ID = "SELECT travel_agency.users";
	private final static String INCREMENT_VISITED_TOURS = "UPDATE travel_agency.user_details det JOIN travel_agency.users us "
			+ "ON (us.user_details_id=det.details_id) "
			+ "SET det.number_of_visited_tours=det.number_of_visited_tours+1, det.loyality_discount=(det.number_of_visited_tours+1)*3 "
			+ "WHERE us.user_id=?";
	private final static String SET_USER_DISCOUNT = "UPDATE travel_agency.user_details "
			+ "SET loyality_discount=number_of_visited_tours*0.03";
	private final static String VISITED = "visited";
	private final static String COMPLETED = "COMPLETED";

	@Override
	public void changeAgencyDiscount(int newDiscount, int detailsId, ThreadLocal<Connection> threadLocal)
			throws DAOException {
		Connection connection = threadLocal.get();
		try (PreparedStatement ps = connection.prepareStatement(CHANGE_AGENCY_DISCOUNT)) {
			ps.setInt(1, newDiscount);
			ps.setInt(2, detailsId);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Error while setting agency discount", e);
		}
	}

	@Override
	public void setTourAsVisited(TourOrder order, ThreadLocal<Connection> threadLocal) throws DAOException {
		Connection connection = threadLocal.get();
		setTourStatusAsVisited(order, connection);
		setOrderStatusAsCompleted(order, connection);
		createVisitedTour(order, connection);	
		setUserDiscount(order, connection);
	}
	
	private void setUserDiscount(TourOrder order, Connection connection) throws DAOException {
		try (PreparedStatement ps = connection.prepareStatement(INCREMENT_VISITED_TOURS)) {
			ps.setInt(1, order.getUser().getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Error while setting user discount", e);
		}
	}

	private void setTourStatusAsVisited(TourOrder order, Connection connection) throws DAOException {
		try (PreparedStatement ps = connection.prepareStatement(SET_TOUR_STATUS)) {
			ps.setString(1, VISITED);
			ps.setInt(2, order.getTour().getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Error while setting tour_status as visited", e);
		}
	}
	
	private void setOrderStatusAsCompleted(TourOrder order, Connection connection) throws DAOException {
		try(PreparedStatement ps = connection.prepareStatement(SET_TOUR_ORDER_STATUS)) {
			ps.setString(1, COMPLETED);
			ps.setInt(2, order.getTour().getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Error while setting order_status as comleted", e);
		}
	}
	
	private void createVisitedTour(TourOrder order, Connection connection) throws DAOException {
		try(PreparedStatement ps = connection.prepareStatement(CREATE_VISITED_TOUR)) {
			ps.setInt(1, order.getUser().getId());
			ps.setInt(2, order.getTour().getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Error while adding to db table 'visited_tours'", e);
		}
	}
	
	@Override
	public void cancelTour(ThreadLocal<Connection> threadLocal, Tour tour, User user) throws DAOException {
		Connection connection = threadLocal.get();
		try (PreparedStatement ps = connection.prepareStatement(CHANGE_ORDER_STATUS)) {
			int i = 1;
			ps.setInt(i++, user.getId());
			ps.setInt(i++, tour.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Error while setting order status as free", e);
		}

		try (PreparedStatement ps = connection.prepareStatement(CHANGE_TOUR_STATUS)) {
			ps.setInt(1, tour.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Error while setting tour status as canceled", e);
		}
	}
}
