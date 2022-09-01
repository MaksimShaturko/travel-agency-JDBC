package by.epam.shaturko.service;

import by.epam.shaturko.service.impl.FillingDBServiceImpl;
import by.epam.shaturko.service.impl.GenerateToursServiceImpl;
import by.epam.shaturko.service.impl.RegistrationServiceImpl;
import by.epam.shaturko.service.impl.ServiceAuthorizationImpl;
import by.epam.shaturko.service.impl.ServiceChangingDataImpl;
import by.epam.shaturko.service.impl.ServiceCreateSpecialOfferImpl;
import by.epam.shaturko.service.impl.ServiceGettingDataImpl;
import by.epam.shaturko.service.impl.ServiceGettingSOToursImpl;
import by.epam.shaturko.service.impl.ServiceOrderTourImpl;
import by.epam.shaturko.service.impl.ServiceWriteMessageImpl;

public class ServiceProvider {

	private static ServiceProvider instance = null;

	private ServiceProvider() {

	}

	public static ServiceProvider getInstance() {
		if (instance == null) {
			instance = new ServiceProvider();
		}
		return instance;
	}

	public RegistrationService getRegistrationService() {

		RegistrationService service;

		service = new RegistrationServiceImpl();

		return service;
	}

	public FillingDBService getFillingDBService() {

		FillingDBService fillingDBService;

		fillingDBService = new FillingDBServiceImpl();

		return fillingDBService;
	}

	public ServiceAuthorization getServiceAuthorization() {

		ServiceAuthorization serviceAuthorization;

		serviceAuthorization = new ServiceAuthorizationImpl();

		return serviceAuthorization;
	}

	public GenerateToursService getGenerateToursService() {

		GenerateToursService generateToursService;

		generateToursService = new GenerateToursServiceImpl();

		return generateToursService;
	}

	public ServiceGettingData getServiceGettingData() {

		ServiceGettingData serviceGettingData;

		serviceGettingData = new ServiceGettingDataImpl();

		return serviceGettingData;
	}

	public ServiceCreateSpecialOffer getServiceCreateSpecialOffer() {

		ServiceCreateSpecialOffer serviceCreateSpecialOffer;

		serviceCreateSpecialOffer = new ServiceCreateSpecialOfferImpl();

		return serviceCreateSpecialOffer;
	}

	public ServiceChangingData getServiceChangingData() {

		ServiceChangingData serviceChangingData;

		serviceChangingData = new ServiceChangingDataImpl();

		return serviceChangingData;
	}
	
	public ServiceOrderTour getServiceOrderTour() {

		ServiceOrderTour serviceOrderTour;

		serviceOrderTour = new ServiceOrderTourImpl();

		return serviceOrderTour;
	}
	
	public ServiceWriteMessage getServiceWriteMessage() {

		ServiceWriteMessage serviceWriteMessage;

		serviceWriteMessage = new ServiceWriteMessageImpl();

		return serviceWriteMessage;
	}
	
	public ServiceGettingSOTours getServiceGettingSOTours() {

		ServiceGettingSOTours serviceGettingSOTours;

		serviceGettingSOTours = new ServiceGettingSOToursImpl();

		return serviceGettingSOTours;
	}
}
