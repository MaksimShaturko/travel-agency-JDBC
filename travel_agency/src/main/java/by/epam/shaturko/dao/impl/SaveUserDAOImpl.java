package by.epam.shaturko.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.shaturko.bean.user.Details;
import by.epam.shaturko.bean.user.User;
import by.epam.shaturko.bean.user.UserRole;
import by.epam.shaturko.dao.SaveUserDAO;
import by.epam.shaturko.dao.exception.DAOException;

/**
 * The SaveUserDAOImpl class represents SaveUserDAO implementation.
 *
 * @author Maksim Shaturko
 * @version 1.0
 */
public class SaveUserDAOImpl implements SaveUserDAO {
	private static final Logger logger = LogManager.getLogger(AuthorizationDAOImpl.class);
	private final String SAVE_DETAILS = "INSERT INTO travel_agency.user_details (name, surname, phone_number, "
			+ "number_of_visited_tours, loyality_discount, agency_additional_discount, image_avatar_path) VALUES (?,?,?,?,?,?,?)";
	private final String GET_DETAILS_ID = "SELECT LAST_INSERT_ID()";
	private final String LAST_ID_COLUMN = "LAST_INSERT_ID()";
	private final String SAVE_USER = "INSERT INTO travel_agency.users (email, login, password, user_status, user_role_id, user_details_id) VALUES (?,?,?,?,?,?)";
	private final String ACTIVE_STATUS = "active";
	private final String SELECT_USER_BY_LOGIN = "SELECT * from travel_agency.users where login=?";
	private final String SELECT_USER_BY_EMAIL = "SELECT * from travel_agency.users where email=?";

	@Override
	public boolean saveUser(User user, Details details, ThreadLocal<Connection> threadLocal) throws DAOException {
		Connection connection = threadLocal.get();
		if (saveDetails(connection, details)) {
			int detailsId = getLastDetailsId(connection);
			try(PreparedStatement ps = connection.prepareStatement(SAVE_USER)) {
				ps.setString(1, user.getEmail());
				ps.setString(2, user.getLogin());
				ps.setString(3, user.getPassword());
				ps.setString(4, ACTIVE_STATUS);
				ps.setInt(5, user.getRole().getRoleId());
				ps.setInt(6, detailsId);
				return ps.executeUpdate() > 0;				
			} catch (SQLException e) {
				throw new DAOException("Error while saving the user", e);
			}
		} else {
			return false;
		}
	}

	@Override
	public boolean checkLogin(String login, ThreadLocal<Connection> threadLocal) throws DAOException {
		Connection connection = threadLocal.get();
		try (PreparedStatement ps = connection.prepareStatement(SELECT_USER_BY_LOGIN)) {
			ps.setString(1, login);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return false;
			} else {
				return true;
			}
		} catch (SQLException e) {
			throw new DAOException("Error when checking whether the login is available", e);			
		}
	}

	@Override
	public boolean checkEmail(String email, ThreadLocal<Connection> threadLocal) throws DAOException {
		Connection connection = threadLocal.get();
		try (PreparedStatement ps = connection.prepareStatement(SELECT_USER_BY_EMAIL)) {
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return false;
			} else {
				return true;
			}
		} catch (SQLException e) {
			throw new DAOException("Error when checking whether the email is available", e);			
		}
	}
	
	private boolean saveDetails(Connection connection, Details details) throws DAOException {
		try(PreparedStatement ps = connection.prepareStatement(SAVE_DETAILS)) {
			ps.setString(1, details.getName());
			ps.setString(2, details.getSurname());
			ps.setString(3, details.getPhoneNumber());
			ps.setInt(4, details.getNumberOfVisitedTours());
			ps.setInt(5, details.getNumberOfVisitedTours());
			ps.setInt(6, details.getAgencyAdditionalDiscount());
			ps.setString(7, details.getImageAvatar());			
			return ps.executeUpdate() > 0;			
		} catch (SQLException e) {
			throw new DAOException("Error while saving the details", e);
		}		
	}
	
	private int getLastDetailsId(Connection connection) throws DAOException {
		try(PreparedStatement ps = connection.prepareStatement(GET_DETAILS_ID)) {
			ResultSet rs = ps.executeQuery();
			rs.next();			
			return rs.getInt(LAST_ID_COLUMN);		
		} catch (SQLException e) {
			throw new DAOException("Error when trying to get last added details id", e);
		}	
	}
}
