package com.bowling.game.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
	private List<Round> rounds = new ArrayList<>();
	private Map<Integer, Integer> scoreForEachRound = new HashMap<>();


	public Map<Integer, Integer> getScoreForEachRound() {
		return scoreForEachRound;
	}
	
	public void setTotalScoreForEachRound(int roundNumber) {
		int total = 0;
		for(int i=0; i <= roundNumber; i++) {
			Round round = rounds.get(i);
			if(round.getScore() != -1) {
				total = total + rounds.get(i).getScore();
				scoreForEachRound.put(i, total);
			}
		}
	}

	public List<Round> getRounds() {
		return rounds;
	}

	public void setRound(Round round) {
		rounds.add(round);
	}
	
	public void displayScore() {
		System.out.println();
		System.out.println("-----------------------------------------------SCORE BOARD-------------------------------------------");
		for (int i=0; i < rounds.size(); i++) {
			System.out.print("Round " +(i+1) + "\t\t");
		}
		System.out.println();
		for (int i=0; i< rounds.size(); i++) {
			if(rounds.get(i).getTosses().size() == 1)
				System.out.print(rounds.get(i).getTosses().get(0).getScore());
			else {
				System.out.print(rounds.get(i).getTosses().get(0).getScore() + " , " + rounds.get(i).getTosses().get(1).getScore());
			}
			if( i == 9 && rounds.get(i).getTosses().size() == 3)
				System.out.print(" , " + rounds.get(i).getTosses().get(2).getScore());
			System.out.print("\t\t");
		}
		
		System.out.println();
		for (Map.Entry<Integer, Integer> entry : scoreForEachRound.entrySet()) {
			System.out.print(entry.getValue() + "\t\t");
		}
	}
}
