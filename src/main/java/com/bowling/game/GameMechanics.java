package com.bowling.game;

import java.util.List;

import com.bowling.game.exception.GameNotInitializedException;
import com.bowling.game.exception.InvalidRoundException;
import com.bowling.game.exception.InvalidTossException;
import com.bowling.game.model.Game;
import com.bowling.game.model.Round;
import com.bowling.game.model.RoundTypeConstants.RoundType;
import com.bowling.game.model.Toss;

/**
 * PlayGame class:
 * 1. Create Round object for each round
 * 2. Set Tosses played in each Round
 * 3. Calculates score for each round
 * 3. After every round, calculates total score
 * @author I320266
 *
 */
public class GameMechanics {
	
	/**
	 * calls playRound() method to create a new Round object and set Tosses in it
	 * calls calculateScore() method to calculate score for this round and calculate total score
	 * calls displayScore() method of game object to display score
 	 * @param game object defines game that is being played currently
	 * @param tossScores input score for tosses
	 * @param roundNumber round number that will be played
	 * @throws InvalidTossException thrown for invalid tosses
	 * @throws InvalidRoundException thrown for invalid round number
	 * @throws GameNotInitializedException thrown when game is not initialized
	 */
	public static void playNewRound(Game game, List<Integer> tossScores, int roundNumber) throws GameNotInitializedException, InvalidRoundException, InvalidTossException {
		validateInput(game, tossScores, roundNumber);
		initializeRound(game, tossScores, roundNumber);
		calculateScore(game, roundNumber);
		game.displayScore();
	}

	private static void initializeRound(Game game, List<Integer> tossScores, int roundNumber) {
		Round round = new Round();
		game.setRound(round);
		createNewTossAndSetToRound(round, tossScores.get(0));

		//if it's not a strike, then two tosses are played. so set second toss in 'round' object
		if(tossScores.get(0) != 10)
			createNewTossAndSetToRound(round, tossScores.get(1));

		//for last round, if it's a strike or spare, set third toss in 'round' object
		if(roundNumber == 9 && tossScores.size() == 3) {
			if(tossScores.get(0) == 10)
				createNewTossAndSetToRound(round, tossScores.get(1));
			createNewTossAndSetToRound(round, tossScores.get(2));
		}
	}
	
	private static void validateInput(Game game, List<Integer> tossScores, int roundNumber) 
			throws GameNotInitializedException, InvalidRoundException, InvalidTossException {
		//throw exception if game is not initialized
		if(game == null)
			throw new GameNotInitializedException();
		
		//throw exception if roundNumber exceeds 9
		if(roundNumber > 9)
			throw new InvalidRoundException();
		
		if(!(tossesValid(tossScores, roundNumber)))
			throw new InvalidTossException();
	}
	
	private static boolean tossesValid(List<Integer> tossScores, int roundNumber) {
		boolean isValid = false;
		if(roundNumber == 9) {
			//if last round has 2 tosses, then it must be OPENFRAME
			if((tossScores.size() == 2) && (sumOfTossScores(tossScores) < 10))
				isValid = true;
			
			//if last has 3 tosses, then it must be STRIKE or SPARE
			else if(tossScores.size() == 3) {
				if((tossScores.get(0) == 10) && (sumOfTossScores(tossScores) <= 30))
					isValid = true;
				else if((tossScores.get(0) + tossScores.get(1) == 10) && (tossScores.get(2) <= 10))
					isValid = true;
			}
		}
		else {
			//total pins in a round can be maximum 10
			if((tossScores.size() == 2) && (sumOfTossScores(tossScores) <= 10))
				isValid = true;
			//if only one toss exists, then it must be STRIKE
			else if((tossScores.size() == 1) && (tossScores.get(0)== 10))
				isValid = true;
		}
		return isValid;
	}

	private static int sumOfTossScores(List<Integer> tossScores) {
		int sum = 0;
		for (Integer score : tossScores) {
			sum = sum + score;
		}
		return sum;
	}

	private static void createNewTossAndSetToRound(Round round, int tossScore) {
		Toss toss = new Toss();
		round.setToss(toss);
		toss.setScore(tossScore);
	}

	private static void calculateScore(Game game, int roundNumber) {
		Round round = game.getRounds().get(roundNumber);
		List<Toss> tosses = round.getTosses();
		if(tosses.size() == 2) {
			if(sumOfTosses(tosses) == 10) {
				round.setRoundType(RoundType.SPARE);
			}
			else {
				round.setRoundType(RoundType.OPENFRAME);
				round.setScore(sumOfTosses(tosses));
			}
		}
		else if(tosses.size() == 1) {
			round.setRoundType(RoundType.STRIKE);
		}
		
		//if last round has 3 tosses, set score for the last round
		if(roundNumber == 9 && tosses.size() == 3) {
			setRoundTypeForLastRound(tosses, round);
			round.setScore(sumOfTosses(tosses) );
			
		}
		
		handlePreviousStrikeAndSpareRound(game, roundNumber);
		game.setTotalScoreForEachRound(roundNumber);
	}
	
	private static void setRoundTypeForLastRound(List<Toss> tosses, Round round) {
		if(tosses.get(0).getScore() == 10)
			round.setRoundType(RoundType.STRIKE);
		else
			round.setRoundType(RoundType.SPARE);
	}

	private static int sumOfTosses(List<Toss> tosses) {
		int sum = 0;
		for (Toss toss : tosses) {
			sum = sum + toss.getScore();
		}
		return sum;
	}
	
	private static void handlePreviousSpareRound(Round roundPrevious, Round roundCurrent) {
		int bonus = roundCurrent.getTosses().get(0).getScore();
		roundPrevious.setScore(10 + bonus);
	}
	
	private static void handlePreviousStrikeRound(Round roundPrevious, List<Round> rounds, int roundNumber) {
		Round roundCurrent = rounds.get(roundNumber);
		
		//if current round has more than 1 toss then score can be calculated for previous strike round
		if(roundCurrent.getTosses().size() > 1) {
			int bonus = roundCurrent.getTosses().get(0).getScore() + roundCurrent.getTosses().get(1).getScore();
			roundPrevious.setScore(10 + bonus);
		}
		
		//check if previous to previous round was strike and calculate score
		if(roundNumber > 1) {
			Round roundPrevious2 = rounds.get(roundNumber - 2);
			if(roundPrevious2.getRoundType() == RoundType.STRIKE) {
				int bonus = 10 + roundCurrent.getTosses().get(0).getScore();
				roundPrevious2.setScore(10 + bonus);
			}
		}
	}
	
	private static void handlePreviousStrikeAndSpareRound(Game game, int roundNumber) {
		if(roundNumber > 0) {
			List<Round> rounds = game.getRounds();
			Round roundCurrent = rounds.get(roundNumber);
			Round roundPrevious1 = rounds.get(roundNumber - 1);
			//check previous round for SPARE
			if(roundPrevious1.getRoundType() == RoundType.SPARE)
				handlePreviousSpareRound(roundPrevious1, roundCurrent);
			//check previous round for STRIKE
			else if(roundPrevious1.getRoundType() == RoundType.STRIKE) {
				handlePreviousStrikeRound(roundPrevious1, rounds, roundNumber);
			}
		}
	}
}
