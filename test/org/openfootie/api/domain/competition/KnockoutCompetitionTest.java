package org.openfootie.api.domain.competition;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openfootie.api.environment.Environment;
import org.openfootie.resources.DataResources;

public class KnockoutCompetitionTest {
	
	private static Environment environment;
	
	private KnockoutCompetition clubCompetition;
	private KnockoutCompetition nationCompetition;
	
	@BeforeClass
	public static void setUp() throws Exception {
		
		environment = new Environment(
				DataResources.ROOT + "/nations.sjon", 
				DataResources.ROOT + "/teams.sjon", 
				DataResources.ROOT + "/results_nations.sjon", 
				DataResources.ROOT + "/results_teams.sjon");
	}
	
	@Test
	public void testClubCompetition() {	
		this.clubCompetition = 
				new KnockoutCompetition(environment.getRankableClubs(), environment.getRankings().get(Environment.Ranking.CLUB_DEFAULT), environment.getClubMatches(),
						KnockoutCompetitionTemplate.SINGLE_MATCH_NEUTRAL, KnockoutCompetitionTemplate.SINGLE_MATCH_NEUTRAL);
		this.clubCompetition.play();
		System.out.println(this.clubCompetition.toString());
	}
	
	@Test
	public void testNationCompetition() {
		this.nationCompetition = 
				new KnockoutCompetition(environment.getRankableNations(), environment.getRankings().get(Environment.Ranking.NATION_DEFAULT), environment.getNationMatches(),
						KnockoutCompetitionTemplate.SINGLE_MATCH_NEUTRAL, KnockoutCompetitionTemplate.SINGLE_MATCH_NEUTRAL);
		this.nationCompetition.play();
		System.out.println(this.nationCompetition.toString());
	}
}
