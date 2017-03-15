package com.bowling.game;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bowling.game.GameMechanics;
import com.bowling.game.exception.GameNotInitializedException;
import com.bowling.game.exception.InvalidRoundException;
import com.bowling.game.exception.InvalidTossException;
import com.bowling.game.model.Game;

public class GameMechanicsTest {
	Game game;
	@Before
	public void setup() {
		game = new Game();
	}
	
	@Test
	public void testSingleValidRound(){
		List<Integer> tossScores = new ArrayList<Integer>();
		tossScores.add(4);
		tossScores.add(4);
		playNewRound(game, tossScores, 0);
		assertEquals(8, game.getScoreForEachRound().get(0).intValue());
	}
	
	@Test
	public void testSingleValidSpareRound(){
		List<Integer> tossScores = new ArrayList<Integer>();
		tossScores.add(4);
		tossScores.add(6);
		playNewRound(game, tossScores, 0);
		//Value should be null as it is Spare and score calculation will happen after next toss.Hence object should be null.
		assertEquals(null, game.getScoreForEachRound().get(0));
	}
	
	@Test
	public void testSingleValidStrikeRound(){
		List<Integer> tossScores = new ArrayList<Integer>();
		tossScores.add(10);
		playNewRound(game, tossScores, 0);
		//Value should be null as it is Strike and score calculation will happen after next Round.Hence object should be null.
		assertEquals(null, game.getScoreForEachRound().get(0));
	}
	
	@Test
	public void testAllZeroes() {
		playManyRounds(10, 0);
		assertEquals(0, game.getScoreForEachRound().get(9).intValue());
	}
	
	@Test
	public void testAllOnes() {
		playManyRounds(10, 1);
		assertEquals(20, game.getScoreForEachRound().get(9).intValue());
	}
	
	@Test
	public void testAllStrikes() {
		playManyRoundsWithDifferentPins(9, 10, 0);
		List<Integer> tossScores = new ArrayList<Integer>();
		setTossScores(tossScores, 3, 10);
		playNewRound(game, tossScores, 9);
		assertEquals(300, game.getScoreForEachRound().get(9).intValue());
	}
	
	@Test
	public void testAllSpares() {
		playManyRoundsWithDifferentPins(9, 4, 6);
		List<Integer> tossScores = new ArrayList<Integer>();
		tossScores.add(4);
		tossScores.add(6);
		tossScores.add(10);
		playNewRound(game, tossScores, 9);
		assertEquals(146, game.getScoreForEachRound().get(9).intValue());
	}
	

	
	
	@Test
	public void testInvalidValueOfTosses() {
	 List<Integer> tossScores = new ArrayList<Integer>();
	 tossScores.add(10);
	 tossScores.add(12);
	 boolean caughtException = playNewRound(game,tossScores,8);
	 assertEquals(true, caughtException);
	}
	
	@Test
	public void testInvalidValueOfRound() {
	 List<Integer> tossScores = new ArrayList<Integer>();
	 tossScores.add(1);
	 tossScores.add(1);
	 boolean caughtException = playNewRound(game,tossScores,18);
	 assertEquals(true, caughtException);
	}
	
	private void playManyRounds(int rounds, int pins) {
		for(int i =0; i < rounds; i++) {
			List<Integer> tossScores = new ArrayList<Integer>();
			setTossScores(tossScores, 2, pins);
			playNewRound(game, tossScores, i);
		}
	}
	
	private void playManyRoundsWithDifferentPins(int rounds, int pins1, int pins2) {
		for(int i =0; i < rounds; i++) {
			List<Integer> tossScores = new ArrayList<Integer>();
			tossScores.add(pins1);
			tossScores.add(pins2);
			playNewRound(game, tossScores, i);
		}
	}
	
	boolean playNewRound(Game game, List<Integer> tossScores, int roundNumber){
		boolean caughtException = false;
		try {
			GameMechanics.playNewRound(game, tossScores, roundNumber);
		} catch (GameNotInitializedException | InvalidRoundException | InvalidTossException e) {
			caughtException= true;
		}
		return caughtException;
	}
	
	private void setTossScores(List<Integer> tossScore, int n, int pins) {
		for(int i = 0; i < n; i++) {
			tossScore.add(pins);
		}
	}
	
	@After
	public void trash() {
		game = null;
	}
}

