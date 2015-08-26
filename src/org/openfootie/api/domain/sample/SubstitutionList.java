package org.openfootie.api.domain.sample;

import java.util.ArrayList;
import java.util.List;

import org.lily.parser.ListItem;

public class SubstitutionList {
	
	private List<Substitution> subs;
	
	public SubstitutionList(List<Substitution> subs) {
		this.subs = subs;
	}
	
	public static SubstitutionList createSubsList(List<ListItem> li) {
		
		List<Substitution> subs = new ArrayList<Substitution>();
		
		for (ListItem substitution: li) {
			subs.add(Substitution.createSubstitution(substitution));
		}
		
		return new SubstitutionList(subs);
	}
}
