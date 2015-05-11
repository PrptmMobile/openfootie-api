package org.openfootie.api.domain.competition;

import java.util.List;

public class RoundRobinSchedule {
	
	private List<RoundRobinRound> rounds;
	
	public RoundRobinSchedule(List<RoundRobinRound> rounds) {
		// System.out.println("Number of rounds: " + rounds.size());
		this.rounds = rounds;
	}
	
	public List<RoundRobinRound> getRounds() {
		return this.rounds;
	}
	
	@Override
	public String toString() {
		
		StringBuilder scheduleStr = new StringBuilder();
		
		for (int i = 0; i < rounds.size(); i++) {
			scheduleStr.append("Round " + (i + 1));
			scheduleStr.append("\n");
			scheduleStr.append("\n");
			scheduleStr.append(rounds.get(i).toString());
			scheduleStr.append("\n");
		}
		
		return scheduleStr.toString();
	}
}
