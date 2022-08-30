package by.epam.shaturko.controller.command.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.shaturko.controller.Constant;
import by.epam.shaturko.controller.RequestParameter;
import by.epam.shaturko.controller.SessionAttribute;
import by.epam.shaturko.controller.command.Command;
import by.epam.shaturko.entity.tour.City;
import by.epam.shaturko.entity.tour.Hotel;
import by.epam.shaturko.entity.tour.SpecialOffer;
import by.epam.shaturko.entity.tour.Tour;
import by.epam.shaturko.service.ServiceCreateSpecialOffer;
import by.epam.shaturko.service.ServiceGettingData;
import by.epam.shaturko.service.ServiceProvider;
import by.epam.shaturko.service.exception.ServiceException;
import by.epam.shaturko.validator.UserValidator;

public class CreateSpecialOffer implements Command {
	private final static CreateSpecialOffer INSTANCE = new CreateSpecialOffer();
	private final static String CHOSEN = "chosen";
	private final static String YES = "yes";
	private static final Logger logger = LogManager.getLogger();

	private CreateSpecialOffer() {
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String titleSO = request.getParameter(RequestParameter.TITLE);
		String descSO = request.getParameter(RequestParameter.DESCRIPTION);
		String discountSO = request.getParameter(RequestParameter.DISCOUNT);
		Map<String, String> parameters = new HashMap<>();
		parameters.put(RequestParameter.TITLE, titleSO);
		parameters.put(RequestParameter.DESCRIPTION, descSO);
		parameters.put(RequestParameter.DISCOUNT, discountSO);
		HttpSession session = request.getSession(true);
		String requestUrl = (String) session.getAttribute(SessionAttribute.REQUEST_URL);
		session.setAttribute(SessionAttribute.PARAMETERS, parameters);
		try {
			ServiceProvider provider = ServiceProvider.getInstance();
			ServiceCreateSpecialOffer serviceCreateSO = provider.getServiceCreateSpecialOffer();
			if (session.getAttribute(SessionAttribute.TOURS_REQUEST) != null) {
				if (session.getAttribute(SessionAttribute.VIEW_TOUR).equals(YES)) {

					int tourId = Integer.parseInt(request.getParameter(RequestParameter.TOUR_ID));
					Tour tour = new Tour();
					tour.setId(tourId);
					List<Tour> tours = List.of(tour);

					if (request.getParameter(RequestParameter.APPLY_SO) != null) {
						String soTitle = request.getParameter(RequestParameter.SPECIAL_OFFER);
						serviceCreateSO.createSOByTours(null, tours, soTitle);
						requestUrl = Constant.URL_TO_MESSAGE_PAGE;
						session.setAttribute(SessionAttribute.MESSAGE_PAGE_INFO,
								RequestParameter.APPLY_SO_SUCCESS_MESSAGE);
						resetAttributes(session);
						session.removeAttribute(SessionAttribute.VIEW_TOUR);
					} else {
						if (UserValidator.isSOParametersValid(parameters)) {
							SpecialOffer so = new SpecialOffer(titleSO, descSO, Integer.parseInt(discountSO));
							session.removeAttribute(SessionAttribute.TOURS_REQUEST);
							serviceCreateSO.createSOByTours(so, tours, null);
							requestUrl = Constant.URL_TO_MESSAGE_PAGE;
							session.setAttribute(SessionAttribute.MESSAGE_PAGE_INFO,
									RequestParameter.CREATE_APPLY_SO_SUCCESS_MESSAGE);
							resetAttributes(session);
							session.removeAttribute(SessionAttribute.VIEW_TOUR);
						} else {
							requestUrl = Constant.URL_TO_VIEW_TOUR + parameters.get(Constant.MESSAGE);
						}
					}
					System.out.println("############");
				} else {
					if (session.getAttribute(SessionAttribute.TOURS_REQUEST).equals(CHOSEN)) {
						List<Tour> tours = null;
						if (request.getParameter(RequestParameter.TOUR_ID) != null) {
							int tourId = Integer.parseInt(request.getParameter(RequestParameter.TOUR_ID));
							Tour tour = new Tour();
							tour.setId(tourId);
							tours = List.of(tour);
						} else {
							tours = (List<Tour>) session.getAttribute(SessionAttribute.TOURS_LIST);
						}
						if (request.getParameter(RequestParameter.APPLY_SO) != null) {
							String soTitle = request.getParameter(RequestParameter.SPECIAL_OFFER);
							serviceCreateSO.createSOByTours(null, tours, soTitle);
							requestUrl = Constant.URL_TO_MESSAGE_PAGE;
							session.setAttribute(SessionAttribute.MESSAGE_PAGE_INFO,
									RequestParameter.APPLY_SO_SUCCESS_MESSAGE);
							resetAttributes(session);
						} else {
							if (UserValidator.isSOParametersValid(parameters)) {
								SpecialOffer so = new SpecialOffer(titleSO, descSO, Integer.parseInt(discountSO));
								if (!tours.isEmpty()) {
									session.removeAttribute(SessionAttribute.TOURS_REQUEST);
									serviceCreateSO.createSOByTours(so, tours, null);
									requestUrl = Constant.URL_TO_MESSAGE_PAGE;
									session.setAttribute(SessionAttribute.MESSAGE_PAGE_INFO,
											RequestParameter.CREATE_APPLY_SO_SUCCESS_MESSAGE);
									resetAttributes(session);
								} else {
									session.removeAttribute(SessionAttribute.TOURS_REQUEST);
									serviceCreateSO.createSO(so);
								}
							} else {
								requestUrl = Constant.URL_TO_TOURS_PAGE + parameters.get(Constant.MESSAGE);
							}
						}

					}
				}
			} else {
				if (request.getParameter(RequestParameter.START_AGAIN) != null) {
					session.removeAttribute(SessionAttribute.SHOW_COUNTRIES);
					session.setAttribute(SessionAttribute.SHOW_CITIES, false);
					session.setAttribute(SessionAttribute.SHOW_HOTELS, false);
				}
				ServiceGettingData serviceGettingData = provider.getServiceGettingData();
				Boolean showCountries = (Boolean) session.getAttribute(SessionAttribute.SHOW_COUNTRIES);
				Boolean showCities = (Boolean) session.getAttribute(SessionAttribute.SHOW_CITIES);
				Boolean showHotels = (Boolean) session.getAttribute(SessionAttribute.SHOW_HOTELS);
				if (showCountries != null && !showCities && !showHotels) {
					String[] countryNames = request.getParameterValues(RequestParameter.COUNTRY_NAMES);
					if (request.getParameter(RequestParameter.CHOOSE_C) != null) {
						List<City> citiesList = serviceGettingData.getListOfCities(countryNames);
						session.setAttribute(SessionAttribute.SHOW_CITIES, true);
						session.setAttribute(SessionAttribute.CITIES_LIST, citiesList);
						session.setAttribute(SessionAttribute.SHOW_COUNTRIES, false);
						session.setAttribute(SessionAttribute.CHOSEN_COUNTRIES_NAMES, countryNames);
						if (citiesList.isEmpty()) {
							requestUrl = requestUrl + Constant.MESSAGE_NO_COUNTRIES;
						}
					} else {
						if (countryNames == null) {
							if (request.getParameter(RequestParameter.APPLY_SO) != null) {
								requestUrl = Constant.URL_TO_SO_PAGE + Constant.MESSAGE_NO_COUNTRIES_TO_APPLY_SO;
								resetAttributes(session);
							} else {
								if (UserValidator.isSOParametersValid(parameters)) {
									SpecialOffer so = new SpecialOffer(titleSO, descSO, Integer.parseInt(discountSO));
									serviceCreateSO.createSO(so);
									requestUrl = Constant.URL_TO_MESSAGE_PAGE;
									session.setAttribute(SessionAttribute.MESSAGE_PAGE_INFO,
											RequestParameter.CREATE_SO_SUCCESS_MESSAGE);
									resetAttributes(session);
								} else {
									requestUrl = Constant.URL_TO_SO_PAGE + parameters.get(Constant.MESSAGE);
								}
							}
						}
						if (countryNames != null) {
							if (request.getParameter(RequestParameter.APPLY_SO) != null) {
								String soTitle = request.getParameter(RequestParameter.SPECIAL_OFFER);
								serviceCreateSO.createSOByCountries(null, countryNames, soTitle);
								requestUrl = Constant.URL_TO_MESSAGE_PAGE;
								session.setAttribute(SessionAttribute.MESSAGE_PAGE_INFO,
										RequestParameter.APPLY_SO_SUCCESS_MESSAGE);
								resetAttributes(session);
							} else {
								if (UserValidator.isSOParametersValid(parameters)) {
									SpecialOffer so = new SpecialOffer(titleSO, descSO, Integer.parseInt(discountSO));
									serviceCreateSO.createSOByCountries(so, countryNames, null);
									requestUrl = Constant.URL_TO_MESSAGE_PAGE;
									session.setAttribute(SessionAttribute.MESSAGE_PAGE_INFO,
											RequestParameter.CREATE_APPLY_SO_SUCCESS_MESSAGE);
									resetAttributes(session);
								} else {
									requestUrl = Constant.URL_TO_SO_PAGE + parameters.get(Constant.MESSAGE);
								}
							}
						}
					}
				}
				if (showCities && !showHotels) {
					String[] citiesNames = request.getParameterValues(RequestParameter.CITIES_NAMES);
					if (request.getParameter(RequestParameter.CHOOSE_H) != null) {
						List<Hotel> hotelsList = serviceGettingData.getListOfHotels(citiesNames,
								(List<City>) session.getAttribute(SessionAttribute.CITIES_LIST));
						if (hotelsList != null) {
							session.setAttribute(SessionAttribute.SHOW_HOTELS, true);
							session.setAttribute(SessionAttribute.HOTELS_LIST, hotelsList);
							session.setAttribute(SessionAttribute.SHOW_CITIES, false);
							session.setAttribute(SessionAttribute.CHOSEN_CITIES_NAMES, citiesNames);
							if (hotelsList.isEmpty()) {
								requestUrl = requestUrl + Constant.MESSAGE_NO_CITIES;
							}
						}
					} else {
						if (citiesNames == null) {
							if (request.getParameter(RequestParameter.APPLY_SO) != null) {
								String soTitle = request.getParameter(RequestParameter.SPECIAL_OFFER);
								serviceCreateSO.createSOByCountries(null,
										(String[]) session.getAttribute(SessionAttribute.CHOSEN_COUNTRIES_NAMES),
										soTitle);
								requestUrl = Constant.URL_TO_MESSAGE_PAGE;
								session.setAttribute(SessionAttribute.MESSAGE_PAGE_INFO,
										RequestParameter.APPLY_SO_SUCCESS_MESSAGE);
								resetAttributes(session);
							} else {
								if (UserValidator.isSOParametersValid(parameters)) {
									SpecialOffer so = new SpecialOffer(titleSO, descSO, Integer.parseInt(discountSO));
									serviceCreateSO.createSOByCountries(so,
											(String[]) session.getAttribute(SessionAttribute.CHOSEN_COUNTRIES_NAMES),
											null);
									requestUrl = Constant.URL_TO_MESSAGE_PAGE;
									session.setAttribute(SessionAttribute.MESSAGE_PAGE_INFO,
											RequestParameter.CREATE_APPLY_SO_SUCCESS_MESSAGE);
									resetAttributes(session);
								} else {
									requestUrl = Constant.URL_TO_SO_PAGE + parameters.get(Constant.MESSAGE);
								}
							}
						}
						if (citiesNames != null) {
							if (request.getParameter(RequestParameter.APPLY_SO) != null) {
								String soTitle = request.getParameter(RequestParameter.SPECIAL_OFFER);
								serviceCreateSO.createSOByCities(null, citiesNames, soTitle);
								requestUrl = Constant.URL_TO_MESSAGE_PAGE;
								session.setAttribute(SessionAttribute.MESSAGE_PAGE_INFO,
										RequestParameter.APPLY_SO_SUCCESS_MESSAGE);
								resetAttributes(session);
							} else {
								if (UserValidator.isSOParametersValid(parameters)) {
									SpecialOffer so = new SpecialOffer(titleSO, descSO, Integer.parseInt(discountSO));
									serviceCreateSO.createSOByCities(so, citiesNames, null);
									requestUrl = Constant.URL_TO_MESSAGE_PAGE;
									session.setAttribute(SessionAttribute.MESSAGE_PAGE_INFO,
											RequestParameter.CREATE_APPLY_SO_SUCCESS_MESSAGE);
									resetAttributes(session);
								} else {
									requestUrl = Constant.URL_TO_SO_PAGE + parameters.get(Constant.MESSAGE);
								}
							}
						}
					}
				}
				if (!showCities && showHotels) {
					String[] hotelsNames = request.getParameterValues(RequestParameter.HOTELS_NAMES);
					if (hotelsNames == null) {
						if (request.getParameter(RequestParameter.APPLY_SO) != null) {
							String soTitle = request.getParameter(RequestParameter.SPECIAL_OFFER);
							serviceCreateSO.createSOByCities(null,
									(String[]) session.getAttribute(SessionAttribute.CHOSEN_CITIES_NAMES), soTitle);
							requestUrl = Constant.URL_TO_MESSAGE_PAGE;
							session.setAttribute(SessionAttribute.MESSAGE_PAGE_INFO,
									RequestParameter.APPLY_SO_SUCCESS_MESSAGE);
							resetAttributes(session);
						} else {

							if (UserValidator.isSOParametersValid(parameters)) {
								SpecialOffer so = new SpecialOffer(titleSO, descSO, Integer.parseInt(discountSO));
								serviceCreateSO.createSOByCities(so,
										(String[]) session.getAttribute(SessionAttribute.CHOSEN_CITIES_NAMES), null);
								requestUrl = Constant.URL_TO_MESSAGE_PAGE;
								session.setAttribute(SessionAttribute.MESSAGE_PAGE_INFO,
										RequestParameter.CREATE_APPLY_SO_SUCCESS_MESSAGE);
								resetAttributes(session);
							} else {
								requestUrl = Constant.URL_TO_SO_PAGE + parameters.get(Constant.MESSAGE);
							}
						}
					}
					if (hotelsNames != null) {
						if (request.getParameter(RequestParameter.APPLY_SO) != null) {
							String soTitle = request.getParameter(RequestParameter.SPECIAL_OFFER);
							serviceCreateSO.createSOByHotels(null, hotelsNames, soTitle);
							requestUrl = Constant.URL_TO_MESSAGE_PAGE;
							session.setAttribute(SessionAttribute.MESSAGE_PAGE_INFO,
									RequestParameter.APPLY_SO_SUCCESS_MESSAGE);
							resetAttributes(session);
						} else {
							if (UserValidator.isSOParametersValid(parameters)) {
								SpecialOffer so = new SpecialOffer(titleSO, descSO, Integer.parseInt(discountSO));
								serviceCreateSO.createSOByHotels(so, hotelsNames, null);
								requestUrl = Constant.URL_TO_MESSAGE_PAGE;
								session.setAttribute(SessionAttribute.MESSAGE_PAGE_INFO,
										RequestParameter.CREATE_APPLY_SO_SUCCESS_MESSAGE);
								resetAttributes(session);
							} else {
								requestUrl = Constant.URL_TO_SO_PAGE + parameters.get(Constant.MESSAGE);
							}
						}
					}
				}
			}
		} catch (ServiceException e) {
			logger.log(Level.ERROR, "Error while trying to cancel the tour", e);
			session.setAttribute(SessionAttribute.ERROR_TYPE, Constant.ERROR_500);
			session.setAttribute(SessionAttribute.ERROR_MESSAGE, e);
			requestUrl = Constant.ERROR_COMMAND;
		}
		response.sendRedirect(requestUrl);
	}

	private void resetAttributes(HttpSession session) {
		session.removeAttribute(SessionAttribute.SHOW_COUNTRIES);
		session.setAttribute(SessionAttribute.SHOW_CITIES, false);
		session.setAttribute(SessionAttribute.SHOW_HOTELS, false);
	}

	public static CreateSpecialOffer getInstance() {
		return INSTANCE;
	}
}
