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
import by.epam.shaturko.entity.tour.Tour;
import by.epam.shaturko.entity.user.User;
import by.epam.shaturko.service.ServiceGettingData;
import by.epam.shaturko.service.ServiceProvider;
import by.epam.shaturko.service.exception.ServiceException;

public class GetVisitedTours implements Command{
	private final static GetVisitedTours INSTANCE = new GetVisitedTours();
	private final static Logger logger = LogManager.getLogger();
	private final static String VISITED = "visited";
	
	private GetVisitedTours() {}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute(SessionAttribute.USER);
		String requestUrl = Constant.URL_TO_TOURS_PAGE;
		ServiceProvider provider = ServiceProvider.getInstance();
		ServiceGettingData serviceGettingData = provider.getServiceGettingData();
		try {
			List<Tour> toursList = serviceGettingData.getListOfVisitedTours(user);
			session.setAttribute(SessionAttribute.TOURS_LIST, toursList);
			session.setAttribute(SessionAttribute.TOURS_REQUEST, VISITED);
		} catch (ServiceException e) {
			logger.log(Level.ERROR, "Error while getting visited tours from DB", e);
			session.setAttribute(SessionAttribute.ERROR_TYPE, Constant.ERROR_500);
			session.setAttribute(SessionAttribute.ERROR_MESSAGE, e);
			requestUrl = Constant.ERROR_COMMAND;
		}		
		response.sendRedirect(requestUrl);		
	}
	
	public static GetVisitedTours getInstance() {
		return INSTANCE;
	}
}
