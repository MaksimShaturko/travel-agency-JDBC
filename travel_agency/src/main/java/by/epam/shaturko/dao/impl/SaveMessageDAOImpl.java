package by.epam.shaturko.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import by.epam.shaturko.bean.Message;
import by.epam.shaturko.bean.tour.Tour;
import by.epam.shaturko.bean.user.User;
import by.epam.shaturko.dao.SaveMessageDAO;
import by.epam.shaturko.dao.exception.DAOException;

public class SaveMessageDAOImpl implements SaveMessageDAO{
	private final static String CREATE_MESSAGE = "INSERT into travel_agency.messages (tour_id, user_id, message, message_time) VALUES (?,?,?,?)";

	@Override
	public void saveMessage(Message message, ThreadLocal<Connection> threadLocal) throws DAOException {
		Connection con = threadLocal.get();		
		try(PreparedStatement ps = con.prepareStatement(CREATE_MESSAGE)) {
			int i = 1;
			ps.setInt(i++, message.getTour().getId());
			ps.setInt(i++, message.getUser().getId());
			ps.setString(i++, message.getText());
			ps.setObject(i++, message.getDateTime());			
			ps.executeUpdate();			
		} catch (SQLException e) {
			throw new DAOException("Error while saving a mesage to the DB", e);
		}		
	}
}
