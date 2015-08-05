package org.openfootie.api.environment;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openfootie.api.domain.Club;
import org.openfootie.api.domain.Match;
import org.openfootie.api.domain.Nation;
import org.openfootie.api.simulator.TeamRanking;
import org.openfootie.resources.DataResources;

@Deprecated
public class EnvironmentTest {
	
	private static Environment environment;
	
	@BeforeClass
	public static void setUp() throws Exception {
		environment = new Environment(
				DataResources.ROOT + "/nations.sjon", 
				DataResources.ROOT + "/teams.sjon", 
				DataResources.ROOT + "/results_nations.sjon", 
				DataResources.ROOT + "/results_teams.sjon");
	}
	
	/*
	@Test
	public void testNationLoad() {
		assertEquals("South Korea", environment.getNations().get(28).getName());
	}
	
	@Test
	public void testClubLoad() {
		
		assertEquals("Chelsea", environment.getClubs().get(3).getName());
		
		assertEquals("Copenhagen", environment.getClubs().get(49).getName());
		
		assertEquals("Malaga", environment.getClubs().get(51).getName());
		
		assertEquals("Inter Milan", environment.getClubs().get(12).getName());
		
		assertEquals("Club Brugge", environment.getClubs().get(65).getName());
		assertEquals("Belgium", environment.getClubs().get(65).getCountryName());
		assertEquals(41.76, environment.getClubs().get(65).getSquadValue(), 0.01);
		
		assertEquals("Levante", environment.getClubs().get(66).getName());
		assertEquals(38.28, environment.getClubs().get(66).getSquadValue(), 0.01);
		
		assertEquals("Manchester United", environment.getClubs().get(5).getName());
		assertEquals("England", environment.getClubs().get(5).getCountryName());
		
		assertEquals("Lazio", environment.getClubs().get(40).getName());
		
		assertEquals("Shakhtar Donetsk", environment.getClubs().get(17).getName());
		
		assertEquals("Lille", environment.getClubs().get(48).getName());
		assertEquals("France", environment.getClubs().get(48).getCountryName());
	}
	
	@Test
	public void testNationMatchLoad() {
		
		Match match1 = environment.getNationMatches().get(116);
		Match match2 = environment.getNationMatches().get(208);
		
		assertEquals("Israel", match1.getHomeTeamName());
		assertEquals("Northern Ireland", match1.getAwayTeamName());
		assertEquals(1, match1.getHomeTeamScore().intValue());
		assertEquals(1, match1.getAwayTeamScore().intValue());
		assertEquals(false, match1.isNeutral());
		
		assertEquals("Guinea", match2.getHomeTeamName());
		assertEquals("Uganda", match2.getAwayTeamName());
		assertEquals(2, match2.getHomeTeamScore().intValue());
		assertEquals(true, match2.isNeutral());
	}
	
	@Test
	public void testClubMatchLoad() {
		
		Match match1 = environment.getClubMatches().get(12);
		Match match2 = environment.getClubMatches().get(46);
		Match match3 = environment.getClubMatches().get(72);
		
		assertEquals(0, match1.getHomeTeamScore().intValue());
		assertEquals(2, match1.getAwayTeamScore().intValue());
		assertEquals(Match.Status.PLAYED, match1.getStatus());
		
		assertEquals(1, match2.getHomeTeamScore().intValue());
		assertEquals(Match.Status.PLAYED, match2.getStatus());
		assertEquals(false, match2.isNeutral());
		
		assertEquals("Athletic Club", match3.getHomeTeamName());
		assertEquals("Seville", match3.getAwayTeamName());
		assertEquals(1, match3.getHomeTeamScore().intValue());
		assertEquals(0, match3.getAwayTeamScore().intValue());
		assertEquals(false, match3.isNeutral());
	}
	
	@Test
	public void testRankingLoad() {
	
		TeamRanking nationRanking = environment.getRankings().get(Environment.Ranking.NATION_DEFAULT);
		TeamRanking clubRanking = environment.getRankings().get(Environment.Ranking.CLUB_DEFAULT);
		
		Nation morocco = (Nation) nationRanking.getTeamByPosition(94);
		Nation antigua = (Nation) nationRanking.getTeamByPosition(78);
		Nation uganda = (Nation) nationRanking.getTeamByPosition(90);
		Nation peru = (Nation) nationRanking.getTeamByPosition(40);
		Nation sweden = (Nation) nationRanking.getTeamByPosition(36);
		Nation nigeria = (Nation) nationRanking.getTeamByPosition(23);
		Nation ukraine = (Nation) nationRanking.getTeamByPosition(33);
		Nation mali = (Nation) nationRanking.getTeamByPosition(73);
		Nation netherlands = (Nation) nationRanking.getTeamByPosition(3);
		Nation italy = (Nation) nationRanking.getTeamByPosition(14);
		Nation china = (Nation) nationRanking.getTeamByPosition(93);
		Nation scotland = (Nation) nationRanking.getTeamByPosition(44);
		Nation cameroon = (Nation) nationRanking.getTeamByPosition(28);
		
		assertEquals(94, nationRanking.getPositionByTeam(morocco).intValue());
		assertEquals(78, nationRanking.getPositionByTeam(antigua).intValue());
		assertEquals(90, nationRanking.getPositionByTeam(uganda).intValue());
		assertEquals(40, nationRanking.getPositionByTeam(peru).intValue());
		assertEquals(36, nationRanking.getPositionByTeam(sweden).intValue());
		assertEquals(23, nationRanking.getPositionByTeam(nigeria).intValue());
		assertEquals(33, nationRanking.getPositionByTeam(ukraine).intValue());
		assertEquals(73, nationRanking.getPositionByTeam(mali).intValue());
		assertEquals(3, nationRanking.getPositionByTeam(netherlands).intValue());
		assertEquals(14, nationRanking.getPositionByTeam(italy).intValue());
		assertEquals(93, nationRanking.getPositionByTeam(china).intValue());
		assertEquals(44, nationRanking.getPositionByTeam(scotland).intValue());
		assertEquals(28, nationRanking.getPositionByTeam(cameroon).intValue());
		
		Club clubBrugge = (Club) clubRanking.getTeamByPosition(66);
		Club apoel = (Club) clubRanking.getTeamByPosition(73);
		Club salzburg = (Club) clubRanking.getTeamByPosition(46);
		Club psv = (Club) clubRanking.getTeamByPosition(29);
		Club benfica = (Club) clubRanking.getTeamByPosition(5);
		
		assertEquals(66, clubRanking.getPositionByTeam(clubBrugge).intValue());
		assertEquals(73, clubRanking.getPositionByTeam(apoel).intValue());
		assertEquals(46, clubRanking.getPositionByTeam(salzburg).intValue());
		assertEquals(29, clubRanking.getPositionByTeam(psv).intValue());
		assertEquals(5, clubRanking.getPositionByTeam(benfica).intValue());
		
		assertEquals("Morocco", morocco.getName());
		assertEquals("Antigua and Barbuda", antigua.getName());
		assertEquals("Uganda", uganda.getName());
		assertEquals("Peru", peru.getName());
		assertEquals("Sweden", sweden.getName());
		assertEquals("Nigeria", nigeria.getName());
		assertEquals("Ukraine", ukraine.getName());
		assertEquals("Mali", mali.getName());
		assertEquals("Netherlands", netherlands.getName());
		assertEquals("Italy", italy.getName());
		assertEquals("China", china.getName());
		assertEquals("Scotland", scotland.getName());
		assertEquals("Cameroon", cameroon.getName());
		
		assertEquals("Club Brugge", clubBrugge.getName());
		assertEquals("APOEL", apoel.getName());
		assertEquals("Salzburg", salzburg.getName());
		assertEquals("PSV", psv.getName());
		assertEquals("Benfica", benfica.getName());
	}
	*/
}
