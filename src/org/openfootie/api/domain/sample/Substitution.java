package org.openfootie.api.domain.sample;

import org.lily.parser.ListItem;

public class Substitution {
	
	private String player;
	private double duration;
	private Position position;
	
	public Substitution(String player, double duration, Position position) {
		this.player = player;
		this.duration = duration;
		this.position = position;
	}

	public static Substitution createSubstitution(ListItem li) {
		
		String player = li.getListContent().get(0).getLiteral();
		double duration = Double.parseDouble(li.getListContent().get(1).getLiteral());
		Position position = Position.fromString(li.getListContent().get(2).getLiteral());
		
		return new Substitution(player, duration, position);
	}
}
