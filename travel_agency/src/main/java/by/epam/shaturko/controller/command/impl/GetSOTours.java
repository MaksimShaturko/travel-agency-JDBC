package by.epam.shaturko.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.shaturko.controller.Constant;
import by.epam.shaturko.controller.SessionAttribute;
import by.epam.shaturko.controller.command.Command;
import by.epam.shaturko.entity.user.User;
import by.epam.shaturko.service.ServiceGettingData;
import by.epam.shaturko.service.ServiceGettingSOTours;
import by.epam.shaturko.service.ServiceProvider;

public class GetSOTours implements Command {
	private final static GetSOTours INSTANCE = new GetSOTours();

	private GetSOTours() {
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(true);
		String requestUrl = Constant.URL_TO_TOURS_PAGE;
		ServiceProvider provider = ServiceProvider.getInstance();
		ServiceGettingSOTours serviceGettingSOTours = provider.getServiceGettingSOTours();

	}
	
	public static GetSOTours getInstance() {
		return INSTANCE;
	}
}
