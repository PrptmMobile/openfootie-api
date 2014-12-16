package org.openfootie.api.simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openfootie.api.domain.Match;
import org.openfootie.api.domain.Rankable;
import org.openfootie.api.engine.MatchEngine;

public class ScoreSimulator implements MatchEngine {
	
	private TeamRanking teamRanking;
	private List<Match> sampleMatches;
	
	private int QUANTILES = 5;
	
	private static Random rnd = new Random();

	public ScoreSimulator(TeamRanking teamRanking, List<Match> sampleMatches) {
		this.teamRanking = teamRanking;
		this.sampleMatches = sampleMatches;
	}
	
	void setQuantiles(int quantiles) {
		QUANTILES = quantiles;
	}
	
	public void play(Match match) {
	
		// Get match details
		String homeTeamName = match.getHomeTeamName();
		String awayTeamName = match.getAwayTeamName();
		
		// Split ranked teams to quantiles
		List<List<Rankable>> teamQuantiles = teamRanking.getQuantiles(QUANTILES);
		
		List<List<String>> quantileRangesIndex = new ArrayList<List<String>>();
		
		for (List<Rankable> quantileRange:teamQuantiles) {
			
			List<String> currentRangeIndex = new ArrayList<String>();
			
			for (Rankable team:quantileRange) {
				currentRangeIndex.add(team.getName());
			}
			
			quantileRangesIndex.add(currentRangeIndex);
		}
		
		int homeTeamQuantileRange = getTeamQuantile(homeTeamName, quantileRangesIndex);
		int awayTeamQuantileRange = getTeamQuantile(awayTeamName, quantileRangesIndex);
		
		List<Match> filteredHASample = new ArrayList<Match>();
		List<Match> filteredNSample = new ArrayList<Match>(); // Neutral
		List<Match> filteredPNSample = new ArrayList<Match>(); // Pseudo-neutral
		List<Match> filteredRNSample = new ArrayList<Match>(); // Reverse-neutral
		
		// Filter match sample
		for (Match sampleMatch:sampleMatches) {
			
			// System.out.println(sampleMatch);
		
			int sampleHomeTeamQlRange = getTeamQuantile(sampleMatch.getHomeTeamName(), quantileRangesIndex);
			int sampleAwayTeamQlRange = getTeamQuantile(sampleMatch.getAwayTeamName(), quantileRangesIndex);
			
			if (
				sampleHomeTeamQlRange == homeTeamQuantileRange && 
				sampleAwayTeamQlRange == awayTeamQuantileRange &&
				!sampleMatch.isNeutral()
			) {
				filteredHASample.add(sampleMatch);
				filteredPNSample.add(sampleMatch);
				// System.out.println("HA");
			}
			if (
				sampleHomeTeamQlRange == awayTeamQuantileRange &&
				sampleAwayTeamQlRange == homeTeamQuantileRange &&
				!sampleMatch.isNeutral() 
			) {
				// System.out.println("RN");
				filteredRNSample.add(sampleMatch);
			}
			if (sampleMatch.isNeutral()) {
				// System.out.println("Neutral");
				if (
					sampleHomeTeamQlRange == homeTeamQuantileRange && 
					sampleAwayTeamQlRange == awayTeamQuantileRange
				) {
					filteredNSample.add(sampleMatch);
				}
				if (
					sampleHomeTeamQlRange == awayTeamQuantileRange &&
					sampleAwayTeamQlRange == homeTeamQuantileRange
				) {
					filteredNSample.add(sampleMatch);
				}
			}
		}
		
		// Simple case; just use the sample directly
		if (!match.isNeutral()) {
			
			int simMatchIndex = rnd.nextInt(filteredHASample.size());
			Match simMatch = filteredHASample.get(simMatchIndex);
			
			match.setHomeTeamScore(simMatch.getHomeTeamScore());
			match.setAwayTeamScore(simMatch.getAwayTeamScore());
			
		} else { // Simulate venue neutrality
			
			int simNeutralMatchIndex = -1;
			int simPNeutralMatchIndex = -1;
			int simRNNeutralMatchIndex = -1;
			
			try {
				simNeutralMatchIndex = rnd.nextInt(filteredNSample.size());
			} catch (IllegalArgumentException iaex) {}
			
			try {
				simPNeutralMatchIndex = rnd.nextInt(filteredPNSample.size());
				simRNNeutralMatchIndex = rnd.nextInt(filteredRNSample.size());
			} catch (IllegalArgumentException iaex) {}
			
			Match simPNMatch = null;
			Match simRNMatch = null;
			
			Match simNMatch = null;
			
			if (simPNeutralMatchIndex > 0 && simRNNeutralMatchIndex > 0) {
				simPNMatch = filteredPNSample.get(simPNeutralMatchIndex);
				simRNMatch = filteredRNSample.get(simRNNeutralMatchIndex);
			}
			
			if (simNeutralMatchIndex > 0) {
				simNMatch = filteredNSample.get(simNeutralMatchIndex);
			}
			
			long simHomeScore = -1;
			long simAwayScore = -1;
			
			if (simPNMatch != null && simRNMatch != null) {
				simHomeScore = Math.round((simPNMatch.getHomeTeamScore() + simRNMatch.getAwayTeamScore()) / 2d);
				simAwayScore = Math.round((simPNMatch.getAwayTeamScore() + simRNMatch.getHomeTeamScore()) / 2d);	
			}
			
			long finalHomeScore = -1;
			long finalAwayScore = -1;
			
			if (simHomeScore > -1 && simAwayScore > -1 && simNMatch != null) {
				finalHomeScore = Math.round((simNMatch.getHomeTeamScore() + simHomeScore) / 2d);
				finalAwayScore = Math.round((simNMatch.getAwayTeamScore() + simAwayScore) / 2d);
			} else if (simNMatch != null) {
				finalHomeScore = simNMatch.getHomeTeamScore();
				finalAwayScore = simNMatch.getAwayTeamScore();
			} else {
				finalHomeScore = simHomeScore;
				finalAwayScore = simAwayScore;
			}
			
			match.setHomeTeamScore( (int) finalHomeScore);
			match.setAwayTeamScore( (int) finalAwayScore);
		}
	}

	private int getTeamQuantile(String teamName, List<List<String>> quantileRangesIndex) {
		for (int i = 0; i < quantileRangesIndex.size(); i++) {
			if (quantileRangesIndex.get(i).contains(teamName)) {
				return i;
			}
		}
		return -1;
	}
}
