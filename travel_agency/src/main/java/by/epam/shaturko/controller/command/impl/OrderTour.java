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

import by.epam.shaturko.bean.tour.Tour;
import by.epam.shaturko.bean.user.User;
import by.epam.shaturko.controller.Constant;
import by.epam.shaturko.controller.RequestParameter;
import by.epam.shaturko.controller.SessionAttribute;
import by.epam.shaturko.controller.command.Command;
import by.epam.shaturko.service.ServiceOrderTour;
import by.epam.shaturko.service.ServiceProvider;
import by.epam.shaturko.service.exception.ServiceException;

public class OrderTour implements Command {
	private final static OrderTour INSTANCE = new OrderTour();
	private final static Logger logger = LogManager.getLogger();
	
	private OrderTour() {
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		List<Tour> listOfTours = (List<Tour>) session.getAttribute(SessionAttribute.TOURS_LIST);
		int tourId = Integer.parseInt(request.getParameter(RequestParameter.TOUR_ID));
		Tour tour = null;
		for (Tour tourVar : listOfTours) {
			if (tourVar.getId() == tourId) {
				tour = tourVar;
				break;
			}
		}
		User user = (User) session.getAttribute(SessionAttribute.USER);
		String requestUrl = (String) session.getAttribute(SessionAttribute.REQUEST_URL);
		ServiceProvider provider = ServiceProvider.getInstance();
		ServiceOrderTour serviceOrderTour = provider.getServiceOrderTour();		
		try {
			serviceOrderTour.orderTour(user, tour);
			requestUrl = Constant.URL_TO_MESSAGE_PAGE;
			session.setAttribute(SessionAttribute.MESSAGE_PAGE_INFO,
					RequestParameter.ORDER_SUCCESS_MESSAGE);
		} catch (ServiceException e) {
			logger.log(Level.ERROR, "Error while getting visited tours from DB", e);
			session.setAttribute(SessionAttribute.ERROR_TYPE, Constant.ERROR_500);
			session.setAttribute(SessionAttribute.ERROR_MESSAGE, e);
			requestUrl = Constant.ERROR_COMMAND;
		}			
		response.sendRedirect(requestUrl);
	}

	public static OrderTour getInstance() {
		return INSTANCE;
	}
}
