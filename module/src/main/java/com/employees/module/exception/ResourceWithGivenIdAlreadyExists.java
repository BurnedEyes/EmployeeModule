package com.employees.module.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class ResourceWithGivenIdAlreadyExists extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ResourceWithGivenIdAlreadyExists(String msg) {
		super(msg);
	}

	public <T> ResourceWithGivenIdAlreadyExists(Class<T> objectType, Long id) {
		super("Resource of type " + objectType.getSimpleName() + " with ID " + id + " already exists.");
	}
}
