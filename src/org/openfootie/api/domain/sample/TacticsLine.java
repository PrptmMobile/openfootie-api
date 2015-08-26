package org.openfootie.api.domain.sample;

import java.util.ArrayList;
import java.util.List;

import org.lily.parser.ListItem;

public class TacticsLine {
	
	private List<Object> players;
	
	public TacticsLine(List<Object> players) {
		this.players = players;
	}
	
	public static TacticsLine createTacticsLine(List<ListItem> li) {
		
		List<Object> players = new ArrayList<>();
		
		for (ListItem playerLI: li) {
			if (playerLI.isNestedList()) {
				players.add(PlayerParticipation.createPlayerParticipation(playerLI));
			} else {
				players.add(playerLI.getLiteral());
			}
		}
		
		return new TacticsLine(players);
	}
}
