package org.openfootie.api.simulator;

import org.junit.Before;
import org.junit.Test;
import org.openfootie.api.domain.Match;
import org.openfootie.api.environment.Environment;
import org.openfootie.resources.DataResources;

public class ScoreSimulatorTest {
	
	private Environment environment;
	private ScoreSimulator clubScoreSimulator;
	private ScoreSimulator nationScoreSimulator;
	
	@Before
	public void setUp() throws Exception {
		
		environment = new Environment(
				DataResources.ROOT + "/nations.sjon", 
				DataResources.ROOT + "/teams.sjon", 
				DataResources.ROOT + "/results_nations.sjon", 
				DataResources.ROOT + "/results_teams.sjon");
		
		this.clubScoreSimulator = new ScoreSimulator(environment.getRankings().get(Environment.Ranking.CLUB_DEFAULT), environment.getClubMatches());
		this.nationScoreSimulator = new ScoreSimulator(environment.getRankings().get(Environment.Ranking.NATION_DEFAULT), environment.getNationMatches());
	}
	
	@Test
	public void testClubMatchCalculationNeutral() {
		
		// Fiorentina - Napoli 1-3 (Quantiles: 2)
		
		Match testMatch = new Match("Fiorentina", "Napoli", Match.Status.FIXTURE, true);
		
		int quantiles = 1;
		while ((testMatch.getHomeTeamScore() != 1 || testMatch.getAwayTeamScore() != 3) && (testMatch.getHomeTeamScore() != -1 || testMatch.getAwayTeamScore() != -1)) {
			this.clubScoreSimulator.setQuantiles(quantiles++);
			this.clubScoreSimulator.play(testMatch);
			System.out.println(testMatch);
		}
		
		int finalQuantiles = quantiles - 1;
		System.out.println("Quantiles: " + finalQuantiles);
		
	}
	
	@Test
	public void testNationMatchCalculationNeutral() {
		
		// Netherlands - Argentina 0-0
		
		Match testMatch = new Match("Netherlands", "Argentina", Match.Status.FIXTURE, true);
		
		int quantiles = 1;
		while ((testMatch.getHomeTeamScore() != 0 || testMatch.getAwayTeamScore() != 0) && (testMatch.getHomeTeamScore() != -1 || testMatch.getAwayTeamScore() != -1)) {
			this.nationScoreSimulator.setQuantiles(quantiles++);
			this.nationScoreSimulator.play(testMatch);
			System.out.println(testMatch);
		}
		
		int finalQuantiles = quantiles - 1;
		System.out.println("Quantiles: " + finalQuantiles);
		
	}
	
	@Test
	public void testClubMatchCalculation() {
		
		// Fiorentina - PAOK 1-1 (Club quantiles: 2)
		
		Match testMatch = new Match("Fiorentina", "PAOK", Match.Status.FIXTURE, false);
		
		int quantiles = 1;
		while (testMatch.getHomeTeamScore() != 1 || testMatch.getAwayTeamScore() != 1) {
			this.clubScoreSimulator.setQuantiles(quantiles++);
			this.clubScoreSimulator.play(testMatch);
			System.out.println(testMatch);
		}
		
		int finalQuantiles = quantiles - 1;
		System.out.println("Quantiles: " + finalQuantiles);
	}
	
	@Test
	public void testNationMatchCalculation() {
		
		// Peru - Paraguay 2-1 (Nation quantiles: 9)
		
		Match testMatch = new Match("Peru", "Paraguay", Match.Status.FIXTURE, false);
		
		int quantiles = 1;
		while (testMatch.getHomeTeamScore() != 2 || testMatch.getAwayTeamScore() != 1) {
			this.nationScoreSimulator.setQuantiles(quantiles++);
			this.nationScoreSimulator.play(testMatch);
			System.out.println(testMatch);
		}
		
		int finalQuantiles = quantiles - 1;
		System.out.println("Quantiles: " + finalQuantiles);
		
	}

}
