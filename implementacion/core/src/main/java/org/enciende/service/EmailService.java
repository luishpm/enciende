package org.enciende.service;

import org.enciende.exception.ServiceException;

public interface EmailService {
	public void sendEmail(String to, String message, String subject) throws ServiceException;
}
