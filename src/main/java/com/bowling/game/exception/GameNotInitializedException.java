package com.bowling.game.exception;

public class GameNotInitializedException extends Exception{
	@Override
	public String getMessage() {
		return "Game is not initialized";
	}
}
