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
		
		int properRounds = (int) Math.log(this.participants.size()) / (int) Math.log(2);
		int extraTeams = this.participants.size() % properRounds;
	
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
		for (int i = 0; i < rounds.size(); i++) {
			rounds.get(i).draw();
			rounds.get(i).play();
			rounds.get(i + 1).addParticipants(rounds.get(i).getWinners());
		}
	}
	
	@Override
	public String toString() {
		
		StringBuilder transcript = new StringBuilder();
		
		for (int i = 0; i < rounds.size(); i++) {
			transcript.append("Round " + i + 1);
			transcript.append("\n");
		}
		
		// TODO: TBC...
		
		return transcript.toString();
	}
	
	public Rankable getWinner() {
		return rounds.get(rounds.size() - 1).getWinners().get(0);
	}
}
 