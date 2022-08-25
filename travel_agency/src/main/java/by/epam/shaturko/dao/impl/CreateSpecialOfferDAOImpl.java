package by.epam.shaturko.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.epam.shaturko.dao.ColumnName;
import by.epam.shaturko.dao.CreateSpecialOfferDAO;
import by.epam.shaturko.dao.exception.DAOException;
import by.epam.shaturko.entity.tour.City;
import by.epam.shaturko.entity.tour.Country;
import by.epam.shaturko.entity.tour.Hotel;
import by.epam.shaturko.entity.tour.SpecialOffer;
import by.epam.shaturko.entity.tour.Tour;

public class CreateSpecialOfferDAOImpl implements CreateSpecialOfferDAO {
	private final static String CREATE_SO = "INSERT into travel_agency.special_offers (title, description, discount) values(?,?,?)";
	private final static String SO_ID_BY_TITLE = "SELECT special_offer_id from travel_agency.special_offers where title=?";
	private final static String SET_SO_ID_INTO_TOURS_BY_COUNTRIES_NAMES = "UPDATE travel_agency.tours SET travel_agency.tours.special_offers_id=? "
			+ "where travel_agency.tours.hotel_id IN (SELECT travel_agency.hotels.hotel_id from travel_agency.hotels "
			+ "where travel_agency.hotels.city_id IN(SELECT travel_agency.cities.city_id from travel_agency.cities "
			+ "where travel_agency.cities.country_id IN (SELECT travel_agency.countries.country_id from travel_agency.countries "
			+ "where travel_agency.countries.name IN";
	private final static String SET_SO_ID_INTO_TOURS_BY_CITIES_NAMES = "UPDATE travel_agency.tours SET travel_agency.tours.special_offers_id=? "
			+ "where travel_agency.tours.hotel_id IN (SELECT travel_agency.hotels.hotel_id from travel_agency.hotels "
			+ "where travel_agency.hotels.city_id IN(SELECT travel_agency.cities.city_id from travel_agency.cities "
			+ "where travel_agency.cities.name IN ";
	private final static String SET_SO_ID_INTO_TOURS_BY_HOTELS_NAMES = "UPDATE travel_agency.tours SET travel_agency.tours.special_offers_id=? "
			+ "where travel_agency.tours.hotel_id IN (SELECT travel_agency.hotels.hotel_id from travel_agency.hotels "
			+ "where travel_agency.hotels.name IN ";
	private final static String SET_SO_ID_INTO_TOURS_BY_TOURS_ID = "UPDATE travel_agency.tours SET travel_agency.tours.special_offers_id=? "
			+ "where travel_agency.tours.tour_id IN ";
	private final static String GET_ALL_SO = "SELECT * from travel_agency.special_offers";
	private final static String PARENTHESES_1 = ")";
	private final static String PARENTHESES_2 = "))";
	private final static String PARENTHESES_3 = ")))";

	@Override
	public void createSO(SpecialOffer so, ThreadLocal<Connection> threadLocal) throws DAOException {
		Connection connection = threadLocal.get();
		try (PreparedStatement ps = connection.prepareStatement(CREATE_SO)) {
			int i = 1;
			ps.setString(i++, so.getTitle());
			ps.setString(i++, so.getDescription());
			ps.setInt(i++, so.getDiscount());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Error while inserting special offer into the db", e);
		}
	}

	private int getSOIdByTitle(String soTitle, Connection connection) throws DAOException {
		try (PreparedStatement ps = connection.prepareStatement(SO_ID_BY_TITLE)) {
			ps.setString(1, soTitle);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int soId = rs.getInt(1);
			return soId;
		} catch (SQLException e) {
			throw new DAOException("Error while getting special offer id", e);
		}
	}

	@Override
	public void createSOByCountries(SpecialOffer so, String[] countriesNames, String soTitle, ThreadLocal<Connection> threadLocal)
			throws DAOException {
		Connection connection = threadLocal.get();
		int soID;
		if (so != null) {
			createSO(so, threadLocal);
			soID = getSOIdByTitle(so.getTitle(), connection);
		} else {
			soID = getSOIdByTitle(soTitle, connection);
		}
		String countriesForQuery = getIdsForQuery(List.of(countriesNames));
		try (PreparedStatement ps = connection
				.prepareStatement(SET_SO_ID_INTO_TOURS_BY_COUNTRIES_NAMES + countriesForQuery + PARENTHESES_3)) {
			int i = 1;
			ps.setInt(i++, soID);
			for (String name : countriesNames) {
				ps.setString(i++, name);
			}
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Error while setting special offer id into tours by countries names", e);
		}
	}

	private String getIdsForQuery(Iterable<? extends Object> collection) {
		StringBuilder ids = new StringBuilder("(");
		for (Object id : collection) {
			ids.append("?,");
		}
		ids.deleteCharAt(ids.length() - 1).append(")");
		return ids.toString();
	}

	@Override
	public void createSOByCities(SpecialOffer so, String[] citiesNames, String soTitle, ThreadLocal<Connection> threadLocal)
			throws DAOException {
		Connection connection = threadLocal.get();
		int soID;
		if (so != null) {
			createSO(so, threadLocal);
			soID = getSOIdByTitle(so.getTitle(), connection);
		} else {
			soID = getSOIdByTitle(soTitle, connection);
		}
		String citiesForQuery = getIdsForQuery(List.of(citiesNames));
		String insertIntoTours = SET_SO_ID_INTO_TOURS_BY_CITIES_NAMES + citiesForQuery + PARENTHESES_2;
		try (PreparedStatement ps = connection.prepareStatement(insertIntoTours)) {
			int i = 1;
			ps.setInt(i++, soID);
			for (String name : citiesNames) {
				ps.setString(i++, name);
			}
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Error while setting special offer id into tours by cities names", e);
		}
	}

	@Override
	public void createSOByHotels(SpecialOffer so, String[] hotelsNames, String soTitle, ThreadLocal<Connection> threadLocal)
			throws DAOException {
		Connection connection = threadLocal.get();
		int soID;
		if (so != null) {
			createSO(so, threadLocal);
			soID = getSOIdByTitle(so.getTitle(), connection);
		} else {
			soID = getSOIdByTitle(soTitle, connection);
		}
		String hotelsForQuery = getIdsForQuery(List.of(hotelsNames));		
		String insertIntoTours = SET_SO_ID_INTO_TOURS_BY_HOTELS_NAMES + hotelsForQuery + PARENTHESES_1;
		try (PreparedStatement ps = connection.prepareStatement(insertIntoTours);) {
			int i = 1;
			ps.setInt(i++, soID);
			for (String name : hotelsNames) {
				ps.setString(i++, name);
			}
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Error while setting special offer id into tours by hotels names", e);
		}
	}

	@Override
	public void createSOByTours(SpecialOffer so, List<Tour> tours, String soTitle, ThreadLocal<Connection> threadLocal)
			throws DAOException {
		Connection connection = threadLocal.get();
		int soID;
		if (so != null) {
			createSO(so, threadLocal);
			soID = getSOIdByTitle(so.getTitle(), connection);
		} else {
			soID = getSOIdByTitle(soTitle, connection);
		}
		String toursForQuery = getIdsForQuery(tours);
		String insertIntoTours = SET_SO_ID_INTO_TOURS_BY_TOURS_ID + toursForQuery;
		try (PreparedStatement ps = connection.prepareStatement(insertIntoTours);) {
			int i = 1;
			ps.setInt(i++, soID);
			for (Tour tour : tours) {
				ps.setInt(i++, tour.getId());
			}
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Error while setting special offer id into tours by tours id", e);
		}
	}

	@Override
	public List<SpecialOffer> getAllSO(ThreadLocal<Connection> threadLocal) throws DAOException {
		List<SpecialOffer> offersList = new ArrayList<>();
		Connection connection = threadLocal.get();
		try (PreparedStatement ps = connection.prepareStatement(GET_ALL_SO)) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				SpecialOffer so = new SpecialOffer(rs.getInt(ColumnName.SPECIAL_OFFERS_SPECIAL_OFFER_ID),
						rs.getString(ColumnName.SPECIAL_OFFERS_TITLE),
						rs.getString(ColumnName.SPECIAL_OFFERS_DESCRIPTION),
						rs.getInt(ColumnName.SPECIAL_OFFERS_DISCOUNT));
				offersList.add(so);
			}
			return offersList;
		} catch (SQLException e) {
			throw new DAOException("Error while getting all special offers", e);
		}
	}
}
