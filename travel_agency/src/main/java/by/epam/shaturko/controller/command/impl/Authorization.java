package by.epam.shaturko.controller.command.impl;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.shaturko.bean.user.User;
import by.epam.shaturko.bean.user.UserRole;
import by.epam.shaturko.controller.Constant;
import by.epam.shaturko.controller.RequestParameter;
import by.epam.shaturko.controller.SessionAttribute;
import by.epam.shaturko.controller.command.Command;
import by.epam.shaturko.service.ServiceAuthorization;
import by.epam.shaturko.service.ServiceProvider;
import by.epam.shaturko.service.exception.ServiceException;

public class Authorization implements Command {
	private static final Logger logger = LogManager.getLogger();
	private final static Authorization INSTANCE = new Authorization();
	private final static ServiceProvider SERVICE_PROVIDER = ServiceProvider.getInstance();

	private Authorization() {
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		ServiceAuthorization serviceAuthorization = SERVICE_PROVIDER.getServiceAuthorization();
		HttpSession session = request.getSession(true);
		String requestUrl = (String) session.getAttribute(SessionAttribute.REQUEST_URL);
		String login = request.getParameter(RequestParameter.LOGIN);
		String password = request.getParameter(RequestParameter.PASSWORD);
		try {
			Optional<User> optionalUser = serviceAuthorization.logIn(login, password);
			if (optionalUser.isPresent() && !optionalUser.isEmpty()) {
				User user = optionalUser.get();
				UserRole userRole = user.getRole();
				String role = userRole.toString();
				session.setAttribute(SessionAttribute.AUTHORIZED, true);
				session.setAttribute(SessionAttribute.ROLE, role);
				session.setAttribute(SessionAttribute.USER, user);
				logger.log(Level.INFO, "Authorized successfully");
			} else {
				requestUrl = requestUrl + Constant.MESSAGE_REWRITE_DATA;
			}
		} catch (ServiceException e) {
			logger.log(Level.ERROR, "Error while trying to log in", e);
			session.setAttribute(SessionAttribute.ERROR_TYPE, Constant.ERROR_500);
			session.setAttribute(SessionAttribute.ERROR_MESSAGE, e);
			requestUrl = Constant.ERROR_COMMAND;
		}
		response.sendRedirect(requestUrl);
	}

	public static Authorization getInstance() {
		return INSTANCE;
	}
}
