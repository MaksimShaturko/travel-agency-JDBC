package by.epam.shaturko.service.impl;

import java.sql.Connection;

import by.epam.shaturko.bean.Message;
import by.epam.shaturko.dao.DAOProvider;
import by.epam.shaturko.dao.SaveMessageDAO;
import by.epam.shaturko.dao.TransactionalDAO;
import by.epam.shaturko.dao.exception.DAOException;
import by.epam.shaturko.dao.exception.TransactionException;
import by.epam.shaturko.service.ServiceWriteMessage;
import by.epam.shaturko.service.exception.ServiceException;

public class ServiceWriteMessageImpl implements ServiceWriteMessage {
	private final DAOProvider PROVIDER = DAOProvider.getInstance();
	private final TransactionalDAO TRANSACTION = PROVIDER.getTransactionalDAO();
	private final SaveMessageDAO SAVE_MESSAGE_DAO = PROVIDER.getSaveMessageDAO();

	@Override
	public void writeMessage(Message message) throws ServiceException {
		try {
			ThreadLocal<Connection> threadLocal = TRANSACTION.startTransaction();
			SAVE_MESSAGE_DAO.saveMessage(message, threadLocal);
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
