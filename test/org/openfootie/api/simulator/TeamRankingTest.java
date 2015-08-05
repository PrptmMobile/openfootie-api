package org.openfootie.api.simulator;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openfootie.api.domain.Rankable;
import org.openfootie.api.environment.Environment;
import org.openfootie.resources.DataResources;

public class TeamRankingTest {
	
	private static Environment environment;
	
	private TeamRanking clubRanking;
	private TeamRanking nationRanking;
	
	@Before
	public void setUp() throws Exception {
		
		environment = new Environment(
				DataResources.ROOT + "/nations.sjon", 
				DataResources.ROOT + "/teams.sjon", 
				DataResources.ROOT + "/results_nations.sjon", 
				DataResources.ROOT + "/results_teams.sjon");
		
		clubRanking = environment.getRankings().get(Environment.Ranking.CLUB_DEFAULT);
		nationRanking = environment.getRankings().get(Environment.Ranking.NATION_DEFAULT);
	}
	
	/*
	@Test
	public void testQuantiles() {
		
		List<List<Rankable>> clubQuantiles;
		List<List<Rankable>> nationQuantiles;
		
		// 1 quantile
		clubQuantiles = clubRanking.getQuantiles(1);
		nationQuantiles = nationRanking.getQuantiles(1);
		
		assertEquals(1, clubQuantiles.size());
		assertEquals(1, nationQuantiles.size());
		
		assertEquals(76, clubQuantiles.get(0).size());
		
		Rankable leverkusen = clubQuantiles.get(0).get(23);
		assertEquals("Leverkusen", leverkusen.getName());
		
		assertEquals(97, nationQuantiles.get(0).size());
		
		Rankable uganda = nationQuantiles.get(0).get(89);
		assertEquals("Uganda", uganda.getName());
		
		// 2 quantiles
		clubQuantiles = clubRanking.getQuantiles(2);
		nationQuantiles = nationRanking.getQuantiles(2);
		
		assertEquals(2, clubQuantiles.size());
		assertEquals(2, nationQuantiles.size());
		
		assertEquals(38, clubQuantiles.get(0).size());
		assertEquals(38, clubQuantiles.get(1).size());
		
		Rankable barcelona = clubQuantiles.get(0).get(1);
		assertEquals("Barcelona", barcelona.getName());
		
		Rankable spartak = clubQuantiles.get(1).get(20);
		assertEquals("Spartak Moscow", spartak.getName());
		
		assertEquals(49, nationQuantiles.get(0).size());
		assertEquals(48, nationQuantiles.get(1).size());
		
		Rankable honduras = nationQuantiles.get(0).get(31);
		assertEquals("Honduras", honduras.getName());
		
		Rankable gabon = nationQuantiles.get(1).get(27);
		assertEquals("Gabon", gabon.getName());
		
		// 3 quantiles
		clubQuantiles = clubRanking.getQuantiles(3);
		nationQuantiles = nationRanking.getQuantiles(3);
		
		assertEquals(3, clubQuantiles.size());
		assertEquals(3, nationQuantiles.size());
		
		assertEquals(26, clubQuantiles.get(0).size());
		assertEquals(25, clubQuantiles.get(1).size());
		assertEquals(25, clubQuantiles.get(2).size());
		
		Rankable benfica = clubQuantiles.get(0).get(4);
		assertEquals("Benfica", benfica.getName());
		
		Rankable villarreal = clubQuantiles.get(1).get(12);
		assertEquals("Villarreal", villarreal.getName());
		
		Rankable hamburger = clubQuantiles.get(2).get(10);
		assertEquals("Hamburger", hamburger.getName());
		
		assertEquals(33, nationQuantiles.get(0).size());
		assertEquals(32, nationQuantiles.get(1).size());
		assertEquals(32, nationQuantiles.get(2).size());
		
		Rankable france = nationQuantiles.get(0).get(6);
		assertEquals("France", france.getName());
		
		Rankable panama = nationQuantiles.get(1).get(11);
		assertEquals("Panama", panama.getName());
		
		Rankable uganda3 = nationQuantiles.get(2).get(24);
		assertEquals("Uganda", uganda3.getName());
		
		// quartiles
		clubQuantiles = clubRanking.getQuantiles(4);
		nationQuantiles = nationRanking.getQuantiles(4);
		
		assertEquals(4, clubQuantiles.size());
		assertEquals(4, nationQuantiles.size());
		
		assertEquals(19, clubQuantiles.get(0).size());
		assertEquals(19, clubQuantiles.get(1).size());
		assertEquals(19, clubQuantiles.get(2).size());
		assertEquals(19, clubQuantiles.get(3).size());
		
		Rankable dortmund = clubQuantiles.get(0).get(14);
		assertEquals("Dortmund", dortmund.getName());
		
		Rankable psv = clubQuantiles.get(1).get(9);
		assertEquals("PSV", psv.getName());
		
		Rankable fener = clubQuantiles.get(2).get(14);
		assertEquals("Fenerbahce", fener.getName());
		
		Rankable levante = clubQuantiles.get(3).get(9);
		assertEquals("Levante", levante.getName());
		
		assertEquals(25, nationQuantiles.get(0).size());
		assertEquals(24, nationQuantiles.get(1).size());
		assertEquals(24, nationQuantiles.get(2).size());
		assertEquals(24, nationQuantiles.get(3).size());
		
		Rankable algeria = nationQuantiles.get(0).get(18);
		assertEquals("Algeria", algeria.getName());
		
		Rankable russia = nationQuantiles.get(1).get(4);
		assertEquals("Russia", russia.getName());
		
		Rankable bulgaria = nationQuantiles.get(2).get(3);
		assertEquals("Bulgaria", bulgaria.getName());
		
		Rankable china = nationQuantiles.get(3).get(19);
		assertEquals("China", china.getName());
	}
	*/
}
