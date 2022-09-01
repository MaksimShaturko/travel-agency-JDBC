package by.epam.shaturko.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.epam.shaturko.dao.GettingSOToursDAO;
import by.epam.shaturko.dao.exception.DAOException;
import by.epam.shaturko.entity.tour.SpecialOffer;
import by.epam.shaturko.entity.tour.Tour;

public class GettingSOToursDAOImpl implements GettingSOToursDAO {
	private final static String GET_TOURS_BY_SO_ID = "SELECT * FROM travel_agency.tours where special_offers_id = ?";

	@Override
	public List<Tour> getToursBySOId(ThreadLocal<Connection> threadLocal, SpecialOffer id) throws DAOException {
		Connection connection = threadLocal.get();
		List<Tour> tours = new ArrayList<>();
		try (PreparedStatement ps = connection.prepareStatement(GET_TOURS_BY_SO_ID)) {
			ps.setInt(1, id);
			return null;
		} catch (SQLException e) {
			throw new DAOException("Error while getting tours by SO id", e);
		}
	}

	@Override
	public List<Tour> getAllSOTours(ThreadLocal<Connection> threadLocal) {
		// TODO Auto-generated method stub
		return null;
	}

}
