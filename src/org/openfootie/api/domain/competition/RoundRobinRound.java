package org.openfootie.api.domain.competition;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openfootie.api.domain.Fixture;
import org.openfootie.api.domain.Match;

public class RoundRobinRound {
	
	private List<Fixture> fixtures;
	private List<Match> matches = new ArrayList<Match>();
	
	public RoundRobinRound(List<Fixture> fixtures) {
		this.fixtures = fixtures;
	}
	
	public List<Fixture> getFixtures() {
		return this.fixtures;
	}
	
	public void play(CompetitionTable competitionTable) {
		for (Fixture fixture: fixtures) {
			Match match = fixture.createMatch();
			match.play(RoundRobinCompetition.matchEngine, true);
			this.matches.add(match);
			competitionTable.update(match);
		}
	}
	
	public List<Match> getMatches() {
		return this.matches;
	}
	
	public void display() {
		for (Match match: this.matches) {
			System.out.println(match);
		}
	}
	
	public boolean validate() {
		
		Set<String> teamNames = new HashSet<String>();
		
		for (Fixture fixture:this.fixtures){
			
			String homeTeamName = fixture.getHomeTeam().getName();
			String awayTeamName = fixture.getAwayTeam().getName();
			
			if (!teamNames.contains(homeTeamName)) {
				teamNames.add(homeTeamName);
			} else {
				return false;
			}
			if (!teamNames.contains(awayTeamName)) {
				teamNames.add(awayTeamName);
			} else {
				return false;
			}
		}
			
		return true;
	}
	
	public RoundRobinRound reverse() {
		
		List<Fixture> reverseFixtures = new ArrayList<Fixture>();
		
		for (Fixture fixture:this.fixtures) {
			reverseFixtures.add(new Fixture(fixture.getAwayTeam(), fixture.getHomeTeam()));
		}
		
		return new RoundRobinRound(reverseFixtures);
	}
	
	@Override
	public String toString() {
		
		StringBuilder roundFixtures = new StringBuilder();
		
		for (Fixture fixture: this.fixtures) {
			roundFixtures.append(fixture.toString());
			roundFixtures.append("\n");
		}
		
		return roundFixtures.toString();
	}
}
