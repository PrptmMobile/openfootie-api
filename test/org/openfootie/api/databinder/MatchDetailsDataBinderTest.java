package org.openfootie.api.databinder;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openfootie.api.domain.sample.Match;

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
	}
}
