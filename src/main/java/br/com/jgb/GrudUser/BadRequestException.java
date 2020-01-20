package br.com.jgb.GrudUser;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = -5887563808675112513L;

	public BadRequestException(final String message) {
		super(message);
	}

}