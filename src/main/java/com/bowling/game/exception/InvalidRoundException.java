package com.bowling.game.exception;

public class InvalidRoundException extends Exception {
	@Override
	public String getMessage() {
		return "Wrong Input. There can be maximum 10 Rounds";
	}
}
