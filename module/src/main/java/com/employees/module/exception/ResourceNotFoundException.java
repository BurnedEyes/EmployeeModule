package com.employees.module.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String msg) {
		super(msg);
	}

	public <T> ResourceNotFoundException(Class<T> objectType, Long id) {
		super("Resource of type " + objectType.getSimpleName() + " with ID " + id + " not found.");
	}
}
