package org.openfootie.api.simulator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openfootie.api.domain.Rankable;

public class TeamRanking {
	
	private Map<Integer, Rankable> ranking;
	private Map<Rankable, Integer> reverseRanking;
	
	public TeamRanking(List<? extends Rankable> teams) {
		
		int currentPosition = 1;
		
		this.ranking = new HashMap<Integer, Rankable>();
		this.reverseRanking = new HashMap<Rankable, Integer>();
		
		for (Rankable team:teams) {
			ranking.put(currentPosition, team);
			reverseRanking.put(team, currentPosition);
			currentPosition++;
		}
	}
	
	/*
	public void addTeamInPosition(Integer position, Rankable team) {
		
	}
	*/
	
	public Rankable getTeamByPosition(Integer position) {
		return ranking.get(position);
	}
	
	public Integer getPositionByTeam(Rankable team) {
		return reverseRanking.get(team);
	}
}
