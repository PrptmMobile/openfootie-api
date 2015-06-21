package org.openfootie.api.domain;

import org.openfootie.api.domain.Match.Status;

public class Fixture {
	
	private Rankable homeTeam;
	private Rankable awayTeam;
	
	public Fixture(Rankable homeTeam, Rankable awayTeam) {
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
	}
	
	public Rankable getHomeTeam() {
		return this.homeTeam;
	}
	
	public Rankable getAwayTeam() {
		return this.awayTeam;
	}
	
	public Fixture getReverse() {
		return new Fixture(this.awayTeam, this.homeTeam);
	}
	
	public Match createMatch() {
		/**
		 * DEBUG
		*/
		/*
		System.out.println("DEBUG: Creating match");
		System.out.println("Home team: " + homeTeam.getName());
		System.out.println("Away team: " + awayTeam.getName());
		System.out.println();
		*/
		return new Match(homeTeam.getName(), awayTeam.getName(), Status.FIXTURE, false);
	}
	
	@Override
	public String toString() {
		
		if (homeTeam.getName() == null) {
			return this.awayTeam.getName() + " day off";
		}
		
		if (awayTeam.getName() == null) {
			return this.homeTeam.getName() + " day off";
		}
		
		return this.homeTeam.getName() + " - " + this.awayTeam.getName();
	}
}
