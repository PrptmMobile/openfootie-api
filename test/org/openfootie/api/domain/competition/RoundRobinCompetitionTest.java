package org.openfootie.api.domain.competition;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openfootie.api.environment.Environment;
import org.openfootie.resources.DataResources;

public class RoundRobinCompetitionTest {
	
	private static Environment environment;
	private RoundRobinCompetition roundRobinCompetition;
	
	@BeforeClass
	public static void setUp() throws Exception {
		
		environment = new Environment(
				DataResources.ROOT + "/nations.sjon", 
				DataResources.ROOT + "/teams.sjon", 
				DataResources.ROOT + "/results_nations.sjon", 
				DataResources.ROOT + "/results_teams.sjon");
	}
	
	@Test
	public void testRoundRobinCompetition_4() {
		this.roundRobinCompetition = 
				new RoundRobinCompetition(environment.getTopRankableClubs(4), environment.getRankings().get(Environment.Ranking.CLUB_DEFAULT), environment.getClubMatches());
		this.roundRobinCompetition.generateSchedule();
		RoundRobinSchedule schedule = this.roundRobinCompetition.getSchedule();
		
		System.out.println("Fixtures");
		System.out.println();
		System.out.println(schedule);
		
		this.roundRobinCompetition.play();
		this.roundRobinCompetition.display();
	}
	
	@Test
	public void testRoundRobinCompetition_5() {
		this.roundRobinCompetition = 
				new RoundRobinCompetition(environment.getTopRankableClubs(5), environment.getRankings().get(Environment.Ranking.CLUB_DEFAULT), environment.getClubMatches());
		this.roundRobinCompetition.generateSchedule();
		RoundRobinSchedule schedule = this.roundRobinCompetition.getSchedule();
		System.out.println(schedule);
	}
	
	@Test
	public void testRoundRobinCompetition_6() {
		this.roundRobinCompetition = 
				new RoundRobinCompetition(environment.getTopRankableClubs(6), environment.getRankings().get(Environment.Ranking.CLUB_DEFAULT), environment.getClubMatches());
		this.roundRobinCompetition.generateSchedule();
		RoundRobinSchedule schedule = this.roundRobinCompetition.getSchedule();
		System.out.println(schedule);
	}
	
	@Test
	public void testRoundRobinCompetition_8() {
		this.roundRobinCompetition = 
				new RoundRobinCompetition(environment.getTopRankableClubs(8), environment.getRankings().get(Environment.Ranking.CLUB_DEFAULT), environment.getClubMatches());
		this.roundRobinCompetition.generateSchedule();
		RoundRobinSchedule schedule = this.roundRobinCompetition.getSchedule();
		System.out.println(schedule);
	}
	
	@Test
	public void testRoundRobinCompetition_10() {
		this.roundRobinCompetition = 
				new RoundRobinCompetition(environment.getTopRankableClubs(10), environment.getRankings().get(Environment.Ranking.CLUB_DEFAULT), environment.getClubMatches());
		this.roundRobinCompetition.generateSchedule();
		RoundRobinSchedule schedule = this.roundRobinCompetition.getSchedule();
		System.out.println(schedule);
	}
	
	@Test
	public void testRoundRobinCompetition_12() {
		this.roundRobinCompetition = 
				new RoundRobinCompetition(environment.getTopRankableClubs(12), environment.getRankings().get(Environment.Ranking.CLUB_DEFAULT), environment.getClubMatches());
		this.roundRobinCompetition.generateSchedule();
		RoundRobinSchedule schedule = this.roundRobinCompetition.getSchedule();
		System.out.println(schedule);
	}
	
	@Test
	public void testRoundRobinCompetition_14() {
		this.roundRobinCompetition = 
				new RoundRobinCompetition(environment.getTopRankableClubs(14), environment.getRankings().get(Environment.Ranking.CLUB_DEFAULT), environment.getClubMatches());
		this.roundRobinCompetition.generateSchedule();
		RoundRobinSchedule schedule = this.roundRobinCompetition.getSchedule();
		System.out.println(schedule);
	}
	
	@Test
	public void testRoundRobinCompetition_16() {
		this.roundRobinCompetition = 
				new RoundRobinCompetition(environment.getTopRankableClubs(16), environment.getRankings().get(Environment.Ranking.CLUB_DEFAULT), environment.getClubMatches());
		this.roundRobinCompetition.generateSchedule();
		RoundRobinSchedule schedule = this.roundRobinCompetition.getSchedule();
		System.out.println(schedule);
	}
	
	@Test
	public void testRoundRobinCompetition_18() {
		this.roundRobinCompetition = 
				new RoundRobinCompetition(environment.getTopRankableClubs(18), environment.getRankings().get(Environment.Ranking.CLUB_DEFAULT), environment.getClubMatches());
		this.roundRobinCompetition.generateSchedule();
		RoundRobinSchedule schedule = this.roundRobinCompetition.getSchedule();
		System.out.println(schedule);
	}
	
	@Test
	public void testRoundRobinCompetition_20() {
		this.roundRobinCompetition = 
				new RoundRobinCompetition(environment.getTopRankableClubs(20), environment.getRankings().get(Environment.Ranking.CLUB_DEFAULT), environment.getClubMatches());
		this.roundRobinCompetition.generateSchedule();
		RoundRobinSchedule schedule = this.roundRobinCompetition.getSchedule();
		System.out.println(schedule);
	}
	
	@Test
	public void testRoundRobinCompetition_22() {
		this.roundRobinCompetition = 
				new RoundRobinCompetition(environment.getTopRankableClubs(22), environment.getRankings().get(Environment.Ranking.CLUB_DEFAULT), environment.getClubMatches());
		this.roundRobinCompetition.generateSchedule();
		RoundRobinSchedule schedule = this.roundRobinCompetition.getSchedule();
		System.out.println(schedule);
	}
	
	@Test
	public void testRoundRobinCompetition_24() {
		this.roundRobinCompetition = 
				new RoundRobinCompetition(environment.getTopRankableClubs(24), environment.getRankings().get(Environment.Ranking.CLUB_DEFAULT), environment.getClubMatches());
		this.roundRobinCompetition.generateSchedule();
		RoundRobinSchedule schedule = this.roundRobinCompetition.getSchedule();
		System.out.println(schedule);
	}
}
