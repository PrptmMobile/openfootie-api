package org.openfootie.api.databinder;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openfootie.api.domain.sample.Lineup;
import org.openfootie.api.domain.sample.Match;
import org.openfootie.api.domain.sample.PlayerParticipation;
import org.openfootie.api.domain.sample.TacticsLine;

public class MatchDetailsDataBinderTest {
	
	private MatchDetailsDataBinder dataBinder;
	
	@Before
	public void setUp() throws Exception {
		this.dataBinder = new MatchDetailsDataBinder("resources/data/match data.liy");
		this.dataBinder.doBind();
	}
	
	@Test
	public void testBinding() {
		
		List<Match> matchDetails = this.dataBinder.getMatchDetails();
		
		assertEquals(223, matchDetails.size());
		
		// 143
		
		Match mJuveVsNapoli = matchDetails.get(143);
		
		// Home team: Juventus
		assertEquals("Juventus", mJuveVsNapoli.getHomeTeam());
		
		// Away team: Napoli
		assertEquals("Napoli", mJuveVsNapoli.getAwayTeam());
		
		// Home score: 3
		assertEquals(3, mJuveVsNapoli.getHomeScore());
		
		// Away score: 1
		assertEquals(1, mJuveVsNapoli.getAwayScore());
		
		// Juventus lineup numbers
		Lineup juventus = mJuveVsNapoli.getHomeLineup();
		
		assertEquals(4, juventus.getDefenders().getCardinality());
		assertEquals(4, juventus.getMidfielders().getCardinality());
		assertEquals(2, juventus.getForwards().getCardinality());
		assertEquals(3, juventus.getSubs().getCardinality());
		
		Lineup napoli = mJuveVsNapoli.getAwayLineup();
		
		// Napoli Goalkeeper: Andújar
		assertEquals("Andújar", napoli.getGoalkeeper());
		
		// Napoli Midfields: David Lopez, (Insigne, 0.76)
		assertEquals("David López", napoli.getMidfielders().getPlayers().get(0));
		assertEquals("Insigne", ((PlayerParticipation) napoli.getMidfielders().getPlayers().get(4)).getPlayer());
		assertEquals(0.76, ((PlayerParticipation) napoli.getMidfielders().getPlayers().get(4)).getDuration(), 0.01);
		
		// Napoli Forwards: (Higuain, 0.5)
		assertEquals("Higuaín", ((PlayerParticipation) napoli.getForwards().getPlayers().get(0)).getPlayer());
		assertEquals(0.5, ((PlayerParticipation) napoli.getForwards().getPlayers().get(0)).getDuration(), 0.1);
		
		// Napoli Subs: 3
		assertEquals(3, napoli.getSubs().getCardinality());
		
		// 41: 1, 1, 0, 1, 1, 0, 0
		Match mBarcaVsPSG = matchDetails.get(40);
		
		assertEquals("Barcelona", mBarcaVsPSG.getHomeTeam());
		assertEquals("PSG", mBarcaVsPSG.getAwayTeam());
		assertEquals(1, mBarcaVsPSG.getAwayScore());
		
		Calendar matchDate = Calendar.getInstance();
		
		matchDate.setTime(mBarcaVsPSG.getMatchDate());
		
		assertEquals(10, matchDate.get(Calendar.DAY_OF_MONTH));
		assertEquals(12, matchDate.get(Calendar.MONTH) + 1);
		assertEquals(2014, matchDate.get(Calendar.YEAR));
		
		// 66
		Match mManCityVsArsenal = matchDetails.get(65);
		
		assertEquals("Manchester City", mManCityVsArsenal.getHomeTeam());
		assertEquals("Arsenal", mManCityVsArsenal.getAwayTeam());
		assertEquals(0, mManCityVsArsenal.getHomeScore());
		assertEquals(2, mManCityVsArsenal.getAwayScore());
		
		matchDate.setTime(mManCityVsArsenal.getMatchDate());
		
		assertEquals(18, matchDate.get(Calendar.DAY_OF_MONTH));
		assertEquals(1, matchDate.get(Calendar.MONTH) + 1);
		assertEquals(2015, matchDate.get(Calendar.YEAR));
		
		// 143
		Match currentMatch = matchDetails.get(142);
		
		assertEquals("Juventus", currentMatch.getAwayTeam());
		assertEquals(1, currentMatch.getHomeScore());
		assertEquals(3, currentMatch.getAwayScore());
		
		matchDate.setTime(currentMatch.getMatchDate());
		
		assertEquals(11, matchDate.get(Calendar.DAY_OF_MONTH));
		assertEquals(1, matchDate.get(Calendar.MONTH) + 1);
		assertEquals(2015, matchDate.get(Calendar.YEAR));
		
		// Napoli
		
		TacticsLine napoliMid = currentMatch.getHomeLineup().getMidfielders();
		PlayerParticipation napCallejon = (PlayerParticipation) napoliMid.getPlayers().get(2); // Check casting
		PlayerParticipation napHamsik = (PlayerParticipation) napoliMid.getPlayers().get(3); // Check casting
		
		TacticsLine napoliAtt = currentMatch.getHomeLineup().getForwards();
		assertEquals("Higuaín", napoliAtt.getPlayers().get(0));
		
		//
		
		// Juventus
		assertEquals("Buffon", currentMatch.getAwayLineup().getGoalkeeper());
		assertEquals(0.89, ((PlayerParticipation) currentMatch.getAwayLineup().getDefenders().getPlayers().get(0)).getDuration(), 0.01);
		assertEquals("Chiellini", currentMatch.getAwayLineup().getDefenders().getPlayers().get(2));
		assertEquals("Tevez", currentMatch.getAwayLineup().getForwards().getPlayers().get(0));
	}
}
