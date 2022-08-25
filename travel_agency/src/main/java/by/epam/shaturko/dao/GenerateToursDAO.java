package by.epam.shaturko.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import by.epam.shaturko.bean.tour.Hotel;
import by.epam.shaturko.bean.tour.Tour;
import by.epam.shaturko.dao.exception.DAOException;

public interface GenerateToursDAO {
	
	void saveGeneratedToursToDB(List<Tour> toursList, ThreadLocal<Connection> threadLocal) throws DAOException;
	
	Map<Hotel, List<Integer>> getMapHotelCategoryId(ThreadLocal<Connection> threadLocal) throws DAOException;

}
