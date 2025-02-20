package com.mmight1.points_demo.exception;

public class InsufficientPointsException extends RuntimeException {
    private static final long serialVersionUID = -652747455078529434L;

	public InsufficientPointsException(String message) {
        super(message);
    }
}