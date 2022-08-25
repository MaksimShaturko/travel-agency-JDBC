package by.epam.shaturko.service.impl;

import java.sql.Connection;

import by.epam.shaturko.dao.ChangingDataDAO;
import by.epam.shaturko.dao.DAOProvider;
import by.epam.shaturko.dao.TransactionalDAO;
import by.epam.shaturko.dao.exception.DAOException;
import by.epam.shaturko.dao.exception.TransactionException;
import by.epam.shaturko.entity.tour.Tour;
import by.epam.shaturko.entity.tour.TourOrder;
import by.epam.shaturko.entity.user.User;
import by.epam.shaturko.service.ServiceChangingData;
import by.epam.shaturko.service.exception.ServiceException;

public class ServiceChangingDataImpl implements ServiceChangingData {
	private final DAOProvider PROVIDER = DAOProvider.getInstance();
	private final ChangingDataDAO CHANGING_DATA = PROVIDER.getChangingDataDAO();
	private final TransactionalDAO TRANSACTION = PROVIDER.getTransactionalDAO();

	@Override
	public void changeAgencyDiscount(int newDiscount, int detailsId) throws ServiceException {
		try {
			ThreadLocal<Connection> threadLocal = TRANSACTION.startTransaction();
			CHANGING_DATA.changeAgencyDiscount(newDiscount, detailsId, threadLocal);
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

	@Override
	public void setTourAsVisited(TourOrder order) throws ServiceException {
		try {
			ThreadLocal<Connection> threadLocal = TRANSACTION.startTransaction();
			CHANGING_DATA.setTourAsVisited(order, threadLocal);
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

	@Override
	public void cancelTour(Tour tour, User user) throws ServiceException {
		try {
			ThreadLocal<Connection> threadLocal = TRANSACTION.startTransaction();
			CHANGING_DATA.cancelTour(threadLocal, tour, user);
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
}
