package by.epam.shaturko.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.shaturko.controller.Constant;
import by.epam.shaturko.controller.RequestParameter;
import by.epam.shaturko.controller.SessionAttribute;
import by.epam.shaturko.controller.command.Command;
import by.epam.shaturko.service.ServiceChangingData;
import by.epam.shaturko.service.ServiceProvider;
import by.epam.shaturko.service.exception.ServiceException;

public class ChangeAgencyDiscount implements Command {
	private final static ChangeAgencyDiscount INSTANCE = new ChangeAgencyDiscount();
	private static final Logger logger = LogManager.getLogger();

	private ChangeAgencyDiscount() {
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		int newDiscount = Integer.parseInt(request.getParameter(RequestParameter.EXTRA));
		int detailsId = Integer.parseInt(request.getParameter(RequestParameter.DETAILS_ID));
		String requestUrl = Constant.URL_GET_ALL_USERS;
		HttpSession session = request.getSession();
		try {
			ServiceProvider provider = ServiceProvider.getInstance();
			ServiceChangingData changingData = provider.getServiceChangingData();
			changingData.changeAgencyDiscount(newDiscount, detailsId);			
		} catch (ServiceException e) {
			logger.log(Level.ERROR, "Error while trying to log in", e);
			session.setAttribute(SessionAttribute.ERROR_TYPE, Constant.ERROR_500);
			session.setAttribute(SessionAttribute.ERROR_MESSAGE, e);
			requestUrl = Constant.ERROR_COMMAND;
		}
		response.sendRedirect(requestUrl);
	}

	public static ChangeAgencyDiscount getInstance() {
		return INSTANCE;
	}

}
