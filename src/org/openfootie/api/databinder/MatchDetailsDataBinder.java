package org.openfootie.api.databinder;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.lily.parser.ListItem;
import org.lily.parser.Parser;
import org.lily.parser.exceptions.LookaheadException;
import org.lily.scanner.Analyzer;
import org.openfootie.api.domain.sample.Lineup;
import org.openfootie.api.domain.sample.Match;

public class MatchDetailsDataBinder {
	
	private static final int HOME_TEAM_OFFSET = 0;
	private static final int AWAY_TEAM_OFFSET = 1;
	private static final int HOME_SCORE_OFFSET = 2;
	private static final int AWAY_SCORE_OFFSET = 3;
	private static final int DATE_OFFSET = 4;
	private static final int HOME_LINEUP_OFFSET = 5;
	private static final int AWAY_LINEUP_OFFSET = 6;
	private static final int VENUE_OFFSET = 7;
	
	private static final int OPTIONAL_FIELDS_OFFSET = 7;
	
	private String file;
	private List<Match> matchDetails = new ArrayList<Match>();
	
	private int recordCounter = 0;
	
	public MatchDetailsDataBinder(String file) {
		this.file = file;
	}
	
	public List<Match> getMatchDetails() {
		return this.matchDetails;
	}
	
	public void doBind() throws IOException, LookaheadException {
	
		Analyzer analyzer = new Analyzer(readFile(this.file, StandardCharsets.UTF_8));
		Parser parser = new Parser(analyzer);
		parser.parse();

		List<List<ListItem>> matches = parser.getDocument();
		
		for (List<ListItem> rawMatchDetails: matches) {
			
			++this.recordCounter;
			
			try {
				this.matchDetails.add(bindMatch(rawMatchDetails));
			} catch (NullPointerException | IndexOutOfBoundsException ex ) {
				System.out.println("Exception at record: " + this.recordCounter);
				// ex.printStackTrace();
				// break;
			}
		}
	}
	
	private Match bindMatch(List<ListItem> rawMatchDetails) {
		
		Match match = null;
		
		String homeTeam = rawMatchDetails.get(HOME_TEAM_OFFSET).getLiteral();
		String awayTeam = rawMatchDetails.get(AWAY_TEAM_OFFSET).getLiteral();
		
		try {
		
			int homeTeamScore = Integer.parseInt(rawMatchDetails.get(HOME_SCORE_OFFSET).getLiteral());
			int awayTeamScore = Integer.parseInt(rawMatchDetails.get(AWAY_SCORE_OFFSET).getLiteral());
			
			String matchDateFormat = "dd-MM-yyyy";
			String dateStr = rawMatchDetails.get(DATE_OFFSET).getLiteral();
			
			Date matchDate = new SimpleDateFormat(matchDateFormat).parse(dateStr);
			
			Lineup homeLineup = Lineup.createLineup(rawMatchDetails.get(HOME_LINEUP_OFFSET).getListContent());
			Lineup awayLineup = Lineup.createLineup(rawMatchDetails.get(AWAY_LINEUP_OFFSET).getListContent());
			
			boolean neutralVenue = false;
			
			if (rawMatchDetails.size() > OPTIONAL_FIELDS_OFFSET) {
				if (rawMatchDetails.get(VENUE_OFFSET).getLiteral().equals("neutral")) {
					neutralVenue = true;
				}
			}
			
			match = new Match.Builder(homeTeam, awayTeam)
							.homeScore(homeTeamScore)
							.awayScore(awayTeamScore)
							.matchDate(matchDate)
							.homeLineup(homeLineup)
							.awayLineup(awayLineup)
							.neutralVenue(neutralVenue).build();
			
		} catch (NumberFormatException nfex) {
			System.out.println("Invalid number in record: " + this.recordCounter + ", " + homeTeam + " - " + awayTeam);
			// nfex.printStackTrace();
		} catch (ParseException dpex) {
			System.out.println("Invalid date in record: " + this.recordCounter + ", " + homeTeam + " - " + awayTeam);
		}
		
		return match;
	}
	
	private String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
}
