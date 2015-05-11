package org.openfootie.api.domain.competition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openfootie.api.domain.Match;
import org.openfootie.api.domain.Rankable;

public class CompetitionTable {
	
	private final static int WIN_POINTS = 3;
	private final static int DRAW_POINTS = 1;
	
	private class CompetitionTableRecord implements Comparable {
		
		private String teamName;
		private int points;
		
		private int homeWins;
		private int homeDraws;
		private int homeDefeats;
		
		private int awayWins;
		private int awayDraws;
		private int awayDefeats;
		
		private int homeGoalsFor;
		private int homeGoalsAgainst;
		
		private int awayGoalsFor;
		private int awayGoalsAgainst;
		
		public void addHomeGoalsFor(int goals) {
			this.homeGoalsFor += goals;
		}
		
		public void addHomeGoalsAgainst(int goals) {
			this.homeGoalsAgainst += goals;
		}
		
		public void addAwayGoalsFor(int goals) {
			this.awayGoalsFor += goals;
		}
		
		public void addAwayGoalsAgainst(int goals) {
			this.awayGoalsAgainst += goals;
		}
		
		public void addHomeWin() {
			this.homeWins++;
			this.points += WIN_POINTS;
		}
		
		public void addHomeDraw() {
			this.homeDraws++;
			this.points += DRAW_POINTS;
		}
		
		public void addHomeDefeat() {
			this.homeDefeats++;
		}
		
		public void addAwayWin() {
			this.awayWins++;
			this.points += WIN_POINTS;
		}
		
		public void addAwayDraw() {
			this.awayDraws++;
			this.points += DRAW_POINTS;
		}
		
		public void addAwayDefeat() {
			this.awayDefeats++;
		}
		
		public CompetitionTableRecord(String teamName) {
			this.teamName = teamName;
		}
		
		public int getGoalDifference() {
			return this.homeGoalsFor + this.awayGoalsFor - this.homeGoalsAgainst - this.awayGoalsAgainst;
		}
		
		public int getGoalsScored() {
			return this.homeGoalsFor + this.awayGoalsFor;
		}
		
		public int getGoalsConceded() {
			return this.homeGoalsAgainst + this.awayGoalsAgainst;
		}
		
		public int getWinsNum() {
			return this.homeWins + this.awayWins;
		}
		
		public int compareTo(Object tableRecordObject) {
			
			CompetitionTableRecord tableRecord = (CompetitionTableRecord) tableRecordObject;
			
			if (this.points == tableRecord.points) {
				if (this.getGoalDifference() == tableRecord.getGoalDifference()) {
					if (this.getGoalsScored() == tableRecord.getGoalsScored()) {
						if (this.getWinsNum() == tableRecord.getWinsNum()) {
							return 0;
						} else {
							return tableRecord.getWinsNum() - this.getWinsNum();
						}
					} else {
						return tableRecord.getGoalsScored() - this.getGoalsScored();
					}
				} else {
					return tableRecord.getGoalDifference() - this.getGoalDifference();
				}
			} else {
				return tableRecord.points - this.points;
			}
		}
	}
	
	public void initialize(List<Rankable> participants) {
		
		this.tableRecords = new HashMap<String, CompetitionTableRecord>();
		
		for (Rankable rankable: participants) {
			this.tableRecords.put(rankable.getName(), new CompetitionTableRecord(rankable.getName()));
		}
	}
	
	public void update(Match match) {
		
		CompetitionTableRecord homeTeamTableRecord = tableRecords.get(match.getHomeTeamName());
		CompetitionTableRecord awayTeamTableRecord = tableRecords.get(match.getAwayTeamName());
		
		homeTeamTableRecord.addHomeGoalsFor(match.getHomeTeamScore());
		homeTeamTableRecord.addHomeGoalsAgainst(match.getAwayTeamScore());
		
		awayTeamTableRecord.addAwayGoalsFor(match.getAwayTeamScore());
		awayTeamTableRecord.addAwayGoalsAgainst(match.getHomeTeamScore());
		
		if (match.getHomeTeamScore() == match.getAwayTeamScore()) {
			homeTeamTableRecord.addHomeDraw();
			awayTeamTableRecord.addAwayDraw();
		}
		
		if (match.getHomeTeamScore() > match.getAwayTeamScore()) {
			homeTeamTableRecord.addHomeWin();
			awayTeamTableRecord.addAwayDefeat();
		} else if (match.getAwayTeamScore() > match.getHomeTeamScore()) {
			awayTeamTableRecord.addAwayWin();
			homeTeamTableRecord.addHomeDefeat();
		}
	}
	
	private Map<String, CompetitionTableRecord> tableRecords;
	
	public void display() {
		
		List<CompetitionTableRecord> leagueTable = new ArrayList<CompetitionTableRecord>();
		
		// Sort competition table records in a list and display them
		for (CompetitionTableRecord tableRecord: this.tableRecords.values()) {
			leagueTable.add(tableRecord);
		}
		
		Collections.sort(leagueTable);
		
		final int paddingSize = 15;
		
		for (int i = 0; i < leagueTable.size(); i++) {
			
			StringBuilder tableLine = new StringBuilder();
			
			tableLine.append((i + 1) + ". ");
			tableLine.append(leagueTable.get(i).teamName);
			for (int j = 0; j < paddingSize - leagueTable.get(i).teamName.length(); j++) {
				tableLine.append(" ");
			}
			tableLine.append(leagueTable.get(i).points + "  ");
			tableLine.append(leagueTable.get(i).getGoalsScored() + " - ");
			tableLine.append(leagueTable.get(i).getGoalsConceded());
			
			System.out.println(tableLine.toString());
		}
	}
}
