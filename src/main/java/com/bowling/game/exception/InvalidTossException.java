package com.bowling.game.exception;

public class InvalidTossException extends Exception {
	@Override
	public String getMessage() {
		return "Wrong Input. Total pins for a round cannot be more than 10";
	}
}
