package org.openfootie.api.domain.competition;

import java.util.ArrayList;
import java.util.List;

import org.openfootie.api.domain.Match;
import org.openfootie.api.domain.Rankable;
import org.openfootie.api.simulator.ScoreSimulator;
import org.openfootie.api.simulator.TeamRanking;

public class KnockoutCompetition {
	
	private List<KnockoutRound> rounds = new ArrayList<KnockoutRound>();
	
	private TeamRanking ranking;
	private List<Rankable> participants;
	
	public static ScoreSimulator matchEngine;
	
	public KnockoutCompetition(List<Rankable> participants, TeamRanking ranking, List<Match> sampleMatches) {
		
		matchEngine = new ScoreSimulator(ranking, sampleMatches);
		
		this.participants = participants;
		this.ranking = ranking.filterByParticipants(this.participants);
		
		int properRounds = new Double(Math.log(this.participants.size()) / Math.log(2)).intValue();
		int extraTeams = (int) (this.participants.size() - Math.pow(2, properRounds));
		
		/*
		 * TODO: DEBUG
		 */
		System.out.println("Number of participants: " + this.participants.size());
		System.out.println("Number of proper rounds: " + properRounds);
		System.out.println("Extra teams: " + extraTeams);
	
		int roundsNum = 0;
		
		if (extraTeams > 0) {
			roundsNum = properRounds + 1;
		} else {
			roundsNum = properRounds;
		}
		
		for (int i = 0; i < roundsNum; i++) {
			this.rounds.add(new KnockoutRound());
		}
		
		if (extraTeams > 0) {
			this.rounds.get(0).setParticipants(this.ranking.getBottomTeams(2 * extraTeams));
			this.rounds.get(1).setParticipants(this.ranking.getTopTeams(this.participants.size() - 2 * extraTeams));
		} else {
			this.rounds.get(0).setParticipants(this.participants);
		}
	}
	
	public void play() {
		
		for (int i = 0;; i++) {
			
			rounds.get(i).draw();
			rounds.get(i).play();
			
			if (i == rounds.size() - 1) { // No next round
				return;
			}
			
			rounds.get(i + 1).addParticipants(rounds.get(i).getWinners());
		}
	}
	
	@Override
	public String toString() {
		
		StringBuilder transcript = new StringBuilder();
		
		for (int i = 0; i < rounds.size(); i++) {
			
			transcript.append("Round " + (i + 1));
			transcript.append("\n");
			
			transcript.append(rounds.get(i).toString());
			transcript.append("\n");
		}
		
		transcript.append("\n");
		transcript.append(getWinner().getName() + " wins the competition");
		
		return transcript.toString();
	}
	
	public Rankable getWinner() {
		return rounds.get(rounds.size() - 1).getWinners().get(0);
	}
}
 