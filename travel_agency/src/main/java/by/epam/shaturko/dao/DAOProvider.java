package by.epam.shaturko.dao;

import by.epam.shaturko.dao.impl.AuthorizationDAOImpl;
import by.epam.shaturko.dao.impl.ChangingDataDAOImpl;
import by.epam.shaturko.dao.impl.CreateSpecialOfferDAOImpl;
import by.epam.shaturko.dao.impl.FillingDBDAOImpl;
import by.epam.shaturko.dao.impl.GenerateToursDAOImpl;
import by.epam.shaturko.dao.impl.GettingDataDAOImpl;
import by.epam.shaturko.dao.impl.GettingSOToursDAOImpl;
import by.epam.shaturko.dao.impl.OrderTourDAOImpl;
import by.epam.shaturko.dao.impl.SaveMessageDAOImpl;
import by.epam.shaturko.dao.impl.SaveUserDAOImpl;
import by.epam.shaturko.dao.impl.TransactionalDAOImpl;

public class DAOProvider {

	private static DAOProvider instance = null;

	private DAOProvider() {

	}

	public static DAOProvider getInstance() {
		if (instance == null) {
			instance = new DAOProvider();
		}
		return instance;
	}

	public SaveUserDAO getSaveUserDAO() {

		SaveUserDAO saveUserDAO;
		saveUserDAO = new SaveUserDAOImpl();

		return saveUserDAO;
	}

	public TransactionalDAO getTransactionalDAO() {

		TransactionalDAO transactionalDAO;
		transactionalDAO = TransactionalDAOImpl.getTransaction();

		return transactionalDAO;
	}

	public FillingDBDAO getFillingDBDAO() {

		FillingDBDAO fillingDB;
		fillingDB = new FillingDBDAOImpl();

		return fillingDB;
	}

	public AuthorizationDAO getAuthorizationDAO() {

		AuthorizationDAO authorizationDAO;
		authorizationDAO = new AuthorizationDAOImpl();

		return authorizationDAO;
	}

	public GenerateToursDAO getGenerateToursDAO() {

		GenerateToursDAO generateToursDAO;
		generateToursDAO = new GenerateToursDAOImpl();

		return generateToursDAO;
	}

	public GettingDataDAO getGettingDataDAO() {

		GettingDataDAO gettingDataDAO;
		gettingDataDAO = new GettingDataDAOImpl();

		return gettingDataDAO;
	}

	public CreateSpecialOfferDAO getCreateSpecialOfferDAO() {

		CreateSpecialOfferDAO createSpecialOfferDAO;
		createSpecialOfferDAO = new CreateSpecialOfferDAOImpl();

		return createSpecialOfferDAO;
	}

	public ChangingDataDAO getChangingDataDAO() {

		ChangingDataDAO changingDataDAO;
		changingDataDAO = new ChangingDataDAOImpl();

		return changingDataDAO;
	}

	public OrderTourDAO getOrderTourDAO() {

		OrderTourDAO orderTourDAO;
		orderTourDAO = new OrderTourDAOImpl();

		return orderTourDAO;
	}

	public SaveMessageDAO getSaveMessageDAO() {

		SaveMessageDAO saveMessageDAO;
		saveMessageDAO = new SaveMessageDAOImpl();

		return saveMessageDAO;
	}
	
	public GettingSOToursDAO getGettingSOToursDAO() {

		GettingSOToursDAO gettingSOToursDAO;
		gettingSOToursDAO = new GettingSOToursDAOImpl();

		return gettingSOToursDAO;
	}


}
