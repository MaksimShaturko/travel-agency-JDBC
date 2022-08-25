package by.epam.shaturko.controller.command.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.shaturko.bean.user.Details;
import by.epam.shaturko.bean.user.User;
import by.epam.shaturko.controller.Constant;
import by.epam.shaturko.controller.RequestParameter;
import by.epam.shaturko.controller.SessionAttribute;
import by.epam.shaturko.controller.command.Command;
import by.epam.shaturko.service.RegistrationService;
import by.epam.shaturko.service.ServiceProvider;
import by.epam.shaturko.service.exception.ServiceException;
import by.epam.shaturko.validator.UserValidator;

public class Registration implements Command {
	private static final Logger logger = LogManager.getLogger();
	private final static Registration INSTANCE = new Registration();
	private final static String EMPTY_STRING = "";
	private final static ServiceProvider SERVICE_PROVIDER = ServiceProvider.getInstance();

	private Registration() {
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession(true);
		RegistrationService registrationService = SERVICE_PROVIDER.getRegistrationService();
		String login = null;
		String password = null;
		String repeatPassword = null;
		String email = null;
		String goTo = EMPTY_STRING;
		User user = null;
		Details details = null;
		boolean checkLogin = false;
		boolean checkEmail = false;
		Map<String, String> parameters = null;
		if (session.getAttribute(SessionAttribute.PARAMETERS) == null) {
			parameters = new HashMap<>();
		} else {
			parameters = (Map<String, String>) session.getAttribute(SessionAttribute.PARAMETERS);
		}

		if (session.getAttribute(SessionAttribute.IS_DETAILS) == null) {
			goTo = Constant.URL_TO_REGISTRATION_PAGE;
			login = request.getParameter(RequestParameter.LOGIN);
			password = request.getParameter(RequestParameter.PASSWORD);
			repeatPassword = request.getParameter(RequestParameter.REPEAT_PASSWORD);
			email = request.getParameter(RequestParameter.EMAIL);
			parameters.put(RequestParameter.LOGIN, login);
			parameters.put(RequestParameter.PASSWORD, password);
			parameters.put(RequestParameter.REPEAT_PASSWORD, repeatPassword);
			parameters.put(RequestParameter.EMAIL, email);
			session.setAttribute(SessionAttribute.PARAMETERS, parameters);
			try {
				checkLogin = registrationService.checkLogin(login);
				checkEmail = registrationService.checkEmail(email);
				if (UserValidator.isUserParametersValid(parameters)) {
					if (checkLogin && checkEmail) {
						session.setAttribute(SessionAttribute.IS_DETAILS, true);
					} else {
						if (!checkLogin && !checkEmail) {
							goTo = goTo + Constant.MESSAGE_REWRITE_DATA;
							parameters.put(SessionAttribute.LOGIN, EMPTY_STRING);
							parameters.put(SessionAttribute.EMAIL, EMPTY_STRING);
						} else {
							if (!checkEmail) {
								goTo = goTo + Constant.MESSAGE_REWRITE_EMAIL;
								parameters.put(SessionAttribute.EMAIL, EMPTY_STRING);
							} else {
								goTo = goTo + Constant.MESSAGE_REWRITE_LOGIN;
								parameters.put(SessionAttribute.LOGIN, EMPTY_STRING);
							}
						}
					}
				} else {
					goTo = goTo + parameters.get(Constant.MESSAGE);
				}
			} catch (ServiceException e) {
				logger.log(Level.ERROR, "Error while checking for free email and login", e);
				session.setAttribute(SessionAttribute.ERROR_TYPE, Constant.ERROR_500);
				session.setAttribute(SessionAttribute.ERROR_MESSAGE, e);
				goTo = Constant.ERROR_COMMAND;
			}
		} else {
			goTo = Constant.URL_TO_REGISTRATION_PAGE;
			String name = request.getParameter(RequestParameter.NAME);
			String surname = request.getParameter(RequestParameter.SURNAME);
			String phoneNumber = request.getParameter(RequestParameter.PHONE_NUMBER);
			parameters.put(RequestParameter.NAME, name);
			parameters.put(RequestParameter.SURNAME, surname);
			parameters.put(RequestParameter.PHONE_NUMBER, phoneNumber);
			String pathToImage = EMPTY_STRING;
			try {
				if (UserValidator.isDetailsParametersValid(parameters)) {					
					login = ((Map<String, String>) session.getAttribute(SessionAttribute.PARAMETERS))
							.get(RequestParameter.LOGIN);
					password = ((Map<String, String>) session.getAttribute(SessionAttribute.PARAMETERS))
							.get(RequestParameter.PASSWORD);
					email = ((Map<String, String>) session.getAttribute(SessionAttribute.PARAMETERS))
							.get(RequestParameter.EMAIL);
					details = new Details(name, surname, phoneNumber, pathToImage);
					user = new User(email, login, password);
					if (registrationService.registerUser(user, details)) {
						goTo = Constant.URL_TO_MESSAGE_PAGE;
						session.setAttribute(SessionAttribute.MESSAGE_PAGE_INFO,
								RequestParameter.REGISTRATION_SUCCESS_MESSAGE);
						session.removeAttribute(SessionAttribute.IS_DETAILS);
						session.removeAttribute(SessionAttribute.PARAMETERS);
					} else {
						goTo = goTo + Constant.MESSAGE_WRONG_DATA;
					}
				} else {
					goTo = goTo + parameters.get(Constant.MESSAGE);
				}
			} catch (ServiceException e) {
				logger.log(Level.ERROR, "Error while register a user", e);
				session.setAttribute(SessionAttribute.ERROR_TYPE, Constant.ERROR_500);
				session.setAttribute(SessionAttribute.ERROR_MESSAGE, e);
				goTo = Constant.ERROR_COMMAND;
			}
		}
		response.sendRedirect(goTo);
	}

	public static Registration getInstance() {
		return INSTANCE;
	}
}
