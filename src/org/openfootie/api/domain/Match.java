package org.openfootie.api.domain;

public class Match {
	
	public static enum Status {
		FIXTURE,PLAYING,PLAYED,POSTPONED,INTERRUPTED,CANCELED
	}
	
	private Status status;
	
	private String homeTeamName;
	private String awayTeamName;
	private Integer homeTeamScore;
	private Integer awayTeamScore;
	private boolean neutral;
	
	public Match(String homeTeamName, String awayTeamName, Integer homeTeamScore, Integer awayTeamScore, Status status, boolean neutral) {
		this.homeTeamName = homeTeamName;
		this.awayTeamName = awayTeamName;
		this.homeTeamScore = homeTeamScore;
		this.awayTeamScore = awayTeamScore;
		this.status = status;
		this.neutral = neutral;
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

}
