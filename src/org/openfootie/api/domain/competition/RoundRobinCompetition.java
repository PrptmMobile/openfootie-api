package org.openfootie.api.domain.competition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openfootie.api.domain.DummyTeam;
import org.openfootie.api.domain.Fixture;
import org.openfootie.api.domain.Match;
import org.openfootie.api.domain.Rankable;
import org.openfootie.api.simulator.ScoreSimulator;
import org.openfootie.api.simulator.TeamRanking;

public class RoundRobinCompetition extends Competition {
	
	private RoundRobinSchedule schedule;
	private CompetitionTable table = new CompetitionTable();
	
	public RoundRobinCompetition(List<Rankable> participants, TeamRanking ranking, List<Match> sampleMatches) {

		matchEngine = new ScoreSimulator(ranking, sampleMatches);
		
		this.participants = participants;
		this.ranking = ranking.filterByParticipants(this.participants);
		
		this.table.initialize(participants);
	}
	
	public void play() {
		for (RoundRobinRound round: schedule.getRounds()) {
			round.play(this.table);
		}
	}
	
	public void display() {
		
		int matchDayCounter = 0;
		
		for (RoundRobinRound round: schedule.getRounds()) {
			matchDayCounter++;
			System.out.println("Round: " + matchDayCounter);
			System.out.println();
			round.display();
			System.out.println();
		}
		
		System.out.println("Competition table");
		System.out.println();
		
		this.table.display();
	}
	
	public CompetitionTable getTable() {
		return this.table;
	}
	
	private class ScheduleGraph {
		
		private Map<Integer, Rankable> representationList = new HashMap<Integer, Rankable>();
		int fixedNodeIndex;
		
		public ScheduleGraph(List<Rankable> participants) {
			
			int count = 0;
			
			for (Rankable participant: participants) {
				// System.out.println("Add participant team: " + participant.getName());
				this.representationList.put(count++, participant);
			}
			
			if (this.representationList.size() % 2 != 0) {
				this.representationList.put(count++, new DummyTeam());
			}
			
			fixedNodeIndex = count - 1;
		}
		
		public void rotate() {
			
			Map<Integer, Rankable> rotatedList = new HashMap<Integer, Rankable>();
			
			// System.out.println("Fixed node index: " + this.fixedNodeIndex);
			
			for (Map.Entry<Integer, Rankable> teamEntry: this.representationList.entrySet()) {
				
				int currentKey = teamEntry.getKey() + 1;
				
				if (currentKey == this.fixedNodeIndex) {
					currentKey = 0;
				}
				
				if (currentKey > this.fixedNodeIndex) {
					currentKey = this.fixedNodeIndex;
				}
				
				rotatedList.put(currentKey, teamEntry.getValue());
			}
			
			/*
			System.out.println("Current rotation");
			System.out.println();
			for (Map.Entry<Integer, Rankable> teamEntry: rotatedList.entrySet()) {
				System.out.println(teamEntry.getValue().getName());
			}
			System.out.println();
			*/
			
			this.representationList.clear();
			for (Map.Entry<Integer, Rankable> teamEntry: rotatedList.entrySet()) {
				// System.out.println(teamEntry.getKey() + " -> " + teamEntry.getValue());
				this.representationList.put(teamEntry.getKey(), teamEntry.getValue());
			}
		}
	}
	
	public RoundRobinRound generateRound(List<List<Integer>> pairsIndices, ScheduleGraph scheduleGraph) {
		
		List<Fixture> fixtures = new ArrayList<Fixture>();
		
		for (List<Integer> pair: pairsIndices) {
			// System.out.println("Indices: " + pair.get(0) + " - " + pair.get(1));
			Fixture fixture = new Fixture(scheduleGraph.representationList.get(pair.get(0)), scheduleGraph.representationList.get(pair.get(1)));
			// System.out.println("Adding fixture: " + fixture);
			fixtures.add(fixture);
		}
		
		return new RoundRobinRound(fixtures);
	}
	
	public List<List<Integer>> generatePairsIndices(int cardinality) {
		
		List<List<Integer>> pairsIndices = new ArrayList<List<Integer>>();
		
		for (int i = 0; i < cardinality / 2; i++) {
			
			List<Integer> pair = new ArrayList<Integer>();
			
			pair.add(i);
			pair.add(cardinality - i - 1);
			
			pairsIndices.add(pair);
		}
		
		return pairsIndices;
	}
	
	private List<RoundRobinRound> balanceMatchHosts(List<RoundRobinRound> rounds, List<Rankable> participants) {
		
		List<RoundRobinRound> retVal = new ArrayList<RoundRobinRound>();
		Map<Rankable, Integer> homeMatches = new HashMap<Rankable, Integer>();
		
		for (Rankable participant: this.participants) {
			homeMatches.put(participant, 0);
		}
		
		for (RoundRobinRound round: rounds) {
			
			List<Fixture> fixtures = new ArrayList<Fixture>();
			
			for (Fixture fixture:round.getFixtures()) {
				
				if (fixture.getHomeTeam().getName() == null || fixture.getAwayTeam().getName() == null) {
					continue;
				}
				
				if (homeMatches.get(fixture.getHomeTeam()) > homeMatches.get(fixture.getAwayTeam())) {
					fixtures.add(fixture.getReverse());
					homeMatches.put(fixture.getAwayTeam(), homeMatches.get(fixture.getAwayTeam()) + 1);
				} else {
					fixtures.add(fixture);
					homeMatches.put(fixture.getHomeTeam(), homeMatches.get(fixture.getHomeTeam()) + 1);
				}
			}
			
			retVal.add(new RoundRobinRound(fixtures));
		}
		
		return retVal;	
	}
	
	public void generateSchedule() {
		
		List<RoundRobinRound> rounds = new ArrayList<RoundRobinRound>();
		
		int numRounds = 0;
		
		if (this.participants.size() % 2 == 0) {
			numRounds = this.participants.size() - 1;
		} else {
			numRounds = this.participants.size();
		}
		
		// System.out.println("Number of rounds: " + numRounds);
		
		// Initialize
		ScheduleGraph scheduleGraph = new ScheduleGraph(this.participants);
		
		List<List<Integer>> pairsIndices = generatePairsIndices(scheduleGraph.representationList.size());
		
		for (int i = 0; i < numRounds; i++) {
			
			RoundRobinRound currentRound = generateRound(pairsIndices, scheduleGraph);
			
			if (i % 2 > 0 && numRounds > 3) {
				rounds.add(currentRound.reverse());
			} else {
				rounds.add(currentRound);
			}
			
			scheduleGraph.rotate();
		}
		
		if (numRounds <= 3) {
			rounds = balanceMatchHosts(rounds, this.participants);
		}
		
		// Get the other half of reverse rounds
		List<RoundRobinRound> clausura = new ArrayList<RoundRobinRound>();
		
		for (RoundRobinRound round:rounds) {
			clausura.add(round.reverse());
		}
		
		rounds.addAll(clausura);
		
		this.schedule = new RoundRobinSchedule(rounds);
	}
	
	public RoundRobinSchedule getSchedule() {
		return this.schedule;
	}
}
