package by.epam.shaturko.controller.command.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.shaturko.controller.Constant;
import by.epam.shaturko.controller.PagePath;
import by.epam.shaturko.controller.RequestParameter;
import by.epam.shaturko.controller.SessionAttribute;
import by.epam.shaturko.controller.command.Command;
import by.epam.shaturko.entity.Message;
import by.epam.shaturko.entity.tour.Tour;
import by.epam.shaturko.entity.tour.TourOrder;
import by.epam.shaturko.entity.user.User;
import by.epam.shaturko.service.ServiceGettingData;
import by.epam.shaturko.service.ServiceProvider;
import by.epam.shaturko.service.exception.ServiceException;

public class ViewTour implements Command {
	private final static ViewTour INSTANCE = new ViewTour();
	private final static Logger logger = LogManager.getLogger();
	private final static String ADMIN = "ADMIN";
	private final static String CLIENT = "CLIENT";

	private ViewTour() {
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		session.setAttribute(SessionAttribute.REQUEST_URL, Constant.URL_TO_VIEW_TOUR);
		List<Tour> listOfTours = (List<Tour>) session.getAttribute(SessionAttribute.TOURS_LIST);
		int tourId;
		Tour tour = null;
		if (request.getParameter(RequestParameter.TOUR_ID) != null) {
			tourId = Integer.parseInt(request.getParameter(RequestParameter.TOUR_ID));
			for (Tour tourVar : listOfTours) {
				if (tourVar.getId() == tourId) {
					tour = tourVar;
					break;
				}
			}
		} else {
			tour = (Tour) session.getAttribute(SessionAttribute.ORDERED_TOUR);
		}
		User user = (User) session.getAttribute(SessionAttribute.USER);
		session.setAttribute(SessionAttribute.ORDERED_TOUR, tour);
		ServiceProvider provider = ServiceProvider.getInstance();
		ServiceGettingData gettingData = provider.getServiceGettingData();
		Optional<TourOrder> order = null;
		try {
			if (user.getRole().toString().equals(ADMIN)) {
				order = gettingData.getOrderByTourId(tour);
			}
			if (user.getRole().toString().equals(CLIENT)) {
				order = gettingData.getOrderByTourIdUserId(tour, user);
			}
			if (!order.isEmpty()) {
				session.setAttribute(SessionAttribute.ORDER, order.get());
				List<Message> messages = gettingData.getMessages(user, tour);
				session.setAttribute(SessionAttribute.MESSAGES, messages);
				request.setAttribute(RequestParameter.FREE_TOUR, false);
			} else {
				request.setAttribute(RequestParameter.FREE_TOUR, true);
			}
		} catch (ServiceException e) {
			logger.log(Level.ERROR, "Error while getting data for a tour.jsp page", e);
			session.setAttribute(SessionAttribute.ERROR_TYPE, Constant.ERROR_500);
			session.setAttribute(SessionAttribute.ERROR_MESSAGE, e);
			response.sendRedirect(Constant.ERROR_COMMAND);
		}
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.TOUR);
		requestDispatcher.forward(request, response);
	}

	public static ViewTour getInstance() {
		return INSTANCE;
	}
}
