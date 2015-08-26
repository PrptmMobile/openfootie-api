package org.openfootie.api.domain.sample;

import java.util.Date;

public class Match {
	
	private String homeTeam;
	private String awayTeam;
	private int homeScore;
	private int awayScore;
	private Date matchDate;
	private Lineup homeLineup;
	private Lineup awayLineup;
	private boolean neutralVenue;
	
	public static class Builder {
		
		private final String homeTeam;
		private final String awayTeam;
		
		private int homeScore = -1;
		private int awayScore = -1;
		private Date matchDate;
		private Lineup homeLineup = null;
		private Lineup awayLineup = null;
		private boolean neutralVenue = false;
		
		public Builder(String homeTeam, String awayTeam) {
			this.homeTeam = homeTeam;
			this.awayTeam = awayTeam;
		}
		
		public Builder homeScore(int score) {
			this.homeScore = score;
			return this;
		}
		
		public Builder awayScore(int score) {
			this.awayScore = score;
			return this;
		}
		
		public Builder matchDate(Date date) {
			this.matchDate = date;
			return this;
		}
		
		public Builder homeLineup(Lineup lineup) {
			this.homeLineup = lineup;
			return this;
		}
		
		public Builder awayLineup(Lineup lineup) {
			this.awayLineup = lineup;
			return this;
		}
		
		public Builder neutralVenue(boolean neutralVenue) {
			this.neutralVenue = neutralVenue;
			return this;
		}
		
		public Match build() {
			return new Match(this);
		}	
	}

	private Match(Builder builder) {
		this.homeTeam = builder.homeTeam;
		this.awayTeam = builder.awayTeam;
		this.homeScore = builder.homeScore;
		this.awayScore = builder.awayScore;
		this.matchDate = builder.matchDate;
		this.homeLineup = builder.homeLineup;
		this.awayLineup = builder.awayLineup;
		this.neutralVenue = builder.neutralVenue;
	}

	public String getHomeTeam() {
		return homeTeam;
	}

	public String getAwayTeam() {
		return awayTeam;
	}

	public int getHomeScore() {
		return homeScore;
	}

	public int getAwayScore() {
		return awayScore;
	}

	public Date getMatchDate() {
		return matchDate;
	}

	public Lineup getHomeLineup() {
		return homeLineup;
	}

	public Lineup getAwayLineup() {
		return awayLineup;
	}

	public boolean isNeutralVenue() {
		return neutralVenue;
	}
	
}
