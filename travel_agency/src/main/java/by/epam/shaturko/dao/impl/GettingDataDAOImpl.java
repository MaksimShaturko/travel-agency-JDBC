package by.epam.shaturko.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import by.epam.shaturko.controller.RequestParameter;
import by.epam.shaturko.dao.ColumnName;
import by.epam.shaturko.dao.GettingDataDAO;
import by.epam.shaturko.dao.exception.DAOException;
import by.epam.shaturko.entity.Message;
import by.epam.shaturko.entity.tour.Categories;
import by.epam.shaturko.entity.tour.City;
import by.epam.shaturko.entity.tour.Country;
import by.epam.shaturko.entity.tour.Hotel;
import by.epam.shaturko.entity.tour.SpecialOffer;
import by.epam.shaturko.entity.tour.Tour;
import by.epam.shaturko.entity.tour.TourOrder;
import by.epam.shaturko.entity.tour.TourStatus;
import by.epam.shaturko.entity.tour.TypeOfFood;
import by.epam.shaturko.entity.tour.TypeOfPlacement;
import by.epam.shaturko.entity.tour.TypeOfRoom;
import by.epam.shaturko.entity.user.Details;
import by.epam.shaturko.entity.user.User;
import by.epam.shaturko.entity.user.UserRole;

public class GettingDataDAOImpl implements GettingDataDAO {
	private final static String SELECT_COUNTRY_NAMES = "SELECT name from travel_agency.countries";
	private final static String GET_CITY_BY_ID = "SELECT * from travel_agency.cities where city_id=?";
	private final static String GET_ALL_USERS = "SELECT * from travel_agency.users us JOIN travel_agency.user_details det "
			+ "ON(us.user_details_id = det.details_id)";
	private final static String GET_USERS_WITH_MESSAGES = "SELECT * from travel_agency.users us JOIN travel_agency.user_details det "
			+ "ON(us.user_details_id = det.details_id) where us.user_id IN ";
	private final static String GET_ADMINS = "SELECT * FROM travel_agency.users where user_role_id = 1";
	private final static String GET_SPECIAL_OFFERS = "SELECT * from travel_agency.special_offers";
	private final static String GET_CHOSEN_TOUR_PART1 = "SELECT tour_id, price_start, date_of_departure, date_of_return, status, type_of_food, "
			+ "type_of_placement, type_of_room, category_id, hotel_id, special_offers_id FROM travel_agency.tours "
			+ "where ? BETWEEN DATE_ADD(date_of_departure, INTERVAL -3 DAY) "
			+ "AND DATE_ADD(date_of_departure, INTERVAL 3 DAY) "
			+ "AND date_of_return BETWEEN DATE_ADD(?, INTERVAL ? DAY) " + "AND DATE_ADD(?, INTERVAL ? DAY) "
			+ "AND type_of_placement ";
	private final static String GET_CHOSEN_TOUR_PART2 = " AND type_of_room IN ";
	private final static String GET_CHOSEN_TOUR_PART3 = " AND type_of_food IN ";
	private final static String GET_CHOSEN_TOUR_PART4 = " AND hotel_id IN ";
	private final static String GET_CHOSEN_TOUR_PART5 = " AND status = 'free' AND category_id = ?";
	private final static String GET_ORDERED_TOURS = "SELECT * FROM travel_agency.tours where tour_id IN ";
	private final static String GET_COUNTRY_BY_NAME = "SELECT * from travel_agency.countries where name = ?";
	private final static String GET_COUNTRY_BY_ID = "SELECT * from travel_agency.countries where country_id = ?";
	private final static String GET_HOTEL_BY_ID = "SELECT hotel_id, name, description, image_path, stars, city_id from travel_agency.hotels where hotel_id=?";
	private final static String GET_HOTEL_BY_CITY_IDS = "SELECT hotel_id, name, description, image_path, stars, city_id from travel_agency.hotels where city_id IN ";
	private final static String EMPTY_STRING = "";
	private final static String GET_TOUR_ID_FOR_CLIENT = "SELECT tour_id FROM travel_agency.tour_orders where user_id=? AND order_status='ACTIVE'";
	private final static String GET_TOUR_ID_FOR_ADMIN = "SELECT tour_id FROM travel_agency.tour_orders where order_status='ACTIVE'";
	private final static String GET_CONFIRMATION_DATE_OF_ORDER = "SELECT confirmation_date from travel_agency.tour_orders where user_id=? AND tour_id=?";
	private final static String GET_MESSAGES_PART1 = "SELECT * from travel_agency.messages where user_id IN ";
	private final static String GET_MESSAGES_PART2 = " AND tour_id=? AND message_time > ?";
	private final static String GET_MESSAGES_BY_TOUR_ID = "SELECT * from travel_agency.messages where tour_id=?";
	private final static String CLIENT = "CLIENT";
	private final static String ADMIN = "ADMIN";
	private final static String GET_ACTIVE_ORDERS = "SELECT * FROM travel_agency.tour_orders WHERE tour_id=? AND order_status='ACTIVE'";
	private final static String GET_ORDERS = "SELECT * FROM travel_agency.tour_orders WHERE tour_id=? AND user_id=?";
	private final static String GET_USER_BY_ID = "SELECT * FROM travel_agency.users us JOIN travel_agency.user_details det "
			+ "ON(us.user_details_id = det.details_id) WHERE us.user_id=?";
	private final static String GET_VISITED_TOURS_BY_USER_ID = "SELECT * FROM travel_agency.tours where tour_id IN "
			+ "(SELECT tour_id from travel_agency.visited_tours where user_id=?)";

	@Override
	public List<String> getListOfCountriesNames(ThreadLocal<Connection> threadLocal) throws DAOException {
		List<String> listOfCountriesNames = new ArrayList<>();
		Connection connection = threadLocal.get();
		try (Statement statement = connection.createStatement()) {
			ResultSet rs = statement.executeQuery(SELECT_COUNTRY_NAMES);
			while (rs.next()) {
				listOfCountriesNames.add(rs.getString(ColumnName.COUNTRIES_NAME));
			}
		} catch (SQLException e) {
			throw new DAOException("Error while getting list of countries names", e);
		}
		return listOfCountriesNames;
	}

	@Override
	public List<Tour> getListOfChosenTours(ThreadLocal<Connection> threadLocal, Map<String, Object> requestParameters,
			User user) throws DAOException {
		Connection connection = threadLocal.get();
		Country country = getCountry(connection, (String) requestParameters.get(RequestParameter.COUNTRY_NAME));
		List<City> citiesList = getCities(connection, country);
		List<Hotel> hotelsList = getHotels(connection, citiesList);
		String roomForTourQuery = getRoomForTourQuery((String[]) requestParameters.get(RequestParameter.ROOM_TYPE));
		String foodForTourQuery = getFoodForTourQuery((String[]) requestParameters.get(RequestParameter.FOOD));
		String typeOfPlacement = getTypeOfPlacementsForTourQuery(
				(String) requestParameters.get(RequestParameter.TYPE_OF_PLACEMENT));
		Map<Integer, Hotel> mapIdHotel = getMapIdHotel(hotelsList);
		String hotelIdsForQuery = getIdsForQuery(mapIdHotel.keySet());
		
		try (PreparedStatement ps = connection.prepareStatement(GET_CHOSEN_TOUR_PART1 + typeOfPlacement
				+ GET_CHOSEN_TOUR_PART2 + roomForTourQuery + GET_CHOSEN_TOUR_PART3 + foodForTourQuery
				+ GET_CHOSEN_TOUR_PART4 + hotelIdsForQuery + GET_CHOSEN_TOUR_PART5)) {
			LocalDate date = LocalDate.parse((String) requestParameters.get(RequestParameter.DEPARTURE_DATE));
			Date dateDeparture = Date.valueOf(date);

			int i = 1;
			ps.setDate(i++, dateDeparture);
			ps.setDate(i++, dateDeparture);
			Integer from = Integer.valueOf((String) requestParameters.get(RequestParameter.DURATION_FROM));
			Integer to = Integer.valueOf((String) requestParameters.get(RequestParameter.DURATION_TO));
			ps.setInt(i++, from);
			ps.setDate(i++, dateDeparture);
			ps.setInt(i++, to);
			if (typeOfPlacement.equals("IN (?,?,?,?)")) {
				for (TypeOfPlacement placement : TypeOfPlacement.values()) {
					ps.setString(i++, placement.toString());
				}
			}
			String[] typeOfRoom = (String[]) requestParameters.get(RequestParameter.ROOM_TYPE);
			if (typeOfRoom != null) {
				for (String roomStr : typeOfRoom) {
					ps.setString(i++, roomStr);
				}
			} else {
				for (TypeOfRoom roomType : TypeOfRoom.values()) {
					ps.setString(i++, roomType.toString());
				}
			}
			String[] food = (String[]) requestParameters.get(RequestParameter.FOOD);
			if (food != null) {
				for (String foodStr : food) {
					ps.setString(i++, foodStr);
				}
			} else {
				for (TypeOfFood foodType : TypeOfFood.values()) {
					ps.setString(i++, foodType.toString());
				}
			}
			for (Hotel hotel : hotelsList) {
				ps.setInt(i++, hotel.getId());
			}
			int categoryId = Categories.valueOf((String) requestParameters.get(RequestParameter.CATEGORY)).ordinal()
					+ 1;
			ps.setInt(i++, categoryId);

			ResultSet rs = ps.executeQuery();
			Categories[] categories = Categories.values();
			Map<Integer, SpecialOffer> mapIdSpecial = getMapIdSpecialOffer(connection);
			List<Tour> toursList = new ArrayList<>();

			while (rs.next()) {
				SpecialOffer specialOffer = mapIdSpecial.get(rs.getInt(ColumnName.TOURS_SPECIAL_OFFERS_ID));
				double priceStart = rs.getDouble(ColumnName.TOURS_PRICE_START);
				double discounts = getDiscounts(specialOffer, user);
				double realPriceWithinDiscounts = Math.round(priceStart * (100 - discounts) / 100);
				if (realPriceWithinDiscounts >= Double
						.parseDouble((String) requestParameters.get(RequestParameter.PRICE_FROM))
						&& realPriceWithinDiscounts <= Double
								.parseDouble((String) requestParameters.get(RequestParameter.PRICE_TO))) {
					Tour tour = getTour(connection, rs, priceStart, realPriceWithinDiscounts, categories, specialOffer);
					toursList.add(tour);
				}
			}
			return toursList;
		} catch (SQLException e) {
			throw new DAOException("Error while getting the chosen tours", e);
		}
	}

	@Override
	public List<Hotel> getListOfHotels(ThreadLocal<Connection> threadLocal, String[] citiesNames, List<City> citiesList)
			throws DAOException {
		Connection connection = threadLocal.get();
		List<City> listOfChosenCities = new ArrayList<>();
		if (citiesNames != null) {
			for (String name : citiesNames) {
				for (City city : citiesList) {
					if (city.getName().equals(name)) {
						listOfChosenCities.add(city);
						break;
					}
				}
			}
		}
		return getHotels(connection, listOfChosenCities);
	}

	@Override
	public List<City> getListOfCities(ThreadLocal<Connection> threadLocal, String[] countriesNames)
			throws DAOException {
		Connection connection = threadLocal.get();
		List<City> cities = new ArrayList<>();
		if (countriesNames != null) {
			for (String countryName : countriesNames) {
				Country country = getCountry(connection, countryName);
				cities.addAll(getCities(connection, country));
			}
		}
		return cities;
	}

	@Override
	public List<Country> getListOfCountries(ThreadLocal<Connection> threadLocal, String[] countriesNames)
			throws DAOException {
		List<Country> listOfCountries = new ArrayList<>();
		Connection con = threadLocal.get();
		for (String countryName : countriesNames) {
			listOfCountries.add(getCountry(con, countryName));
		}
		return listOfCountries;
	}

	@Override
	public List<User> getAllUsers(ThreadLocal<Connection> threadLocal) throws DAOException {
		Connection connection = threadLocal.get();
		try (PreparedStatement ps = connection.prepareStatement(GET_ALL_USERS)) {
			List<User> listOfUsers = new ArrayList<>();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int userId = rs.getInt(ColumnName.USERS_USER_ID);
				String login = rs.getString(ColumnName.USERS_LOGIN);
				String email = rs.getString(ColumnName.USERS_EMAIL);
				String password = rs.getString(ColumnName.USERS_PASSWORD);
				String status = rs.getString(ColumnName.USERS_USER_STATUS);
				UserRole role = UserRole.values()[(Integer.parseInt(rs.getString(ColumnName.USERS_USER_ROLE_ID)))];

				int detailsId = rs.getInt(ColumnName.USER_DETAILS_DETAILS_ID);
				String name = rs.getString(ColumnName.USER_DETAILS_NAME);
				String surname = rs.getString(ColumnName.USER_DETAILS_SURNAME);
				String phoneNumber = rs.getString(ColumnName.USER_DETAILS_PHONE_NUMBER);
				int numberOfVisitedTours = rs.getInt(ColumnName.USER_DETAILS_NUMBER_OF_VISITED_TOURS);
				int loyalityDiscount = rs.getInt(ColumnName.USER_DETAILS_LOYALITY_DISCOUNT);
				int agencyAdditionalDiscount = rs.getInt(ColumnName.USER_DETAILS_AGENCY_ADDITIONAL_DISCOUNT);

				Details details = new Details(detailsId, name, surname, phoneNumber, numberOfVisitedTours,
						loyalityDiscount, agencyAdditionalDiscount, EMPTY_STRING);
				details.setId(rs.getInt(ColumnName.USER_DETAILS_DETAILS_ID));
				User user = new User(userId, email, login, password, status, details, role);
				listOfUsers.add(user);
			}
			return listOfUsers;
		} catch (SQLException e) {
			throw new DAOException("Error while getting all users", e);
		}
	}

	@Override
	public List<Tour> getListOfOrderedTours(ThreadLocal<Connection> threadLocal, User user) throws DAOException {
		Connection connection = threadLocal.get();
		List<Tour> listOfTours = new ArrayList<>();
		List<Integer> listOfOrderIds = getListOfOrderIds(connection, user);
		Map<Integer, TourOrder> mapIdOrders = new HashMap<>();
		for (Integer id : listOfOrderIds) {
			Tour tour = new Tour();
			tour.setId(id);
			mapIdOrders.put(id, getOrderByTourId(threadLocal, tour).get());
		}

		if (!listOfOrderIds.isEmpty()) {
			String tourIdsForQuery = getIdsForQuery(listOfOrderIds);
			Map<Integer, SpecialOffer> mapIdSpecial = getMapIdSpecialOffer(connection);
			try (PreparedStatement ps = connection.prepareStatement(GET_ORDERED_TOURS + tourIdsForQuery);) {
				int i = 1;
				for (Integer tourId : listOfOrderIds) {
					ps.setInt(i++, tourId);
				}
				ResultSet rs = ps.executeQuery();
				Categories[] categories = Categories.values();
				while (rs.next()) {
					SpecialOffer specialOffer = mapIdSpecial.get(rs.getInt(ColumnName.TOURS_SPECIAL_OFFERS_ID));
					double priceStart = rs.getDouble(ColumnName.TOURS_PRICE_START);
					double discounts = 0;
					if (user.getRole().toString().equals(CLIENT)) {
						discounts = getDiscounts(specialOffer, user);
					} 
					if (user.getRole().toString().equals(ADMIN)) {
						discounts = getDiscounts(specialOffer, mapIdOrders.get(rs.getInt(ColumnName.TOURS_TOUR_ID)).getUser());
					}
					double realPriceWithinDiscounts = Math.round(priceStart * (100 - discounts) / 100);
					Tour tour = getTour(connection, rs, priceStart, realPriceWithinDiscounts, categories, specialOffer);
					listOfTours.add(tour);
				}
			} catch (SQLException e) {
				throw new DAOException("Error while getting a tour", e);
			}
		}
		return listOfTours;
	}

	@Override
	public List<Message> getMessages(ThreadLocal<Connection> threadLocal, User user, Tour tour) throws DAOException {
		Connection connection = threadLocal.get();
		LocalDateTime confirmationDate = getOrderConfirmationDate(connection, user, tour);
		List<Message> messages = new ArrayList<>();

		if (user.getRole().toString().equals(CLIENT)) {
			List<User> admins = getAdmins(connection);
			List<Integer> ids = admins.stream().map(User::getId).collect(Collectors.toList());
			String adminIdsForQuery = getIdsForQuery(ids);
			adminIdsForQuery = adminIdsForQuery.substring(0, adminIdsForQuery.length() - 1).concat(",?)");
			try (PreparedStatement ps = connection
					.prepareStatement(GET_MESSAGES_PART1 + adminIdsForQuery + GET_MESSAGES_PART2)) {
				int i = 1;
				for (User admin : admins) {
					ps.setInt(i++, admin.getId());
				}
				ps.setInt(i++, user.getId());
				ps.setInt(i++, tour.getId());
				ps.setObject(i++, confirmationDate);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					if (user.getId() == rs.getInt(ColumnName.MESSAGES_USER_ID)) {
						messages.add(new Message(rs.getInt(ColumnName.MESSAGES_MESSAGE_ID), user, tour,
								rs.getString(ColumnName.MESSAGES_MESSAGE),
								(LocalDateTime) rs.getObject(ColumnName.MESSAGES_MESSAGE_TIME)));
					} else {
						for (User admin : admins) {
							if (admin.getId() == rs.getInt(ColumnName.MESSAGES_USER_ID)) {
								messages.add(new Message(rs.getInt(ColumnName.MESSAGES_MESSAGE_ID), admin, tour,
										rs.getString(ColumnName.MESSAGES_MESSAGE),
										(LocalDateTime) rs.getObject(ColumnName.MESSAGES_MESSAGE_TIME)));
								break;
							}
						}
					}
				}

			} catch (SQLException e) {
				throw new DAOException("Error while getting messages by client", e);
			}
		}
		if (user.getRole().toString().equals(ADMIN)) {
			List<Integer> userIds = new ArrayList<>();
			Message message = null;
			int userId = 0;
			try (PreparedStatement ps = connection.prepareStatement(GET_MESSAGES_BY_TOUR_ID)) {
				ps.setInt(1, tour.getId());
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					message = new Message(rs.getInt(ColumnName.MESSAGES_MESSAGE_ID),
							new User(rs.getInt(ColumnName.MESSAGES_USER_ID)), tour,
							rs.getString(ColumnName.MESSAGES_MESSAGE),
							(LocalDateTime) rs.getObject(ColumnName.MESSAGES_MESSAGE_TIME));
					messages.add(message);
					if (userId != rs.getInt(ColumnName.MESSAGES_USER_ID)) {
						userId = rs.getInt(ColumnName.MESSAGES_USER_ID);
						userIds.add(userId);
					}
				}
			} catch (SQLException e) {
				throw new DAOException("Error while getting messages by tour id", e);
			}
			String userIdsForQuery = getIdsForQuery(userIds);
			try (PreparedStatement ps = connection.prepareStatement(GET_USERS_WITH_MESSAGES + userIdsForQuery)) {
				int i = 1;
				for (int id : userIds) {
					ps.setInt(i++, id);
				}
				ResultSet rs = ps.executeQuery();
				List<User> users = new ArrayList<>();
				while (rs.next()) {
					User userMes = new User(rs.getInt(ColumnName.USERS_USER_ID), rs.getString(ColumnName.USERS_EMAIL),
							rs.getString(ColumnName.USERS_LOGIN), rs.getString(ColumnName.USERS_PASSWORD),
							rs.getString(ColumnName.USERS_USER_STATUS),
							new Details(rs.getInt(ColumnName.USER_DETAILS_DETAILS_ID),
									rs.getString(ColumnName.USER_DETAILS_NAME),
									rs.getString(ColumnName.USER_DETAILS_SURNAME),
									rs.getString(ColumnName.USER_DETAILS_PHONE_NUMBER),
									rs.getInt(ColumnName.USER_DETAILS_NUMBER_OF_VISITED_TOURS),
									rs.getInt(ColumnName.USER_DETAILS_LOYALITY_DISCOUNT),
									rs.getInt(ColumnName.USER_DETAILS_AGENCY_ADDITIONAL_DISCOUNT),
									rs.getString(ColumnName.USER_DETAILS_IMAGE_AVATAR_PATH)),
							UserRole.values()[rs.getInt(ColumnName.USERS_USER_ROLE_ID) - 1]);
					users.add(userMes);
				}
				for (Message mes : messages) {
					int mesUsId = mes.getUser().getId();
					for (User userM : users) {
						if (mesUsId == userM.getId()) {
							mes.setUser(userM);
							break;
						}
					}
				}
			} catch (SQLException e) {
				throw new DAOException("Error while users have written messages", e);
			}
		}
		return messages;
	}

	@Override
	public Optional<TourOrder> getOrderByTourId(ThreadLocal<Connection> threadLocal, Tour tour) throws DAOException {
		Connection connection = threadLocal.get();
		try (PreparedStatement ps = connection.prepareStatement(GET_ACTIVE_ORDERS)) {
			ps.setInt(1, tour.getId());
			ResultSet rs = ps.executeQuery();
			Optional<TourOrder> order = Optional.empty();
			if (rs.next()) {
				int orderId = rs.getInt(ColumnName.TOUR_ORDERS_ORDER_ID);
				int userId = rs.getInt(ColumnName.TOUR_ORDERS_USER_ID);
				LocalDateTime confirmationDate = (LocalDateTime) rs.getObject(ColumnName.TOUR_ORDERS_CONFIRMATION_DATE);
				String orderStatus = rs.getString(ColumnName.TOUR_ORDERS_ORDER_STATUS);
				User userOrderedTour = getUserById(connection, userId);
				order = Optional.of(new TourOrder(orderId, userOrderedTour, tour, confirmationDate, orderStatus));
			}
			return order;
		} catch (SQLException e) {
			throw new DAOException("Error while getting active orders", e);
		}
	}

	@Override
	public Optional<TourOrder> getOrderByTourIdUserId(ThreadLocal<Connection> threadLocal, Tour tour, User user)
			throws DAOException {
		Connection con = threadLocal.get();
		try (PreparedStatement ps = con.prepareStatement(GET_ORDERS)) {
			ps.setInt(1, tour.getId());
			ps.setInt(2, user.getId());
			ResultSet rs = ps.executeQuery();
			Optional<TourOrder> order = Optional.empty();
			if (rs.next()) {
				do {
					int orderId = rs.getInt(ColumnName.TOUR_ORDERS_ORDER_ID);
					LocalDateTime confirmationDate = (LocalDateTime) rs
							.getObject(ColumnName.TOUR_ORDERS_CONFIRMATION_DATE);
					String orderStatus = rs.getString(ColumnName.TOUR_ORDERS_ORDER_STATUS);
					order = Optional.of(new TourOrder(orderId, user, tour, confirmationDate, orderStatus));
				} while (rs.next());

			}
			return order;
		} catch (SQLException e) {
			throw new DAOException("Error while getting orders", e);
		}
	}

	@Override
	public List<Tour> getListOfVisitedTours(ThreadLocal<Connection> threadLocal, User user) throws DAOException {
		Connection connection = threadLocal.get();
		List<Tour> listOfVisitedTours = new ArrayList<>();
		try (PreparedStatement ps = connection.prepareStatement(GET_VISITED_TOURS_BY_USER_ID)) {
			ps.setInt(1, user.getId());
			ResultSet rs = ps.executeQuery();
			Categories[] categories = Categories.values();
			while (rs.next()) {
				double priceStart = rs.getDouble(ColumnName.TOURS_PRICE_START);
				Tour tour = getTour(connection, rs, priceStart, priceStart, categories, null);
				listOfVisitedTours.add(tour);
			}
			return listOfVisitedTours;
		} catch (SQLException e) {
			throw new DAOException("Error while getting visited tours", e);
		}
	}

	private Country getCountry(Connection connection, int id) throws DAOException {
		try (PreparedStatement ps = connection.prepareStatement(GET_COUNTRY_BY_ID)) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			Country country = new Country(rs.getInt(ColumnName.COUNTRIES_COUNTRY_ID),
					rs.getString(ColumnName.COUNTRIES_NAME), rs.getString(ColumnName.COUNTRIES_DESCRIPTION),
					rs.getString(ColumnName.COUNTRIES_FLAG_IMAGE_PATH));
			return country;
		} catch (SQLException e) {
			throw new DAOException("Error while getting a country", e);
		}
	}

	private Tour getTour(Connection connection, ResultSet rs, double priceStart, double realPrice,
			Categories[] categories, SpecialOffer specialOffer) throws DAOException {
		try {
			int tourId = rs.getInt(ColumnName.TOURS_TOUR_ID);
			LocalDate tourDateOfDeparture = rs.getDate(ColumnName.TOURS_DATE_OF_DEPARTURE).toLocalDate();
			LocalDate dateOfReturn = rs.getDate(ColumnName.TOURS_DATE_OF_RETURN).toLocalDate();
			TourStatus status = TourStatus.valueOf(rs.getString(ColumnName.TOURS_STATUS).toUpperCase());
			TypeOfFood tourFood = TypeOfFood.valueOf(rs.getString(ColumnName.TOURS_TYPE_OF_FOOD));
			TypeOfPlacement placement = TypeOfPlacement.valueOf(rs.getString(ColumnName.TOURS_TYPE_OF_PLACEMENT));
			TypeOfRoom room = TypeOfRoom.valueOf(rs.getString(ColumnName.TOURS_TYPE_OF_ROOM));
			Hotel hotel = getHotelById(rs.getInt(ColumnName.TOURS_HOTEL_ID), connection);
			Categories tourCategory = categories[rs.getInt(ColumnName.TOURS_CATEGORY_ID) - 1];
			Tour tour = new Tour(tourId, priceStart, tourDateOfDeparture, dateOfReturn, status, tourFood, placement,
					room, hotel, tourCategory, specialOffer);
			tour.setRealPrice(realPrice);
			return tour;
		} catch (SQLException e) {
			throw new DAOException("Error while getting a tour", e);
		}
	}

	private double getDiscounts(SpecialOffer specialOffer, User user) {
		double discounts = 0;
		if (specialOffer == null) {
			discounts = user.getDetails().getAgencyAdditionalDiscount() + user.getDetails().getLoyalityDiscount();
		} else {
			discounts = user.getDetails().getAgencyAdditionalDiscount() + user.getDetails().getLoyalityDiscount()
					+ specialOffer.getDiscount();
		}
		return discounts;
	}

	private Map<Integer, SpecialOffer> getMapIdSpecialOffer(Connection connection) throws DAOException {
		Map<Integer, SpecialOffer> mapIdSpecial = new HashMap<>();
		try (PreparedStatement ps = connection.prepareStatement(GET_SPECIAL_OFFERS)) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				mapIdSpecial.put(rs.getInt(ColumnName.SPECIAL_OFFERS_SPECIAL_OFFER_ID),
						new SpecialOffer(rs.getInt(ColumnName.SPECIAL_OFFERS_SPECIAL_OFFER_ID),
								rs.getString(ColumnName.SPECIAL_OFFERS_TITLE),
								rs.getString(ColumnName.SPECIAL_OFFERS_DESCRIPTION),
								rs.getInt(ColumnName.SPECIAL_OFFERS_DISCOUNT)));
			}
			return mapIdSpecial;
		} catch (SQLException e) {
			throw new DAOException("Error while getting Special Offers", e);
		}
	}

	private String getFoodForTourQuery(String[] food) {
		StringBuilder foodIn = new StringBuilder("(");
		if (food != null) {
			for (String foodStr : food) {
				foodIn.append("?,");
			}
			foodIn.deleteCharAt(foodIn.length() - 1).append(")");
		} else {
			foodIn.append("?,?,?,?,?,?)");
		}
		return foodIn.toString();
	}

	private String getRoomForTourQuery(String[] typeOfRoom) {
		StringBuilder roomForQuery = new StringBuilder("(");
		if (typeOfRoom != null) {
			for (String typeStr : typeOfRoom) {
				roomForQuery.append("?,");
			}
			roomForQuery.deleteCharAt(roomForQuery.length() - 1).append(")");
		} else {
			roomForQuery.append("?,?,?,?)");
		}
		return roomForQuery.toString();
	}

	private String getTypeOfPlacementsForTourQuery(String typeOfPlacement) {
		if (typeOfPlacement == null) {
			typeOfPlacement = "IN (?,?,?,?)";
		} else {
			typeOfPlacement = "= '" + typeOfPlacement + "'";
		}
		return typeOfPlacement;
	}

	private Map<Integer, Hotel> getMapIdHotel(List<Hotel> hotelsList) {
		Map<Integer, Hotel> mapIdHotel = new HashMap<>();
		for (Hotel hotel : hotelsList) {
			mapIdHotel.put(hotel.getId(), hotel);
		}
		return mapIdHotel;
	}

	private Country getCountry(Connection connection, String countryName) throws DAOException {
		try (PreparedStatement ps = connection.prepareStatement(GET_COUNTRY_BY_NAME)) {
			ps.setString(1, countryName);
			ResultSet rs = ps.executeQuery();
			rs.next();
			Country country = new Country(rs.getInt(ColumnName.COUNTRIES_COUNTRY_ID),
					rs.getString(ColumnName.COUNTRIES_NAME), rs.getString(ColumnName.COUNTRIES_DESCRIPTION),
					rs.getString(ColumnName.COUNTRIES_FLAG_IMAGE_PATH));

			return country;
		} catch (SQLException e) {
			throw new DAOException("Error while getting a country", e);
		}
	}

	private List<City> getCities(Connection connection, Country country) throws DAOException {
		List<City> citiesList = new ArrayList<>();
		try (PreparedStatement ps = connection.prepareStatement(
				"SELECT city_id, name, description, image_path from travel_agency.cities where country_id IN (?)")) {
			ps.setInt(1, country.getId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				citiesList.add(new City(rs.getInt(ColumnName.CITY_ID), rs.getString(ColumnName.CITY_NAME),
						rs.getString(ColumnName.CITY_DESCRIPTION), rs.getString(ColumnName.CITY_IMAGE_PATH), country));
			}
			return citiesList;
		} catch (SQLException e) {
			throw new DAOException("Error while getting cities by country_id", e);
		}
	}

	private List<Hotel> getHotels(Connection connection, List<City> citiesList) throws DAOException {
		Map<Integer, City> mapIdCity = new HashMap<>();
		for (City city : citiesList) {
			mapIdCity.put(city.getId(), city);
		}
		String cityIdsForQuery = getIdsForQuery(mapIdCity.keySet());
		try (PreparedStatement ps = connection.prepareStatement(GET_HOTEL_BY_CITY_IDS + cityIdsForQuery)) {
			int i = 1;
			for (Integer cityId : mapIdCity.keySet()) {
				ps.setInt(i++, cityId);
			}
			ResultSet rs = ps.executeQuery();
			List<Hotel> hotelsList = new ArrayList<>();
			while (rs.next()) {
				hotelsList.add(new Hotel(rs.getInt(ColumnName.HOTELS_HOTEL_ID), rs.getString(ColumnName.HOTELS_NAME),
						rs.getString(ColumnName.HOTELS_DESCRIPTION), rs.getString(ColumnName.HOTELS_IMAGE_PATH),
						rs.getInt(ColumnName.HOTELS_STARS), mapIdCity.get(rs.getInt(ColumnName.HOTELS_CITY_ID))));
			}
			return hotelsList;
		} catch (SQLException e) {
			throw new DAOException("Error while getting a hotel", e);
		}
	}

	private String getIdsForQuery(Collection<Integer> collection) {
		StringBuilder ids = new StringBuilder("(");
		if (!collection.isEmpty()) {
			for (Integer id : collection) {
				ids.append("?,");
			}
			ids.deleteCharAt(ids.length() - 1).append(")");
		} else {
			ids.append("0)");
		}
		return ids.toString();
	}

	private List<Integer> getListOfOrderIds(Connection connection, User user) throws DAOException {
		List<Integer> listOfOrderIds = new ArrayList<>();
		try {
			PreparedStatement ps = null;
			if (user.getRole().toString() == CLIENT) {
				ps = connection.prepareStatement(GET_TOUR_ID_FOR_CLIENT);
				ps.setInt(1, user.getId());
			}
			if (user.getRole().toString() == ADMIN) {
				ps = connection.prepareStatement(GET_TOUR_ID_FOR_ADMIN);
			}
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Integer id = rs.getInt(ColumnName.TOUR_ORDERS_TOUR_ID);
				listOfOrderIds.add(id);
			}
			if (ps != null) {
				ps.close();
			}
		} catch (SQLException e) {
			throw new DAOException("Error while getting orders id", e);
		}
		return listOfOrderIds;
	}

	private Hotel getHotelById(int hotelId, Connection connection) throws DAOException {
		try (PreparedStatement ps = connection.prepareStatement(GET_HOTEL_BY_ID)) {
			ps.setInt(1, hotelId);
			ResultSet rs = ps.executeQuery();
			rs.next();
			City city = getCityById(connection, rs.getInt(ColumnName.HOTELS_CITY_ID));
			Hotel hotel = new Hotel(hotelId, rs.getString(ColumnName.HOTELS_NAME),
					rs.getString(ColumnName.HOTELS_DESCRIPTION), rs.getString(ColumnName.HOTELS_IMAGE_PATH),
					rs.getInt(ColumnName.HOTELS_STARS), city);
			return hotel;
		} catch (SQLException e) {
			throw new DAOException("Error while getting hotel by id", e);
		}
	}

	private City getCityById(Connection connection, int id) throws DAOException {
		try (PreparedStatement ps = connection.prepareStatement(GET_CITY_BY_ID)) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			String name = rs.getString(ColumnName.CITY_NAME);
			String description = rs.getString(ColumnName.CITY_DESCRIPTION);
			String imagePath = rs.getString(ColumnName.CITY_IMAGE_PATH);
			Country country = getCountry(connection, rs.getInt(ColumnName.CITY_COUNTRY_ID));
			City city = new City(id, name, description, imagePath, country);
			return city;
		} catch (SQLException e) {
			throw new DAOException("Error while getting city by id", e);
		}
	}

	private LocalDateTime getOrderConfirmationDate(Connection connection, User user, Tour tour) throws DAOException {
		LocalDateTime confirmationDate = LocalDateTime.of(2000, Month.JANUARY, 1, 00, 00);
		try (PreparedStatement ps = connection.prepareStatement(GET_CONFIRMATION_DATE_OF_ORDER)) {
			int i = 1;
			ps.setInt(i++, user.getId());
			ps.setInt(i++, tour.getId());
			ResultSet rs = ps.executeQuery();
			LocalDateTime orderDateTemp = LocalDateTime.of(2000, Month.JANUARY, 1, 00, 00);
			while (rs.next()) {
				orderDateTemp = (LocalDateTime) rs.getObject(1);
				if (orderDateTemp.isAfter(confirmationDate)) {
					confirmationDate = orderDateTemp;
				}
			}
			return confirmationDate;
		} catch (SQLException e) {
			throw new DAOException("Error while getting a confirmation date of an order", e);
		}
	}

	private List<User> getAdmins(Connection connection) throws DAOException {
		List<User> admins = new ArrayList<>();
		try (PreparedStatement ps = connection.prepareStatement(GET_ADMINS)) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				admins.add(new User(rs.getInt(ColumnName.USERS_USER_ID), rs.getString(ColumnName.USERS_EMAIL),
						rs.getString(ColumnName.USERS_LOGIN), rs.getString(ColumnName.USERS_PASSWORD),
						rs.getString(ColumnName.USERS_USER_STATUS), null, UserRole.ADMIN));
			}
			return admins;
		} catch (SQLException e) {
			throw new DAOException("Error while getting all admins", e);
		}
	}

	private User getUserById(Connection con, int userId) throws DAOException {
		User user = null;

		try (PreparedStatement ps = con.prepareStatement(GET_USER_BY_ID)) {
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			rs.next();
			String email = rs.getString(ColumnName.USERS_EMAIL);
			String login = rs.getString(ColumnName.USERS_LOGIN);
			String password = rs.getString(ColumnName.USERS_PASSWORD);
			String name = rs.getString(ColumnName.USER_DETAILS_NAME);
			String userStatus = rs.getString(ColumnName.USERS_USER_STATUS);
			String surname = rs.getString(ColumnName.USER_DETAILS_SURNAME);
			String phoneNumber = rs.getString(ColumnName.USER_DETAILS_PHONE_NUMBER);
			int numberOfVisitedTours = rs.getInt(ColumnName.USER_DETAILS_NUMBER_OF_VISITED_TOURS);
			int loyalityDiscount = rs.getInt(ColumnName.USER_DETAILS_LOYALITY_DISCOUNT);
			int agencyAdditionalDiscount = rs.getInt(ColumnName.USER_DETAILS_AGENCY_ADDITIONAL_DISCOUNT);
			Details details = new Details(name, surname, phoneNumber, numberOfVisitedTours, loyalityDiscount,
					agencyAdditionalDiscount, EMPTY_STRING);
			details.setId(rs.getInt(ColumnName.USER_DETAILS_DETAILS_ID));
			user = new User(userId, email, login, password, userStatus, details,
					UserRole.values()[rs.getInt(ColumnName.USERS_USER_ROLE_ID)]);
			return user;
		} catch (SQLException e) {
			throw new DAOException("Error while getting a user by id", e);
		}
	}
}
