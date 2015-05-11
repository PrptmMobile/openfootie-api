package org.openfootie.api.environment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openfootie.api.domain.Club;
import org.openfootie.api.domain.Match;
import org.openfootie.api.domain.Nation;
import org.openfootie.api.domain.Rankable;
import org.openfootie.api.environment.exceptions.EnvironmentLoadingException;
import org.openfootie.api.environment.exceptions.InconsistentDataException;
import org.openfootie.api.simulator.TeamRanking;
import org.sjon.db.SjonRecord;
import org.sjon.db.SjonTable;
import org.sjon.parser.SjonParsingException;
import org.sjon.parser.SjonScanningException;

public class Environment {
	
	private List<Nation> nations = new ArrayList<Nation>();
	private List<Club> clubs = new ArrayList<Club>();
	private List<Match> nationMatches = new ArrayList<Match>();
	private List<Match> clubMatches = new ArrayList<Match>();
	private Map<Ranking, TeamRanking> rankings = new HashMap<Ranking, TeamRanking>();
	
	public enum Ranking {
		NATION_DEFAULT,
		CLUB_DEFAULT
	}
	
	public Environment(String nationDataSource, String clubsDataSource, String nationMatchesDataSource, String clubMatchesDataSource) 
			throws EnvironmentLoadingException, InconsistentDataException {
		
		SjonTable nationsTable;
		SjonTable clubsTable;
		SjonTable nationMatchesTable;
		SjonTable clubMatchesTable;
		
		try {
			
			nationsTable = new SjonTable(nationDataSource);
			clubsTable = new SjonTable(clubsDataSource);
			nationMatchesTable = new SjonTable(nationMatchesDataSource);
			clubMatchesTable = new SjonTable(clubMatchesDataSource);
			
		} catch (IOException ioEx) {
			ioEx.printStackTrace();
			throw new EnvironmentLoadingException("Error loading full enviornment");
		}
			
		List<SjonRecord> nationRecords;
		List<SjonRecord> clubRecords;
		List<SjonRecord> nationMatchesRecords;
		List<SjonRecord> clubMatchesRecords;
		
		try {
		
			nationRecords = nationsTable.getData();
			clubRecords = clubsTable.getData();
			nationMatchesRecords = nationMatchesTable.getData();
			clubMatchesRecords = clubMatchesTable.getData();
			
		} catch (SjonScanningException | SjonParsingException sjonReadException) {
			throw new EnvironmentLoadingException("Invalid data source file");
		}
		
		// Load the environment object
		
		// Nations (0:position, 1:name, 2:confederation, fields:fullName)
		for (SjonRecord nationRecord:nationRecords) {
			
			String nationName = nationRecord.getValue(1);
			String confederation = nationRecord.getValue(2);
			
			Nation nation = new Nation(nationName, confederation);
			
			String fullName = nationRecord.getValue("fullName");
			
			if (fullName != null) {
				nation.setFullName(fullName);
			} else {
				nation.setFullName(nationName);
			}
			
			this.nations.add(nation);
		}
		
		// Clubs (0:position, 1:name, 2:nation, fields:value, nativeName, fullName)
		for (SjonRecord clubRecord:clubRecords) {
			
			String clubName = clubRecord.getValue(1);
			String clubNation = clubRecord.getValue(2);
			
			Club club = new Club(clubName, clubNation);
			
			if (clubRecord.getValue("value") != null) {
				club.setSquadValue(Double.parseDouble(clubRecord.getValue("value")));
			}
			if (clubRecord.getValue("nativeName") != null) {
				club.setNativeName(clubRecord.getValue("nativeName"));
			}
			if (clubRecord.getValue("fullName") != null) {
				club.setFullName(clubRecord.getValue("fullName"));
			}
			
			this.clubs.add(club);
		}
		
		TeamRanking nationRanking = new TeamRanking(this.nations);
		TeamRanking clubRanking = new TeamRanking(this.clubs);
		
		// Nation matches
		for (SjonRecord matchRecord:nationMatchesRecords) {
			
			Match match = extractMatch(matchRecord);
			
			if (!match.hasRankedOpponents(nationRanking)) {
				throw new InconsistentDataException("Match teams not ranked: " + match.getHomeTeamName() + " - " + match.getAwayTeamName());
			}
			
			this.nationMatches.add(match);
		}
		
		// Club matches
		for (SjonRecord matchRecord:clubMatchesRecords) {
			
			Match match = extractMatch(matchRecord);
			
			if (!match.hasRankedOpponents(clubRanking)) {
				throw new InconsistentDataException("Match teams not ranked: " + match.getHomeTeamName() + " - " + match.getAwayTeamName());
			}
			
			this.clubMatches.add(match);
		}
		
		this.rankings.put(Ranking.NATION_DEFAULT, new TeamRanking(this.nations));
		this.rankings.put(Ranking.CLUB_DEFAULT, new TeamRanking(this.clubs));
	}
	
	public List<Nation> getNations() {
		return nations;
	}
	
	public List<Rankable> getRankableNations() {
		
		List<Rankable> nations = new ArrayList<Rankable>();
		
		for (Nation nation:this.getNations()) {
			nations.add(nation);
		}
		
		return nations;
	}

	public List<Club> getClubs() {
		return clubs;
	}
	
	public List<Rankable> getRankableClubs() {
		
		List<Rankable> clubs = new ArrayList<Rankable>();
		
		for (Club club:this.getClubs()) {
			clubs.add(club);
		}
		
		return clubs;
	}
	
	public List<Rankable> getTopRankableClubs(int numberOfParticipants) {
			
		List<Rankable> clubs = new ArrayList<Rankable>();
		
		for (int i = 0; i < numberOfParticipants; i++) {
			clubs.add(this.clubs.get(i));
		}
		
		return clubs;
	}

	public List<Match> getNationMatches() {
		return nationMatches;
	}

	public List<Match> getClubMatches() {
		return clubMatches;
	}

	public Map<Ranking, TeamRanking> getRankings() {
		return rankings;
	}

	private Match extractMatch(SjonRecord matchRecord) {
		
		String homeTeamName = matchRecord.getValue(0);
		String awayTeamName = matchRecord.getValue(1);
		Integer homeTeamScore = Integer.parseInt(matchRecord.getValue(2));
		Integer awayTeamScore = Integer.parseInt(matchRecord.getValue(3));
		
		boolean neutral = false;
		
		if (matchRecord.getValue("neutral") != null && matchRecord.getValue("neutral").equals("true")) {
			neutral = true;
		}
		
		Match.Status status = Match.Status.PLAYED;
		
		return new Match(homeTeamName, awayTeamName, homeTeamScore, awayTeamScore, status, neutral);
	}
}
