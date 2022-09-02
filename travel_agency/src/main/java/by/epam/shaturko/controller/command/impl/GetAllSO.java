package by.epam.shaturko.controller.command.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.shaturko.controller.Constant;
import by.epam.shaturko.controller.SessionAttribute;
import by.epam.shaturko.controller.command.Command;
import by.epam.shaturko.entity.tour.SpecialOffer;
import by.epam.shaturko.entity.user.User;
import by.epam.shaturko.service.ServiceCreateSpecialOffer;
import by.epam.shaturko.service.ServiceGettingData;
import by.epam.shaturko.service.ServiceProvider;
import by.epam.shaturko.service.exception.ServiceException;

public class GetAllSO implements Command {
	private final static GetAllSO INSTANCE = new GetAllSO();
	private final static Logger logger = LogManager.getLogger();
	private final static ServiceProvider PROVIDER = ServiceProvider.getInstance();
	private final static ServiceCreateSpecialOffer SERVICE_SO = PROVIDER.getServiceCreateSpecialOffer();

	private GetAllSO() {
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(true);
		String requestUrl = Constant.URL_TO_ALL_SO_PAGE;
		try {
			List<SpecialOffer> offersList = SERVICE_SO.getAllSO();
			session.setAttribute(SessionAttribute.ALL_SO, offersList);
		} catch (ServiceException e) {
			logger.log(Level.ERROR, "Error while getting all SO", e);
			session.setAttribute(SessionAttribute.ERROR_TYPE, Constant.ERROR_500);
			session.setAttribute(SessionAttribute.ERROR_MESSAGE, e);
			requestUrl = Constant.ERROR_COMMAND;
		}
		response.sendRedirect(requestUrl);
	}

	public static GetAllSO getInstance() {
		return INSTANCE;
	}
}
