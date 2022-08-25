package by.epam.shaturko.service.impl;

import java.sql.Connection;

import by.epam.shaturko.dao.DAOProvider;
import by.epam.shaturko.dao.OrderTourDAO;
import by.epam.shaturko.dao.TransactionalDAO;
import by.epam.shaturko.dao.exception.DAOException;
import by.epam.shaturko.dao.exception.TransactionException;
import by.epam.shaturko.entity.tour.Tour;
import by.epam.shaturko.entity.user.User;
import by.epam.shaturko.service.ServiceOrderTour;
import by.epam.shaturko.service.exception.ServiceException;

public class ServiceOrderTourImpl implements ServiceOrderTour{
	private final DAOProvider PROVIDER = DAOProvider.getInstance();
	private final TransactionalDAO TRANSACTION = PROVIDER.getTransactionalDAO();
	private final OrderTourDAO ORDER_TOUR_DAO = PROVIDER.getOrderTourDAO();

	@Override
	public void orderTour(User user, Tour tour) throws ServiceException {
		try {
		ThreadLocal<Connection> threadLocal = TRANSACTION.startTransaction();
		ORDER_TOUR_DAO.orderTour(user, tour, threadLocal);
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
