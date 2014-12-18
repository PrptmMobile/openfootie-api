package org.openfootie.api.domain.competition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openfootie.api.domain.Match;
import org.openfootie.api.domain.Rankable;

public class KnockoutRound {
	
	private static class Pair {
		
		private Match singleMatch;
		
		private Rankable team1;
		private Rankable team2;
		
		public Pair (Rankable team1, Rankable team2) {
			this.team1 = team1;
			this.team2 = team2;
		}
		
		private Rankable winner;
		
		public Rankable getWinner() {
			return this.winner;
		}
		
		public void play() {
			this.singleMatch = new Match(team1.getName(), team2.getName(), Match.Status.FIXTURE, true);
			this.singleMatch.play(KnockoutCompetition.matchEngine, false);
			if (this.singleMatch.isDecidedOnPenalties()) {
				this.winner = this.singleMatch.getHomeTeamPenaltyScore() > this.singleMatch.getAwayTeamPenaltyScore() ? team1 : team2;
			} else {
				this.winner = this.singleMatch.getHomeTeamScore() > this.singleMatch.getAwayTeamScore() ? team1 : team2;
			}
		}
	}
	
	private List<Pair> pairs = new ArrayList<Pair>();
	private List<Rankable> participants = new ArrayList<Rankable>();
	
	private List<Rankable> winners = new ArrayList<Rankable>();
	
	public List<Rankable> getWinners() {
		return this.winners;
	}
	
	public void setParticipants(List<Rankable> participants) {
		this.participants = participants;
	}
	
	public void addParticipants(List<Rankable> participants) {
		this.participants.addAll(participants);
	}
	
	public void draw() {
		Collections.shuffle(this.participants);
		for (int i = 0; i < this.participants.size(); i+=2) {
			this.pairs.add(new Pair(this.participants.get(i), this.participants.get(i + 1)));
		}
	}
	
	public void play() {
		for (Pair pair:this.pairs) {
			pair.play();
			this.winners.add(pair.getWinner());
		}
	}
}
