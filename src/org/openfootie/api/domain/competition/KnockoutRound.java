package org.openfootie.api.domain.competition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openfootie.api.domain.Match;
import org.openfootie.api.domain.Rankable;

public class KnockoutRound {
	
	private KnockoutCompetitionTemplate template;
	
	public KnockoutRound(KnockoutCompetitionTemplate template) {
		this.template = template;
	}
	
	private class Pair {
		
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
			switch(template) {
			case SINGLE_MATCH_NEUTRAL:
				this.singleMatch = new Match(team1.getName(), team2.getName(), Match.Status.FIXTURE, true);
				this.singleMatch.play(KnockoutCompetition.matchEngine, false);
				if (this.singleMatch.isDecidedOnPenalties()) {
					this.winner = this.singleMatch.getHomeTeamPenaltyScore() > this.singleMatch.getAwayTeamPenaltyScore() ? team1 : team2;
				} else {
					this.winner = this.singleMatch.getHomeTeamScore() > this.singleMatch.getAwayTeamScore() ? team1 : team2;
				}
				break;
			}
		}
		
		@Override
		public String toString() {
			
			StringBuilder fixture = new StringBuilder();
			
			fixture.append(team1.getName() + " - " + team2.getName());
			
			if (this.singleMatch.getStatus().equals(Match.Status.PLAYED)) {
				
				fixture.append(" ");
				
				if (!this.singleMatch.isExtraTimePlayed()) {
					fixture.append(this.singleMatch.getHomeTeamScore() + " - " + this.singleMatch.getAwayTeamScore());
				} else {
					fixture.append(this.singleMatch.getNormalTimeHomeTeamScore() + " - " + this.singleMatch.getNormalTimeAwayTeamScore());
					fixture.append(", ");
					fixture.append(this.singleMatch.getHomeTeamScore() + " - " + this.singleMatch.getAwayTeamScore() + " (aet)");
				}
				
				if (this.singleMatch.isDecidedOnPenalties()) {
					fixture.append(", " + this.singleMatch.getHomeTeamPenaltyScore() + " - " + this.singleMatch.getAwayTeamPenaltyScore() + " (pens)");
				}
				
			} 
			
			return fixture.toString();
		}
	}
	
	@Override
	public String toString() {
		
		StringBuilder retVal = new StringBuilder();
		
		for (Pair pair:this.pairs) {
			retVal.append(pair.toString());
			retVal.append("\n");
		}
		
		return retVal.toString();
	}
	
	private String label = null;
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return this.label;
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
	
	public void updateLabel() {
		switch(participants.size()) {
		case 2:
			this.label = "Final";
			break;
		case 4:
			this.label = "Semi final";
			break;
		case 8:
			this.label = "Quarter final";
		}
	}
	
	public void addParticipants(List<Rankable> participants) {
		this.participants.addAll(participants);
	}
	
	/*
	public KnockoutRound() {
		switch(pairs.size()) {
		case 1:
			this.label = "Final";
			break;
		case 2:
			this.label = "Semi final";
			break;
		case 4:
			this.label = "Quarter final";
			break;
		default:
			this.label = null;
		}
	}
	*/
	
	public void draw() {
		
		if (this.participants.size() <= 2) {
			this.pairs.add(new Pair(this.participants.get(0), this.participants.get(1))); // Hardcode the draw
			return; 
		}
		
		Collections.shuffle(this.participants);
		for (int i = 0; i < this.participants.size(); i += 2) {
			// DEBUG
			// System.out.println(i + " vs. " + (i + 1));
			this.pairs.add(new Pair(this.participants.get(i), this.participants.get(i + 1)));
		}
	}
	
	public void play() {
		
		for (Pair pair:this.pairs) {
			
			pair.play();
			
			/**
			 * DEBUG
			 */
			// System.out.println("Winner: " + pair.getWinner().getName());
			
			this.winners.add(pair.getWinner());
		}
	}
}
