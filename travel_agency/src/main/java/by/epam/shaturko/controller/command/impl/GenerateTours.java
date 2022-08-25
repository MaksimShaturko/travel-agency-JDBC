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
import by.epam.shaturko.controller.SessionAttribute;
import by.epam.shaturko.controller.command.Command;
import by.epam.shaturko.service.GenerateToursService;
import by.epam.shaturko.service.ServiceProvider;
import by.epam.shaturko.service.exception.ServiceException;

public class GenerateTours implements Command {
	private final static GenerateTours INSTANCE = new GenerateTours();
	private final static Logger logger = LogManager.getLogger();

	private GenerateTours() {
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		ServiceProvider serviceProvider = ServiceProvider.getInstance();
		GenerateToursService generateToursService = serviceProvider.getGenerateToursService();
		HttpSession session = request.getSession(true);
		String requestUrl = (String) session.getAttribute(SessionAttribute.REQUEST_URL);
		try {
			generateToursService.generateTours();
		} catch (ServiceException e) {
			logger.log(Level.ERROR, "Error while generating tours", e);
			session.setAttribute(SessionAttribute.ERROR_TYPE, Constant.ERROR_500);
			session.setAttribute(SessionAttribute.ERROR_MESSAGE, e);
			requestUrl = Constant.ERROR_COMMAND;
		}
		response.sendRedirect(requestUrl);
	}

	public static GenerateTours getInstance() {
		return INSTANCE;
	}
}
