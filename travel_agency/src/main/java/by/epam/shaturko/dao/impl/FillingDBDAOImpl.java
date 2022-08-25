package by.epam.shaturko.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import by.epam.shaturko.dao.FillingDBDAO;
import by.epam.shaturko.dao.exception.DAOException;
import by.epam.shaturko.entity.tour.City;
import by.epam.shaturko.entity.tour.Country;
import by.epam.shaturko.entity.tour.Hotel;
import by.epam.shaturko.entity.user.UserRole;

public class FillingDBDAOImpl implements FillingDBDAO {
	private final static String FILL_IN_CATEGORIES = "INSERT into travel_agency.categories (name) VALUES (?)";
	private final static String FILL_IN_COUNTRIES = "INSERT INTO travel_agency.countries (name, description, flag_image_path) VALUES (?,?,?)";
	private final static String GET_COUNTRY_ID_BY_NAME = "SELECT country_id from travel_agency.countries where name=?";
	private final static String FILL_IN_CITIES = "INSERT INTO travel_agency.cities (name, description, image_path, country_id) VALUES (?,?,?,?)";
	private final static String GET_CITY_ID_BY_NAME = "SELECT city_id from travel_agency.cities where name=?";
	private final static String FILL_IN_HOTELS = "INSERT INTO travel_agency.hotels (name, description, image_path, stars, city_id) VALUES (?,?,?,?,?)";
	private final static String FILL_IN_CITIES_HAS_CATEGORIES = "INSERT INTO travel_agency.cities_has_categories (city_id, category_id) VALUES (?,?)";

	@Override
	public void fillInCategories(List<String> listOfCategories, ThreadLocal<Connection> threadLocal) throws DAOException {
		Connection connection = threadLocal.get();
		try(PreparedStatement ps = connection.prepareStatement(FILL_IN_CATEGORIES)) {
			for (String category : listOfCategories) {
				ps.setString(1, category);
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			throw new DAOException("Error while filling categories in the DB", e);
		}
	}

	@Override
	public void fillInCountries(List<Country> countryList, ThreadLocal<Connection> threadLocal) throws DAOException {
		Connection connection = threadLocal.get();
		try(PreparedStatement ps = connection.prepareStatement(
				FILL_IN_COUNTRIES)) {
			for (Country country : countryList) {		
				ps.setString(1, country.getName());
				ps.setString(2, country.getDescription());
				ps.setString(3, country.getImage());
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			throw new DAOException("Error while filling countries in the DB", e);
		}
	}

	@Override
	public void fillInCities(List<City> cityList, ThreadLocal<Connection> threadLocal) throws DAOException {
		Connection connection = threadLocal.get();
		try {
			for (City city : cityList) {
				PreparedStatement ps = connection.prepareStatement(GET_COUNTRY_ID_BY_NAME);
				ps.setString(1, city.getCountry().getName());
				ResultSet rs = ps.executeQuery();
				rs.next();
				int country_id = rs.getInt(1);
				ps.close();

				ps = connection.prepareStatement(FILL_IN_CITIES);
				String name = city.getName();
				String description = city.getDescription();
				String image = city.getImage();
				int i = 1;
				ps.setString(i++, name);
				ps.setString(i++, description);
				ps.setString(i++, image);
				ps.setInt(i++, country_id);
				ps.executeUpdate();
				ps.close();
			}
		} catch (SQLException e) {
			throw new DAOException("Error while filling cities in the DB", e);
		}
	}

	@Override
	public void fillInHotels(List<Hotel> hotelList, ThreadLocal<Connection> threadLocal) throws DAOException {
		Connection connection = threadLocal.get();
		try {
			for (Hotel hotel : hotelList) {
				PreparedStatement ps = connection.prepareStatement(GET_CITY_ID_BY_NAME);
				ps.setString(1, hotel.getCity().getName());
				ResultSet rs = ps.executeQuery();
				rs.next();				
				int city_id = rs.getInt(1);
				ps.close();
				
				ps = connection.prepareStatement(FILL_IN_HOTELS);
				String name = hotel.getName();
				String description = hotel.getDescription();
				String image = hotel.getImage();
				int stars = hotel.getStars();
				int i = 1;
				ps.setString(i++, name);
				ps.setString(i++, description);
				ps.setString(i++, image);
				ps.setInt(i++, stars);
				ps.setInt(i++, city_id);
				ps.executeUpdate();
				ps.close();
			}
		} catch (SQLException e) {
			throw new DAOException("Error while filling hotels in the DB", e);
		}
	}

	@Override
	public void fillInCitiesHasCategories(List<Integer> cityCategoryList, ThreadLocal<Connection> threadLocal) throws DAOException {
		Connection connection = threadLocal.get();
		try(PreparedStatement ps = connection.prepareStatement(FILL_IN_CITIES_HAS_CATEGORIES)) {
			int city_id;
			int category_id;
			for (int i = 0; i < cityCategoryList.size() - 1; i = i + 2) {				
				city_id = cityCategoryList.get(i);
				category_id = cityCategoryList.get(i + 1);
				ps.setInt(1, city_id);
				ps.setInt(2, category_id);
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			throw new DAOException("Error while filling cities_has_categories in the DB", e);
		}
	}
}
