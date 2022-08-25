package by.epam.shaturko.controller;

import java.io.IOException;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.shaturko.controller.command.Command;
import by.epam.shaturko.controller.command.CommandProvider;

/**
 * The MainController class represents main controller.
 *
 * @author Maksim Shaturko
 * @version 1.0
 */
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 83167873723639000L;
	private final CommandProvider commandProvider = CommandProvider.getInstance();

	public Controller() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		process(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		process(request, response);
	}

	private void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String commandName = request.getParameter(RequestParameter.COMMAND_NAME);
		Optional<Command> commandOptional = commandProvider.getCommand(commandName);
		Command command = commandOptional.orElseThrow(IllegalArgumentException::new);
		command.execute(request, response);
	}
}
