package org.openfootie.api.domain.sample;

import java.util.List;

import org.lily.parser.ListItem;

public class Lineup {
	
	private static final int GK_OFFSET = 0;
	private static final int DEF_OFFSET = 1;
	private static final int MID_OFFSET = 2;
	private static final int FOR_OFFSET = 3;
	private static final int SUB_OFFSET = 4;
	
	private Object goalkeeper;
	private TacticsLine defenders;
	private TacticsLine midfielders;
	private TacticsLine forwards;
	private SubstitutionList subs;
	
	public Lineup(Object goalkeeper, TacticsLine defenders, TacticsLine midfielders, TacticsLine forwards, SubstitutionList subs) {
		this.goalkeeper = goalkeeper;
		this.defenders = defenders;
		this.midfielders = midfielders;
		this.forwards = forwards;
		this.subs = subs;
	}
	
	public static Lineup createLineup(List<ListItem> li) {
	
		Object goalkeeper;
		TacticsLine defenders;
		TacticsLine midfielders;
		TacticsLine forwards;
		SubstitutionList subs;
		
		if (li.get(GK_OFFSET).isNestedList()) {
			goalkeeper = PlayerParticipation.createPlayerParticipation(li.get(GK_OFFSET));
		} else {
			goalkeeper = li.get(GK_OFFSET).getLiteral();
		}
		
		defenders = TacticsLine.createTacticsLine(li.get(DEF_OFFSET).getListContent());
		midfielders = TacticsLine.createTacticsLine(li.get(MID_OFFSET).getListContent());
		forwards = TacticsLine.createTacticsLine(li.get(FOR_OFFSET).getListContent());
		
		// Next: factory method for subs list
		
		try {
			subs = SubstitutionList.createSubsList(li.get(SUB_OFFSET).getListContent());
		} catch (IndexOutOfBoundsException ex) {
			subs = null; // No substitutions made
		}
		
		return new Lineup(goalkeeper, defenders, midfielders, forwards, subs);
	}

	public Object getGoalkeeper() {
		return goalkeeper;
	}

	public TacticsLine getDefenders() {
		return defenders;
	}

	public TacticsLine getMidfielders() {
		return midfielders;
	}

	public TacticsLine getForwards() {
		return forwards;
	}

	public SubstitutionList getSubs() {
		return subs;
	}	
}
