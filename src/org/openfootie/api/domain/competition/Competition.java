package org.openfootie.api.domain.competition;

import java.util.List;

import org.openfootie.api.domain.Rankable;
import org.openfootie.api.simulator.ScoreSimulator;
import org.openfootie.api.simulator.TeamRanking;

public abstract class Competition {
	
	protected List<Rankable> participants;
	protected TeamRanking ranking;
	public static ScoreSimulator matchEngine;

}
