package by.epam.shaturko.service;

import by.epam.shaturko.entity.Message;
import by.epam.shaturko.service.exception.ServiceException;

public interface ServiceWriteMessage {

	void writeMessage(Message message) throws ServiceException;

}
