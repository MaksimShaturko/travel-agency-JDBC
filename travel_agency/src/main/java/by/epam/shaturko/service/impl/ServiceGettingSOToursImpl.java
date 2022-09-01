package by.epam.shaturko.service.impl;

import java.sql.Connection;
import java.util.List;

import by.epam.shaturko.dao.DAOProvider;
import by.epam.shaturko.dao.GettingSOToursDAO;
import by.epam.shaturko.dao.TransactionalDAO;
import by.epam.shaturko.dao.exception.DAOException;
import by.epam.shaturko.dao.exception.TransactionException;
import by.epam.shaturko.entity.tour.SpecialOffer;
import by.epam.shaturko.entity.tour.Tour;
import by.epam.shaturko.service.ServiceGettingSOTours;
import by.epam.shaturko.service.exception.ServiceException;

public class ServiceGettingSOToursImpl implements ServiceGettingSOTours{
	private final static DAOProvider PROVIDER = DAOProvider.getInstance();
	private final static TransactionalDAO TRANSACTION = PROVIDER.getTransactionalDAO();
	private final static GettingSOToursDAO GETTING_SO_TOURS_DAO = PROVIDER.getGettingSOToursDAO();

	@Override
	public List<Tour> getToursBySOId(int SOId) throws ServiceException {
		try {
			ThreadLocal<Connection> threadLocal = TRANSACTION.startTransaction();
			List<Tour> tours = GETTING_SO_TOURS_DAO.getToursBySOId(threadLocal, id);
			TRANSACTION.commitTransaction();
			return tours;
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

	@Override
	public List<Tour> getAllSOTours() throws ServiceException {
		try {
			ThreadLocal<Connection> threadLocal = TRANSACTION.startTransaction();
			List<Tour> tours = GETTING_SO_TOURS_DAO.getAllSOTours(threadLocal);
			TRANSACTION.commitTransaction();
			return tours;
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
