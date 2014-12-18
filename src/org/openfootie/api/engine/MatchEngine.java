package org.openfootie.api.engine;

import org.openfootie.api.domain.Match;

public interface MatchEngine {
	
	public void play(Match match);
	public void simulateExtraTime(Match match);
	public void simulatePenaltyShootOut(Match match);
	
}
