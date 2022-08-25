package by.epam.shaturko.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import by.epam.shaturko.bean.tour.Categories;
import by.epam.shaturko.bean.tour.Hotel;
import by.epam.shaturko.bean.tour.Tour;
import by.epam.shaturko.dao.ColumnName;
import by.epam.shaturko.dao.GenerateToursDAO;
import by.epam.shaturko.dao.exception.DAOException;

public class GenerateToursDAOImpl implements GenerateToursDAO {
	private static final String INSERT_TOURS = "INSERT into travel_agency.tours (price_start, date_of_departure, date_of_return, "
			+ "status, type_of_food, type_of_placement, type_of_room, category_id, hotel_id) VALUES(?,?,?,?,?,?,?,?,?)";
	private static final String SELECT_HOTELS = "SELECT hotel_id, stars, city_id from travel_agency.hotels";
	private static final String SELECT_CATEGORIES = "SELECT category_id from travel_agency.cities_has_categories where city_id = ?";

	@Override
	public void saveGeneratedToursToDB(List<Tour> toursList, ThreadLocal<Connection> threadLocal) throws DAOException {
		Connection connection = threadLocal.get();
		try {
			for (Tour tour : toursList) {
				PreparedStatement ps = connection.prepareStatement(INSERT_TOURS);
				int i = 1;
				ps.setDouble(i++, tour.getPriceStart());
				ps.setDate(i++, Date.valueOf(tour.getDateOfDeparture()));
				ps.setDate(i++, Date.valueOf(tour.getDateOfReturn()));
				ps.setString(i++, tour.getStatus().name());
				ps.setString(i++, tour.getFood().name());
				ps.setString(i++, tour.getPlacement().name());
				ps.setString(i++, tour.getRoom().name());
				ps.setDouble(i++, tour.getCategory().ordinal() + 1);
				ps.setDouble(i++, tour.getHotel().getId());
				ps.executeUpdate();
				ps.close();
			}
		} catch (SQLException e) {
			throw new DAOException("Error while getting inserting tours into the db", e);
		}
	}

	@Override
	public Map<Hotel, List<Integer>> getMapHotelCategoryId(ThreadLocal<Connection> threadLocal) throws DAOException {
		Connection connection = threadLocal.get();
		try {
			PreparedStatement ps = connection.prepareStatement(SELECT_HOTELS);
			ResultSet rs = ps.executeQuery();
			List<Integer> categoriesId = new ArrayList<>();
			Map<Hotel, List<Integer>> mapHotelCategories = new HashMap<>();			
			while (rs.next()) {	
				Hotel hotel = new Hotel();
				hotel.setId(rs.getInt(ColumnName.HOTELS_HOTEL_ID));
				hotel.setStars(rs.getInt(ColumnName.HOTELS_STARS));
				int cityId = rs.getInt(ColumnName.HOTELS_CITY_ID);
				PreparedStatement ps2 = connection.prepareStatement(SELECT_CATEGORIES);
				ps2.setInt(1, cityId);
				ResultSet rs2 = ps2.executeQuery();
				while (rs2.next()) {
					categoriesId.add(rs2.getInt(ColumnName.CITIES_HAS_CATEGORIES_CATEGORY_ID));
				}
				mapHotelCategories.put(hotel, categoriesId);
				categoriesId = new ArrayList<>();
				if (ps2 != null) {
					ps2.close();
				}
			}			
			if (ps != null) {
				ps.close();
			}
			return mapHotelCategories;
		} catch (SQLException e) {
			throw new DAOException("Error while getting mapHotelCategories", e);
		}
	}
}
