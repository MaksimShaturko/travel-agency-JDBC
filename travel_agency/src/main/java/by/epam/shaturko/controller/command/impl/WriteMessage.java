package by.epam.shaturko.controller.command.impl;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.shaturko.bean.Message;
import by.epam.shaturko.bean.tour.Tour;
import by.epam.shaturko.bean.user.User;
import by.epam.shaturko.controller.Constant;
import by.epam.shaturko.controller.RequestParameter;
import by.epam.shaturko.controller.SessionAttribute;
import by.epam.shaturko.controller.command.Command;
import by.epam.shaturko.service.ServiceProvider;
import by.epam.shaturko.service.ServiceWriteMessage;
import by.epam.shaturko.service.exception.ServiceException;

public class WriteMessage implements Command {
	private final static WriteMessage INSTANCE = new WriteMessage();
	private final static Logger logger = LogManager.getLogger();

	private WriteMessage() {
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String messageText = request.getParameter(RequestParameter.MESSAGE_TEXT);
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute(SessionAttribute.USER);
		Tour tour = (Tour) session.getAttribute(SessionAttribute.ORDERED_TOUR);
		String requestUrl = Constant.URL_TO_VIEW_TOUR;
		Message message = new Message(user, tour, messageText, LocalDateTime.now());
		ServiceProvider provider = ServiceProvider.getInstance();
		ServiceWriteMessage writeMessage = provider.getServiceWriteMessage();
		try {
			writeMessage.writeMessage(message);
		} catch (ServiceException e) {
			logger.log(Level.ERROR, "Error while writing a message", e);
			session.setAttribute(SessionAttribute.ERROR_TYPE, Constant.ERROR_500);
			session.setAttribute(SessionAttribute.ERROR_MESSAGE, e);
			requestUrl = Constant.ERROR_COMMAND;
		}		
		response.sendRedirect(requestUrl);
	}

	public static WriteMessage getInstance() {
		return INSTANCE;
	}
}
