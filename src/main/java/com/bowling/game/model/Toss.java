package com.bowling.game.model;

public class Toss {
	private int score;

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
		System.out.println(score + " Pins down");
	}
}
