package by.epam.shaturko.controller.command;

import java.util.Optional;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The {@code CommandProvider} class represents command provider.
 *
 * @author Maksim Shaturko
 * @version 1.0
 */
public class CommandProvider {
	private static final Logger logger = LogManager.getLogger(CommandProvider.class);

	private static CommandProvider instance = null;

	private CommandProvider() {

	}

	public static CommandProvider getInstance() {
		if (instance == null) {
			instance = new CommandProvider();
		}
		return instance;
	}

	/**
	 * Getting command.
	 *
	 * @param commandName the command name
	 * @return the optional of created command
	 */
	public Optional<Command> getCommand(String commandName) {
		Optional<Command> command;
		if (commandName != null && !commandName.trim().isEmpty()) {
			try {
				CommandName currentCommand = CommandName.valueOf(commandName.toUpperCase());
				command = Optional.of(currentCommand.getCommand());
			} catch (IllegalArgumentException e) {
				logger.log(Level.ERROR, "Incorrect command type", commandName, e);
				command = Optional.empty();
			}
		} else {
			command = Optional.empty();
		}
		return command;

	}

}
