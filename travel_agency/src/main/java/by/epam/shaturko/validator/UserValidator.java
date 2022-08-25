package by.epam.shaturko.validator;

import java.util.Map;

import by.epam.shaturko.controller.Constant;
import by.epam.shaturko.controller.RequestParameter;

public class UserValidator {
	private static final String EMPTY_STRING = "";
	private static final String LOGIN_REGEX = "^[a-zA-Z][a-zA-Z0-9-_\\.]{2,25}$";
	private static final String NAME_REGEX = "^[а-яА-ЯёЁa-zA-Z]{2,}$";
	private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9]+\\.[A-Za-z0-9]+$";
	private static final String PASSWORD_REGEX = "^[0-9a-zA-Z]{6,16}$";
	private static final String PHONE_REGEX = "^(\\s*)?(\\+)?([- _():=+]?\\d[- _():=+]?){7,15}(\\s*)?$";
	private static final String TITLE_REGEX = "^.{5,50}$";
	private static final String DESCRIPTION_REGEX = "^.{5,1000}$";
	private static final String DISCOUNT_REGEX = "^[0-9]{1,2}$";

	public static boolean isUserParametersValid(Map<String, String> parameters) {
		boolean isValid = true;
		boolean isLoginValid = true;
		if (!isStringParameterValid(parameters.get(RequestParameter.LOGIN), LOGIN_REGEX)) {
			isValid = false;
			parameters.put(RequestParameter.LOGIN, EMPTY_STRING);
			parameters.put(Constant.MESSAGE, Constant.MESSAGE_LOGIN_INVALID);
			isLoginValid = false;
		}
		if (!isStringParameterValid(parameters.get(RequestParameter.EMAIL), EMAIL_REGEX)) {
			isValid = false;
			parameters.put(RequestParameter.EMAIL, EMPTY_STRING);
			if (isLoginValid) {
				parameters.put(Constant.MESSAGE, Constant.MESSAGE_EMAIL_INVALID);
			} else {
				parameters.put(Constant.MESSAGE, Constant.MESSAGE_DATA_INVALID);
			}
		}
		if (isValid) {
			if (!isStringParameterValid(parameters.get(RequestParameter.PASSWORD), PASSWORD_REGEX) || !(parameters
					.get(RequestParameter.PASSWORD).equals(parameters.get(RequestParameter.REPEAT_PASSWORD)))) {

				isValid = false;
				parameters.put(RequestParameter.PASSWORD, EMPTY_STRING);
				parameters.put(RequestParameter.REPEAT_PASSWORD, EMPTY_STRING);
				parameters.put(Constant.MESSAGE, Constant.MESSAGE_PASSWORD_INVALID);
			}
		} else {
			parameters.put(RequestParameter.PASSWORD, EMPTY_STRING);
			parameters.put(RequestParameter.REPEAT_PASSWORD, EMPTY_STRING);
		}
		return isValid;
	}

	public static boolean isDetailsParametersValid(Map<String, String> parameters) {
		boolean isValid = true;
		boolean isNameValid = true;
		if (!isStringParameterValid(parameters.get(RequestParameter.NAME), NAME_REGEX)) {
			isValid = false;
			parameters.put(RequestParameter.NAME, EMPTY_STRING);
			parameters.put(Constant.MESSAGE, Constant.MESSAGE_NAME_INVALID);
			isNameValid = false;
		}
		if (!isStringParameterValid(parameters.get(RequestParameter.SURNAME), NAME_REGEX)) {
			isValid = false;
			parameters.put(RequestParameter.SURNAME, EMPTY_STRING);
			if (isNameValid) {
				parameters.put(Constant.MESSAGE, Constant.MESSAGE_SURNAME_INVALID);
			} else {
				parameters.put(Constant.MESSAGE, Constant.MESSAGE_NAMES_INVALID);
			}
		}
		if (!isStringParameterValid(parameters.get(RequestParameter.PHONE_NUMBER), PHONE_REGEX)) {
			parameters.put(RequestParameter.PHONE_NUMBER, EMPTY_STRING);
			if (isValid) {
				isValid = false;
				parameters.put(Constant.MESSAGE, Constant.MESSAGE_PHONE_NUMBER_INVALID);
			} else {
				parameters.put(Constant.MESSAGE, Constant.MESSAGE_DETAILS_INVALID);
			}
		}
		return isValid;
	}

	public static boolean isStringParameterValid(String parameter, String regex) {
		boolean result = false;
		if (parameter != null && !parameter.trim().isEmpty()) {
			result = parameter.matches(regex);
		}
		return result;
	}

	public static boolean isSOParametersValid(Map<String, String> parameters) {
		boolean isValid = true;
		if (!isStringParameterValid(parameters.get(RequestParameter.TITLE), TITLE_REGEX)) {
			isValid = false;
			parameters.put(RequestParameter.TITLE, EMPTY_STRING);
			parameters.put(Constant.MESSAGE, Constant.MESSAGE_SO_TITLE_INVALID);
		}
		if (!isStringParameterValid(parameters.get(RequestParameter.DESCRIPTION), DESCRIPTION_REGEX)) {
			parameters.put(RequestParameter.DESCRIPTION, EMPTY_STRING);
			if (isValid) {
				isValid = false;
				parameters.put(Constant.MESSAGE, Constant.MESSAGE_SO_DESCRIPTION_INVALID);
			} else {
				parameters.put(Constant.MESSAGE, Constant.MESSAGE_SO_PARAMETERS_INVALID);
			}
		}
		if (!isStringParameterValid(parameters.get(RequestParameter.DISCOUNT), DISCOUNT_REGEX)) {
			parameters.put(RequestParameter.DISCOUNT, EMPTY_STRING);
			if (isValid) {
				isValid = false;
				parameters.put(Constant.MESSAGE, Constant.MESSAGE_SO_DISCOUNT_INVALID);
			} else {
				parameters.put(Constant.MESSAGE, Constant.MESSAGE_PARAMETERS_WITH_DISCOUNT_INVALID);
			}
		}
		return isValid;
	}
}
