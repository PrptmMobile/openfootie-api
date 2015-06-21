package org.openfootie.api.domain;

import org.openfootie.api.engine.MatchEngine;
import org.openfootie.api.simulator.TeamRanking;

public class Match {
	
	public static enum Status {
		FIXTURE,PLAYING,PLAYED,POSTPONED,INTERRUPTED,CANCELED,VIRTUAL
	}
	
	private Status status;
	
	private String homeTeamName;
	private String awayTeamName;
	private Integer homeTeamScore;
	private Integer awayTeamScore;
	private boolean neutral;
	
	private Integer normalTimeHomeTeamScore;
	private Integer normalTimeAwayTeamScore;
	
	private Integer homeTeamPenaltyScore;
	private Integer awayTeamPenaltyScore;
	
	private boolean extraTime;
	private boolean penalties;
	
	private int homeTeamAggregateScore;
	private int awayTeamAggregateScore;
	
	public Match(String homeTeamName, String awayTeamName, Integer homeTeamScore, Integer awayTeamScore, Status status, boolean neutral) {
		this.homeTeamName = homeTeamName;
		this.awayTeamName = awayTeamName;
		this.homeTeamScore = homeTeamScore;
		this.awayTeamScore = awayTeamScore;
		this.status = status;
		this.neutral = neutral;
	}
	
	public Match(String homeTeamName, String awayTeamName, Status status, boolean neutral) {
		this.homeTeamName = homeTeamName;
		this.awayTeamName = awayTeamName;
		this.status = status;
		this.neutral = neutral;
		this.homeTeamScore = -2;
		this.awayTeamScore = -2;
	}
	
	@Override
	public String toString() {
		
		if (this.homeTeamName == null) {
			return this.awayTeamName + " day off";
		}
		
		if (this.awayTeamName == null) {
			return this.homeTeamName + " day off";
		}
		
		return this.homeTeamName + " - " + this.awayTeamName + " " + this.homeTeamScore + " - " + this.awayTeamScore;
	}
	
	public void play(MatchEngine matchEngine) {
		matchEngine.play(this);
		this.status = Status.PLAYED;
	}
	
	public void setDecidedOnPenalties() {
		this.penalties = true;
	}
	
	public void saveExtraTime() {
		this.extraTime = true;
		this.normalTimeHomeTeamScore = this.homeTeamScore;
		this.normalTimeAwayTeamScore = this.awayTeamScore;
	}
	
	public void play(MatchEngine matchEngine, boolean drawAllowed) {
		
		if (this.homeTeamName == null) {
			// System.out.println(this.awayTeamName + " day off");
			this.status = Status.VIRTUAL;
			return;
		}
		
		if (this.awayTeamName == null) {
			// System.out.println(this.homeTeamName + " day off");
			this.status = Status.VIRTUAL;
			return;
		}
		
		matchEngine.play(this);
		
		if (!drawAllowed) {
			if (this.homeTeamScore == this.awayTeamScore) {
				matchEngine.simulateExtraTime(this);
			}
			if (this.homeTeamScore == this.awayTeamScore) {
				matchEngine.simulatePenaltyShootOut(this);
			}
		}
		this.status = Status.PLAYED;
	}
	
	public void play(MatchEngine matchEngine, int homeAggregateScore, int awayAggregateScore) {
		
		matchEngine.play(this);
		this.homeTeamAggregateScore = this.homeTeamScore + homeAggregateScore;
		this.awayTeamAggregateScore = this.awayTeamScore + awayAggregateScore;
		
		if (this.homeTeamAggregateScore == this.awayTeamAggregateScore) {
			
			int homeTeamAwayGoals = homeAggregateScore;
			
			// Check away goals
			if (this.awayTeamScore == homeTeamAwayGoals) { // Tie break until penalty-shoot-out. Otherwise no special tie breaking action should take place (away goals rule)
				
				matchEngine.simulateExtraTime(this);
				this.homeTeamAggregateScore += (this.homeTeamScore - this.normalTimeHomeTeamScore);
				this.awayTeamAggregateScore += (this.awayTeamScore - this.normalTimeAwayTeamScore);
				
				if (this.homeTeamAggregateScore == this.awayTeamAggregateScore) { 
					matchEngine.simulatePenaltyShootOut(this);
				}
			} 
		}
		this.status = Status.PLAYED;
	}

	public boolean hasRankedOpponents(TeamRanking ranking) {
		return (ranking.getPositionByName(this.homeTeamName) != null && ranking.getPositionByName(this.awayTeamName) != null);
	}
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Integer getHomeTeamScore() {
		return homeTeamScore;
	}

	public void setHomeTeamScore(Integer homeTeamScore) {
		this.homeTeamScore = homeTeamScore;
	}

	public Integer getAwayTeamScore() {
		return awayTeamScore;
	}

	public void setAwayTeamScore(Integer awayTeamScore) {
		this.awayTeamScore = awayTeamScore;
	}

	public String getHomeTeamName() {
		return homeTeamName;
	}

	public String getAwayTeamName() {
		return awayTeamName;
	}

	public boolean isNeutral() {
		return neutral;
	}

	public Integer getNormalTimeHomeTeamScore() {
		return normalTimeHomeTeamScore;
	}

	public void setNormalTimeHomeTeamScore(Integer normalTimeHomeTeamScore) {
		this.normalTimeHomeTeamScore = normalTimeHomeTeamScore;
	}

	public Integer getNormalTimeAwayTeamScore() {
		return normalTimeAwayTeamScore;
	}

	public void setNormalTimeAwayTeamScore(Integer normalTimeAwayTeamScore) {
		this.normalTimeAwayTeamScore = normalTimeAwayTeamScore;
	}

	public Integer getHomeTeamPenaltyScore() {
		return homeTeamPenaltyScore;
	}

	public void setHomeTeamPenaltyScore(Integer homeTeamPenaltyScore) {
		this.homeTeamPenaltyScore = homeTeamPenaltyScore;
	}

	public Integer getAwayTeamPenaltyScore() {
		return awayTeamPenaltyScore;
	}

	public void setAwayTeamPenaltyScore(Integer awayTeamPenaltyScore) {
		this.awayTeamPenaltyScore = awayTeamPenaltyScore;
	}

	public boolean isExtraTimePlayed() {
		return extraTime;
	}

	public boolean isDecidedOnPenalties() {
		return penalties;
	}
	
	public int getHomeTeamAggregateScore() {
		return this.homeTeamAggregateScore;
	}
	
	public int getAwayTeamAggregateScore() {
		return this.awayTeamAggregateScore;
	}
}
