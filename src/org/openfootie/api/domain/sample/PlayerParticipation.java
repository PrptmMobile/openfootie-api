package org.openfootie.api.domain.sample;

import java.util.List;

import org.lily.parser.ListItem;

public class PlayerParticipation {
	
	private String player;
	private double duration;
	
	public PlayerParticipation(String player, double duration) {
		this.player = player;
		this.duration = duration;
	}
	
	public static PlayerParticipation createPlayerParticipation(ListItem li) {
		return new PlayerParticipation(li.getListContent().get(0).getLiteral(), Double.parseDouble(li.getListContent().get(1).getLiteral()));
	}

	public String getPlayer() {
		return player;
	}

	public double getDuration() {
		return duration;
	}
}
