package com.bowling.game.model;

import java.util.ArrayList;
import java.util.List;

import com.bowling.game.model.RoundTypeConstants.RoundType;

public class Round {
	private List<Toss> tosses = new ArrayList<Toss>();
	private int score = -1;
	private RoundType roundType;

	public RoundType getRoundType() {
		return roundType;
	}

	public void setRoundType(RoundType roundType) {
		this.roundType = roundType;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public List<Toss> getTosses() {
		return tosses;
	}

	public void setToss(Toss toss) {
		tosses.add(toss);
	}
}
