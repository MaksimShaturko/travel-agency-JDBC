package by.epam.shaturko.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.shaturko.controller.Controller;
import by.epam.shaturko.dao.AuthorizationDAO;
import by.epam.shaturko.dao.ColumnName;
import by.epam.shaturko.dao.exception.DAOException;
import by.epam.shaturko.entity.user.Details;
import by.epam.shaturko.entity.user.User;
import by.epam.shaturko.entity.user.UserRole;

public class AuthorizationDAOImpl implements AuthorizationDAO {
	private static final Logger logger = LogManager.getLogger(AuthorizationDAOImpl.class);
	private final String SELECT_USER = "SELECT * from travel_agency.users users JOIN travel_agency.user_roles roles ON (users.user_role_id = roles.role_id)"
			+ "	JOIN travel_agency.user_details details ON (users.user_details_id = details.details_id) where users.login=? AND users.password=?";

	@Override
	public Optional<User> logIn(String login, String password, ThreadLocal<Connection> threadLocal)
			throws DAOException {
		Connection connection = threadLocal.get();
		try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER)) {
			preparedStatement.setString(1, login);
			preparedStatement.setString(2, password);
			ResultSet resultSet = preparedStatement.executeQuery();
			Optional<User> userOptional = Optional.empty();

			if (resultSet.next()) {
				int userId = resultSet.getInt(ColumnName.USERS_USER_ID);
				String userStatus = resultSet.getString(ColumnName.USERS_USER_STATUS);
				int id = resultSet.getInt(ColumnName.USER_DETAILS_DETAILS_ID);
				String name = resultSet.getString(ColumnName.USER_DETAILS_NAME);
				String surname = resultSet.getString(ColumnName.USER_DETAILS_SURNAME);
				String phoneNumber = resultSet.getString(ColumnName.USER_DETAILS_PHONE_NUMBER);
				String email = resultSet.getString(ColumnName.USERS_EMAIL);
				int numberOfVisitedTours = resultSet.getInt(ColumnName.USER_DETAILS_NUMBER_OF_VISITED_TOURS);
				int loyalityDiscount = resultSet.getInt(ColumnName.USER_DETAILS_LOYALITY_DISCOUNT);
				int agencyAdditionalDiscount = resultSet.getInt(ColumnName.USER_DETAILS_AGENCY_ADDITIONAL_DISCOUNT);
				String imageAvatar = resultSet.getString(ColumnName.USER_DETAILS_IMAGE_AVATAR_PATH);
				Details details = new Details(id, name, surname, phoneNumber, numberOfVisitedTours,
						loyalityDiscount, agencyAdditionalDiscount, imageAvatar);
				UserRole userRole = UserRole.valueOf(resultSet.getString(ColumnName.USER_ROLES_ROLE_NAME));
				User user = new User(userId, email, login, password, userStatus, details, userRole);
				userOptional = Optional.of(user);
			}			
			return userOptional;		
			
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
}
