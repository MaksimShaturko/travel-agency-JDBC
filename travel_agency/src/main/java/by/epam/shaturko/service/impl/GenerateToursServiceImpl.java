package by.epam.shaturko.service.impl;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import by.epam.shaturko.bean.tour.Categories;
import by.epam.shaturko.bean.tour.Hotel;
import by.epam.shaturko.bean.tour.Tour;
import by.epam.shaturko.bean.tour.TourStatus;
import by.epam.shaturko.bean.tour.TypeOfFood;
import by.epam.shaturko.bean.tour.TypeOfPlacement;
import by.epam.shaturko.bean.tour.TypeOfRoom;
import by.epam.shaturko.dao.DAOProvider;
import by.epam.shaturko.dao.FillingDBDAO;
import by.epam.shaturko.dao.GenerateToursDAO;
import by.epam.shaturko.dao.TransactionalDAO;
import by.epam.shaturko.dao.exception.DAOException;
import by.epam.shaturko.dao.exception.TransactionException;
import by.epam.shaturko.service.GenerateToursService;
import by.epam.shaturko.service.exception.ServiceException;

public class GenerateToursServiceImpl implements GenerateToursService {
	private final double START_TEMP_PRICE = 300.0;
	private final int NUMBER_OF_GENERATING_TOURS = 3000;
	private final DAOProvider PROVIDER = DAOProvider.getInstance();
	private final TransactionalDAO TRANSACTION = PROVIDER.getTransactionalDAO();
	private final GenerateToursDAO GENERATE_TOURS_DAO = PROVIDER.getGenerateToursDAO();

	@Override
	public void generateTours() throws ServiceException {
		Map<Integer, Double> mapStarsMultiplier = fillingStarsMultiplier();
		Map<Integer, Double> mapPeopleMultiplier = fillingPeopleMultiplier();
		Map<Hotel, List<Integer>> mapHotelCategories = getMapHotelCategoriesId();
		List<Tour> tourList = new ArrayList<>();

		for (int i = 0; i < NUMBER_OF_GENERATING_TOURS; i++) {
			Random random = new Random();
			int startDateFromToday = random.nextInt(180);
			LocalDate dateOfDeparture = LocalDate.now();
			dateOfDeparture = dateOfDeparture.plusDays(startDateFromToday);
			int duration = random.nextInt(24) + 7;
			LocalDate dateOfReturn = dateOfDeparture.plusDays(duration);
			TypeOfPlacement placement = TypeOfPlacement.values()[random.nextInt(TypeOfPlacement.values().length)];
			double multiplierPeople = mapPeopleMultiplier.get(placement.ordinal() + 1);
			TypeOfFood food = TypeOfFood.values()[random.nextInt(TypeOfFood.values().length)];
			TypeOfRoom room = TypeOfRoom.values()[random.nextInt(TypeOfRoom.values().length)];
			List<Hotel> hotelList = new ArrayList<>(mapHotelCategories.keySet());
			Hotel hotel = hotelList.get(random.nextInt(hotelList.size()));
			List<Integer> categoriesIdList = mapHotelCategories.get(hotel);
			Categories category = Categories.values()[categoriesIdList.get(random.nextInt(categoriesIdList.size())) - 1];
			double multiplierStars = mapStarsMultiplier.get(hotel.getStars());
			double priceStart = START_TEMP_PRICE * (duration / 7) * multiplierPeople * multiplierStars
					* (random.nextDouble() * (1.4 - 0.8) + 0.8);
			if (duration > 12 && duration < 20) {
				priceStart = priceStart * 0.8;
			}
			if (duration >= 20) {
				priceStart = priceStart * 0.7;
			}
			if (priceStart < START_TEMP_PRICE) {
				priceStart = START_TEMP_PRICE;
			}
			priceStart = Math.round(priceStart);
				tourList.add(new Tour(priceStart, dateOfDeparture, dateOfReturn, TourStatus.FREE, food, placement, room,
						hotel, category));
			
		}
		try {
			ThreadLocal<Connection> threadLocal = TRANSACTION.startTransaction();
			GENERATE_TOURS_DAO.saveGeneratedToursToDB(tourList, threadLocal);
			TRANSACTION.commitTransaction();
		} catch (TransactionException e) {
			throw new ServiceException(e);
		} catch (DAOException e) {
			try {
				TRANSACTION.rollbackTransaction();
			} catch (TransactionException e1) {
				throw new ServiceException(e1);
			}
			throw new ServiceException(e);
		}
	}

	private Map<Integer, Double> fillingStarsMultiplier() {
		Map<Integer, Double> mapStarsMultiplier = new HashMap<>();
		mapStarsMultiplier.put(2, 1.0);
		mapStarsMultiplier.put(3, 1.3);
		mapStarsMultiplier.put(4, 1.7);
		mapStarsMultiplier.put(5, 2.0);
		return mapStarsMultiplier;
	}

	private Map<Integer, Double> fillingPeopleMultiplier() {
		Map<Integer, Double> mapPeopleMultiplier = new HashMap<>();
		mapPeopleMultiplier.put(1, 1.0);
		mapPeopleMultiplier.put(2, 1.5);
		mapPeopleMultiplier.put(3, 2.0);
		mapPeopleMultiplier.put(4, 3.0);
		return mapPeopleMultiplier;
	}

	private Map<Hotel, List<Integer>> getMapHotelCategoriesId() throws ServiceException {
		ThreadLocal<Connection> threadLocal;
		try {
			threadLocal = TRANSACTION.startTransaction();
			Map<Hotel, List<Integer>> mapHotelCategories = GENERATE_TOURS_DAO.getMapHotelCategoryId(threadLocal);
			TRANSACTION.commitTransaction();
			return mapHotelCategories;
		} catch (TransactionException e) {
			throw new ServiceException(e);
		} catch (DAOException e) {
			try {
				TRANSACTION.rollbackTransaction();
			} catch (TransactionException e1) {
				throw new ServiceException(e1);
			}
			throw new ServiceException(e);
		}
	}
}
