package by.epam.shaturko.dao;

import java.sql.Connection;

import by.epam.shaturko.dao.exception.TransactionException;

public interface TransactionalDAO {
	
	ThreadLocal<Connection> startTransaction() throws TransactionException;
	
	void commitTransaction() throws TransactionException;
	
	void rollbackTransaction() throws TransactionException;

}
