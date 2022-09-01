package by.epam.shaturko.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.shaturko.controller.command.Command;

public class GoToAllSOPage implements Command {
	private final static GoToAllSOPage INSTANCE = new GoToAllSOPage();

	private GoToAllSOPage() {
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		

	}
	
	public static GoToAllSOPage getInstance() {
		return INSTANCE;
	}

}
