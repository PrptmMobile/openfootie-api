package org.openfootie.api.sample.match.dao;

import java.util.Set;

import org.openfootie.api.sample.match.Match;

public interface MatchDao {
	
	public Set<Match> getAll();
	
}
